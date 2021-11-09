package com.example.uv2.largeadapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.uv2.MediaVideo;
import com.example.uv2.tagvideo.TagVideo;

import java.util.List;

public class ModelLarge implements IModelLarge{
    private Context context;
    private static long lastClickTime = 0;
    private List<MediaVideo> list;

    @Override
    public void setInformation(Context context, List<MediaVideo> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public MediaVideo getItem(int position){
        return list.get(position);
    }

    @Override
    public int getSize(){
        return list.size();
    }

    @Override
    public void showLike1(LargeAdapter.MyViewHolder holder,MediaVideo mediaVideo){
        holder.flyHeartView.startFly(1350f,2100f);
        holder.flyHeartView.startFly(1350f,2100f);
        holder.flyHeartView.startFly(1350f,2100f);
        mediaVideo.setLike(mediaVideo.getLike()+1);
        mediaVideo.save();
        holder.likeButton.setText("赞："+String.valueOf(mediaVideo.getLike()));
    }

    @Override
    public void showLike2(LargeAdapter.MyViewHolder holder, MediaVideo mediaVideo,float x,float y){
        long currentT = SystemClock.uptimeMillis();
        if(currentT - lastClickTime < 300) {
            holder.flyHeartView.startFly(x,y);
            holder.flyHeartView.startFly(x,y);
            holder.flyHeartView.startFly(x,y);
            mediaVideo.setLike(mediaVideo.getLike()+1);
            mediaVideo.save();
            holder.likeButton.setText("赞："+String.valueOf(mediaVideo.getLike()));
        }else if(!holder.videoView.isPlaying()){
            holder.videoView.start();
        }
        lastClickTime = currentT;
    }

    @Override
    public void goToTag(MediaVideo mediaVideo){
        Intent intent = new Intent(context , TagVideo.class);
        intent.putExtra("extra_data2",mediaVideo.getTag());
        context.startActivity(intent);
    }

    @Override
    public void DialogOkay(MediaVideo mediaVideo, String name, String sts, String tag){
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(context, "视频名不能为空!", Toast.LENGTH_SHORT).show();
            mediaVideo.setName("<未命名>");
        }else{
            mediaVideo.setName(name);
        }
        if(!TextUtils.isEmpty(sts)) mediaVideo.setSentence(sts);
        if(!TextUtils.isEmpty(tag)) mediaVideo.setTag(tag);
        mediaVideo.save();
    }
}
