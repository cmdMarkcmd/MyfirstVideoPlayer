package com.example.uv2.bigadapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


import com.example.uv2.MediaVideo;

import java.util.List;

public class ModelBigAdapter implements IModelBigAdapter{
    private Context context;
    private List<MediaVideo> list;
    final private IFinishBigAdapter listener;

    ModelBigAdapter(IFinishBigAdapter iFinishAdapter){
        listener = iFinishAdapter;
    }


    @Override
    public void AdapterBigInModel(Context context, List<MediaVideo> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemNumber(){
        return list.size();
    }

    @Override
    public MediaVideo getListItem(int position){
        return list.get(position);
    }

    @Override
    public void PwdCheck(String pwd, MediaVideo mediaVideo){
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(context, "密码不能为空!", Toast.LENGTH_SHORT).show();
        }else{
            if (!mediaVideo.isPwd()){
                mediaVideo.setPwd(true);
                mediaVideo.setPassword(pwd);
                mediaVideo.save();
                Toast.makeText(context, "加密成功", Toast.LENGTH_SHORT).show();
            }else {
                if (mediaVideo.getPassword().equals(pwd)){
                    mediaVideo.setPwd(false);
                    mediaVideo.setPassword("((((~null!@#$%w#je*wf$y5#@feg))))");
                    mediaVideo.save();
                    Toast.makeText(context, "解锁成功", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(context, "密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
