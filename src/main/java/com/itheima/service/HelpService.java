package com.itheima.service;

import com.itheima.entity.PageBean;
import com.itheima.entity.help;

import java.util.List;


public interface HelpService {
    //根据页数和条件查询数据总数并且显示
    PageBean<help> selectByPageAndCondition(int currentPage, int pageSize, help help);
    void deleteByids(int[] ids);
    void deleteByid(int id);
    help selectByid(int id);
    void add(help help);
    void update(help help);
    //根据选中数据批量查询
    List<help> selectByids(int[] ids);
    List<help> selectAllByRid(String rid);
    help youxian(String rid);
    List<help> hquanju(String keyword);
}
