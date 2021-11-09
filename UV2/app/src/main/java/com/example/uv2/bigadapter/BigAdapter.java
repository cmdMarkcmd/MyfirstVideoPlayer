package com.example.uv2.bigadapter;
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
public class BigAdapter extends RecyclerView.Adapter<BigAdapter.MyViewHolder> implements IViewBigAdapter {

    private Context context;
    private View inflater;
    private IPresenterBigAdapter presenterAdapter;
    //构造方法，传入数据
    public BigAdapter(Context context, List<MediaVideo> list){
        presenterAdapter = new PresenterBigAdapter(this);
        presenterAdapter.AdapterInPresenter(context,list);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.item_pwd,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }


    @Override
    public void setView(BigAdapter.MyViewHolder holder, MediaVideo mediaVideo){
        Bitmap bitmap = BitmapFactory.decodeFile(mediaVideo.getVideoId());
        holder.imageView.setImageBitmap(bitmap);
        if(mediaVideo.isPwd()) {holder.textView.setText(mediaVideo.getName()+":"+"已加密，点击图片解锁");}
        else {holder.textView.setText(mediaVideo.getName()+":"+"未加密，点击图片加密");}
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return presenterAdapter.getSize();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //将数据和控件绑定
        presenterAdapter.showVideos(holder,position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        presenterAdapter.getPass(holder,pwd,position);
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
        });
    }


    //内部类，绑定控件
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewA);
            textView = (TextView) itemView.findViewById(R.id.yesView);

        }
    }
}