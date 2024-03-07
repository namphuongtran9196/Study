#include <opencv2/core.hpp>
#include <opencv2/videoio.hpp>
#include <opencv2/highgui.hpp>
#include <iostream>
#include <stdio.h>
#include "yolov5.h"
#include "handlmk.h"
#include "handpose.h"
using namespace cv;
using namespace std;

int main(int, char * argv[])
{
    Mat frame;
    //--- INITIALIZE VIDEOCAPTURE
    VideoCapture cap;
    // open the default camera using default API
    // cap.open(0);
    // OR advance usage: select any API backend
    int deviceID = 0;             // 0 = open default camera
    int apiID = cv::CAP_ANY;      // 0 = autodetect default API
    // open selected camera using selected API
    cap.open(deviceID, apiID);
    // check if we succeeded
    if (!cap.isOpened()) {
        cerr << "ERROR! Unable to open camera\n";
        return -1;
    }

    // TFlite model
    YoloV5TFLite *model = new YoloV5TFLite((char *)"../../models/model.tflite");
    HandLmkTFLite *model_hand_lmk = new HandLmkTFLite((char *)"../../models/hand_landmark_full.tflite");

    //--- GRAB AND WRITE LOOP
    cout << "Start grabbing" << endl
        << "Press any key to terminate" << endl;
    for (;;)
    {
        // wait for a new frame from camera and store it into 'frame'
        cap.read(frame);
        // check if we succeeded
        if (frame.empty()) {
            cerr << "ERROR! blank frame grabbed\n";
            break;
        }
        flip(frame, frame, 1);
        Mat image;
        cvtColor(frame.clone(), image, COLOR_BGR2RGB);
        letterbox(image, image, Size(640,640), 32, false);
        Yolov5Result res[MAX_OUTPUT];
        model->detect(image,res);

        for (int i = 0; i < MAX_OUTPUT; i ++){
            if (res[i].score > 0.25){
                float bboxes[4] = {res[i].xmin, res[i].ymin, res[i].xmax,res[i].ymax};
                float old_shape[2] = {640.0f,640.0f};
                float raw_shape[2] = {(float)frame.size().height, (float) frame.size().width};
                scale_boxes(old_shape, bboxes, raw_shape);
                float new_xyxy[4]; 
                preprocessHand(frame, image, bboxes, new_xyxy);

                HandLandmarkResult res_hand[1];
                model_hand_lmk->detect(image, res_hand);

                if (res_hand[0].hand_score > 0.5){
                    scale_hand_landmark(res_hand[0].hand_landmark[0], new_xyxy);
                    for (int j = 0; j < 21; j ++){
                        float x = res_hand[0].hand_landmark[j][0];
                        float y = res_hand[0].hand_landmark[j][1];
                        circle(frame, Point(x,y), 2, Scalar(0,0,255), -1);
                    }
                } 

                if (isOpenFinger(res_hand[0].hand_landmark, 0.7f, 1)
                    && isOpenFinger(res_hand[0].hand_landmark, 0.7f, 5)  
                    && !isOpenFinger(res_hand[0].hand_landmark, 0.7f, 9) 
                    && !isOpenFinger(res_hand[0].hand_landmark, 0.7f, 13)
                    && !isOpenFinger(res_hand[0].hand_landmark, 0.7f, 17) 
                    && isLineLineIntersect(res_hand[0].hand_landmark, 1, 5)
                ){
                    putText(frame, "<3 <3", Point(new_xyxy[0], new_xyxy[1]), FONT_HERSHEY_SIMPLEX, 1, Scalar(0,0,255), 2);
                } 
            } else{
                break;
            }
        }
        // show live and wait for a key with timeout long enough to show images
        imshow("Live", frame);
        if (waitKey(5) >= 0)
            break;
    }
    // the camera will be deinitialized automatically in VideoCapture destructor
    return 0;
}