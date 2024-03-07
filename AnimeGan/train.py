import os
import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt
from src.models.model import build_animeGAN
from src.losses_function.losses import AnimeGeneratorLoss,AnimeDiscriminatorLoss
from src.dataset.dataset import load_dataset

BATCH_SIZE = 16
def main(args):
    dataset_raw = load_dataset(dataset_dir="/content/AnimeGAN/data", batch_size=BATCH_SIZE,target_size=(256,256))
    dataset = iter(dataset_raw)
    model = build_animeGAN(input_shape=(256,256,3))
    model.save('model.h5')
    model.summary()
    model = tf.keras.models.load_model("/content/AnimeGAN/model.h5")
    
    gen_loss_func = AnimeGeneratorLoss(batch_size=BATCH_SIZE,from_logits=True)
    dis_loss_func = AnimeDiscriminatorLoss(batch_size=BATCH_SIZE,from_logits=True)
    
    optimizer = tf.keras.optimizers.Adam(learning_rate=0.0002, beta_1=0.5)
    
    @tf.function
    def train_step(inputs,labels):
        model.get_layer("discriminator").trainable = True
        with tf.GradientTape() as tape:
            outputs = model(inputs)
            dis_loss = dis_loss_func(labels,outputs)
        gradients = tape.gradient(dis_loss,model.trainable_variables)
        optimizer.apply_gradients(zip(gradients,model.trainable_variables))
            
        model.get_layer("discriminator").trainable = False
        with tf.GradientTape() as tape:
            outputs = model(inputs)
            gen_loss = gen_loss_func(labels,outputs)
        gradients = tape.gradient(gen_loss,model.trainable_variables)
        optimizer.apply_gradients(zip(gradients,model.trainable_variables))
        
        return gen_loss, dis_loss,outputs
    os.makedirs("/content/drive/Shareddrives/DSC_GAN/AnimeGAN/Checkpoints/images", exist_ok=True)
    for epoch in range(10):
        step = 0
        while(True):
            step+=1
            data = next(dataset,None)
            if data is None:
                dataset_raw.on_epoch_end()
                dataset=iter(dataset_raw)
                break
            inputs, labels = data
            gen_loss,dis_loss,outputs = train_step(inputs, labels)
            if step % 5 == 0:
              print("Epoch: {}/{}Steps: {}/??,, gen_loss: {}, dis_loss: {}".format(epoch,10,step,gen_loss,dis_loss))
            if step % 100 == 0:
              cartoon_gen = outputs["cartoon_gen"].numpy()
              cartoon_gen *= 255.0
              cartoon_gen = cartoon_gen.astype(np.uint8)
              plt.figure(figsize=(50, 50))
              for i in range(16):
                plt.subplot(4, 4, i+1)
                plt.imshow(cartoon_gen[i])
                plt.axis('off')
                plt.savefig("/content/drive/Shareddrives/DSC_GAN/AnimeGAN/Checkpoints/images/{}_{}.jpg".format(str(epoch).zfill(4),str(step).zfill(3)))
                plt.close()
        model.save('/content/drive/Shareddrives/DSC_GAN/AnimeGAN/Checkpoints/model.h5')
        
    
if __name__ == '__main__':
    args = None
    main(args)