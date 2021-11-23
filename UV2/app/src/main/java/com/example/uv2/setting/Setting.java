//package com.example.uv2.setting;
////formal code
//
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.core.content.FileProvider;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.media.MediaMetadataRetriever;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.DocumentsContract;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//import android.widget.VideoView;
//
//import com.example.uv2.MediaPicture;
//import com.example.uv2.MediaVideo;
//import com.example.uv2.R;
//import com.example.uv2.ShowFull;
//
//import org.litepal.LitePal;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.List;
//import java.util.Locale;
//
//public class SOS extends AppCompatActivity implements IViewSettingActivity{
//
//    public static final int TAKE_PHOTO = 1;
//
//    public static final int CHOOSE_PHOTO = 2;
//
//    public static final int CHOOSE_Video = 3;
//
//
//    private ImageView pic2;
//
//    private Uri imageUri;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_setting);
//
//        pic2 = (ImageView) findViewById(R.id.imageView2) ;
//        List<MediaPicture> mediaPictures= LitePal.findAll(MediaPicture.class);
//        if(!mediaPictures.isEmpty()){
//            String path = LitePal.findLast(MediaPicture.class).getPath();
//            Bitmap bitmap =BitmapFactory.decodeFile(path);
//            pic2.setImageBitmap(bitmap);
//        }
//        Button chooseFromAlbum = (Button) findViewById(R.id.button4);
//        Button takePhoto = (Button) findViewById(R.id.button5);
//        takePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 创建File对象，用于存储拍照后的图片
//                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
//                try {
//                    if (outputImage.exists()) {
//                        outputImage.delete();
//                    }
//                    outputImage.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (Build.VERSION.SDK_INT < 24) {
//                    imageUri = Uri.fromFile(outputImage);
//                } else {
//                    imageUri = FileProvider.getUriForFile(SettingActivity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
//                }
//                // 启动相机程序
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(intent, TAKE_PHOTO);
//            }
//        });
//        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(SettingActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
//                } else {
//                    openAlbum();
//                }
//            }
//
//        });
//        Button chooseVideo = (Button) findViewById(R.id.videoAdd);
//        chooseVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(SettingActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 3);
//                } else {
//                    // 初始化MediaPlayer
//                    initVideoPath();
//
//                }
//            }
//        });
//        Button pwdButton = (Button) findViewById(R.id.buttonpwd);
//        pwdButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingActivity.this, ShowFull.class);
//                startActivity(intent);
//            }
//        });
//    }
////_______________________________________________________________________________________
//    @Override
//    public void initVideoPath() {
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("video/*");
//        startActivityForResult(intent, CHOOSE_Video); // 打开相册
//    }
//
//    @Override
//    public void openAlbum() {
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
//        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
//    }
////________________________________________________________________________________________
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    openAlbum();
//                } else {
//                    Toast.makeText(this, "Y拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 3:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    initVideoPath();
//                } else {
//                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//        }
//    }
//
//    @SuppressLint("MissingSuperCall")
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case TAKE_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    try {
////                         将拍摄的照片显示出来
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
////                        keepPicture(bitmap);
//                        keepPicture(CreatePath(bitmap));
////                        Do something;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
//            case CHOOSE_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    // 判断手机系统版本号
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        // 4.4及以上系统使用这个方法处理图片
//                        handleImageOnKitKat(data);
//                    } else {
//                        // 4.4以下系统使用这个方法处理图片
//                        handleImageBeforeKitKat(data);
//                    }
//                }
//                break;
//            case CHOOSE_Video:
//                if (resultCode == RESULT_OK) {
//                    // 判断手机系统版本号
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        // 4.4及以上系统使用这个方法处理视频
//                        handleVideoOnKitKat(data);
//                    } else {
//                        // 4.4以下系统使用这个方法处理视频
//                        handleVideoBeforeKitKat(data);
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//
//
//
//
//    @TargetApi(19)
//    private void handleImageOnKitKat(Intent data) {
//        String imagePath = null;
//        Uri uri = data.getData();
//        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
//        if (DocumentsContract.isDocumentUri(this, uri)) {
//            // 如果是document类型的Uri，则通过document id处理
//            String docId = DocumentsContract.getDocumentId(uri);
//            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
//                String id = docId.split(":")[1]; // 解析出数字格式的id
//                String selection = MediaStore.Images.Media._ID + "=" + id;
//                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
//            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
//                imagePath = getImagePath(contentUri, null);
//            }
//        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            // 如果是content类型的Uri，则使用普通方式处理
//            imagePath = getImagePath(uri, null);
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            // 如果是file类型的Uri，直接获取图片路径即可
//            imagePath = uri.getPath();
//        }
//        displayImage(imagePath); // 根据图片路径显示图片
//    }
//
//    @Override
//    public void handleImageBeforeKitKat(Intent data) {
//        Uri uri = data.getData();
//        String imagePath = getImagePath(uri, null);
//        displayImage(imagePath);
//    }
//
//    @Override
//    public String getImagePath(Uri uri, String selection) {
//        String path = null;
//        // 通过Uri和selection来获取真实的图片路径
//        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//
//    @Override
//    public void displayImage(String imagePath) {
//        if (imagePath != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            keepPicture(imagePath);
//        } else {
//            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
//        }
//    }
//    @Override
//    public void keepPicture(String path){
//        MediaPicture mediaPicture = new MediaPicture();
//        mediaPicture.setName("1");
//        Bitmap bitmap =BitmapFactory.decodeFile(path);
//        path = CreatePath(bitmap);
//        mediaPicture.setPath(path);
//        mediaPicture.save();
////        bitmap = BitmapFactory.decodeFile(path);
////        List<MediaPicture> mediaPictures= LitePal.findAll(MediaPicture.class);
//        pic2.setImageBitmap(bitmap);
//    }
//
//    @Override
//    public String CreatePath(Bitmap bitmap)  {
//        try {
//            Calendar calendar = new GregorianCalendar();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
//            String picture_Name = simpleDateFormat.format(calendar.getTime());
//            String framePath = getExternalFilesDir(null).getAbsolutePath()+"/Picture";
//            File frameFile = new File(framePath);
//            if(!frameFile.exists()){
//                frameFile.mkdirs();
//            }
//            File picture_file = new File(frameFile,picture_Name+".jpg");
//            FileOutputStream out = new FileOutputStream(picture_file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
//            out.flush();
//            out.close();
//            return picture_file.getAbsolutePath();
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @TargetApi(19)
//    private void handleVideoOnKitKat(Intent data) {
//        String VideoPath = null;
//        Uri uri = data.getData();
//        Log.d("TAG", "handleVideoOnKitKat: uri is " + uri);
//        if (DocumentsContract.isDocumentUri(this, uri)) {
//            // 如果是document类型的Uri，则通过document id处理
//            String docId = DocumentsContract.getDocumentId(uri);
//            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
//                String id = docId.split(":")[1]; // 解析出数字格式的id
//                String selection = MediaStore.Video.Media._ID + "=" + id;
//                VideoPath = getVideoPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, selection);
//            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
//                VideoPath = getVideoPath(contentUri, null);
//            }
//        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            // 如果是content类型的Uri，则使用普通方式处理
//            VideoPath = getVideoPath(uri, null);
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            VideoPath = uri.getPath();
//        }
//        displayVideo(VideoPath);
//    }
//
//    @Override
//    public void handleVideoBeforeKitKat(Intent data) {
//        Uri uri = data.getData();
//        String VideoPath = getVideoPath(uri, null);
//        displayVideo(VideoPath);
//    }
//
//    @Override
//    public String getVideoPath(Uri uri, String selection) {
//        String path = null;
//        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//
//    @Override
//    public void displayVideo(String VideoPath) {
//        if (VideoPath != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(VideoPath);
//            keepVideo(VideoPath);
//        } else {
//            Toast.makeText(this, "failed to get video", Toast.LENGTH_SHORT).show();
//        }
//    }
//    @Override
//    public void keepVideo(String path){
//        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//        mmr.setDataSource(path);
//        Bitmap bitmap= mmr.getFrameAtTime(0);
//        mmr.release();
//        MediaVideo mediaVideo = new MediaVideo();
//        mediaVideo.setLike(0);
//        mediaVideo.setPath(path);
//        mediaVideo.setVideoId(CreatePath(bitmap));
//        mediaVideo.setPwd(false);
//        mediaVideo.setName("<未命名>");
//        mediaVideo.setSentence("无");
//        mediaVideo.setTag("视频");
//        mediaVideo.setPassword("((((~null!@#$%w#je*wf$y5#@feg))))");
//        customDialog(mediaVideo);
//        mediaVideo.save();
//    }
//    public void customDialog(MediaVideo mediaVideo) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
//        final AlertDialog dialog = builder.create();
//        View dialogView = View.inflate(SettingActivity.this, R.layout.alert_dialog, null);
//        dialog.setView(dialogView);
//        dialog.show();
//
//        final EditText et_name = dialogView.findViewById(R.id.et_name);
//        final EditText et_sts = dialogView.findViewById(R.id.et_sts);
//        final EditText et_tag = dialogView.findViewById(R.id.et_tag);
//        final Button btn_okay = dialogView.findViewById(R.id.btn_okay);
//        final Button btn_cancel = dialogView.findViewById(R.id.btn_cancel);
//
//        btn_okay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String name =et_name.getText().toString();
//                final String sts = et_sts.getText().toString();
//                final String tag = et_tag.getText().toString();
//                if (TextUtils.isEmpty(name)) {
//                    mediaVideo.setName("<未命名>");
//                    Toast.makeText(SettingActivity.this, "视频名不能为空!", Toast.LENGTH_SHORT).show();
//                }else{
//                    mediaVideo.setName("  "+name);
//                }
//                if(!TextUtils.isEmpty(sts)) mediaVideo.setSentence(sts);
//                if(!TextUtils.isEmpty(tag)) mediaVideo.setTag(tag);
//                mediaVideo.save();
//                dialog.dismiss();
//                Toast.makeText(SettingActivity.this, "视频载入成功，请返回主页面查看", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//    }
//}