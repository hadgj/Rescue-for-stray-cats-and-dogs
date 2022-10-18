package com.itheima.service;

import com.itheima.entity.lanmu;

import java.util.List;

public interface LanmuService {
    //查询所有
    List<lanmu> selectAll();
    //修改
    void update(lanmu lanmu);
    //单条删除
    void deleteByid(int id);
    //批量删除
    void deleteByids(int[] ids);
    //新增
    void add(lanmu lanmu);
}
