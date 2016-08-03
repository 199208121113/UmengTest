//test.cpp
// Created by root on 16-8-2.
//

#include "umeng_x_con_umengtest_JniTest.h"
#include <stdio.h>

JNIEXPORT jstring JNICALL Java_umeng_x_con_umengtest_JniTest_get(JNIEnv *env, jobject thiz){
    printf("invoke get from C++\n");
    return env->NewStringUTF("Hello from JNI !");
}

JNIEXPORT void JNICALL Java_umeng_x_con_umengtest_JniTest_set(JNIEnv *env, jobject thiz, jstring string){
      printf("invoke set from c++\n");
      char* str = (char*)env->GetStringUTFChars(string,NULL);
      printf("%s\n",str);
      env->ReleaseStringUTFChars(string,str);
}
