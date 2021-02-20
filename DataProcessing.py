import numpy as np
from tqdm import tqdm
from PIL import Image
from scipy.io import loadmat
import os

def generate_image_data(image_paths, size=224):
    """
    Read and return numpy array list images
    :param image_paths: list of N paths image
    :param size: size for image like 224x224x3
    :return: numpy array size(N,size,size,3)
    """

    image_array = np.zeros((len(image_paths),size,size,3), dtype='uint8')
    for idx, image_path in tqdm(enumerate(image_paths)):
        image = Image.open(image_path).resize((size,size))
        if len(np.array(image).shape) != 3:
            image = image.convert(mode='RGB')
            # image2 = np.zeros((size,size,3))
            # image2[:,:,0] = image
            # image2[:,:,1] = image
            # image2[:,:,2] = image
            # image = image2
        image_array[idx] = np.array(image)
    return image_array

def get_list_paths(folder):
    '''
    Read and return all paths in folder
    :param folder: folder paths
    :return: all paths in folder
    '''
    list_file_paths = [os.path.join(folder, f) for f in os.listdir(folder)]
    return list_file_paths

def get_cars_annos(file,index, name='labels'):
    '''

    :param file: the path of mat file
    :param index: index column return
    :return: numpy array index column
    '''
    annots = loadmat(file)
    con_list= [ele[index] for ele in annots['annotations'][0]]
    y_train = np.array(con_list)
    y_train = y_train.reshape(y_train.shape[0])
    return y_train
