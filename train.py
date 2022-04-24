from pickletools import optimize
import tensorflow as tf

from src.models.model import build_animeGAN
from src.losses_function.losses import AnimeGeneratorLoss,AnimeDiscriminatorLoss
from src.dataset.dataset import load_dataset

def main(args):
    dataset = load_dataset(dataset_dir="", batch_size=64,target_size=(256,256))
    model = build_animeGAN(input_shape=(256,256,3))
    model.summary()
    
    model.compiled(optimizers=tf.keras.optimizers.Adam(lr=0.0002, beta_1=0.5),
                   loss=[AnimeGeneratorLoss(),AnimeDiscriminatorLoss()])
    
    model.fit(dataset, epochs=100, steps_per_epoch=10)
    
if __name__ == '__main__':
    args = None
    main(args)