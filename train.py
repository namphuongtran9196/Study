from pickletools import optimize
import tensorflow as tf

from src.models.model import build_animeGAN
from src.losses_function.losses import AnimeGeneratorLoss,AnimeDiscriminatorLoss
from src.dataset.dataset import load_dataset

def main(args):
    dataset_raw = load_dataset(dataset_dir="", batch_size=64,target_size=(256,256))
    dataset = iter(dataset_raw)
    model = build_animeGAN(input_shape=(256,256,3))
    model.summary()
    
    gen_loss_func = AnimeGeneratorLoss(batch_size=64)
    dis_loss_func = AnimeDiscriminatorLoss(batch_size=64)
    
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
        
        return gen_loss, dis_loss
        
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
            gen_loss,dis_loss = train_step(inputs, labels)
            print("Epoch: {}/{}Steps: {}/??,, gen_loss: {}, dis_loss: {}".format(epoch,10,step,gen_loss,dis_loss))
        
    
if __name__ == '__main__':
    args = None
    main(args)