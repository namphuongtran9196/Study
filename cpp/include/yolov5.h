#include <math.h>
#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>
#include "tensorflow/lite/interpreter.h"
#include "tensorflow/lite/kernels/register.h"
#include "tensorflow/lite/model.h"
#include "tensorflow/lite/optional_debug_tools.h"


#include <iostream>

using namespace cv;
using namespace std;

struct Yolov5Result {
	float score = 0;
	float ymin = 0.0;
	float xmin = 0.0;
	float ymax = 0.0;
	float xmax = 0.0;
};

const int MAX_OUTPUT = 1000;

class YoloV5TFLite {
public:
	YoloV5TFLite(const char *model, long modelSize);
	YoloV5TFLite(const char *model);
    ~YoloV5TFLite();
    void *detect(Mat src, Yolov5Result *res);
	void initDetectionModel(const char *model);
	void initDetectionModel(const char *model, long modelSize);
private:
	// members
	bool m_hasDetectionModel = false;
	char *m_modelBytes = nullptr;
	std::unique_ptr<tflite::FlatBufferModel> m_model;
	std::unique_ptr<tflite::Interpreter> m_interpreter;
};

void letterbox(Mat src, Mat& dst, Size new_shape = Size(640,640), int stride=32, bool auto_mode = true, 
				Scalar color= Scalar(114,114,114),  bool scaleFill=false, bool scaleup=true );

void scale_boxes(float* img1_shape, float* bboxes, float* img0_shape);