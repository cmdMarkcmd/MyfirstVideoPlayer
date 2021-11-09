package com.example.uv2.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;


import com.example.uv2.MediaVideo;
import com.example.uv2.showvideo.ShowVideo;

import java.util.List;

public class ModelAdapter implements IModelAdapter{
    private Context context;
    private List<MediaVideo> list;
    final private IFinishAdapter listener;

    ModelAdapter(IFinishAdapter iFinishAdapter){
        listener = iFinishAdapter;
    }


    @Override
    public void AdapterInModel(Context context, List<MediaVideo> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemNumber(){
        return list.size();
    }

    @Override
    public MediaVideo getListItem(int position){
        return list.get(position);
    }

    @Override
    public void PlayVideo(MediaVideo mediaVideo){
        if (mediaVideo.isPwd()){
            listener.CreateDialog(mediaVideo);
        }else{
            Intent intent = new Intent(context, ShowVideo.class);
            intent.putExtra("ph",mediaVideo.getVideoId());
            intent.putExtra("data_extra",mediaVideo.getPassword());
            context.startActivity(intent);
        }
    }

    @Override
    public void PwdCheck(String pwd, String turePwd, String Id){
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(context, "密码不能为空!", Toast.LENGTH_SHORT).show();
        } else if (turePwd.equals(pwd)) {
            Toast.makeText(context, "解锁成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ShowVideo.class);
            intent.putExtra("data_extra",turePwd);
            intent.putExtra("ph",Id);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "密码不正确", Toast.LENGTH_SHORT).show();
        }
    }
}
