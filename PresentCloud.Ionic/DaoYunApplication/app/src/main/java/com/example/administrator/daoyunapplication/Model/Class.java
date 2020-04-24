package com.example.administrator.daoyunapplication.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/3/12 0012.
 */

public class Class implements Serializable {
    public String newsIconURl;
    public String newsCourseName;
    public String newsTeacherName;
    public String newsClassName;
    public int newsClassId;

    public int getNewsClassId() {
        return newsClassId;
    }

    public void setNewsClassId(int newsClassId) {
        this.newsClassId = newsClassId;
    }

    public String getNewsClassName() {
        return newsClassName;
    }

    public void setNewsClassName(String newsClassName) {
        this.newsClassName = newsClassName;
    }

    public String getNewsTeacherName() {
        return newsTeacherName;
    }

    public void setNewsTeacherName(String newsTeacherName) {
        this.newsTeacherName = newsTeacherName;
    }

    public String getNewsCourseName() {
        return newsCourseName;
    }

    public void setNewsCourseName(String newsCourseName) {
        this.newsCourseName = newsCourseName;
    }

    public String getNewsIconURl() {
        return newsIconURl;
    }

    public void setNewsIconURl(String newsIconURl) {
        this.newsIconURl = newsIconURl;
    }


    public Class(int newsClassId ,String newsIconURl, String newsCourseName, String newsTeacherName, String newsClassName){
        this.newsIconURl=newsIconURl;
        this.newsCourseName=newsCourseName;
        this.newsTeacherName=newsTeacherName;
        this.newsClassName=newsClassName;
        this.newsClassId=newsClassId;

    }
//    private String mName;
//
//    public Food(String name){
//        mName = name;
//    }
//
//    public String getName() {
//        return mName;
//    }
//
//    public void setName(String name) {
//        mName = name;
//    }

}


