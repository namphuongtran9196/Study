#include <math.h>
#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>

bool isLineLineIntersect(float finger[][3], int start_index_1, int start_index_2);
bool isOpenFinger(float finger[][3], float ratio=0.8f, int start_index=0);