import tensorflow as tf
from tensorflow.keras import layers,Sequential,Model

def conv3x3(filters, strides=1,name='conv3x3'):
    "3x3 convolution with padding"
    conv3x3 = Sequential(layers=[layers.ZeroPadding2D(1),
                                 layers.Conv2D(filters, kernel_size=3, strides=strides,
                                               padding='valid', use_bias=False)],
                         name=name)
    return conv3x3

# Upsale the spatial size by a factor of 2
def upBlock(filters, name='UpBlock'):
    block = Sequential(layers=[
                            layers.UpSampling2D(2),
                            conv3x3(filters,name=name+'_conv3x3'),
                            layers.BatchNormalization(),
                            layers.ReLU()
                                ],
                       name=name)
    return block

def resBlockFunc(inputs,filters,name='ResBlock'):
    short_cut = inputs
    x = conv3x3(filters,name=name+'_conv3x3_1')(inputs)
    x = layers.BatchNormalization()(x)
    x = layers.ReLU()(x)
    x = conv3x3(filters,name=name+"_conv3x3_2")(x)
    x = layers.BatchNormalization()(x)
    x = layers.Add()([x,short_cut])
    output = layers.ReLU()(x)
    return output


