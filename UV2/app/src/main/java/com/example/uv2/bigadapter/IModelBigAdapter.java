package com.example.uv2.bigadapter;

import android.content.Context;

import com.example.uv2.MediaVideo;

import java.util.List;

public interface IModelBigAdapter {

    void AdapterBigInModel(Context context, List<MediaVideo> list);

    int getItemNumber();

    MediaVideo getListItem(int position);

    void PwdCheck(String pwd, MediaVideo mediaVideo);
}