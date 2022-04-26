import os
from re import X
import sys
file_dir = os.path.dirname(__file__)
sys.path.append(file_dir)

import tensorflow as tf
from tensorflow.keras import layers

def downsample_block(filters, kernel_size, padding,name='downsample_block'):
    down_block = tf.keras.Sequential(name=name)
    down_block.add(layers.Conv2D(filters, kernel_size=kernel_size, strides=2, padding=padding))
    down_block.add(layers.Conv2D(filters, kernel_size=kernel_size, strides=1, padding=padding))
    down_block.add(layers.BatchNormalization())
    down_block.add(layers.ReLU(0.2))
    return down_block

def up_block(filters, kernel_size, padding, name='up_block'):
    up_block = tf.keras.Sequential(name=name)
    up_block.add(layers.Conv2DTranspose(filters, kernel_size=kernel_size, strides=2, padding=padding))
    up_block.add(layers.Conv2D(filters, kernel_size=kernel_size, strides=1, padding=padding))
    up_block.add(layers.BatchNormalization())
    up_block.add(layers.ReLU(0.2))
    return up_block

def residual_block(input_shape,filters, kernel_size, strides,padding,name='residual_block'):
    input_layers = layers.Input(shape=input_shape)
    x = layers.Conv2D(filters, kernel_size=kernel_size, strides=strides, padding=padding)(input_layers)
    x = layers.BatchNormalization()(x)
    x = layers.ReLU()(x)
    x = layers.Conv2D(filters, kernel_size=kernel_size, strides=strides, padding=padding)(x)
    x = layers.BatchNormalization()(x)
    output = layers.Add()([input_layers, x])
    return tf.keras.Model(inputs=input_layers, outputs=output,name=name)

def build_generator(input_shape=(None,None,3)):
    
    input_layer = layers.Input(shape=input_shape)
    
    # First Convolution
    conv_1 = layers.Conv2D(64, kernel_size=3, strides=(1, 1), padding='same', activation='relu')(input_layer)
    norm_1 = layers.BatchNormalization()(conv_1)
    lrelu_1 = layers.LeakyReLU(0.2)(norm_1)
    
    # Downsampling block
    x = lrelu_1
    for i in range(1,3):
        x = downsample_block(128*i, kernel_size=3, padding='same',name='downsample_block_' + str(i))(x)
    
    # Residual block
    for i in range(8):
        x = residual_block(input_shape=x.shape[1:], filters=256, kernel_size=3, strides=1, 
                           padding='same', name='residual_block_'+str(i))(x)
        
    # Upsampling block
    for i in range(1,3):
        x = up_block(filters=128//i, kernel_size=3, padding='same', name='up_block_'+str(i))(x)
        
    # Last Convolution
    x = layers.Conv2D(3, kernel_size=7, strides=(1, 1), padding='same', activation='tanh')(x)
    
    # Activation
    x = layers.Activation('sigmoid')(x)
    
    return tf.keras.Model(inputs=input_layer, outputs=x, name='generator')

if __name__ == '__main__':
    generator = build_generator(input_shape = (256,256,3))
    generator.summary()