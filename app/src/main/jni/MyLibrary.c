#include <jni.h>
#include <android/log.h>
#define LOG_TAG "System.out"//输出信息的标签 类似Android中Log.i(TAG,"");
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
JNIEXPORT jstring JNICALL
Java_com_example_learndemo_MyJniTest_getString(JNIEnv *env, jobject instance) {

    // TODO

    LOGI("log info");

    return (*env)->NewStringUTF(env, "hello ndk");
}