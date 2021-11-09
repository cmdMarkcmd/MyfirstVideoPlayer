package com.example.uv2.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uv2.MediaPicture;
import com.example.uv2.MediaVideo;
import com.example.uv2.R;
import com.example.uv2.RoundImage;
import com.example.uv2.adapter.Adapter;
import com.example.uv2.mainactivity.IViewMain;
import com.example.uv2.setting.SettingActivity;

import org.litepal.LitePal;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IViewMain {

    private RecyclerView recyclerView;//声明RecyclerView
    private Adapter adapter;//声明适配器
    private Context context;
    private List<MediaVideo> list;
    private IPresenterMain presenter;
    private RoundImage picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*=================litepal数据库=====================*/
        LitePal.initialize(this);
        //获取到SQLiteDatabase的实例，创建数据库表
        SQLiteDatabase db = LitePal.getDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new PresenterMain(this);
        if(!presenter.askUI()){
            Toast.makeText(MainActivity.this,"无可直接播放视频",Toast.LENGTH_SHORT).show();
        }
        presenter.getUI();


//        context = this;
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        adapter = new Adapter(context,list);
//
//        与ListView效果对应的可以通过LinearLayoutManager来设置
//        与GridView效果对应的可以通过GridLayoutManager来设置
//        与瀑布流对应的可以通过StaggeredGridLayoutManager来设置
//
//        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
//        LinearLayoutManager manager = new LinearLayoutManager(context);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
//        GridLayoutManager manager1 = new GridLayoutManager(context,2);
//        manager1.setOrientation(GridLayoutManager.VERTICAL);
//        StaggeredGridLayoutManager manager= new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(adapter);
        Button set_in = (Button) findViewById(R.id.button);
        set_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        presenter.getUI();
        super.onResume();
    }

}


