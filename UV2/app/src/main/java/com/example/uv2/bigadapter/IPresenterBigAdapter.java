package com.example.uv2.bigadapter;

import android.content.Context;

import com.example.uv2.MediaVideo;

import java.util.List;

public interface IPresenterBigAdapter {
    void AdapterInPresenter(Context context, List<MediaVideo> list);

    void showVideos(BigAdapter.MyViewHolder myViewHolder, int position);

    void getPass(BigAdapter.MyViewHolder myViewHolder, String pwd, int position);

    int getSize();
}
