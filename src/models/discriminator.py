import os
import sys
file_dir = os.path.dirname(__file__)
sys.path.append(file_dir)

import tensorflow as tf
from tensorflow.keras import layers

def get_dicriminator(input_shape=(None,None,3)):
    model = tf.keras.Sequential(name='discriminator')
    model.add(layers.Conv2D(32, kernel_size=3, strides=(2, 2), padding='same', input_shape=input_shape))
    model.add(layers.LeakyReLU(0.2))
    
    model.add(layers.Conv2D(64, kernel_size=3, strides=(2, 2), padding='same'))
    model.add(layers.LeakyReLU(0.2))
    model.add(layers.Conv2D(128, kernel_size=3, strides=(1, 1), padding='same'))
    model.add(layers.BatchNormalization())
    model.add(layers.LeakyReLU(0.2))
    
    model.add(layers.Conv2D(128, kernel_size=3, strides=(2, 2), padding='same'))
    model.add(layers.LeakyReLU(0.2))
    model.add(layers.Conv2D(256, kernel_size=3, strides=(1, 1), padding='same'))
    model.add(layers.BatchNormalization())
    model.add(layers.LeakyReLU(0.2))
    
    model.add(layers.Conv2D(256, kernel_size=3, strides=(1, 1), padding='same'))
    model.add(layers.BatchNormalization())
    model.add(layers.LeakyReLU(0.2))
    
    model.add(layers.Conv2D(1, kernel_size=3, strides=(1, 1), padding='same'))
    
    return model

if __name__ == '__main__':
    model = get_dicriminator(input_shape=(224,224,3))
    model.summary()