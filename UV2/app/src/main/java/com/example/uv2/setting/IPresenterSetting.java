package com.example.uv2.setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.uv2.MediaVideo;

public interface IPresenterSetting {

    void setHeadShot();

    void takePicture();

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void setPwd();

    void initVideoPath() ;

    void openAlbum();

    void Dialog_okay(MediaVideo mediaVideo, String name, String sts, String tag);
}
