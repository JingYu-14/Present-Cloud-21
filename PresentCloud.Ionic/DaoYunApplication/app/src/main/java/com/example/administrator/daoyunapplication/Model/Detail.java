package com.example.administrator.daoyunapplication.Model;

import java.io.Serializable;

public class Detail implements Serializable {
    public String title;
    public String content;

    public Detail(String title, String content) {
        this.title=title;
        this.content=content;
    }

    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
}
