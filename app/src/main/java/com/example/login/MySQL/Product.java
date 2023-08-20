package com.example.login.MySQL;

import com.example.login.ProductInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Product implements InterfaceProduct {
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://rm-cn-zsk3ck77g001h8uo.rwlb.cn-chengdu.rds.aliyuncs.com:3306/user";
    private final static String USERNAME = "test_account";
    private final static String PASSWORD = "AnZhuo666@";
    private String TABLENAME = "product";

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

    public int insertProduct(ProductInfo productInfo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String insert = "INSERT INTO " + TABLENAME + " (title,content,price,image,publishTime,isCollect) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insert);
            pstmt.setString(1, productInfo.getTitle());
            pstmt.setString(2, productInfo.getContent());
            pstmt.setBigDecimal(3, productInfo.getPrice());
            pstmt.setBytes(4, productInfo.getImage());
            pstmt.setString(5, productInfo.getPublishTime());
//            因为imageId为主键且自动递增,所以在插入时不需要设定初值
            pstmt.setInt(6, productInfo.getIsCollect());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            // 捕获数据库异常
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public int deleteProductByImageId(int imageId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String delete = "DELETE FROM " + TABLENAME + " WHERE imageId = ?";
            pstmt = conn.prepareStatement(delete);
            pstmt.setInt(1, imageId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            // 捕获数据库异常
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}
