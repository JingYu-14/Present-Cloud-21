package com.example.administrator.daoyunapplication.Model;

import java.io.Serializable;

public class SignResult implements Serializable {
    private String time;//签到的时间
    private String date;//签到的日期
    private int signNum;//签到的人数
    private int totalNum;//总人数
    public SignResult(){

    }
    public SignResult(String t, String d, int snum, int tonum){
        this.time=t;
        this.date=d;
        this.signNum=snum;
        this.totalNum=tonum;
    }
    public String getTime(){
        return time;
    }
    public String getDate(){
        return date;
    }
    public int getSignNum(){
        return signNum;
    }
    public int getTotalNum(){
        return totalNum;
    }
    public void add(int s){
        if(s==1){
            signNum++;
        }
        totalNum++;
    }
}
