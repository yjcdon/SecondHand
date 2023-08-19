package com.example.login;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;

public class ProductInfo {
    private String title, content;
    private Blob image;
    private LocalDateTime publishTime;
    private int imageId, isCollect;
    private BigDecimal price;

    @Override
    public String toString() {
        return "ProductInfo{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image=" + image +
                ", publishTime=" + publishTime +
                ", imageId=" + imageId +
                ", isCollect=" + isCollect +
                ", price=" + price +
                '}';
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

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
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
