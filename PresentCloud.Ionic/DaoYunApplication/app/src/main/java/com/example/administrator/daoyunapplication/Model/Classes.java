package com.example.administrator.daoyunapplication.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/3/12 0012.
 */

public class Classes implements Serializable {
    public String newsIconURl;//图标，不用
    public String newsCourseName="";//课程名,这个不用了
    public String newsTeacherName="";//老师名
    public String newsClassName="";//班级名
    public int newsClassId;//班级id

    public int tno;//老师id
    public String invitationCode="";//班级邀请码
    public String taskId;//任务id集
    public String task="";//任务名

    public Classes(int newsClassId , String newsTeacherName,
                   String newsClassName, int tno, String invitationCode,
                   String taskId, String task){
//        this.newsIconURl=newsIconURl;
        //  this.newsCourseName=newsCourseName;
        this.newsTeacherName=newsTeacherName;
        this.newsClassName=newsClassName;
        this.newsClassId=newsClassId;
        this.tno=tno;
        this.invitationCode=invitationCode;
        this.taskId=taskId;
        this.task=task;

    }
    public int getTno() {
        return tno;
    }

    public void setTno(int tno) {
        this.tno = tno;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

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




}


