import os
import glob
import skimage.io
import numpy as np
import tensorflow as tf

def load_dataset(dataset_dir,batch_size=16,target_size=(256,256),shuffle=True):
    """load coco dataset"""
    print("load dataset from {}".format(dataset_dir))
    dataset = Dataset(dataset_dir=dataset_dir,
                      batch_size=batch_size,
                      target_size = target_size,
                      shuffle=shuffle)
    return dataset

class Dataset(tf.keras.utils.Sequence):
    """Dataset for loading images and annotations from the coco dataset"""
    def __init__(self, 
                 dataset_dir,
                 batch_size=64,
                 target_size=(256,256),
                 shuffle=True):
        self.indexes = np.arange(len(self.imgs_real))
        self.batch_size = batch_size
        self.shuffle = shuffle
        self.target_size = target_size
        self.build(dataset_dir)
        self.length_cartoon = len(self.imgs_cartoon) // batch_size
        
    def __len__(self):
        return int(np.floor(len(self.imgs_real) / self.batch_size))
    
    def __getitem__(self, idx):
        batch_real = self.indexes[idx * self.batch_size:(idx + 1) *self.batch_size]
        batch_cartoon = batch_real % self.length_cartoon
        return self.make_samples(batch_real,batch_cartoon)
    
    def on_epoch_end(self):
        """
        Updates indexes after each epoch
        :return:
        """
        if self.shuffle:
            np.random.shuffle(self.indexes)

    def build(self,dataset_dir):
        """
        Build the dataset from directory
        """
        self.imgs_real = np.asarray(glob.glob(os.path.join(dataset_dir, 'imgs_real', '*')))
        self.imgs_cartoon =  glob.glob(os.path.join(dataset_dir, 'imgs_cartoon', '*'))
        self.imgs_cartoon_smooth =  glob.glob(os.path.join(dataset_dir, 'imgs_cartoon_smooth', '*'))
    
        self.imgs_cartoon.sort()
        self.imgs_cartoon_smooth.sort()
        
        self.imgs_cartoon = np.asarray(self.imgs_cartoon)
        self.imgs_cartoon_smooth = np.asarray(self.imgs_cartoon_smooth)
    
    def make_samples(self, batch_real, batch_cartoon):
        """
        Make a sample for training
        :param idx: index of batch
        :return: a batch training dataset
        """
        imgs_real = self.imgs_real[batch_real]
        imgs_cartoon = self.imgs_cartoon[batch_cartoon]
        imgs_cartoon_smooth = self.imgs_cartoon_smooth[batch_cartoon]
        batch_imgs_real= []
        batch_imgs_cartoon = []
        batch_cartoon_smooth = []
        for real, cartoon, cartoon_smooth in list(zip(imgs_real, imgs_cartoon, imgs_cartoon_smooth)):
            # load the image from path
            img_real = self.load_image(real)
            img_cartoon = self.load_image(cartoon)
            img_cartoon_smooth = self.load_image(cartoon_smooth)
            
            # resize the image
            img_real = tf.image.resize(img_real, self.target_size)
            img_cartoon = tf.image.resize(img_cartoon, self.target_size)
            img_cartoon_smooth = tf.image.resize(img_cartoon_smooth, self.target_size)
            
            # normalize the image
            img_real = img_real / 255.0
            img_cartoon = img_cartoon / 255.0
            img_cartoon_smooth = img_cartoon_smooth / 255.0
            # add image to batch
            batch_imgs_real.append(img_real)
            batch_imgs_cartoon.append(img_cartoon)
            batch_cartoon_smooth.append(img_cartoon_smooth)
        # Convert to tensor array
        batch_imgs_real = tf.convert_to_tensor(batch_imgs_real,dtype=tf.float32)
        batch_imgs_cartoon = tf.convert_to_tensor(batch_imgs_real,dtype=tf.float32)
        batch_cartoon_smooth = tf.convert_to_tensor(batch_imgs_real,dtype=tf.float32)
        # encode the labels to features pyramids
        
        return batch_imgs_real, batch_imgs_cartoon, batch_cartoon_smooth 
    
    def load_image(self, path):
        """Load the specified image and return a [H,W,3] Numpy array."""
        # Load image
        image = skimage.io.imread(path)
        # If grayscale. Convert to RGB for consistency.
        if len(image.shape) < 3:
            image = skimage.color.gray2rgb(image)
        # If has an alpha channel, remove it for consistency
        if image.shape[-1] == 4:
            image = image[..., :3]
        return image