package com.example.login.MySQL;

import com.example.login.TableInfo.StudentInfo;

public interface InterfaceAccount {
    void insertData(StudentInfo studentInfo);

    void deleteByStuNum(String stuNum);

    void updatePswByStuNumAndPhone(StudentInfo studentInfo);

    StudentInfo searchByStuNum(int stuNum);

    int[] searchAllStuNum();
}
