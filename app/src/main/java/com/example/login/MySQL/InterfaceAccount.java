package com.example.login.MySQL;

import com.example.login.StudentInfo;

public interface InterfaceAccount {
    void insertData(StudentInfo studentInfo);

    void deleteByStuNum(String stuNum);

    void updatePswByStuNumAndPhone(StudentInfo studentInfo);

    StudentInfo searchByStuNum(String stuNum);
}
