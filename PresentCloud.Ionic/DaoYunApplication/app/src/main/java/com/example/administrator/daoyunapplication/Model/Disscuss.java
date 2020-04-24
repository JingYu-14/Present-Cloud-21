package com.example.administrator.daoyunapplication.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/4/11 0011.
 */

public class Disscuss implements Serializable {
    private int questionId;//任务id
    private int userId;//用户id发布的任务
    private int gradeId; //班级号就是classId
    private String  disscussName;//任务名称
    private String  questionName;//题目名称，即题目
    private String dstartTime ;//任务开始时间
    private String dendTime ;//任务开始时间
    public Disscuss() {}
    public Disscuss( int questionId,String  disscussName,String  questionName,String dstartTime,String  dendTime){
        this.questionId=questionId;
        this.disscussName=disscussName;
        this.questionName=questionName;
        this.dstartTime=dstartTime;
        this.dendTime=dendTime;
    }

    public String getDisscussName() {
        return disscussName;
    }

    public void setDisscussName(String disscussName) {
        this.disscussName = disscussName;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getDstartTime() {
        return dstartTime;
    }

    public void setDstartTime(String dstartTime) {
        this.dstartTime = dstartTime;
    }

    public String getDendTime() {
        return dendTime;
    }

    public void setDendTime(String dendTime) {
        this.dendTime = dendTime;
    }
}
