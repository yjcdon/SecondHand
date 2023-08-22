package com.example.login;

/*
这是保存输入数据的类
 */

public class StudentInfo {
    private String  password, phone;
    private int stuNum;

    @Override
    public String toString() {
        return "studentInfo{" +
                "stuNum='" + stuNum + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public int getStuNum() {
        return stuNum;
    }

    public void setStuNum(int stuNum) {
        this.stuNum = stuNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

