import tensorflow as tf

from tensorflow.keras import layers, Model
from src.models.discriminator import build_discriminator
from src.models.generator import build_generator
# from discriminator import build_discriminator
# from generator import build_generator

def build_animeGAN(input_shape=(None,None,3)):
    """
    Builds the AnimeGAN model.
    """
    
    real_photos = layers.Input(shape=input_shape)
    cartoon_photos = layers.Input(shape=input_shape)
    smooth_cartoon_photos = layers.Input(shape=input_shape)
    
    generator = build_generator(input_shape=input_shape)
    discriminator = build_discriminator(input_shape=input_shape)
    
    # Generator
    cartoon_gen = generator(real_photos)
    
    # Discriminator
    cartoon_predict = discriminator(cartoon_photos)
    cartoon_gen_predict = discriminator(cartoon_gen)
    smooth_cartoon_photos_predict = discriminator(smooth_cartoon_photos)
    
    return Model(inputs={"real_photos":real_photos, 
                         "cartoon_photos":cartoon_photos, 
                         "smooth_cartoon_photos":smooth_cartoon_photos}, 
                 outputs={"cartoon_gen" : cartoon_gen,
                          "cartoon_predict":cartoon_predict, 
                          "cartoon_gen_predict":cartoon_gen_predict, 
                          "smooth_cartoon_predict":smooth_cartoon_photos_predict},
                 name = "AnimeGAN")
    
if __name__ == '__main__':
    model = build_animeGAN(input_shape=(224,224,3))
    print(len(model.output))
    model.summary()