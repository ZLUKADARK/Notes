package com.example.notes.adapter;

import java.io.Serializable;

public class ListItem implements Serializable {
    private String title;
    private String desc;
    private String imguri;
    private int id = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImguri() {
        return imguri;
    }

    public void setImguri(String imguri) {
        this.imguri = imguri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
