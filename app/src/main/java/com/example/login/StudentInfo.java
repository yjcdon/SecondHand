package com.example.login;

/*
这是保存输入数据的类
 */

public class StudentInfo {
    private String stuNum, password, phone;

    @Override
    public String toString() {
        return "studentInfo{" +
                "stuNum='" + stuNum + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
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

