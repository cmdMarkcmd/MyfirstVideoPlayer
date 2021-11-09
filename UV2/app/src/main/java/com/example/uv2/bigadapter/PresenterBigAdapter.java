package com.example.uv2.bigadapter;

import android.content.Context;

import com.example.uv2.MediaVideo;

import java.util.List;

public class PresenterBigAdapter implements IPresenterBigAdapter,IFinishBigAdapter{

    IModelBigAdapter iModelAdapter;
    IViewBigAdapter iViewAdapter;

    public PresenterBigAdapter(IViewBigAdapter iViewAdapter){
        this.iViewAdapter = iViewAdapter;
        this.iModelAdapter = new ModelBigAdapter(this);
    }

    @Override
    public void AdapterInPresenter(Context context, List<MediaVideo> list){
        iModelAdapter.AdapterBigInModel(context,list);
    }

    @Override
    public void showVideos(BigAdapter.MyViewHolder myViewHolder, int position){
        iViewAdapter.setView(myViewHolder, iModelAdapter.getListItem(position));
    }


    @Override
    public void getPass(BigAdapter.MyViewHolder myViewHolder, String pwd, int position){
        iModelAdapter.PwdCheck(pwd, iModelAdapter.getListItem(position));
        iViewAdapter.setView(myViewHolder, iModelAdapter.getListItem(position));
    }

    @Override
    public int getSize(){
        return iModelAdapter.getItemNumber();
    }
}
