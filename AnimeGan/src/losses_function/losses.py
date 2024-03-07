
import tensorflow as tf

class ContentLoss(tf.losses.Loss):
    """"""
    def __init__(self,input_shape=(256,256,3), **kwargs):
        super(ContentLoss, self).__init__(**kwargs)
        vgg16 = tf.keras.applications.vgg16.VGG16(input_shape=input_shape, include_top=False)
        self.features_extractor = tf.keras.Model(vgg16.input,vgg16.get_layer('block4_pool').output)
        self.mae = tf.keras.losses.MeanAbsoluteError()
    def call(self, y_true, y_pred):
        return self.mae(self.features_extractor(y_true), self.features_extractor(y_pred))
    
class AdversarialLoss(tf.losses.Loss):
    """"""
    def __init__(self, batch_size,from_logits=False, generator=False,**kwargs):
        super(AdversarialLoss, self).__init__(**kwargs)
        self.real_labels = tf.ones((batch_size,32,32,1))
        self.fake_labels = tf.zeros((batch_size,32,32,1))
        self.bce = tf.keras.losses.BinaryCrossentropy(from_logits=from_logits)
        self.generator = generator
    def call(self,y_true,y_pred):
        if self.generator:
            fake_loss = self.bce(self.real_labels,y_pred)
            return fake_loss
        else:
            real_loss = self.bce(self.real_labels,y_true[0])
            real_loss_smooth = self.bce(self.fake_labels,y_true[1])
            fake_loss = self.bce(self.fake_labels,y_pred)
            return (real_loss + real_loss_smooth + fake_loss) / 3
    
class AnimeGeneratorLoss(tf.losses.Loss):
    """"""
    def __init__(self, w=10,batch_size=16,input_shape=(256,256,3),from_logits=False,**kwargs):
        super(AnimeGeneratorLoss, self).__init__(**kwargs)
        # Controling weight of content loss
        self.w = w
        self.batch_size = batch_size
        
        self.adv_loss = AdversarialLoss(batch_size=batch_size,from_logits=from_logits,generator=True)
        self.content_loss = ContentLoss(input_shape)
        
    def call(self, y_true, y_pred):
        cartoon = y_true[0]
        cartoon_gen= y_pred["cartoon_gen"]
        cartoon_predict = y_pred["cartoon_predict"]
        cartoon_gen_predict = y_pred["cartoon_gen_predict"]
        smooth_cartoon_predict =y_pred["smooth_cartoon_predict"]
        # Adversarial loss
        loss_adv = self.adv_loss((cartoon_predict,smooth_cartoon_predict),cartoon_gen_predict)
        # Content loss
        loss_con = self.content_loss(cartoon, cartoon_gen)
        # Totoal loss
        total_loss = loss_adv + self.w * loss_con
        return total_loss
    
class AnimeDiscriminatorLoss(tf.losses.Loss):
    """"""
    def __init__(self, w=10,batch_size=16,from_logits=False,**kwargs):
        super(AnimeDiscriminatorLoss, self).__init__(**kwargs)
        self.w = w
        self.batch_size = batch_size
        
        self.adv_loss = AdversarialLoss(batch_size=batch_size,from_logits=from_logits)
        
    def call(self, y_true, y_pred):
        cartoon_predict = y_pred["cartoon_predict"]
        cartoon_gen_predict = y_pred["cartoon_gen_predict"]
        smooth_cartoon_predict =y_pred["smooth_cartoon_predict"]
        return self.adv_loss((cartoon_predict,smooth_cartoon_predict),cartoon_gen_predict)