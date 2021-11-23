package com.example.uv2.cutvideo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uv2.FFmpegCmd;
import com.example.uv2.MediaVideo;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class PresenterCut implements IPresenterCut{
    IViewCut view;
    IModelCut model;
    AppCompatActivity appCompatActivity;
    private String OriginPath = "/storage/emulated/0/DCIM/";

    public PresenterCut(IViewCut iViewCut){
        view = iViewCut;
        model = new ModelCut();
        appCompatActivity = (AppCompatActivity) iViewCut;
    }

    @Override
    public String VideoShowTime(int timeLength){
        int ss,mm,hh;
        hh=(timeLength)/1000/60/60;
        mm=(timeLength)/1000/60-hh*60;
        ss=(timeLength)/1000-hh*3600-mm*60;
        return IntToString(hh)+":"+IntToString(mm)+":"+IntToString(ss);
    }

    String IntToString(int value){
        if (value>=10) return String.valueOf(value);
        else return "0"+String.valueOf(value);
    }

    @Override
    public void addBGM(String pathV, String pathA, int Length){
        //ffmpeg -i video.mp4 -stream_loop -1 -i audio.wav -filter_complex [0:a][1:a]amix -t 60 -y out.mp4

        //ffmpeg -i songs.mp3 -i hi.mp4  -t 7.1 -y new1.mp4

//        ArrayList<String> cmd = new ArrayList<>();
//        cmd.add("ffmpeg");
//        cmd.add("-i");
//        cmd.add(pathA);
//        cmd.add("-i");
//        cmd.add(pathV);
//        cmd.add("-t");
//        cmd.add(String.valueOf(Length/1000));
//        cmd.add("-y");


        ArrayList<String> cmd = new ArrayList<>();
        cmd.add("ffmpeg");
        cmd.add("-i");
        cmd.add(pathV);
        cmd.add("-stream_loop");
        cmd.add("-1");
        cmd.add("-i");
        cmd.add(pathA);
        cmd.add("-filter_complex");
        cmd.add("[0:a][1:a]amix");
        cmd.add("-t");
        cmd.add(String.valueOf((double) Length/1000));
        cmd.add("-y");
        pathA=CreatePath();
        cmd.add(pathA);
        FFmpegCmd.run(cmd.toArray(new String[0]));
        leave(pathA);
    }

    @Override
    public void TextInit(String path, String ass){
//        ffmpeg -i 1.mp4 -i model.ass -c copy -c:s mov_text outfile.mp4
//        ass=OriginPath+"model.ass";
        ArrayList<String> cmd = new ArrayList<>();
        cmd.add("ffmpeg");
        cmd.add("-i");
        cmd.add(path);
        cmd.add("-i");
        cmd.add(ass);
        cmd.add("-c");
        cmd.add("copy");
        cmd.add("-c:s");
        cmd.add("mov_text");
        path=CreatePath();
        cmd.add(path);
        //FFmpegCmd.run(cmd.toArray(new String[0]));
        FFmpegCmd.test(ass);
        leave(path);
    }



    @Override
    public void PutVideoTOCut(String path, int firstTime, int lastTime){
        //ffmpeg -ss 0:1:30 -t 0:0:20 -i input.avi -vcodec copy -acodec copy output.avi
        //fmpeg -i input -c:v libx264 -preset slow -crf 22-c:a copy output.mkv
        //ffmpeg -ss {0} -to {1} -accurate_seek -i {2} -c:v libx264  -avoid_negative_ts 1  -y {3}
        //ffmpeg -ss 00:00:00 -i 1.mp4 -vcodec libx264 00:00:20 -y 2.mp4
        //ffmpeg -ss 0 -t 100 -i 1.mp4  -c:v libx264 -c:a aac -strict experimental -b:a 98k 2.mp4
        ArrayList<String> cmd = new ArrayList<>();
        cmd.add("ffmpeg");
        cmd.add("-ss");
        cmd.add(VideoShowTime(firstTime));
        cmd.add("-t");
        cmd.add(VideoShowTime(lastTime));
        cmd.add("-i");
        cmd.add(path);
        cmd.add("-c:v");
        cmd.add("libx264");
        cmd.add("-c:a");
        cmd.add("acc");
        cmd.add("-strict");
        cmd.add("experimental");
        cmd.add("-b:a");
        cmd.add("98k");
        path=CreatePath();
        cmd.add(path);
        FFmpegCmd.run(cmd.toArray(new String[0]));
        leave(path);
    }


    @Override
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
        mediaVideo.setName("<剪辑视频>");
        mediaVideo.setSentence("无");
        mediaVideo.setTag("视频");
        mediaVideo.setPassword("((((~null!@#$%w#je*wf$y5#@feg))))");
        mediaVideo.setSentence("目录："+path);
        mediaVideo.save();
    }

    private void leave(String path){
        Intent intent = new Intent(appCompatActivity,CutActivity.class);
        intent.putExtra("IDV",path);
        appCompatActivity.startActivity(intent);
        appCompatActivity.finish();
    }



    public String CreatePath()  {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String picture_Name = simpleDateFormat.format(calendar.getTime());
        return OriginPath+picture_Name+".mp4";
    }


    public String CreatePath(Bitmap bitmap)  {
        try {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            String picture_Name = simpleDateFormat.format(calendar.getTime());
            String framePath = appCompatActivity.getExternalFilesDir(null).getAbsolutePath()+"/Picture";
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
}