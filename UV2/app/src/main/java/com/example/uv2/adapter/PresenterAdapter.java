package com.example.uv2.adapter;

import android.content.Context;

import com.example.uv2.MediaVideo;

import java.util.List;

public class PresenterAdapter implements IPresenterAdapter,IFinishAdapter{

    IModelAdapter iModelAdapter;
    IViewAdapter iViewAdapter;

    PresenterAdapter(IViewAdapter iViewAdapter){
        this.iViewAdapter = iViewAdapter;
        this.iModelAdapter = new ModelAdapter(this);
    }

    @Override
    public void AdapterInPresenter(Context context, List<MediaVideo> list){
        iModelAdapter.AdapterInModel(context,list);
    }

    @Override
    public void showVideos(Adapter.MyViewHolder myViewHolder, int position){
        iViewAdapter.setView(myViewHolder, iModelAdapter.getListItem(position));
    }

    @Override
    public void PlayVideos(int position){
        iModelAdapter.PlayVideo(iModelAdapter.getListItem(position));
    }

    @Override
    public void CreateDialog(MediaVideo mediaVideo){
        iViewAdapter.CustomDialog(mediaVideo);
    }

    @Override
    public void getPass(String pwd,MediaVideo mediaVideo){
        iModelAdapter.PwdCheck(pwd, mediaVideo.getPassword(), mediaVideo.getVideoId());
    }

    @Override
    public int getSize(){
        return iModelAdapter.getItemNumber();
    }
}
