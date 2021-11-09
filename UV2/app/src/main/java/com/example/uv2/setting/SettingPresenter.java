package com.example.uv2.setting;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.uv2.MediaPicture;
import com.example.uv2.MediaVideo;
import com.example.uv2.showfull.ShowFull;

import org.litepal.LitePal;

import java.util.List;

public class SettingPresenter implements IPresenterSetting, IFinishSettingListener{

    IModelSetting model;

    AppCompatActivity appCompatActivity;

    IViewSettingActivity view;

    public SettingPresenter(IViewSettingActivity iViewSettingActivity){
        this.view = iViewSettingActivity;
        this.model = new ModelSetting(this);
        this.appCompatActivity = (AppCompatActivity) iViewSettingActivity;
    }

    @Override
    public void setHeadShot() {
        if(model.haveHeadShot()) view.setTakePhoto(model.getHeadShot());
    }

    @Override
    public void takePicture() {
        model.openCamera(appCompatActivity);
    }

    @Override
    public void openAlbum() {
        model.ChoosePhoto(appCompatActivity);
    }

    @Override
    public void newHeadShot(Bitmap bitmap){
        view.setTakePhoto(bitmap);
    }

    @Override
    public void initVideoPath() {
        model.ChooseVideo(appCompatActivity);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        model.onRequestPermissionsResult(appCompatActivity,requestCode,permissions,grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        model.onActivityResult(appCompatActivity,requestCode,resultCode,data);
    }

    @Override
    public void setPwd() {
        Intent intent = new Intent(appCompatActivity, ShowFull.class);
        appCompatActivity.startActivity(intent);
    }

    @Override
    public void getDialog(MediaVideo mediaVideo){
        view.customDialog(mediaVideo);
    }

    @Override
    public void Dialog_okay(MediaVideo mediaVideo,String name, String sts, String tag){
        model.setInformation(mediaVideo,name,sts,tag);
    }

}
