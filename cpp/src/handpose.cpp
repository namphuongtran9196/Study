#include "handpose.h"

inline float Det(float a, float b, float c, float d)
{
	return a*d - b*c;
}


bool isLineLineIntersect(float finger[][3], int start_index_1, int start_index_2) {
    //http://mathworld.wolfram.com/Line-LineIntersection.html
    float x1 = finger[start_index_1][0];
    float y1 = finger[start_index_1][1];
    float x2 = finger[start_index_1+3][0];
    float y2 = finger[start_index_1+3][1];

    float x3 = finger[start_index_2][0];
    float y3 = finger[start_index_2][1];
    float x4 = finger[start_index_2+3][0];
    float y4 = finger[start_index_2+3][1];

    float detL1 = Det(x1, y1, x2, y2);
    float detL2 = Det(x3, y3, x4, y4);
    float x1mx2 = x1 - x2;
    float x3mx4 = x3 - x4;
    float y1my2 = y1 - y2;
    float y3my4 = y3 - y4;

    float xnom = Det(detL1, x1mx2, detL2, x3mx4);
    float ynom = Det(detL1, y1my2, detL2, y3my4);
    float denom = Det(x1mx2, y1my2, x3mx4, y3my4);
    if(denom == 0.0)//Lines don't seem to cross
    {
        return false;
    }
    if(!isfinite(xnom / denom) || !isfinite(ynom / denom)) //Probably a numerical issue
        return false;
    return true; //All OK
}

bool isOpenFinger(float finger[][3], float ratio, int start_index){
    
    float *p0 = finger[start_index];
    float *p1 = finger[start_index+1];
    float *p2 = finger[start_index+2];
    float *p3 = finger[start_index+3];

    float head_to_tail_length = sqrt(pow(p0[0] - p3[0], 2) + pow(p0[1] - p3[1], 2));
    float sum_length = 0.0f;
    for (int i = 0; i < 3; i++){
        sum_length += sqrt(pow(finger[i][0] - finger[i+1][0], 2) + pow(finger[i][1] - finger[i+1][1], 2));
    }

    return sum_length*ratio < head_to_tail_length;
}
    