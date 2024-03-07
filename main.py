import tensorflow as tf
import numpy as np
import DataProcessing
from sklearn.model_selection import train_test_split
from tensorflow.python.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.layers import GlobalAveragePooling2D
from tensorflow.keras.layers import Dense

#Set random seed for tensorflow and numpy
tf.random.set_seed(0)
np.random.seed(0)
batch_size = 8
epochs = 50

#Load train data from folder and labels from mat file
train_files = DataProcessing.get_list_paths('Cars_Data/cars_train/')
train_arr = DataProcessing.generate_image_data(train_files)
train_y = DataProcessing.get_cars_annos('Cars_Data/devkit/cars_train_annos.mat',4)

test_files = DataProcessing.get_list_paths('Cars_Data/cars_test/')
test_arr = DataProcessing.generate_image_data(test_files)

#Create one hot labels from train_y for tranning
num_classes = len(np.unique(train_y))
y_ohe = tf.keras.utils.to_categorical(train_y-1, num_classes=num_classes)

#Split data for tranning
x_train, x_valid, y_train_ohe, y_valid_ohe = train_test_split(train_arr, y_ohe, test_size=0.25)

#Create random transform image input
train_datagen = ImageDataGenerator(ImageDataGenerator(
                                    rotation_range=360,
                                   width_shift_range=2,
                                   height_shift_range=2,
                                   shear_range=1,
                                   horizontal_flip=True,
                                   brightness_range=(0, 1)
                                   ).get_random_transform(img_shape=(224, 224, 3)))
y_train_ohe = np.append(y_train_ohe,y_train_ohe,axis=0)
x_train = np.append(x_train,x_train,axis=0)
train_dataset = train_datagen.flow(x_train,y_train_ohe,batch_size=batch_size)

base_model = tf.keras.applications.ResNet152(
    include_top=False, weights='imagenet',
    input_shape=(224,224,3), classes=num_classes)
x = GlobalAveragePooling2D(name='avg_pool')(base_model.output)
predict = Dense(num_classes,activation='softmax')(x)

device = '/cpu:0' if len(tf.config.experimental.list_physical_devices('GPU')) == 0 else '/gpu:0'
tf.device(device)
with tf.device(device):
    # Create model
    model = tf.keras.models.Model(base_model.input, predict)
    # model.load_weights("my_modelh5")
    for layer in base_model.layers:
        layer.trainable = True
    # Create callback to save model had accuracy on the best validation set
    mcp = tf.keras.callbacks.ModelCheckpoint("my_model.h5", monitor="val_accuracy",
                      save_best_only=True, save_weights_only=True)

    # Compile model
    model.compile(optimizer=tf.keras.optimizers.SGD(learning_rate=0.1), loss='categorical_crossentropy',
                  metrics=['accuracy'])

    # Trainning
    model.fit(training_set, epochs=epochs, validation_data=(x_valid,y_valid_ohe), callbacks=[mcp],shuffle=True)
