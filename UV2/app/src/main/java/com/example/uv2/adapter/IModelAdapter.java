package com.example.uv2.adapter;

import android.content.Context;

import com.example.uv2.MediaVideo;

import java.util.List;

public interface IModelAdapter {
    void AdapterInModel(Context context, List<MediaVideo> list);

    int getItemNumber();

    MediaVideo getListItem(int position);

    void PlayVideo(MediaVideo mediaVideo);

    void PwdCheck(String pwd, String turePwd, String Id);
}
