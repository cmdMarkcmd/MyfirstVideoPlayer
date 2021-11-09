package com.example.uv2.adapter;

import android.content.Context;

import com.example.uv2.MediaVideo;

import java.util.List;

public interface IPresenterAdapter {
    void AdapterInPresenter(Context context, List<MediaVideo> list);

    void showVideos(Adapter.MyViewHolder myViewHolder, int position);

    void PlayVideos(int position);

    void getPass(String pwd, MediaVideo mediaVideo);

    int getSize();
}
