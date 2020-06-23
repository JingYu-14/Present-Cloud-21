package com.example.administrator.daoyunapplication.Model;

import java.io.Serializable;

/**
 * Created by 666666 on 2020/6/23.
 */

public class StudentSign implements Serializable {
    private int signId;
    private String code;
    private String sName;
    private int classId;
    private String time;
    private int sno;
    private boolean state;
    private String account;
    private String date;
    public StudentSign(int sid,String c,String s,int cid,String t,int so,boolean sta,String acoun,String da){
        signId=sid;
        code=c;
        sName=s;
        classId=cid;
        time=t;
        sno=so;
        state=sta;
        account=acoun;
        date=da;
    }
    public String getTime(){return time;}
    public String getDate(){
        return date;
    }
    public boolean getState(){
        return state;
    }
}
