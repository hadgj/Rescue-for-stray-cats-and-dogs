package com.itheima.service;

import com.itheima.entity.PageBean;
import com.itheima.entity.Regular;

import java.util.List;

public interface RegularService {
    //根据页数和条件查询数据总数并且显示
    PageBean<Regular> selectByPageAndCondition(int currentPage, int pageSize, Regular Regular);
    void deleteByids(String[] ids);
    void deleteByid(String id);
    void update(Regular Regular);
    Regular selectByid(String id);
    List<Regular> selectAllByNum();
}
