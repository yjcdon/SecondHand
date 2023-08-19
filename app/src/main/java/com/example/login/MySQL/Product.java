package com.example.login.MySQL;

import com.example.login.InterfaceProduct;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Product implements InterfaceProduct {
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://rm-cn-zsk3ck77g001h8uo.rwlb.cn-chengdu.rds.aliyuncs.com:3306/user";
    private final static String USERNAME = "test_account";
    private final static String PASSWORD = "AnZhuo666@";

    // 建立数据库连接
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // 关闭数据库连接
    private static void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }


}
