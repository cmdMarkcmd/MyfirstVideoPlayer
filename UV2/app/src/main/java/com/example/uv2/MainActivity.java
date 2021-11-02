package com.example.uv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;
import org.litepal.tablemanager.callback.DatabaseListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;//声明RecyclerView
    private Adapter adapter;//声明适配器
    private Context context;
    private List<MediaVideo> list;

    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*=================litepal数据库=====================*/
        LitePal.initialize(this);
        //获取到SQLiteDatabase的实例，创建数据库表
        SQLiteDatabase db = LitePal.getDatabase();

        list = LitePal.findAll(MediaVideo.class);
        if(list.isEmpty()){
            Toast.makeText(MainActivity.this,"无可直接播放视频",Toast.LENGTH_SHORT).show();
        }
//        add list

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new Adapter(context,list);
        /*
        与ListView效果对应的可以通过LinearLayoutManager来设置
        与GridView效果对应的可以通过GridLayoutManager来设置
        与瀑布流对应的可以通过StaggeredGridLayoutManager来设置
        */
        //LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
//        LinearLayoutManager manager = new LinearLayoutManager(context);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
//        GridLayoutManager manager1 = new GridLayoutManager(context,2);
//        manager1.setOrientation(GridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager manager= new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

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
        picture = (ImageView) findViewById(R.id.pic_1);
        List<MediaPicture> mediaPictures= LitePal.findAll(MediaPicture.class);
        if(!mediaPictures.isEmpty()){
            String path = LitePal.findLast(MediaPicture.class).getPath();
//            Toast.makeText(MainActivity.this,path,Toast.LENGTH_SHORT).show();
            Bitmap bitmap =BitmapFactory.decodeFile(path);
            picture.setImageBitmap(bitmap);
        }

        list = LitePal.findAll(MediaVideo.class);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new Adapter(context,list);
        StaggeredGridLayoutManager manager= new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager manager = new LinearLayoutManager(context);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        super.onResume();
    }

}


