package com.itheima.entity;

public class chat {
    private Integer id;
    private Integer bstatus;
    private String btele;
    private Integer cstatus;
    private String ctele;
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBstatus() {
        return bstatus;
    }

    public void setBstatus(Integer bstatus) {
        this.bstatus = bstatus;
    }

    public String getBtele() {
        return btele;
    }

    public void setBtele(String btele) {
        this.btele = btele;
    }

    public Integer getCstatus() {
        return cstatus;
    }

    public void setCstatus(Integer cstatus) {
        this.cstatus = cstatus;
    }

    public String getCtele() {
        return ctele;
    }

    public void setCtele(String ctele) {
        this.ctele = ctele;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
