package com.example.uv2;
import android.graphics.Bitmap;

import org.litepal.crud.LitePalSupport;
public class MediaPicture extends LitePalSupport{
    private String name;
    private String path;


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
