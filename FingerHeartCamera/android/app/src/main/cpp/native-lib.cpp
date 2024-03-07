#include <jni.h>
#include <string>
#include <jni.h>
#include <string>
#include <math.h>
#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/calib3d.hpp>
#include <android/log.h>
#include "yolov5.h"
#include "handlmk.h"
#include "handpose.h"
#include "android/bitmap.h"
#include <jni.h>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <android/log.h>

using namespace cv;

// TFlite model
YoloV5TFLite *model_hand_detect;
HandLmkTFLite *model_hand_lmk;
// Convert the bitmap to OpenCV Mat
// Reference https://github.com/opencv/opencv/blob/17234f82d025e3bbfbf611089637e5aa2038e7b8/modules/java/generator/src/cpp/utils.cpp
void bitmapToMat(JNIEnv * env, jobject bitmap, Mat& dst, jboolean needUnPremultiplyAlpha)
{
    AndroidBitmapInfo  info;
    void*              pixels = 0;

    try {
        CV_Assert( AndroidBitmap_getInfo(env, bitmap, &info) >= 0 );
        CV_Assert( info.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
                   info.format == ANDROID_BITMAP_FORMAT_RGB_565 );
        CV_Assert( AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0 );
        CV_Assert( pixels );
        dst.create(info.height, info.width, CV_8UC4);
        if( info.format == ANDROID_BITMAP_FORMAT_RGBA_8888 )
        {
            Mat tmp(info.height, info.width, CV_8UC4, pixels);
            if(needUnPremultiplyAlpha) cvtColor(tmp, dst, COLOR_mRGBA2RGBA);
            else tmp.copyTo(dst);
        } else {
            // info.format == ANDROID_BITMAP_FORMAT_RGB_565
            Mat tmp(info.height, info.width, CV_8UC2, pixels);
            cvtColor(tmp, dst, COLOR_BGR5652RGBA);
        }
        AndroidBitmap_unlockPixels(env, bitmap);
        return;
    } catch(const cv::Exception& e) {
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, e.what());
        return;
    } catch (...) {
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, "Unknown exception in JNI code {nBitmapToMat}");
        return;
    }
}

// Convert a mat to Bitmap
// Reference https://github.com/opencv/opencv/blob/17234f82d025e3bbfbf611089637e5aa2038e7b8/modules/java/generator/src/cpp/utils.cpp
void matToBitmap(JNIEnv * env, Mat src, jobject bitmap, jboolean needPremultiplyAlpha)
{
    AndroidBitmapInfo  info;
    void*              pixels = 0;

    try {
        CV_Assert( AndroidBitmap_getInfo(env, bitmap, &info) >= 0 );
        CV_Assert( info.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
                   info.format == ANDROID_BITMAP_FORMAT_RGB_565 );
        CV_Assert( src.dims == 2);
        CV_Assert(info.height == (uint32_t)src.rows);
        CV_Assert(info.width == (uint32_t)src.cols );
        CV_Assert( src.type() == CV_8UC1 || src.type() == CV_8UC3 || src.type() == CV_8UC4 );
        CV_Assert( AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0 );
        CV_Assert( pixels );
        if( info.format == ANDROID_BITMAP_FORMAT_RGBA_8888 )
        {
            Mat tmp(info.height, info.width, CV_8UC4, pixels);
            if(src.type() == CV_8UC1)
            {
                cvtColor(src, tmp, COLOR_GRAY2RGBA);
            } else if(src.type() == CV_8UC3){
                cvtColor(src, tmp, COLOR_RGB2RGBA);
            } else if(src.type() == CV_8UC4){
                if(needPremultiplyAlpha) cvtColor(src, tmp, COLOR_RGBA2mRGBA);
                else src.copyTo(tmp);
            }
        } else {
            // info.format == ANDROID_BITMAP_FORMAT_RGB_565
            Mat tmp(info.height, info.width, CV_8UC2, pixels);
            if(src.type() == CV_8UC1)
            {
                cvtColor(src, tmp, COLOR_GRAY2BGR565);
            } else if(src.type() == CV_8UC3){
                cvtColor(src, tmp, COLOR_RGB2BGR565);
            } else if(src.type() == CV_8UC4){
                cvtColor(src, tmp, COLOR_RGBA2BGR565);
            }
        }
        AndroidBitmap_unlockPixels(env, bitmap);
        return;
    } catch(const cv::Exception& e) {
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, e.what());
        return;
    } catch (...) {
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, "Unknown exception in JNI code {nMatToBitmap}");
        return;
    }
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_vn_dsc_fingerheart_fragments_CameraFragment_isHaveFingerHeart(JNIEnv *env, jobject thiz,
                                                                   jobject bitmap) {
    try{
        Mat frame;
        bitmapToMat(env, bitmap, frame, false);
        Mat image;
        cvtColor(frame.clone(), image, COLOR_BGR2RGB);
        letterbox(image, image, Size(640,640), 32, false);
        Yolov5Result res[MAX_OUTPUT];
        model_hand_detect->detect(image,res);

        for (int i = 0; i < MAX_OUTPUT; i ++){
            if (res[i].score > 0.25){
                float bboxes[4] = {res[i].xmin, res[i].ymin, res[i].xmax,res[i].ymax};
                float old_shape[2] = {640.0f,640.0f};
                float raw_shape[2] = {(float)frame.size().height, (float) frame.size().width};
                scale_boxes(old_shape, bboxes, raw_shape);
                float new_xyxy[4];
                if (preprocessHand(frame, image, bboxes, new_xyxy) == 0){
                    continue;
                };

                HandLandmarkResult res_hand[1];
                model_hand_lmk->detect(image, res_hand);

                if (isOpenFinger(res_hand[0].hand_landmark, 0.5f, 1)
                    && isOpenFinger(res_hand[0].hand_landmark, 0.5f, 5)
                    && !isOpenFinger(res_hand[0].hand_landmark, 0.5f, 9)
                    && !isOpenFinger(res_hand[0].hand_landmark, 0.5f, 13)
                    && !isOpenFinger(res_hand[0].hand_landmark, 0.5f, 17)
                    && isLineLineIntersect(res_hand[0].hand_landmark, 1, 5)
                        ){
                    image.release();
                    frame.release();
                    return true;
                }
            } else{
                break;
            }
        }
        image.release();
        frame.release();
        return false;
    } catch (const std::exception& e) {
        __android_log_print(ANDROID_LOG_ERROR, "FingerHeart", "isHaveFingerHeart: %s", e.what());
        return false;
    }
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_vn_dsc_fingerheart_fragments_CameraFragment_initTFLite(JNIEnv *env, jobject thiz,
                                                            jobject assetManager) {
    char *bufferHandDetection = nullptr;
    long sizeHandDetection = 0;

    if (!(env->IsSameObject(assetManager, NULL))) {
        AAssetManager *mgr = AAssetManager_fromJava(env, assetManager);
        AAsset *assetHandDetection = AAssetManager_open(mgr, "hand_detection.tflite", AASSET_MODE_UNKNOWN);
        assert(assetHandDetection != nullptr);

        sizeHandDetection = AAsset_getLength(assetHandDetection);
        bufferHandDetection = (char *) malloc(sizeof(char) * sizeHandDetection);
        AAsset_read(assetHandDetection, bufferHandDetection, sizeHandDetection);
        AAsset_close(assetHandDetection);
    }

    model_hand_detect = new YoloV5TFLite(bufferHandDetection, sizeHandDetection);

    char *bufferHandLmk = nullptr;
    long sizeHandLmk = 0;
    if (!(env->IsSameObject(assetManager, NULL))) {
        AAssetManager *mgr = AAssetManager_fromJava(env, assetManager);
        AAsset *assetHandLmk = AAssetManager_open(mgr, "hand_landmark_full.tflite", AASSET_MODE_UNKNOWN);
        assert(assetHandLmk != nullptr);

        sizeHandLmk = AAsset_getLength(assetHandLmk);
        bufferHandLmk = (char *) malloc(sizeof(char) * sizeHandLmk);
        AAsset_read(assetHandLmk, bufferHandLmk, sizeHandLmk);
        AAsset_close(assetHandLmk);
    }
    model_hand_lmk = new HandLmkTFLite(bufferHandLmk, sizeHandLmk);

    free(bufferHandDetection);
    free(bufferHandLmk);
    return true;
}