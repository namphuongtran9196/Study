
import tensorflow as tf

class ContentLoss(tf.losses.Loss):
    """"""
    def __init__(self, **kwargs):
        super(ContentLoss, self).__init__(**kwargs)
        vgg16 = tf.keras.applications.vgg16.VGG16(input_shape=(224,224,3), include_top=False)
        self.features_extractor = tf.keras.Model(vgg16.input,vgg16.get_layer('block4_pool').output)
        self.mae = tf.keras.losses.MeanAbsoluteError()
    def call(self, y_true, y_pred):
        return self.mae(self.features_extractor(y_true), self.features_extractor(y_pred))
    
class AdversarialLoss(tf.losses.Loss):
    """"""
    def __init__(self, batch_size,from_logits=False,**kwargs):
        super(AdversarialLoss, self).__init__(**kwargs)
        self.real_labels = tf.ones((batch_size,1))
        self.fake_labels = tf.zeros((batch_size,1))
        self.bce = tf.keras.losses.BinaryCrossentropy(from_logits=from_logits)
    def call(self,y_true,y_pred, generator=False):
        if generator:
            fake_loss = self.bce(self.real_labels,y_pred)
            return fake_loss
        else:
            real_loss = self.bce(self.real_labels,y_true[0])
            real_loss_smooth = self.bce(self.fake_labels,y_true[1])
            fake_loss = self.bce(self.fake_labels,y_pred)
            return (real_loss + real_loss_smooth + fake_loss) / 3
    
class AnimeGeneratorLoss(tf.losses.Loss):
    """"""
    def __init__(self, w=10,batch_size=16,from_logits=False,**kwargs):
        super(ContentLoss, self).__init__(**kwargs)
        # Controling weight of content loss
        self.w = w
        self.batch_size = batch_size
        
        self.adv_loss = AdversarialLoss(batch_size=batch_size,from_logits=from_logits)
        self.content_loss = ContentLoss()
        
    def call(self, y_true, y_pred):
        cartoon_predict = y_pred["cartoon_predict"]
        cartoon = y_true[0]
        smooth_cartoon = y_true[1]
        # Adversarial loss
        loss_adv = self.adv_loss((cartoon, smooth_cartoon), cartoon_predict, generator=True)
        # Content loss
        loss_con = self.content_loss(cartoon, cartoon_predict)
        # Totoal loss
        total_loss = loss_adv + self.w * loss_con
        return total_loss
    
class AnimeDiscriminatorLoss(tf.losses.Loss):
    """"""
    def __init__(self, w=10,batch_size=16,from_logits=False,**kwargs):
        super(ContentLoss, self).__init__(**kwargs)
        self.w = w
        self.batch_size = batch_size
        
        self.adv_loss = AdversarialLoss(batch_size=batch_size,from_logits=from_logits)
        
    def call(self, y_true, y_pred):
        cartoon_predict = y_pred["cartoon_predict"]
        cartoon_gen_predict = y_pred["cartoon_gen_predict"]
        smooth_cartoon_photos_predict =y_pred["smooth_cartoon_photos_predict"]
        return self.adv_loss((cartoon_predict,smooth_cartoon_photos_predict),cartoon_gen_predict)