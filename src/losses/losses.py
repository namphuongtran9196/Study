
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
    def call(self,y_true,y_pred):
        real_loss = self.bce(self.real_labels,y_true[0])
        real_loss_smooth = self.bce(self.real_labels,y_true[1])
        fake_loss = self.bce(self.fake_labels,y_pred)
        return (real_loss + real_loss_smooth + fake_loss) / 3
    
class AnimeGanLoss(tf.losses.Loss):
    pass
    
