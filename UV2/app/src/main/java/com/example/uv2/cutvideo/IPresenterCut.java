package com.example.uv2.cutvideo;


public interface IPresenterCut {

    String VideoShowTime(int timeLength);

    void addBGM(String pathV, String pathA, int Length);

    void TextInit(String path,String ass);

    void PutVideoTOCut(String path, int firstTime, int lastTime);

    void keepVideo(String path);
}
