package com.example.administrator.daoyunapplication.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/3/12 0012.
 */

public class Message implements Serializable {
    private int messageType;//消息类型，0班课通知，1小蓝消息
    private String messageName ;//消息名称
    private String content;//消息简介
    public Message() {}
    public Message( int messageType,String messageName, String content){
        this.messageType=messageType;
        this.messageName=messageName;
        this.content=content;
    }
    public String getMessageName(){
        return messageName;
    }
    public int getMessageType(){
        return messageType;
    }
    public String getContent(){
        return content;
    }

}


