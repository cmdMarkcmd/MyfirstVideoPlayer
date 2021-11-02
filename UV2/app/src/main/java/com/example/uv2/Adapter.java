package com.example.uv2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.litepal.LitePal;

import java.util.List;

/*
① 创建一个继承RecyclerView.Adapter<VH>的Adapter类
② 创建一个继承RecyclerView.ViewHolder的静态内部类
③ 在Adapter中实现3个方法：
   onCreateViewHolder()
   onBindViewHolder()
   getItemCount()
*/
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private Context context;
    private List<MediaVideo> list;
    private View inflater;
    //构造方法，传入数据
    public Adapter(Context context, List<MediaVideo> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.item_dome,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);

        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //将数据和控件绑定
        String path =list.get(position).getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(list.get(position).getVideoId());
        holder.imageView.setImageBitmap(bitmap);
        holder.textView.setText(list.get(position).getName());
        holder.textView2.setText(list.get(position).getSentence());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaVideo mediaVideo = list.get(position);
                if (mediaVideo.isPwd()){
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
                            if (TextUtils.isEmpty(pwd)) {
                                Toast.makeText(context, "密码不能为空!", Toast.LENGTH_SHORT).show();
                            } else if (mediaVideo.getPassword().equals(pwd)) {
                                Toast.makeText(context, "解锁成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, ShowVideo.class);
                                intent.putExtra("posi", position);
                                intent.putExtra("data_extra",mediaVideo.getPassword());
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "密码不正确", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }else{
                    Intent intent = new Intent(context, ShowVideo.class);
//                    List<MediaVideo> mv = LitePal.where("password == ?",mediaVideo.getPassword()).find(MediaVideo.class);
//                    Toast.makeText(context, String.valueOf(position_T), Toast.LENGTH_SHORT).show();
//                    int position_T = mv.indexOf(mediaVideo);
                    intent.putExtra("ph",mediaVideo.getVideoId());
                    intent.putExtra("data_extra",mediaVideo.getPassword());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return list.size();
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
