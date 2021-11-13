#include <jni.h>

// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("uv2");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("uv2")
//      }
//    }
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_uv2_mainactivity_MainActivity_getStringFromCPP(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("Str from cpp");
    // TODO: implement getStringFromCPP()
}