package com.example.uv2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
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
public class Adapter_Biggest extends RecyclerView.Adapter<Adapter_Biggest.MyViewHolder>{

    private Context context;
    private static long lastClickTime = 0;
    private List<MediaVideo> list;
    private View inflater;
    //构造方法，传入数据
    public Adapter_Biggest(Context context, List<MediaVideo> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.activity_show_video,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);

        return myViewHolder;
    }

    public void CustomDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(context, R.layout.alert_dialog, null);
        dialog.setView(dialogView);
        dialog.show();

        MediaVideo mediaVideo = LitePal.findAll(MediaVideo.class).get(position);
        final EditText et_name = dialogView.findViewById(R.id.et_name);
        final EditText et_sts = dialogView.findViewById(R.id.et_sts);
        final EditText et_tag = dialogView.findViewById(R.id.et_tag);
        final Button btn_okay = dialogView.findViewById(R.id.btn_okay);
        final Button btn_cancel = dialogView.findViewById(R.id.btn_cancel);


        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name =et_name.getText().toString();
                final String sts = et_sts.getText().toString();
                final String tag = et_tag.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(context, "视频名不能为空!", Toast.LENGTH_SHORT).show();
                    mediaVideo.setName("  <未命名>");
                }else{
                    mediaVideo.setName("  "+name);
                }
                if(!TextUtils.isEmpty(sts)) mediaVideo.setSentence("简介："+sts);
                if(!TextUtils.isEmpty(tag)) mediaVideo.setTag("标签："+tag);
                mediaVideo.save();
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //将数据和控件绑定
        position = holder.getAdapterPosition();
        MediaVideo mediaVideo = list.get(position);
        holder.nameButton.setText(mediaVideo.getName());
        holder.tagButton.setText(mediaVideo.getTag());
        holder.textButton.setText(mediaVideo.getSentence());
        holder.likeButton.setText("赞："+String.valueOf(mediaVideo.getLike()));
        holder.videoView.setVideoPath(mediaVideo.getPath());
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.flyHeartView.startFly();
                holder.flyHeartView.startFly();
                holder.flyHeartView.startFly();
                mediaVideo.setLike(mediaVideo.getLike()+1);
                mediaVideo.save();
                holder.likeButton.setText("赞："+String.valueOf(mediaVideo.getLike()));
            }
        });


        MediaController mediaController = new MediaController(context);
        holder.videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(holder.videoView);
        mediaController.setPadding(0,0,0,0);
        holder.videoView.start();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentT = SystemClock.uptimeMillis();
                if(currentT - lastClickTime < 300) {
                    holder. flyHeartView.startFly();
                    holder.flyHeartView.startFly();
                    holder.flyHeartView.startFly();
                    mediaVideo.setLike(mediaVideo.getLike()+1);
                    mediaVideo.save();
                    holder.likeButton.setText("赞："+String.valueOf(mediaVideo.getLike()));
                }else if(!holder.videoView.isPlaying()){
                    holder.videoView.start();
                }
                lastClickTime = currentT;
            }
        });


        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaVideo.delete();
                Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog(holder.getAdapterPosition());
                holder.nameButton.setText(mediaVideo.getName());
                holder.textButton.setText(mediaVideo.getSentence());
                holder.tagButton.setText(mediaVideo.getTag());
            }
        });
        holder.tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context ,TagVideo.class);
                intent1.putExtra("extra_data2",mediaVideo.getTag());
                context.startActivity(intent1);

            }
        });
        holder.itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                holder.videoView.start();
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                holder.videoView.pause();
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
        TextView nameButton;
        TextView textButton ;
        TextView likeButton ;
        TextView tagButton ;
        VideoView videoView ;
        Button deleteButton ;
        Button editButton ;
        FlyHeartView flyHeartView;
        public MyViewHolder(View itemView) {
            super(itemView);
            nameButton = (TextView) itemView.findViewById(R.id.TextViewToShow);
            textButton = (TextView) itemView.findViewById(R.id.TitleViewToShow);
            likeButton = (TextView) itemView.findViewById(R.id.likeText);
            tagButton = (TextView) itemView.findViewById(R.id.TagButton);
            videoView = (VideoView) itemView.findViewById(R.id.VideoViewToShow);
            deleteButton = (Button) itemView.findViewById(R.id.DeleteButton);
            editButton = (Button) itemView.findViewById(R.id.EditButton);
            flyHeartView = (FlyHeartView) itemView.findViewById(R.id.Fly);
        }
    }

}
