package com.example.administrator.daoyunapplication.Model;

/**
 * Created by Administrator on 2020/3/12 0012.
 */

public class Food {
    public String newsIconURl;
    public String newsCourseName;
    public String newsTeacherName;

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

    public String newsClassName;
    public Food(String newsIconURl,String newsCourseName,String newsTeacherName,String newsClassName){
        this.newsIconURl=newsIconURl;
        this.newsCourseName=newsCourseName;
        this.newsTeacherName=newsTeacherName;
        this.newsClassName=newsClassName;

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


