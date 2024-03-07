import os

from tensorflow.python.keras.engine import training
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3' 
import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt
from tensorflow.keras import optimizers,losses,layers
from models.generator import stage1_G
from models.discriminator import stage1_D
from utils.utils import ProgressBar
from utils.dataset import BirdDataset

EPOCHS=600           # Number of training epochs
BATCH_SIZE=64        # Number of batch size for training
IMAGE_SIZE=64      # H,W,_ for images
DATASET_PATH = '../dataset/BirdDataset' # Path for using custom dataset

GAN_FEATURE_DIM=128
TEXT_DIM=384
CONDITION_DIM=128
NOIZE_DIM=100
DIS_FEATURE_DIM=64
STAGE=1
STAGE_I_CHECKPOINTS = ''
# define model
image_shape = (IMAGE_SIZE,IMAGE_SIZE,3)

# Generators     
G_net_stage = stage1_G(gan_feature_dim=GAN_FEATURE_DIM,
                        text_dim=TEXT_DIM,
                        condition_dim=CONDITION_DIM,
                        noise_dim=NOIZE_DIM)
# Discriminators
D_net_stage = stage1_D(dis_feature_dim=DIS_FEATURE_DIM,
                   input_shape=G_net_stage.output[0].shape[1:])


# define optimizers
G_optimizer = optimizers.Adam(learning_rate=2e-4,beta_1=0.5,beta_2=0.999)
D_optimizer = optimizers.Adam(learning_rate=2e-4,beta_1=0.5,beta_2=0.999)

# define loss
adversarial_loss = losses.BinaryCrossentropy(from_logits=True)
def KL_loss(mu, log_sigma):
    loss = -log_sigma + .5 * (-1 + tf.exp(2. * log_sigma) + tf.square(mu))
    loss = tf.reduce_mean(loss)
    return loss

# Adversarial ground truths
real_labels = tf.ones((BATCH_SIZE,1))
fake_labels = tf.zeros((BATCH_SIZE,1))

# # Define noise
# fixed_noise = tf.random.normal([BATCH_SIZE,NOIZE_DIM],0.0,1.0)
# fixed_noise_gaussian = layers.GaussianNoise(1.0)(fixed_noise)

# load custom dataset
print('Loading dataset... It may take a few minutes...',end='')
train_dataset = BirdDataset(DATASET_PATH,size=(IMAGE_SIZE,IMAGE_SIZE),batch_size=BATCH_SIZE)
print(' DONE!')


# Training
@tf.function
def train_G_step(G_net,D_net,
                 text_embedding,noise,real_imgs,
                 real_labels,fake_labels,
                 adversarial_loss,
                 stage=1):
    
    D_net.trainable = False
    if stage==1:
        with tf.GradientTape() as tape:
            fake_imgs,c_code,mu,log_sigma = G_net([text_embedding,noise],training=True)
            fake_logits,_ = D_net([fake_imgs,c_code])
            loss_G = adversarial_loss(real_labels,fake_logits)
            loss_G = loss_G + KL_loss(mu, log_sigma)
    else:
        pass
    # Calculate the gradients for generator
    G_gradients = tape.gradient(loss_G,G_net.trainable_variables)
    # Apply the gradients to the optimizer
    G_optimizer.apply_gradients(zip(G_gradients,G_net.trainable_variables))
    
    return loss_G

@tf.function
def train_D_step(G_net,D_net,
                 text_embedding,noise,real_imgs,
                 real_labels,fake_labels,
                 adversarial_loss,
                 stage=1):
    
    D_net.trainable = True
    
    # Measure discriminator's ability to classify real from generated samples
    fake_imgs,c_code,_,_ = G_net([text_embedding,noise])
    
    if stage==1:
        with tf.GradientTape() as tape:
            # Real loss:
            real_logits,_ = D_net([real_imgs,c_code],training=True)
            real_loss = adversarial_loss(real_labels,real_logits)
            
            # Wrong loss:
            wrong_pair = [real_imgs[:BATCH_SIZE-1],c_code[1:]]
            wrong_logits,_ = D_net(wrong_pair,training=True)
            wrong_loss = adversarial_loss(fake_labels[1:],wrong_logits)
            
            # Fake loss:
            fake_logits,_ = D_net([fake_imgs,c_code],training=True)
            fake_loss = adversarial_loss(fake_labels,fake_logits)
            
            loss_D = real_loss + (fake_loss+wrong_loss)*0.5
    else:
        pass
    
    # Calculate the gradients for discriminator
    D_gradients = tape.gradient(loss_D,D_net.trainable_variables)
    # Apply the gradients to the optimizer
    D_optimizer.apply_gradients(zip(D_gradients,D_net.trainable_variables))
    
    return loss_D

# Create image folder for saving image after each epoch
os.makedirs("images", exist_ok=True)  
# Visualize training     
prog_bar = ProgressBar(train_dataset.__len__(),0)
print('Start training...')
for epoch in range(EPOCHS):
    for step in range(train_dataset.__len__()):
        real_imgs,text_embedding =train_dataset.__getitem__(step)
        # Random noise
        noise = tf.random.normal([BATCH_SIZE,NOIZE_DIM],0,1)
        noise_gaussian = layers.GaussianNoise(1.0)(noise)
        noise_gaussian=noise
        # Training step
        G_loss = train_G_step(G_net_stage,D_net_stage,
                                text_embedding,noise_gaussian,real_imgs,
                                real_labels,fake_labels,
                                adversarial_loss,
                                stage=STAGE)
        D_loss = train_D_step(G_net_stage,D_net_stage,
                                text_embedding,noise_gaussian,real_imgs,
                                real_labels,fake_labels,
                                adversarial_loss,
                                stage=STAGE)
        # Update visualize loss
        prog_bar.update("epoch={}/{}, g_loss={}, d_loss={}".format(epoch+1, EPOCHS,G_loss,D_loss))
    
    fake_imgs,_,_,_ = G_net_stage([text_embedding,noise])
    # Save 9 images generate for each epoch
    fake_imgs = ((fake_imgs * 0.5) + 0.5 )*255
    fake_imgs = tf.cast(fake_imgs,tf.uint8)
    plt.figure(figsize=(50, 50))
    for i in range(len(fake_imgs) if len(fake_imgs)  < 9 else 9):
        plt.subplot(3, 3, i+1)
        plt.imshow(fake_imgs[i].numpy())
        plt.axis('off')
    plt.savefig("images/{}_fake_A.jpg".format(str(epoch).zfill(5)))
    plt.close()
    
    if epoch % 10 ==0:
        # Save model
        G_net_stage.save_weights('G_net_stage.h5')
        D_net_stage.save_weights('D_net_stage.h5')
    train_dataset.on_epoch_end()