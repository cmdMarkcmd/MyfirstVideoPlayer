package com.example.uv2.showvideo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.uv2.MediaVideo;
import com.example.uv2.R;
import com.example.uv2.largeadapter.LargeAdapter;

import org.litepal.LitePal;

import java.util.List;

public class ShowVideo extends AppCompatActivity {
    private RecyclerView recyclerView;//声明RecyclerView
    private LargeAdapter adapter;//声明适配器
    private Context context;
    private List<MediaVideo> list;
    LinearLayoutManager manager;

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
        adapter = new LargeAdapter(context,list);
        manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
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