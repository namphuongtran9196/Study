import cv2
import os
import time

IMAGES_PATH = 'workspace/images'
NUMBER_IMGS = 15

labels = ['watch', 'wallet', 'mobile_phone', 'face_mask','headphone']
print(os.path.join(IMAGES_PATH,labels[0]+'{}.jpg'.format('01')))

for label in labels:
    cap = cv2.VideoCapture(0)
    print('Collecting images for {}'.format(label))
    time.sleep(10)
    for num in range(NUMBER_IMGS):
        ret, frame = cap.read()
        imagename = os.path.join(IMAGES_PATH,label+'{}.jpg'.format(str(num)))
        cv2.imwrite(imagename, frame)
        cv2.imshow('frame', frame)
        time.sleep(4)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
    cap.release()