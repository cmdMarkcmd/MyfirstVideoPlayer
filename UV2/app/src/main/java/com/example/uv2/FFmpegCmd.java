package com.example.uv2;

public class FFmpegCmd {
    static {
        System.loadLibrary("x264");
        System.loadLibrary("uv2");
    }
    private static native int run(int cmdLen, String[] cmd);
//    public static native String test();

    public static int run(String[] cmd){
        return run(cmd.length,cmd);
//        return 0;
    }
    public static native void test(String path);

}
