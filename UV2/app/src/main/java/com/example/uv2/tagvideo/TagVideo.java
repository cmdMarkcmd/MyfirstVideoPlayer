package com.example.uv2.tagvideo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.uv2.adapter.Adapter;
import com.example.uv2.MediaVideo;
import com.example.uv2.R;

import org.litepal.LitePal;

import java.util.List;

public class TagVideo extends AppCompatActivity {
    private RecyclerView recyclerView;//声明RecyclerView
    private Adapter adapter;//声明适配器
    private Context context;
    String tagToShow ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_video);

        TextView textView = (TextView) findViewById(R.id.Tag1);

        Intent intent =getIntent();
        tagToShow = intent.getStringExtra("extra_data2");
        textView.setText(tagToShow);
        List<MediaVideo> list = LitePal.where("tag == ?",tagToShow).find(MediaVideo.class);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        adapter = new Adapter(context,list);
        StaggeredGridLayoutManager manager= new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }
}