package com.example.login.TableInfo;

import java.math.BigDecimal;
import java.lang.String;
import java.util.Arrays;

public class ProductInfo {
    private String title, content, publishTime;
    private byte[] image;
    private int isCollect, stuNum, imageId;
    private BigDecimal price;

    @Override
    public String toString() {
        return "ProductInfo{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", image=" + Arrays.toString(image) +
                ", imageId=" + imageId +
                ", isCollect=" + isCollect +
                ", stuNum=" + stuNum +
                ", price=" + price +
                '}';
    }

    public int getStuNum() {
        return stuNum;
    }

    public void setStuNum(int stuNum) {
        this.stuNum = stuNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    // 构造方法

    // Getter 和 Setter 方法
}
