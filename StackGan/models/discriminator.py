import tensorflow as tf
import tensorflow_addons as tfa
from tensorflow.keras import layers,Model
from models.layers import *

def get_d_logits(dis_feature,condition,dis_feature_dim=64,condition_dim=128,conditions=True):
    c_code = condition
    h_code = dis_feature
    if conditions:
        c_code = layers.Reshape((1,1,condition_dim))(c_code)
        c_code = tf.repeat(c_code,4,axis=-3)
        c_code = tf.repeat(c_code,4,axis=-2)
        h_c_code = tf.concat([h_code,c_code],axis=-1)
        x = conv3x3(dis_feature_dim*8)(h_c_code)
        x = layers.BatchNormalization()(x)
        x = layers.LeakyReLU(0.2)(x)
        x = layers.Conv2D(1,kernel_size=4,strides=4)(x)
    else:
        x = layers.Conv2D(1,kernel_size=4,strides=4)(h_code)
    x = layers.Reshape([1])(x)
    return x
        

def stage1_D(dis_feature_dim=64,condition_dim=128,input_shape=(64,64,3),name='Stage_1D'):
    """
        Discriminator model
    """

    inputs = layers.Input(input_shape)
    c_inputs = layers.Input(condition_dim)
    x = layers.ZeroPadding2D(1)(inputs)
    x = layers.Conv2D(dis_feature_dim,kernel_size=4,strides=2,use_bias=False)(x)
    x = layers.LeakyReLU(0.2)(x)
    # 32x32
    x = layers.ZeroPadding2D(1)(x)
    x = layers.Conv2D(dis_feature_dim*2,kernel_size=4,strides=2,use_bias=False)(x)
    x = layers.BatchNormalization()(x)
    x = layers.LeakyReLU(0.2)(x)
    # 16x16
    x = layers.ZeroPadding2D(1)(x)
    x = layers.Conv2D(dis_feature_dim*4,kernel_size=4,strides=2,use_bias=False)(x)
    x = layers.BatchNormalization()(x)
    x = layers.LeakyReLU(0.2)(x)
    # 8x8
    x = layers.ZeroPadding2D(1)(x)
    x = layers.Conv2D(dis_feature_dim*8,kernel_size=4,strides=2,use_bias=False)(x)
    x = layers.BatchNormalization()(x)
    feature = layers.LeakyReLU(0.2)(x)
    # get logits
    logits = get_d_logits(feature,c_inputs,dis_feature_dim=64,condition_dim=128,conditions=True)
    
    return Model([inputs,c_inputs],[logits,feature],name=name)