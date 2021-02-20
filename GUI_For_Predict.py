import PySimpleGUI as sg
import sys
import numpy as np
import tensorflow as tf
from tensorflow.keras.layers import GlobalAveragePooling2D
from tensorflow.keras.layers import Dropout
from tensorflow.keras.layers import Dense
from PIL import Image
from os.path import dirname



#Train model
base_model = tf.keras.applications.ResNet152(
    include_top=False, weights='imagenet',
    input_shape=(224,224,3), classes=196)
x = GlobalAveragePooling2D(name='avg_pool')(base_model.output)
x = Dense(4096,activation='relu')(x)
x = Dropout(0.5)(x)
x = Dense(4096,activation='relu')(x)
x = Dropout(0.5)(x)
x = Dense(196,activation='softmax')(x)

device = '/cpu:0' if len(tf.config.experimental.list_physical_devices('GPU')) == 0 else '/gpu:0'
batch_size = 32
epochs = 100
tf.device(device)
with tf.device(device):
    # Create model
    model = tf.keras.models.Model(base_model.input, x)
    model.load_weights("my_model.h5")

#GUI

sg.theme('Dark Blue 3')  # please make your windows colorful

layout = [
            [sg.Text('SHA-1 and SHA-256 Hashes for the file')],
            [sg.InputText(), sg.FileBrowse()],
            [sg.Button('Predict'), sg.Cancel()],
            [sg.Image(filename=None,size=(224,224), key='_IMAGE_')],
            [sg.Text(text="PREDICT----------------------------------------------------------------------------------------------------------------\n"
                          "PREDICT----------------------------------------------------------------------------------------------------------------\n"
                          "PREDICT----------------------------------------------------------------------------------------------------------------\n"
                          "PREDICT----------------------------------------------------------------------------------------------------------------\n"
                          "PREDICT----------------------------------------------------------------------------------------------------------------\n"
                          "PREDICT----------------------------------------------------------------------------------------------------------------\n"
                          "PREDICT----------------------------------------------------------------------------------------------------------------\n"
                          "PREDICT----------------------------------------------------------------------------------------------------------------\n"
                          "PREDICT----------------------------------------------------------------------------------------------------------------\n"
                          "PREDICT----------------------------------------------------------------------------------------------------------------\n"
                          "PREDICT----------------------------------------------------------------------------------------------------------------\n"
                          "PREDICT----------------------------------------------------------------------------------------------------------------\n",
                     key='_PREDICT_',
                     auto_size_text=True,
                     click_submits=True)]
         ]
window = sg.Window('Car_Prediction', layout)
path = dirname(sys.argv[0]) +'\\image_predict.PNG'
list_name_cars = np.load('list_name_cars.npy')
while True:
    event, values = window.read()
    image_path = values[0]
    if event == 'Cancel' or event == sg.WIN_CLOSED:
        break
    try:
        image = Image.open(image_path)
        image = image.convert(mode='RGB')
        image = image.resize((224,224))
        image.save(path)
        image_array = np.zeros((1, 224, 224, 3), dtype='uint8')
        image_array[0] = np.array(image)
        window.Element('_IMAGE_').Update(filename=path,size=(224,224))
        predict_car = model.predict(image_array)
        predict = np.sum(predict_car,axis=0)
        idx = [index for index in range(196) if predict[index] >0.0001]
        idx = sorted(idx, key=lambda x: predict[x], reverse=True)
        text = 'My predict:\n'
        for i in idx:
            text += 'Car: ' + str(list_name_cars[i]) + '\nWith confidence:' + str(round(predict[i] * 100, 2)) + '\n'
        window.Element('_PREDICT_').Update(value=text)
    except:
        window.Element('_IMAGE_').Update(filename=None,size=(224,224))




