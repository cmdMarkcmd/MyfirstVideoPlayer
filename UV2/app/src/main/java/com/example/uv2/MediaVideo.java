package com.example.uv2;
import android.graphics.Bitmap;

import org.litepal.crud.LitePalSupport;
public class MediaVideo extends LitePalSupport{
    private String name;
    private String tag;
    private String sentence;
    private String path;
    private String VideoId;
    private String password;
    private int like;
    private boolean pwd;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


    public void setPwd(boolean pwd) {
        this.pwd = pwd;
    }

    public boolean isPwd() {
        return pwd;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public String getVideoId() {
        return VideoId;
    }


    public void setLike(int like) {
        this.like = like;
    }

    public int getLike() {
        return like;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSentence() {
        return sentence;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
