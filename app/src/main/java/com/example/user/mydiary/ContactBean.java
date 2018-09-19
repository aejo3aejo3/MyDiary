package com.example.user.mydiary;

import java.sql.Date;

/**
 * Created by user on 2018/1/21.
 */

public class ContactBean {

    public long id;
    public String name;
    public String phone;
    public String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
