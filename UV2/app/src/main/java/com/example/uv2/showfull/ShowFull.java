package com.example.uv2.showfull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;

import org.litepal.LitePal;

import com.example.uv2.MediaVideo;
import com.example.uv2.R;
import com.example.uv2.bigadapter.BigAdapter;

import java.util.List;

public class ShowFull extends AppCompatActivity {

    private RecyclerView recyclerView;//声明RecyclerView
    private BigAdapter adapter;//声明适配器
    private Context context;
    private List<MediaVideo> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full);
        list = LitePal.findAll(MediaVideo.class);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.Big);
        adapter = new BigAdapter(context,list);
        LinearLayoutManager manager1 = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager1);
        recyclerView.setAdapter(adapter);

    }
}