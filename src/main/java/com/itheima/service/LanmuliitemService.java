package com.itheima.service;

import com.itheima.entity.PageBean;
import com.itheima.entity.lanmuliitem;

import java.util.List;

public interface LanmuliitemService {
    List<lanmuliitem> selectByParentid(int parentid);
    PageBean<lanmuliitem> selectByPageAndCondition(int currentPage,int pageSize,lanmuliitem lanmuliitem);
    void update(lanmuliitem lanmuliitem);
    void deleteByid(int id);
    void deleteByids(int[] ids);
    void add(lanmuliitem lanmuliitem);
}
