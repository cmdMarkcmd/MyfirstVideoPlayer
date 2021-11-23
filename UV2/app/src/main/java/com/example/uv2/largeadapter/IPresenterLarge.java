package com.example.uv2.largeadapter;

import android.content.Context;

import com.example.uv2.MediaVideo;

import java.util.List;

public interface IPresenterLarge {
    void setInfo(Context context, List<MediaVideo> list);

    void VideoShow(LargeAdapter.MyViewHolder holder, int position);

    void cutVideo(int position);

    void showLike(LargeAdapter.MyViewHolder holder, int position);

    void showLike(LargeAdapter.MyViewHolder holder, int position, float x, float y);

    void deleteVideo(int position);

    void goTo(int position);

    void comeDialog(LargeAdapter.MyViewHolder holder, int position);

    void DialogOkay(MediaVideo mediaVideo, String name, String sts, String tag);

    int getSize();
}
