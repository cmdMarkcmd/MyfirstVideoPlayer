package com.example.uv2.largeadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uv2.FlyHeartView;
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
public class LargeAdapter extends RecyclerView.Adapter<LargeAdapter.MyViewHolder> implements IViewLarge{

    private Context context;
    private static long lastClickTime = 0;
    private View inflater;
    private IPresenterLarge presenterLarge;
    //构造方法，传入数据
    public LargeAdapter(Context context, List<MediaVideo> list){
        presenterLarge = new PresenterLarge(this);
        presenterLarge.setInfo(context,list);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.activity_show_video,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void CustomDialog(MediaVideo mediaVideo, MyViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(context, R.layout.alert_dialog, null);
        dialog.setView(dialogView);
        dialog.show();
        final EditText et_name = dialogView.findViewById(R.id.et_name);
        et_name.setText(mediaVideo.getName());
        final EditText et_sts = dialogView.findViewById(R.id.et_sts);
        et_sts.setText(mediaVideo.getSentence());
        final EditText et_tag = dialogView.findViewById(R.id.et_tag);
        et_tag.setText(mediaVideo.getTag());
        final Button btn_okay = dialogView.findViewById(R.id.btn_okay);
        final Button btn_cancel = dialogView.findViewById(R.id.btn_cancel);

        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name =et_name.getText().toString();
                final String sts = et_sts.getText().toString();
                final String tag = et_tag.getText().toString();
                presenterLarge.DialogOkay(mediaVideo,name,sts,tag);
                showVideo(holder,mediaVideo);
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
    public void showVideo(MyViewHolder holder, MediaVideo mediaVideo){
        holder.nameButton.setText(mediaVideo.getName());
        holder.tagButton.setText("标签："+mediaVideo.getTag());
        holder.textButton.setText("简介："+mediaVideo.getSentence());
        holder.likeButton.setText("赞："+String.valueOf(mediaVideo.getLike()));
        holder.videoView.setVideoPath(mediaVideo.getPath());
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        //将数据和控件绑定
        presenterLarge.VideoShow(holder,position);
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterLarge.showLike(holder,position);
            }
        });

        //设置进度条
        MediaController mediaController = new MediaController(context);
        holder.videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(holder.videoView);
        holder.videoView.start();


        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenterLarge.showLike(holder,position,event.getX(),event.getY());
                }
            });
                return false;
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
                presenterLarge.deleteVideo(position);
                Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterLarge.comeDialog(holder,position);
            }
        });

        holder.tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterLarge.goTo(position);
            }
        });
        holder.itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                mediaController.hide();
                holder.videoView.start();
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                mediaController.hide();
                holder.videoView.pause();
            }
        });

    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return presenterLarge.getSize();
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
