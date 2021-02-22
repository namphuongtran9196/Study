I am trying to rebuild the model classification cars based on model ResNet152 of Tensorflow library but instead of classifying 1000 classes, my model only classifying 196 classes.
Model: Resnet152
Data: https://ai.stanford.edu/~jkrause/cars/car_dataset.html
Input: Image(224,224,3)
Loss ≈ 2.01
Accuracy ≈ 65.57%
Classes: 196
Optimizer: Adam Optimization Algorithm
The model can classify 196 types of cars, but the current accuracy of the model is quite low, only about 65.57%%.
I am currently working on improving the accuracy of the model.
If you have any idea how to improve it, please comment below or inbox for me. All my source code has been uploaded to my Github, you take a look.

Model explanation:
I reuse the ResNet152 model of the tensorflow library. I replace the top layer with my neural network:
Hidden layer:
	Dense(4096,activation='relu')
	Dense(4096,activation='relu')
Output layer:
	Dense(196,activation='softmax')
I use a random generator algorithm Input image in Tensorflow's ImageDataGenerator to change the training dataset:
ImageDataGenerator(rotation_range=360,
                   width_shift_range=2,
                   height_shift_range=2,
                   shear_range=1,
                   zoom_range=(0, 100),
                   horizontal_flip=True,
                   vertical_flip=True,
                   brightness_range=(0, 1)
                   ).get_random_transform(img_shape=(224, 224, 3))
References:
       3D Object Representations for Fine-Grained Categorization
       Jonathan Krause, Michael Stark, Jia Deng, Li Fei-Fei
       4th IEEE Workshop on 3D Representation and Recognition, at ICCV 2013 (3dRR-13). Sydney, Australia. Dec. 8, 2013.
       [pdf]   [BibTex]   [slides]



Tôi đang dựng lại mô hình phân loại ô tô dựa trên mô hình ResNet152 của thư viện Tensorflow nhưng thay vì phân loại 1000 lớp thì mô hình tôi rút gọn theo tập dữ liệu chỉ còn phân loại 196 lớp.
Model: Resnet152
Data: https://ai.stanford.edu/~jkrause/cars/car_dataset.html
Input: Image(224,224,3)
Loss ≈ 1.9
Accuracy ≈ 65.57%
Classes: 196
Optimizer: Adam Optimization Algorithm
Mô hình có khả năng phân biệt 196 loại xe ô tô tuy nhiên độ
chính xác hiện tại của mô hình khá là thấp, chỉ đạt khoảng 65.57%%.
Hiện tại tôi đang cố gắng cải thiện độ chính xác của mô hình.
Nếu bạn có ý tưởng cải thiện độ chính xác của mô hình, mong bạn có thể bình luận ở dưới hoặc
nhắn tin cho tôi. Source code đã được tôi tải lên github, bạn có thể
tham khảo ở trên đấy.

Giải thích mô hình:
Về mô hình tôi sử dụng lại toàn bộ phần mô hình trong thư viện
tensorflow (ResNet152).
Tôi thay đổi mô hình ở phần top layer với mạng neural kèm với sử dụng dropout cho mạng:
Hidden layer:
	Dense(4096,activation='relu')
	Dense(4096,activation='relu')
Output layer:
	Dense(196,activation='softmax')
Tôi sử dụng thuật toán khởi tạo ngẫu nhiên đầu vào hình ảnh trong ImageDataGenerator của Tensorflow 
để làm thay đổi dữ liệu huấn luyện với các tham số
ImageDataGenerator(rotation_range=360,
                   width_shift_range=2,
                   height_shift_range=2,
                   shear_range=1,
                   zoom_range=(0, 100),
                   horizontal_flip=True,
                   vertical_flip=True,
                   brightness_range=(0, 1)
                   ).get_random_transform(img_shape=(224, 224, 3))

Trích dẫn:
       3D Object Representations for Fine-Grained Categorization
       Jonathan Krause, Michael Stark, Jia Deng, Li Fei-Fei
       4th IEEE Workshop on 3D Representation and Recognition, at ICCV 2013 (3dRR-13). Sydney, Australia. Dec. 8, 2013.
       [pdf]   [BibTex]   [slides]

