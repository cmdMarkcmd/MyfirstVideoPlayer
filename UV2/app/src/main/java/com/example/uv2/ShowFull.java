package com.example.uv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.List;

public class ShowFull extends AppCompatActivity {

    private RecyclerView recyclerView;//声明RecyclerView
    private Adapter_Big adapter;//声明适配器
    private Context context;
    private List<MediaVideo> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full);
        list = LitePal.findAll(MediaVideo.class);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.Big);
        adapter = new Adapter_Big(context,list);
        LinearLayoutManager manager1 = new LinearLayoutManager(context);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager1);
        recyclerView.setAdapter(adapter);


    }
}