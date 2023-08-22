package com.example.login.MySQL;

import com.example.login.ProductInfo;

import java.util.List;

public interface InterfaceProduct {
    int insertProductInfo(ProductInfo productInfo);

    int deleteProductInfoByImageId(int imageId);

    //    这个用于搜索
    ProductInfo searchProductInfo(String title);

    //    这个用于展示个人的商品,比如已发布,收藏
    List<ProductInfo> searchProductInfo(int stuNum);

    List<ProductInfo> searchAllProductInfo();

    int updateProductInfoByImageId(ProductInfo productInfo, int imageId);

}
