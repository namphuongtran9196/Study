#include "handlmk.h"
#include <iostream>
////////////////////////////////////////////////// Image processing //////////////////////////////////////////////////

int preprocessHand(Mat src, Mat& dst, float *bboxes, float* new_xyxy){
    float width = bboxes[2] - bboxes[0];
    float height = bboxes[3] - bboxes[1];

    // add extra padding
    float new_xmin = max(bboxes[0] - width * 0.5f, 0.0f);
    float new_ymin = max(bboxes[1] - height * 0.5f, 0.0f);
    float new_xmax = min(bboxes[2] + width * 0.5f, (float)src.size().width);
    float new_ymax = min(bboxes[3] + height * 0.5f, (float)src.size().height);
    // crop image
    dst = src(Range(new_ymin, new_ymax), Range(new_xmin, new_xmax));
    if (dst.empty()){
        return 0;
    }
    resize(dst, dst, Size(224,224));
    cvtColor(dst, dst, COLOR_BGR2RGB);

    // normalize
    dst.convertTo(dst, CV_32FC3, 1.0f/255.0f);

    new_xyxy[0] = new_xmin;
    new_xyxy[1] = new_ymin;
    new_xyxy[2] = new_xmax;
    new_xyxy[3] = new_ymax;
    return 1;
}

void scale_hand_landmark(float *landmark, float *new_xyxy){
    for (int i = 0; i < 21; i++){
        landmark[i*3] = landmark[i*3] / 224 * (new_xyxy[2] - new_xyxy[0]) + new_xyxy[0];
        landmark[i*3+1] = landmark[i*3+1] / 224 * (new_xyxy[3] - new_xyxy[1]) + new_xyxy[1];
    }
}

////////////////////////////////////////////////// Tensorflow lite //////////////////////////////////////////////////

HandLmkTFLite::HandLmkTFLite(const char *model, long modelSize) {
	if (modelSize > 0) {
		initDetectionModel(model, modelSize);
	} 
}

HandLmkTFLite::HandLmkTFLite(const char *model) {
	initDetectionModel(model);
}
void HandLmkTFLite::initDetectionModel(const char *tflitemodel) {

	// Copy to model bytes as the caller might release this memory while we need it (EXC_BAD_ACCESS error on ios)
	m_model = tflite::FlatBufferModel::BuildFromFile(tflitemodel);
	assert(m_model != nullptr);

	// Build the interpreter
	tflite::ops::builtin::BuiltinOpResolver resolver;
	tflite::InterpreterBuilder builder(*m_model, resolver);
	builder(&m_interpreter);
	assert(m_interpreter != nullptr);

	// Allocate tensor buffers.
	assert(m_interpreter->AllocateTensors() == kTfLiteOk);
	assert(m_interpreter->Invoke() == kTfLiteOk);

	m_interpreter->SetNumThreads(1);
}

void HandLmkTFLite::initDetectionModel(const char *tflitemodel, long modelSize) {

	// Copy to model bytes as the caller might release this memory while we need it (EXC_BAD_ACCESS error on ios)
	m_modelBytes = (char *) malloc(sizeof(char) * modelSize);
	memcpy(m_modelBytes, tflitemodel, sizeof(char) * modelSize);
	m_model = tflite::FlatBufferModel::BuildFromBuffer(m_modelBytes, modelSize);
	assert(m_model != nullptr);

	// Build the interpreter
	tflite::ops::builtin::BuiltinOpResolver resolver;
	tflite::InterpreterBuilder builder(*m_model, resolver);
	builder(&m_interpreter);
	assert(m_interpreter != nullptr);

	// Allocate tensor buffers.
	assert(m_interpreter->AllocateTensors() == kTfLiteOk);
	assert(m_interpreter->Invoke() == kTfLiteOk);

	m_interpreter->SetNumThreads(1);
}

int *HandLmkTFLite::detect(Mat input, HandLandmarkResult *res) {

    // convert the input image to float32
    input.convertTo(input, CV_32FC3);

	// allocate the tflite tensor
	m_interpreter->AllocateTensors();

	// get input & output layer of tflite model
	float *inputLayer = m_interpreter->typed_input_tensor<float>(0);
	float *outputLayer_hand_landmark = m_interpreter->typed_output_tensor<float>(0);
    float *outputLayer_hand_score = m_interpreter->typed_output_tensor<float>(1);
    float *outputLayer_handedness = m_interpreter->typed_output_tensor<float>(2);
    float *outputLayer_scale_world_landmark = m_interpreter->typed_output_tensor<float>(3);

	float *input_ptr = input.ptr<float>(0);

	// copy the input image to input layer
	memcpy(inputLayer, input_ptr, input.size().width * input.size().height * input.channels() * sizeof(float));

	// compute model instance
	if (m_interpreter->Invoke() != kTfLiteOk) {
		printf("Error invoking detection model");
		return reinterpret_cast<int *>(0);
    } else{
        // left hand
        res[0].hand_score = outputLayer_hand_score[0];
		for (int i = 0; i < 21; ++i) {
            res[0].hand_landmark[i][0] = outputLayer_hand_landmark[i * 3];
            res[0].hand_landmark[i][1] = outputLayer_hand_landmark[i * 3 + 1];
            res[0].hand_landmark[i][2] = outputLayer_hand_landmark[i * 3 + 2];
		}
        
        // right hand
        res[0].handedness = outputLayer_handedness[0];
        for (int i = 0; i < 21; ++i) {
            res[0].scale_world_landmark[i][0] = outputLayer_scale_world_landmark[i * 3];
            res[0].scale_world_landmark[i][1] = outputLayer_scale_world_landmark[i * 3 + 1];
            res[0].scale_world_landmark[i][2] = outputLayer_scale_world_landmark[i * 3 + 2];
		}
	}
	return reinterpret_cast<int *>(1);
}