package com.example.uv2.largeadapter;

import com.example.uv2.MediaVideo;

public interface IViewLarge {
    void CustomDialog(MediaVideo mediaVideo, LargeAdapter.MyViewHolder holder);

    void showVideo(LargeAdapter.MyViewHolder holder, MediaVideo mediaVideo);
}
