package com.example.uv2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uv2.MediaVideo;
import com.example.uv2.R;

import java.util.List;

/*
① 创建一个继承RecyclerView.Adapter<VH>的Adapter类
② 创建一个继承RecyclerView.ViewHolder的静态内部类
③ 在Adapter中实现3个方法：
   onCreateViewHolder()
   onBindViewHolder()
   getItemCount()
*/
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements IViewAdapter{

    final private Context context;
    private View inflater;
    private IPresenterAdapter presenterAdapter;
    //构造方法，传入数据
    public Adapter(Context context, List<MediaVideo> list){
        presenterAdapter = new PresenterAdapter(this);
        presenterAdapter.AdapterInPresenter(context,list);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.item_dome, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void setView(MyViewHolder holder, MediaVideo mediaVideo){
        Bitmap bitmap = BitmapFactory.decodeFile(mediaVideo.getVideoId());
        holder.imageView.setImageBitmap(bitmap);
        holder.textView.setText(mediaVideo.getName());
        holder.textView2.setText(mediaVideo.getSentence());
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //将数据和控件绑定
        presenterAdapter.showVideos(holder,position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterAdapter.PlayVideos(position);
            }
        });
    }

    @Override
    public void CustomDialog(MediaVideo mediaVideo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(context, R.layout.dialog_pwd, null);
        dialog.setView(dialogView);
        dialog.show();
        final EditText ed_pwd = dialogView.findViewById(R.id.et_pwd);
        final Button btn_okay = dialogView.findViewById(R.id.btn_okay2);
        final Button btn_cancel = dialogView.findViewById(R.id.btn_cancel2);
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pwd = ed_pwd.getText().toString();
                presenterAdapter.getPass(pwd,mediaVideo);
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



    @Override
    public int getItemCount() {
        //返回Item总条数
        return presenterAdapter.getSize();
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView0);
            textView = (TextView) itemView.findViewById(R.id.titleView);
            textView2 = (TextView) itemView.findViewById(R.id.longView);

        }
    }
}
