package com.itheima.service;

import com.itheima.entity.zan;

import java.util.List;

public interface ZanService {
    zan selectByid(String rtele,int nid,int status);
    void add(zan zan);
    void deleteByid(String rtele,int nid,int status);
    List<zan> shoucang(String tele,int status);
}
