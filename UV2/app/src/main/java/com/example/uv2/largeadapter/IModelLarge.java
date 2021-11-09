package com.example.uv2.largeadapter;

import android.content.Context;

import com.example.uv2.MediaVideo;

import java.util.List;

public interface IModelLarge {
    void setInformation(Context context, List<MediaVideo> list);

    MediaVideo getItem(int position);

    int getSize();

    void showLike1(LargeAdapter.MyViewHolder holder, MediaVideo mediaVideo);

    void showLike2(LargeAdapter.MyViewHolder holder, MediaVideo mediaVideo,float x,float y);

    void goToTag(MediaVideo mediaVideo);

    void DialogOkay(MediaVideo mediaVideo, String name, String sts, String tag);
}
