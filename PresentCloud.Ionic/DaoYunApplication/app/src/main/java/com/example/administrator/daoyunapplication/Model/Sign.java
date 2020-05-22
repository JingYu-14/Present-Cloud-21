package com.example.administrator.daoyunapplication.Model;

import java.io.Serializable;
import java.util.Arrays;

public class Sign implements Serializable  {
    private int signId;//签到记录的id
//    private String code;//签到码
    private String sname;//签到的学生姓名
    private int classId;//签到班级的id
    private String time;//签到的时间
    private int sno;//学生的id
    private boolean statu;//是否签到，true为签到，false为未签到
    private String account;//账号身份
    private String date;//签到日期
    public Sign(){

    }
    public Sign(int sId,String name,int cId,String t, int sn, boolean sta,String acc,String da){
        this.signId=sId;
        this.sname=name;
        this.classId=cId;
        this.time=t;
        this.sno=sn;
        this.statu=sta;
        this.account=acc;
        this.date=da;
    }
    public int getClassId(){
        return classId;
    }
    public String getTime(){
        return time;
    }
    public String getDate(){
        return date;
    }
    public boolean getState(){
        return statu;
    }
    public String getSname(){
        return sname;
    }
    public int getSno(){
        return sno;
    }
    public int caculate(Sign s[],int k,SignResult sr[]){
        int num=0;
        for(int i=0;i<k;i++){
            if(i==0){//第一个不用处理
                int snum;
                int total=1;
                if(s[0].getState()){
                    snum=1;
                }else{
                    snum=0;
                }
                sr[0]=new SignResult(s[0].getTime(),s[0].getDate(),snum,total);
                num++;
            }else{
                int flag=1;
                int stemp=0;
                if(s[i].getState()){
                    stemp=1;
                }
                for(int j=0;j<num;j++){
                    if((s[i].getTime().equals(sr[j].getTime()))&&(s[i].getDate().equals(sr[j].getDate()))){
                        flag=0;
                        sr[j].add(stemp);
                        break;
                    }
                }
                if(flag==1){
                    //新增一条记录
                    sr[num]=new SignResult(s[i].getTime(),s[i].getDate(),stemp,1);
                    num++;
                }
            }
        }
        sr = Arrays.copyOfRange(sr,0,num);
        return num;
    }
}
