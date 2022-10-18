package com.itheima.service;

import com.itheima.entity.PageBean;
import com.itheima.entity.ro;

import java.util.List;

public interface RoService {
    void add(ro ro);
    void update(ro ro);
    void deleteByid(int id);
    int youxiaojinggao(int bstatus,String btele);
    List<ro> jinggao(int bstatus,String btele);
    ro fengjin(int bstatus,String btele);
    PageBean<ro> selectByPageAndCondition(int currentPage, int pageSize,ro ro);
    void deleteByids(int[] ids);
}
