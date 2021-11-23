
#include <jni.h>
#include <string>
#include "android/log.h"

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "ffmpeg-invoke", __VA_ARGS__)

extern "C" {
#include "fftools/ffmpeg.h"
#include "libavcodec/jni.h"
JNIEXPORT jint JNICALL
Java_com_example_uv2_FFmpegCmd_run(JNIEnv *env, jclass type, jint cmdLen, jobjectArray cmd) {
    //set java vm
    JavaVM *jvm = NULL;
    env->GetJavaVM(&jvm);
    av_jni_set_java_vm(jvm, NULL);

    char *argCmd[cmdLen];
    jstring buf[cmdLen];

    for (int i = 0; i < cmdLen; ++i) {
        buf[i] = static_cast<jstring>(env->GetObjectArrayElement(cmd, i));
        char *string = const_cast<char *>(env->GetStringUTFChars(buf[i], JNI_FALSE));
        argCmd[i] = string;
        LOGD("argCommands=%s", argCmd[i]);
    }

    int retCode = ffmpeg_exec(cmdLen, argCmd);
    LOGD("ffmpeg-uv2(lib): retCode=%d", retCode);
    return retCode;
}
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_uv2_FFmpegCmd_test(JNIEnv *env, jclass thiz, jstring path) {
    // TODO: implement test()
    char *p = const_cast<char *>((*env).GetStringUTFChars(path, 0));
    //LOGD("%s", p);
    freopen(p,"r",stdin);
    char ass[1000];
    while(    scanf("%s",ass)!=EOF)
    LOGD("%s",ass);
}