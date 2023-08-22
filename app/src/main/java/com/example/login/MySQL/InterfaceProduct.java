package com.example.login.MySQL;

import com.example.login.TableInfo.ProductInfo;

import java.util.List;

public interface InterfaceProduct {
    int insertProductInfo(ProductInfo productInfo);

    int deleteProductInfo(int imageId);

    //    这个用于搜索
    List<ProductInfo> searchProductInfo(String title);

    //    这个用于展示个人的商品,比如已发布,收藏
    List<ProductInfo> searchProductInfo(int stuNum);

    List<ProductInfo> searchAllProductInfo();

    //    到时候修改的activity或者fragment可以把发布界面复制过去
    int updateProductInfo(ProductInfo productInfo, int imageId, int stuNum);

}
