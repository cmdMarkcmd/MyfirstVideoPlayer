package com.example.uv2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.litepal.LitePal;

import java.util.List;

public class ShowVideo extends AppCompatActivity {
    private RecyclerView recyclerView;//声明RecyclerView
    private Adapter_Biggest adapter;//声明适配器
    private Context context;
    private List<MediaVideo> list;
    LinearLayoutManager manager;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biggest);
        Intent intent = getIntent();
        String password_T = intent.getStringExtra("data_extra");
        String position_T = intent.getStringExtra("ph");
        if (password_T.equals("((((~null!@#$%w#je*wf$y5#@feg))))")){
            list = LitePal.where("password == ?",password_T).find(MediaVideo.class);
        }else{
            list = LitePal.where("VideoId == ?",position_T).find(MediaVideo.class);
        }
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.bigRecyclerView);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        adapter = new Adapter_Biggest(context,list);
        /*
        与ListView效果对应的可以通过LinearLayoutManager来设置
        与GridView效果对应的可以通过GridLayoutManager来设置
        与瀑布流对应的可以通过StaggeredGridLayoutManager来设置
        */
        manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
//        LinearLayoutManager manager = new LinearLayoutManager(context);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
//        GridLayoutManager manager1 = new GridLayoutManager(context,2);
//        manager1.setOrientation(GridLayoutManager.VERTICAL);
//        StaggeredGridLayoutManager manager= new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        if (password_T.equals("((((~null!@#$%w#je*wf$y5#@feg))))")){
            int index =0;
            for (int i = 0 ;i < list.size(); i ++ ){
                if(list.get(i).getVideoId().equals(position_T)){
                    index = i ;
                    break;
                }
            }
            MoveToPosition(manager,index);
        }

    }
    public static void MoveToPosition(LinearLayoutManager manager,int n){
        manager.scrollToPositionWithOffset(n,0);
        manager.setStackFromEnd(true);
    }

}