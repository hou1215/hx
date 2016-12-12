package com.youmi.tt.entity;

import com.youmi.tt.base.BaseModel;

/**
 * Created by hx on 2016/11/23.
 */
public class TestModel extends BaseModel {

    private String title;
    private int id;
    private String cover_image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", cover_image='" + cover_image + '\'' +
                '}';
    }
}
