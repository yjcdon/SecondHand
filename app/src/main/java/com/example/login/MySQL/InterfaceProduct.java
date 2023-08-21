package com.example.login.MySQL;

import com.example.login.ProductInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface InterfaceProduct {

    int insertProduct(ProductInfo productInfo);

    int deleteProductByImageId(int imageId);

    ProductInfo searchProductByImageId(int imageId);

    int updateProductByImageId(ProductInfo productInfo,int imageId);

}
