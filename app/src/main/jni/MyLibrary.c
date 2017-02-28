#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_learndemo_MyJniTest_getString(JNIEnv *env, jobject instance) {

    // TODO


    return (*env)->NewStringUTF(env, "hello ndk");
}