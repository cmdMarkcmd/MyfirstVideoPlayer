package com.example.uv2.cutvideo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.uv2.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CutActivity extends AppCompatActivity implements IViewCut{
    private static final String TAG = "CutActivity";
    private IPresenterCut presenter;
    private int Duration;
    private VideoView videoView;
    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    public static final int MusicIn = 4;
    private TextView deBug;
    private String audioWay;
    AlertDialog dialog;
    FileHelper fileHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut);
        presenter = new PresenterCut(this);
        videoView = (VideoView) findViewById(R.id.VideoViewToCut);
        seekBar1 = (SeekBar) findViewById(R.id.videoline1);
        deBug = (TextView) findViewById(R.id.Cut1);

        TextView timeMiddle = (TextView) findViewById(R.id.timefirst);
        TextView timeLast = (TextView) findViewById(R.id.timelast);

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(getIntent().getStringExtra("IDV"));
        Duration = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        seekBar1.setMax(Duration);

        videoView.setVideoPath(getIntent().getStringExtra("IDV"));
        timeLast.setText(presenter.VideoShowTime(Duration));
        timeMiddle.setText(presenter.VideoShowTime(0));
        videoView.seekTo(1);
        videoView.pause();

        AlertDialog.Builder builder = new AlertDialog.Builder(CutActivity.this);
        dialog = builder.create();
        View dialogView = View.inflate(CutActivity.this, R.layout.dialog_cut, null);
        dialog.setView(dialogView);


        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
                play();
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()){ videoView.pause();}
                else{
                    videoView.start();
                    play();
                }
           }
        });


        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeMiddle.setText(presenter.VideoShowTime(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                videoView.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                videoView.seekTo(seekBar.getProgress());
                videoView.start();
                play();
            }
        });


        seekBar2 = (SeekBar) findViewById(R.id.videoline2);
        seekBar3 = (SeekBar) findViewById(R.id.videoline3);
        VideoView videoView2 = (VideoView) findViewById(R.id.smallVideo1);
        VideoView videoView3 = (VideoView) findViewById(R.id.smallVideo2);
        TextView textView2 = (TextView) findViewById(R.id.timesmall1);
        TextView textView3 = (TextView) findViewById(R.id.timesmall2);

        seekBar2.setMax(Duration);
        seekBar3.setMax(Duration);
        seekBar2.setProgress(0);
        seekBar3.setProgress(Duration);

        textView2.setText(presenter.VideoShowTime(0));
        textView3.setText(presenter.VideoShowTime(Duration));

        videoView2.setVideoPath(getIntent().getStringExtra("IDV"));
        videoView3.setVideoPath(getIntent().getStringExtra("IDV"));
        videoView2.seekTo(1);
        videoView2.pause();
        videoView3.seekTo(Duration-1);
        videoView3.pause();
//        videoView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView3.start();
//            }
//        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView2.setText(presenter.VideoShowTime(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar2.getProgress();
                textView2.setText(presenter.VideoShowTime(progress));
                videoView2.seekTo(progress);
                videoView2.pause();
                if(progress>seekBar3.getProgress()){
                    seekBar3.setProgress(progress);
                    textView3.setText(presenter.VideoShowTime(progress));
                    videoView3.seekTo(progress);
                    videoView3.pause();
                }
            }
        });

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView3.setText(presenter.VideoShowTime(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar3.getProgress();
                textView3.setText(presenter.VideoShowTime(progress));
                videoView3.seekTo(progress);
                videoView3.pause();
                if(progress<seekBar2.getProgress()){
                    seekBar2.setProgress(progress);
                    textView2.setText(presenter.VideoShowTime(progress));
                    videoView2.seekTo(progress);
                    videoView2.pause();
                }
            }
        });
        Button cutButton = (Button) findViewById(R.id.buttonCutIt);
        cutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.show();
                play2();
            }
        });


        Button keepButton = (Button) findViewById(R.id.buttonForKeep);
        keepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.keepVideo(getIntent().getStringExtra("IDV"));
                Toast.makeText(CutActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
            }
        });

        Button musicButton = (Button) findViewById(R.id.buttonmusicin);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CutActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CutActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                    dialog.show();
                } else {
                    MusicInit();
                    dialog.show();
                }
//                dialog.show();
            }
        });



        EditText editText = (EditText) findViewById(R.id.textView2);
        Button inWord = (Button) findViewById(R.id.buttonwordin);
        inWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileHelper = new FileHelper(CutActivity.this);
                Filled(fileHelper);
                //ffmpeg -i test_1280x720_3.mkv -vf subtitles=test_1280x720_3.srt out.mp4
                String words = editText.getText().toString();
                fileHelper.save(presenter.VideoShowTime(seekBar2.getProgress()),presenter.VideoShowTime(seekBar3.getProgress()),words);
//                editText.setText(fileHelper.getPath());
//                File file = new File(fileHelper.getPath());
//                editText.setText(file.toString());
//                presenter.TextInit(getIntent().getStringExtra("IDV"),fileHelper.getPath());
                dialog.show();
                play4();
            }
        });


    }

    //for debug
    @Override
    public void showString(String x){
        deBug.setText(x);
    }

    private void play2(){
        Thread thread = new Thread(new cutThread());
        thread.start();
    }

    private void play(){

        Thread thread = new Thread(new showThread());
        thread.start();
    }

    private void play3(){
        Thread thread = new Thread(new BGMThread());
        thread.start();
    }

    private void play4(){
        Thread thread = new Thread(new TextThread());
        thread.start();
    }
    class BGMThread implements Runnable{
        @Override
        public void run(){
            videoView.pause();
            if (!audioWay.isEmpty()) presenter.addBGM(getIntent().getStringExtra("IDV"),audioWay,Duration);
            dialog.dismiss();
        }
    }

    class TextThread implements Runnable{
        @Override
        public void run(){
            videoView.pause();
            Log.d(TAG, "run:fileHelper: "+fileHelper.getPath());

            presenter.TextInit(getIntent().getStringExtra("IDV"),fileHelper.getPath());
            dialog.dismiss();
        }
    }


    class cutThread implements Runnable{
        @Override
        public void run(){
            videoView.pause();
            presenter.PutVideoTOCut(getIntent().getStringExtra("IDV"),seekBar2.getProgress(),seekBar3.getProgress()-seekBar2.getProgress());
//            dialog.dismiss();
        }
    }

    class showThread implements Runnable {

        @Override
        //实现run方法
        public void run() {
            //判断状态，在不暂停的情况下向总线程发出信息
            while (videoView.isPlaying()) {
                try {
                    // 每0.1秒更新一次位置
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //发出的信息

                seekBar1.setProgress(videoView.getCurrentPosition());
            }

        }
    }


    private void Filled(FileHelper fileHelper){
        fileHelper.save("[Script Info]");
        fileHelper.save("ScriptType: v4.00+");
        fileHelper.save("WrapStyle: 0");
        fileHelper.save("ScaledBorderAndShadow: yes");
        fileHelper.save("YCbCr Matrix: TV.601");
        fileHelper.save("PlayResX: 300");
        fileHelper.save("PlayResY: 186");
        fileHelper.save("");
        fileHelper.save("[V4+ Styles]");
        fileHelper.save("Format:Name,Fontsize,PrimaryCoulour,BorderStyle,Outline,Shadow,Alignment,MarginL,MarginR,MarginV");
        fileHelper.save("Style:sorry,30,&H008DF89F,1,1.2913,0.538046,2,0,0,1");
        fileHelper.save("");
        fileHelper.save("[Events]");
        fileHelper.save("Format:Layer,Start,End,Style,Text");
    }


    public String CreatePath()  {
        try {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            String picture_Name = simpleDateFormat.format(calendar.getTime());
            String framePath = getExternalFilesDir(null).getAbsolutePath()+"/Text";
            File frameFile = new File(framePath);
            if(!frameFile.exists()){
                frameFile.mkdirs();
            }
            File picture_file = new File(frameFile,picture_Name+".ass");
            FileOutputStream out = new FileOutputStream(picture_file);
            out.flush();
            out.close();
            return picture_file.getAbsolutePath();
        }catch (Exception e){
            e.printStackTrace();
            return "model.ass";
        }
    }







    private void MusicInit() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("audio/*");
        startActivityForResult(intent, MusicIn); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 4:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MusicInit();
                } else {
                    Toast.makeText(this, "Y拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MusicIn:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理视频
                        handleMusicOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理视频
                        handleMusicBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }


    @TargetApi(19)
    private void handleMusicOnKitKat(Intent data) {
        audioWay = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Audio.Media._ID + "=" + id;
                audioWay = getAudioPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                audioWay = getAudioPath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            audioWay = getAudioPath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            audioWay = uri.getPath();
        }
//        showString(audioWay);
        play3();
    }

    public void handleMusicBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        audioWay = getAudioPath(uri, null);
//        showString(audioWay);
        play3();
    }

    @SuppressLint("Range")
    public String getAudioPath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}