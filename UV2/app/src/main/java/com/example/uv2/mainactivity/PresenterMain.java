package com.example.uv2.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

public class PresenterMain implements IPresenterMain{
    IViewMain view;
    IModelMain model;

    PresenterMain(IViewMain iViewMain){
        this.view = iViewMain;
        this.model = new ModelMain((AppCompatActivity)iViewMain);
    }

    @Override
    public boolean askUI(){
        return model.haveVideo();
    }

    @Override
    public void getUI(){
        model.Resume();
    }
}
