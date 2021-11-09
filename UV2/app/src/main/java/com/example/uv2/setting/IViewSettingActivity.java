package com.example.uv2.setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.uv2.MediaVideo;

public interface IViewSettingActivity {

    void setTakePhoto(Bitmap bitmap);

    void customDialog(MediaVideo mediaVideo);

}
