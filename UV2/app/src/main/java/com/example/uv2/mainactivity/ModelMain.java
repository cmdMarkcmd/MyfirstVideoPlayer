package com.example.uv2.mainactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.uv2.MediaPicture;
import com.example.uv2.MediaVideo;
import com.example.uv2.R;
import com.example.uv2.RoundImage;
import com.example.uv2.adapter.Adapter;

import org.litepal.LitePal;

import java.util.List;

public class ModelMain implements IModelMain{

    private RecyclerView recyclerView;//声明RecyclerView
    private Adapter adapter;//声明适配器
    private Context context;
    private AppCompatActivity appCompatActivity;
    private List<MediaVideo> list;
    private RoundImage picture;

    ModelMain(Context context){
        this.context = context;
        appCompatActivity = (AppCompatActivity) context;
    }

    @Override
    public boolean haveVideo(){
        list = LitePal.findAll(MediaVideo.class);
        return (!list.isEmpty());
    }

    @Override
    public void Resume(){
        picture = (RoundImage) appCompatActivity.findViewById(R.id.pic_1);
        List<MediaPicture> mediaPictures= LitePal.findAll(MediaPicture.class);
        if(!mediaPictures.isEmpty()){
            String path = LitePal.findLast(MediaPicture.class).getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            picture.setImageBitmap(bitmap);
        }

        list = LitePal.findAll(MediaVideo.class);
        recyclerView = (RecyclerView) appCompatActivity.findViewById(R.id.recyclerView);
        adapter = new Adapter(context,list);
        StaggeredGridLayoutManager manager= new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
