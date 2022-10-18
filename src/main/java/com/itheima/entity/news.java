package com.itheima.entity;

import java.math.BigDecimal;

public class news {
    private Integer newsID;
    private String newsTitle;
    private String newsContent;
    private String newsDate;
    private String newsImagePath;
    private Integer newsIsTop;
    private Integer newsZan;
    private BigDecimal newsRedu;

    public BigDecimal getNewsRedu() {
        return newsRedu;
    }

    public void setNewsRedu(BigDecimal newsRedu) {
        this.newsRedu = newsRedu;
    }

    public Integer getNewsID() {
        return newsID;
    }

    public void setNewsID(Integer newsID) {
        this.newsID = newsID;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsImagePath() {
        return newsImagePath;
    }

    public void setNewsImagePath(String newsImagePath) {
        this.newsImagePath = newsImagePath;
    }

    public Integer getNewsIsTop() {
        return newsIsTop;
    }

    public void setNewsIsTop(Integer newsIsTop) {
        this.newsIsTop = newsIsTop;
    }

    public Integer getNewsZan() {
        return newsZan;
    }

    public void setNewsZan(Integer newsZan) {
        this.newsZan = newsZan;
    }
}
