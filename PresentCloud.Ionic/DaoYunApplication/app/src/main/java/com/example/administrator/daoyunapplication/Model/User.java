package com.example.administrator.daoyunapplication.Model;

/**
 * Created by zhangyin on 2020/3/27 0027.
 */
//新建用户类
public class User {
//test
    private int userId;//用户id
    private String username ;//账号名称，就是学号或工号
    private String nick;//昵称
    private String college;//院系
    private String name;//姓名
    private String email;//邮箱
    private String  telephone;//电话号码
    private int role;//用户身份，0学生，1老师
    private String  password;//密码
    private int empiricalValue;//经验值，注册时不用

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEmpiricalValue() {
        return empiricalValue;
    }

    public void setEmpiricalValue(int empiricalValue) {
        this.empiricalValue = empiricalValue;
    }
}
