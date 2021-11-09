package com.example.uv2.adapter;

import com.example.uv2.MediaVideo;

public interface IViewAdapter {
    void setView(Adapter.MyViewHolder holder, MediaVideo mediaVideo);

    void CustomDialog(MediaVideo mediaVideo);
}
