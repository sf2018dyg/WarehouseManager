package com.soonfor.warehousemanager.home.setting;

import com.soonfor.warehousemanager.R;

import java.io.Serializable;

/**
 * Created by DingYG on 2017/4/27.
 */

public class Mp3Info implements Serializable {
    private long id;
    private String title;
    private String artist;//艺术家
    private long duration;//时长
    private long size;//文件大小
    private String url;//文件路径
    private String fileName;

    public Mp3Info() {
    }

    public Mp3Info(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title == null ? "未知" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist == null ? "未知" : artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getFileName() {
        fileName = "";
        if (url != null && !url.equals("")) {
            if (url.contains("/")) {
                fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
                if(fileName.contains(".")){
                    fileName = fileName.substring(0,fileName.indexOf("."));
                }
            } else {
                fileName = url;
            }
        }
        return fileName.trim();
    }
}
