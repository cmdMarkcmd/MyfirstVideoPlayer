package com.example.uv2.largeadapter;

import android.content.Context;

import com.example.uv2.MediaVideo;

import java.util.List;

public class PresenterLarge implements IPresenterLarge,IFinishLarge{
    IModelLarge model;
    IViewLarge view;

    PresenterLarge(IViewLarge iViewLarge){
        view = iViewLarge;
        model = new ModelLarge();
    }

    @Override
    public void setInfo(Context context, List<MediaVideo> list){
        model.setInformation(context,list);
    }

    @Override
    public void VideoShow(LargeAdapter.MyViewHolder holder, int position){
        view.showVideo(holder, model.getItem(position));
    }

    @Override
    public void showLike(LargeAdapter.MyViewHolder holder, int position){
        model.showLike1(holder, model.getItem(position));
    }

    @Override
    public void showLike(LargeAdapter.MyViewHolder holder, int position, float x, float y){
        model.showLike2(holder, model.getItem(position),x,y);
    }

    @Override
    public void deleteVideo(int position){
        model.getItem(position).delete();
    }

    @Override
    public void goTo(int position){
        model.goToTag(model.getItem(position));
    }

    @Override
    public void comeDialog(LargeAdapter.MyViewHolder holder, int position){
        view.CustomDialog(model.getItem(position),holder);
    }

    @Override
    public void DialogOkay(MediaVideo mediaVideo,String name,String sts,String tag){
        model.DialogOkay(mediaVideo,name,sts,tag);
    }

    @Override
    public int getSize(){
        return model.getSize();
    }
}