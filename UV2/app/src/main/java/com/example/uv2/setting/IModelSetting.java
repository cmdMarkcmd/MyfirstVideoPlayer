package com.example.uv2.setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uv2.MediaPicture;
import com.example.uv2.MediaVideo;

import java.util.List;

public interface IModelSetting {


    boolean haveHeadShot();

    Bitmap getHeadShot();

    void openCamera(AppCompatActivity appCompatActivity);

    void ChoosePhoto(AppCompatActivity appCompatActivity);

    void ChooseVideo(AppCompatActivity appCompatActivity);

    void onRequestPermissionsResult(AppCompatActivity appCompatActivity, int requestCode, String[] permissions, int[] grantResults);

    void onActivityResult(AppCompatActivity appCompatActivity, int requestCode, int resultCode, Intent data);

    void setInformation(MediaVideo mediaVideo, String name, String sts, String tag);
}
