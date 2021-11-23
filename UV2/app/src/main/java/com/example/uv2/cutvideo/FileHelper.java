package com.example.uv2.cutvideo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class FileHelper {
    private static final String TAG = "FileHelper";
    private Context mContext;
    private String path;

    public FileHelper(Context mContext) {
        clear();
        this.mContext = mContext;
    }

    /**
     * 定义文件保存的方法，写入到文件中，所以是输出流
     */
    public void clear(){
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String picture_Name = simpleDateFormat.format(calendar.getTime());
        path = picture_Name+".ass";
    }


    public void save(String time1, String time2,String word1) {
        String content = "Dialogue: 0,"+time1+","+time2+",sorry,"+word1;
        FileOutputStream fos = null;
        try {
            // Context.MODE_PRIVATE私有权限，Context.MODE_APPEND追加写入到已有内容的后面
            fos = mContext.openFileOutput(getFileName(), Context.MODE_APPEND);
            fos.write(content.getBytes());
            fos.write("\r\n".getBytes());//写入换行
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void save(String x) {
        String content = x;
        FileOutputStream fos = null;
        try {
            // Context.MODE_PRIVATE私有权限，Context.MODE_APPEND追加写入到已有内容的后面
            fos = mContext.openFileOutput(getFileName(), Context.MODE_APPEND);
            fos.write(content.getBytes());
            fos.write("\r\n".getBytes());//写入换行
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 定义文件读取的方法
     */
    public String read() throws IOException {
        String filename = getFileName();
        FileInputStream fis = mContext.openFileInput(filename);
        byte[] buff = new byte[1024];
        StringBuilder sb = new StringBuilder("");
        int len = 0;
        while ((len = fis.read(buff)) > 0) {
            sb.append(new String(buff, 0, len));
        }
        fis.close();
        return sb.toString();
    }


    public String getPath(){
        Log.d(TAG, "getPath: mContext.getCacheDir()"+mContext.getCacheDir()+"/"+path);
        return mContext.getCacheDir()+"/"+path;
    }

    private String getFileName() throws IOException {
        return path;
    }
}