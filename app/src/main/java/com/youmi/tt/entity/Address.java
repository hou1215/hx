package com.youmi.tt.entity;

import com.youmi.tt.base.BaseModel;

/**
 * Created by hx on 2016/11/25.
 */
public class Address extends BaseModel {

    private String name;
    private String phone;
    private String address;
    private String room;
    private int gendle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getGendle() {
        return gendle;
    }

    public void setGendle(int gendle) {
        this.gendle = gendle;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", room='" + room + '\'' +
                ", gendle=" + gendle +
                '}';
    }
}
