import tensorflow as tf
import glob
import numpy as np
from tqdm import tqdm
from utils.utils import SentenceEmbedding


class BirdDataset(tf.keras.utils.Sequence):

    def __init__(self,
                 path: str = 'dataset/Bird_dataset_text2image/images_crop',
                 size: int = (64, 64),
                 batch_size: int = 32,
                 shuffle: bool = True):
        
        # Scan files
        self.img_files = dict()
        self.text_embeddings = dict()
        self.embedding_model = SentenceEmbedding()
        image_paths = glob.glob(path+'/*/*.jpg')
        num_example=0
        for image_path in tqdm(image_paths):
            text_path = image_path.replace('.jpg','.txt')
            with open(text_path,'r') as f:
                data = f.read()
            for text in data.split('\n'):
                if len(text)>0:
                    self.img_files[num_example] = image_path
                    self.text_embeddings[num_example] = self.embedding_model.encode(text)
                    num_example+=1
                    
        # Initialize
        self.indexes = None
        self.size = size
        self.shuffle = shuffle
        self.batch_size = batch_size
        self.on_epoch_end()

    def __len__(self):
        """
        Denotes the number of batches per epoch
        """
        return int(np.floor(len(self.text_embeddings) / self.batch_size))

    def on_epoch_end(self):
        """
        Updates indexes after each epoch
        :return:
        """
        self.indexes = np.arange(len(self.text_embeddings))
        if self.shuffle:
            np.random.shuffle(self.indexes)

    def __getitem__(self, index):
        """
        Generate one batch of data
        """
        # Generate indexes of the batch
        indexes = self.indexes[index * self.batch_size:(index+1) * self.batch_size]
        # Find list of IDs
        ids = [k for k in indexes]
        # Generate data
        x, y = self.__data_generation(ids)
        return x, y

    def __data_generation(self, ids):
        """
        Generates data containing batch_size samples.
        """
        images, labels = [], []
        # Begin load data
        for i in ids:
            # Load image
            image = tf.io.read_file(self.img_files[i])
            # convert the compressed string to a 3D uint8 tensor
            image = tf.image.decode_jpeg(image, channels=3)
            image = tf.image.random_flip_left_right(image)
            image = tf.image.resize(image,self.size,
                           method='nearest', antialias=True)
            image = image/255
            image = tf.keras.layers.Normalization(mean=[0.5, 0.5, 0.5],
                                                variance=[tf.square(0.5), 
                                                            tf.square(0.5), 
                                                            tf.square(0.5)])(image)
            images.append(image)
            
            # Load label json file
            labels.append(self.text_embeddings[i])

        images, labels = np.asarray(images).astype(np.float32), np.asarray(labels).astype(np.float32)
        return images, labels