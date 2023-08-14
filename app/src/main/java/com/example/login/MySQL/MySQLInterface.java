package com.example.login.MySQL;

import com.example.login.Student;

public interface MySQLInterface {
    void insertData(Student student);

    void deleteByStuNum(String stuNum);

    void updatePswByStuNumAndPhone(Student student);

    Student searchByStuNum(String stuNum);
}
