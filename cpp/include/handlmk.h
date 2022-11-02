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

struct HandLandmarkResult {
	float hand_landmark[21][3];
	float hand_score = 0.0;
	float scale_world_landmark[21][3];
	float handedness = 0.0;

};

class HandLmkTFLite {
public:
	HandLmkTFLite(const char *model, long modelSize);
	HandLmkTFLite(const char *model);
    ~HandLmkTFLite();
    int *detect(Mat src, HandLandmarkResult *res);
	void initDetectionModel(const char *model);
	void initDetectionModel(const char *model, long modelSize);
private:
	// members
	bool m_hasDetectionModel = false;
	char *m_modelBytes = nullptr;
	std::unique_ptr<tflite::FlatBufferModel> m_model;
	std::unique_ptr<tflite::Interpreter> m_interpreter;
};

int preprocessHand(Mat src, Mat& dst, float *bboxes, float* new_xyxy);
void scale_hand_landmark(float *landmark, float *new_xyxy);