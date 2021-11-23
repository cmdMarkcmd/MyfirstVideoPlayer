package com.example.uv2.setting;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.uv2.MediaPicture;
import com.example.uv2.MediaVideo;
import com.example.uv2.R;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class ModelSetting implements IModelSetting{

    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    public static final int CHOOSE_Video = 3;

    List<MediaPicture> mediaPictures;
    Uri imageUri ;
    AppCompatActivity appCompatActivityUse;
    IFinishSettingListener listener;

    public ModelSetting(IFinishSettingListener listener){
        this.listener = listener;
    }

    @Override
    public boolean haveHeadShot(){
        mediaPictures = LitePal.findAll(MediaPicture.class);
        return  (!mediaPictures.isEmpty());
    }

    @Override
    public Bitmap getHeadShot(){
        return BitmapFactory.decodeFile(LitePal.findLast(MediaPicture.class).getPath());
    }

    @Override
    public void openCamera(AppCompatActivity appCompatActivity){
        File outputImage = new File(appCompatActivity.getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(appCompatActivity, "com.example.cameraalbumtest.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        appCompatActivity.startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    public void ChoosePhoto(AppCompatActivity appCompatActivity) {
        if (ContextCompat.checkSelfPermission(appCompatActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(appCompatActivity, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        } else {
            // get photo
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            appCompatActivity.startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
        }

    }

    @Override
    public void ChooseVideo(AppCompatActivity appCompatActivity) {
        if (ContextCompat.checkSelfPermission(appCompatActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(appCompatActivity, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 3);
        } else {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("video/*");
            appCompatActivity.startActivityForResult(intent,CHOOSE_Video);
        }
    }

    @Override
    public void onRequestPermissionsResult(AppCompatActivity appCompatActivity, int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ChoosePhoto(appCompatActivity);
                } else {
                    Toast.makeText(appCompatActivity, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ChooseVideo(appCompatActivity);
                } else {
                    Toast.makeText(appCompatActivity, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(AppCompatActivity appCompatActivity, int requestCode, int resultCode, Intent data) {
        appCompatActivityUse = appCompatActivity;
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
//                         将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(appCompatActivity.getContentResolver().openInputStream(imageUri));
//                        keepPicture(bitmap);
                        keepPicture(CreatePath(bitmap));
//                        Do something;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case CHOOSE_Video:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理视频
                        handleVideoOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理视频
                        handleVideoBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }



    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(appCompatActivityUse, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    public void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @SuppressLint("Range")
    public String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = appCompatActivityUse.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            keepPicture(imagePath);
        } else {
            Toast.makeText(appCompatActivityUse, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
    public void keepPicture(String path){
        MediaPicture mediaPicture = new MediaPicture();
        mediaPicture.setName("1");
        Bitmap bitmap =BitmapFactory.decodeFile(path);
        path = CreatePath(bitmap);
        mediaPicture.setPath(path);
        mediaPicture.save();
        listener.newHeadShot(bitmap);
    }

    public String CreatePath(Bitmap bitmap)  {
        try {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            String picture_Name = simpleDateFormat.format(calendar.getTime());
            String framePath = appCompatActivityUse.getExternalFilesDir(null).getAbsolutePath()+"/Picture";
            File frameFile = new File(framePath);
            if(!frameFile.exists()){
                frameFile.mkdirs();
            }
            File picture_file = new File(frameFile,picture_Name+".jpg");
            FileOutputStream out = new FileOutputStream(picture_file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.flush();
            out.close();
            return picture_file.getAbsolutePath();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @TargetApi(19)
    private void handleVideoOnKitKat(Intent data) {
        String VideoPath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleVideoOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(appCompatActivityUse, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Video.Media._ID + "=" + id;
                VideoPath = getVideoPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                VideoPath = getVideoPath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            VideoPath = getVideoPath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            VideoPath = uri.getPath();
        }
        displayVideo(VideoPath);
    }

    public void handleVideoBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String VideoPath = getVideoPath(uri, null);
        displayVideo(VideoPath);
    }

    @SuppressLint("Range")
    public String getVideoPath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = appCompatActivityUse.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public void displayVideo(String VideoPath) {
        if (VideoPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(VideoPath);
            keepVideo(VideoPath);
        } else {
            Toast.makeText(appCompatActivityUse, "failed to get video", Toast.LENGTH_SHORT).show();
        }
    }

    public void keepVideo(String path){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        Bitmap bitmap= mmr.getFrameAtTime(0);
        mmr.release();
        MediaVideo mediaVideo = new MediaVideo();
        mediaVideo.setLike(0);
        mediaVideo.setPath(path);
        mediaVideo.setVideoId(CreatePath(bitmap));
        mediaVideo.setPwd(false);
        mediaVideo.setName("<未命名>");
        mediaVideo.setSentence("无");
        mediaVideo.setTag("视频");
        mediaVideo.setPassword("((((~null!@#$%w#je*wf$y5#@feg))))");
        CreateDialog(mediaVideo);
        mediaVideo.save();
    }

    public void CreateDialog(MediaVideo mediaVideo) {
        listener.getDialog(mediaVideo);
    }

    @Override
    public void setInformation(MediaVideo mediaVideo, String name, String sts, String tag){
        if (TextUtils.isEmpty(name)) {
            mediaVideo.setName("<未命名>");
            Toast.makeText(appCompatActivityUse, "视频名不能为空!", Toast.LENGTH_SHORT).show();
        }else{
            mediaVideo.setName(name);
        }
        if(!TextUtils.isEmpty(sts)) mediaVideo.setSentence(sts);
        if(!TextUtils.isEmpty(tag)) mediaVideo.setTag(tag);
        mediaVideo.save();
    }

}
