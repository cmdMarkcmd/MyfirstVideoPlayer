package com.example.uv2.setting;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.uv2.MediaVideo;
import com.example.uv2.R;

public class SettingActivity extends AppCompatActivity implements IViewSettingActivity{

    private IPresenterSetting presenter;

    private ImageView pic2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        presenter = new SettingPresenter(this);

        Button chooseFromAlbum = (Button) findViewById(R.id.button4);
        Button takePhoto = (Button) findViewById(R.id.button5);
        Button chooseVideo = (Button) findViewById(R.id.videoAdd);
        Button pwdButton = (Button) findViewById(R.id.buttonpwd);
        pic2 = (ImageView) findViewById(R.id.imageView2);

        presenter.setHeadShot();


        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.takePicture();
            }
        });

        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openAlbum();
            }

        });

        chooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {presenter.initVideoPath(); }
        });

        pwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {presenter.setPwd();}
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        presenter.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        presenter.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void setTakePhoto(Bitmap bitmap){
        pic2.setImageBitmap(bitmap);
    }


    @Override
    public void customDialog(MediaVideo mediaVideo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(this, R.layout.alert_dialog, null);
        dialog.setView(dialogView);
        dialog.show();

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
                presenter.Dialog_okay(mediaVideo,name,sts,tag);
                dialog.dismiss();
                Toast.makeText(SettingActivity.this, "视频载入成功，请返回主页面查看", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}