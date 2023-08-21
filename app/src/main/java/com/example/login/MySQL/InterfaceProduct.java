package com.example.login.MySQL;

import com.example.login.ProductInfo;

public interface InterfaceProduct {

    int insertProduct(ProductInfo productInfo);

    int deleteProductByImageId(int imageId);

    ProductInfo searchProductByTitle(String title);

    int updateProductByImageId(ProductInfo productInfo,int imageId);

}
