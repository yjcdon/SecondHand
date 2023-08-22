package com.example.login.MySQL;

import com.example.login.ProductInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product implements InterfaceProduct {
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://rm-cn-zsk3ck77g001h8uo.rwlb.cn-chengdu.rds.aliyuncs.com:3306/user";
    private final static String USERNAME = "test_account";
    private final static String PASSWORD = "AnZhuo666@";
    private String TABLENAME = "product";

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private static void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public int insertProductInfo(ProductInfo productInfo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String insert = "INSERT INTO " + TABLENAME + " (title,content,price,image,publishTime,isCollect,stuNum) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insert);
            pstmt.setString(1, productInfo.getTitle());
            pstmt.setString(2, productInfo.getContent());
            pstmt.setBigDecimal(3, productInfo.getPrice());
            pstmt.setBytes(4, productInfo.getImage());
            pstmt.setString(5, productInfo.getPublishTime());
//            因为imageId为主键且自动递增,所以在插入时不需要设定初值
            pstmt.setInt(6, productInfo.getIsCollect());
            pstmt.setInt(7, productInfo.getStuNum());
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

    public int deleteProductInfoByImageId(int imageId) {
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

    @Override
    public int updateProductInfoByImageId(ProductInfo productInfo, int imageId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String update = "update " + TABLENAME +
                    " set title=?,image=?,content=?,publishTime=?,isCollect=?,price=? where imageId=?";
            pstmt = conn.prepareStatement(update);
            pstmt.setString(1, productInfo.getTitle());
            pstmt.setBytes(2, productInfo.getImage());
            pstmt.setString(3, productInfo.getContent());
            pstmt.setString(4, productInfo.getPublishTime());
            pstmt.setInt(5, productInfo.getIsCollect());
            pstmt.setBigDecimal(6, productInfo.getPrice());
            pstmt.setInt(7, imageId);

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

    public ProductInfo searchProductInfo(String title) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        ProductInfo productInfo = null;
        try {
            conn = getConnection();
            String search = "select * from " + TABLENAME + " where title like ?";
            pstmt = conn.prepareStatement(search);
            pstmt.setString(1, "%" + title + "%");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                productInfo = new ProductInfo();
                productInfo.setTitle(rs.getString("title"));
                productInfo.setImage(rs.getBytes("image"));
                productInfo.setContent(rs.getString("content"));
                productInfo.setPublishTime(rs.getString("publishTime"));
                productInfo.setImageId(rs.getInt("imageId"));
                productInfo.setIsCollect(rs.getInt("isCollect"));
                productInfo.setPrice(rs.getBigDecimal("price"));
                productInfo.setStuNum(rs.getInt("stuNum"));
            }
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
        return productInfo;
    }

    @Override
    public List<ProductInfo> searchProductInfo(int stuNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List<ProductInfo> productList = new ArrayList<>();
        try {
            conn = getConnection();
            String search = "select * from " + TABLENAME + " where stuNum = ?";
            pstmt = conn.prepareStatement(search);
            pstmt.setInt(1, stuNum);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductInfo productInfo = new ProductInfo();
                productInfo.setTitle(rs.getString("title"));
                productInfo.setImage(rs.getBytes("image"));
                productInfo.setContent(rs.getString("content"));
                productInfo.setPublishTime(rs.getString("publishTime"));
                productInfo.setImageId(rs.getInt("imageId"));
                productInfo.setIsCollect(rs.getInt("isCollect"));
                productInfo.setPrice(rs.getBigDecimal("price"));
                productInfo.setStuNum(rs.getInt("stuNum"));

                productList.add(productInfo);
            }
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
        return productList;
    }


    public List<ProductInfo> searchAllProductInfo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List<ProductInfo> productList = new ArrayList<>();
        try {
            conn = getConnection();
            String search = "select * from " + TABLENAME;
            pstmt = conn.prepareStatement(search);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductInfo productInfo = new ProductInfo();
                productInfo.setTitle(rs.getString("title"));
                productInfo.setImage(rs.getBytes("image"));
                productInfo.setContent(rs.getString("content"));
                productInfo.setPublishTime(rs.getString("publishTime"));
                productInfo.setImageId(rs.getInt("imageId"));
                productInfo.setIsCollect(rs.getInt("isCollect"));
                productInfo.setPrice(rs.getBigDecimal("price"));
                productInfo.setStuNum(rs.getInt("stuNum"));

                productList.add(productInfo);
            }
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
        return productList;
    }

}
