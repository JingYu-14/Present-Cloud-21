package com.example.administrator.daoyunapplication.Model;

/**
 * Created by Administrator on 2020/6/6 0006.
 */

public class Courses {

    private int id;//课程名id
    private String cname="";//课程名
    public Courses(int id , String cname){
        this.id=id;
        this.cname=cname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
