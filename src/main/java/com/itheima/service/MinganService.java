package com.itheima.service;

import com.itheima.entity.PageBean;
import com.itheima.entity.mingan;

import java.util.List;


public interface MinganService {
    PageBean<mingan> selectByPageAndCondition(int currentPage, int pageSize,mingan mingan);
    void deleteByids(int[] ids);
    void deleteByid(int id);
    void add(mingan mingan);
    List<mingan> selectAll();
}
