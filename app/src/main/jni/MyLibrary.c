#include <jni.h>
#include <android/log.h>
#define LOG_TAG "System.out"//输出信息的标签 类似Android中Log.i(TAG,"");
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
JNIEXPORT jstring JNICALL
Java_com_example_learndemo_MyJniTest_getString(JNIEnv *env, jobject instance) {
    LOGI("log info");
    jclass clazz = (*env)->FindClass(env,"com/example/learndemo/MyJniTest");
    if (clazz==0)
        LOGI("error");

    //GetMethodID的第一个参数env，第二个参数是上面FindClass的返回值，第三个是方法名（注意代码混淆），第四个参数是方法签名（可以用javap -s查看）
    jmethodID method = (*env)->GetMethodID(env,clazz,"demoLog","(Ljava/lang/String;)I");
    jmethodID method1 = (*env)->GetMethodID(env,clazz,"voidMethod","(Ljava/lang/String;)V");
    //调用没有返回值的方法,第一个参数env，第二个参数instance，第三个参数是GetMethodID返回的methodid，
    // 后面是Java方法中的参数，如果参数是String的，需要用(*env)->NewStringUTF(env,"call from c")这种方式返回
    (*env)->CallVoidMethod(env,instance,method1,(*env)->NewStringUTF(env,"call from c"));
    //调用有返回值的方法
    jint a = (*env)->CallIntMethod(env,instance,method,(*env)->NewStringUTF(env,"call from c"));
    __android_log_print(ANDROID_LOG_WARN,"udp","return data : %d",a);
    return (*env)->NewStringUTF(env, "hello ndk");
}