package com.example.login.MySQL;

import com.example.login.StudentInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Account implements InterfaceAccount {
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

    @Override
    public void insertData(StudentInfo studentInfo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String insert = "INSERT INTO account (stuNum, phone, password) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(insert);
            pstmt.setInt(1, studentInfo.getStuNum());
            pstmt.setString(2, studentInfo.getPhone());
            pstmt.setString(3, studentInfo.getPassword());
            pstmt.executeUpdate();

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
    }

    @Override
    public void deleteByStuNum(String stuNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String delete = "DELETE FROM account WHERE stuNum = ?";
            pstmt = conn.prepareStatement(delete);
            pstmt.setString(1, stuNum);
            pstmt.executeUpdate();


        } catch (SQLException e) {
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
    }

    @Override
    public void updatePswByStuNumAndPhone(StudentInfo studentInfo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String update = "UPDATE account SET password = ? WHERE stuNum = ? and phone = ?";
            pstmt = conn.prepareStatement(update);
            pstmt.setString(1, studentInfo.getPassword());
            pstmt.setInt(2, studentInfo.getStuNum());
            pstmt.setString(3, studentInfo.getPhone());
            pstmt.executeUpdate();


        } catch (SQLException e) {
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
    }

    @Override
    public StudentInfo searchByStuNum(int stuNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StudentInfo studentInfo = null;

        try {
            conn = getConnection();
            String query = "SELECT * FROM account WHERE stuNum = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, stuNum);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                studentInfo = new StudentInfo();
                studentInfo.setStuNum(rs.getInt("stuNum"));
                studentInfo.setPassword(rs.getString("password"));
                studentInfo.setPhone(rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return studentInfo;
    }

    @Override
    public int[] searchAllStuNum() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int[] stuNumsArray = new int[0];

        try {
            conn = getConnection();
            String query = "SELECT stuNum FROM account";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            // 获取结果集的行数
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            // 创建相应大小的 int[] 数组
            stuNumsArray = new int[rowCount];

            for (int i = 0; rs.next(); i++) {
                int stuNum = rs.getInt("stuNum");
                stuNumsArray[i] = stuNum;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return stuNumsArray;
    }

}
