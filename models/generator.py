import tensorflow as tf
import tensorflow_addons as tfa
from tensorflow.keras import layers,Model
from models.layers import *

def CA_NET(text_dim=1024, condition_dim=128,name='Conditioning_augmentation_network'):
    
    inputs = layers.Input(text_dim)
    x = layers.Dense(condition_dim*2, use_bias=True)(inputs)
    x = layers.ReLU()(x)
    mu = x[:,:condition_dim]
    logvar = x[:,condition_dim:]
    std = tf.math.exp(logvar*0.5)
    class Variable_Multiply_layer(layers.Layer):
        def __init__(self,input_shape, **kwargs):
            super(Variable_Multiply_layer, self).__init__(**kwargs)
            self.build(input_shape)
        def build(self,input_shape):
            self.w = tf.Variable(tf.random.normal([input_shape[-1]]),trainable=True)
        def call(self,inputs):
            return inputs*self.w
    c_code = Variable_Multiply_layer([int(std.shape[-1])])(std)
    c_code = layers.Add()([c_code,mu])
    return Model(inputs=inputs,outputs=[c_code, mu, logvar],name=name)

def stage1_G(gan_feature_dim=128,text_dim=1024,condition_dim=128,noise_dim=100,name='Stage1_GAN'):
    gan_feature_dim *= 8
    
    inputs_text = layers.Input(text_dim)
    inputs_noise = layers.Input(noise_dim)
    
    # text -> gan condition
    c_code, mu, logvar =  CA_NET(text_dim=text_dim,condition_dim=condition_dim)(inputs_text)
    z_c_code = layers.Concatenate()([c_code,inputs_noise])
    
    # noise -> 4x4
    x = layers.Dense(gan_feature_dim * 4 * 4,use_bias=False)(z_c_code)
    x = layers.BatchNormalization()(x)
    x = layers.Reshape((4,4,-1))(x)
    
    # 4 x 4 ->  8 x 8
    x = upBlock(gan_feature_dim // 2,name='upBlock_1')(x)
    # 8 x 8 ->  16 x 16
    x = upBlock(gan_feature_dim // 4,name='upBlock_2')(x)
    # 16 x 16 ->  32 x 32
    x = upBlock(gan_feature_dim // 8,name='upBlock_3')(x)
    # 32 x 32 -> 64 x 64
    x = upBlock(gan_feature_dim // 16,name='upBlock_4')(x)
    # Convert to RGB image
    image = conv3x3(3)(x)
    image = layers.Activation('tanh')(image)
    
    return Model(inputs=[inputs_text,inputs_noise],outputs=[image,c_code,mu,logvar],name=name)
