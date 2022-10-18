package com.itheima.service;

import com.itheima.entity.PageBean;
import com.itheima.entity.news;

import java.util.List;

public interface NewsService {
    PageBean<news> selectByPageAndCondition(int currentPage, int pageSize, news news);
    void add(news news);
    void deleteByids(int[] ids);
    void deleteByid(int id);
    void update(news news);
    news selectByid(int id);
    List<news> topten();
    List<news> selectByids(int[] ids);
    List<news> nquanju(String keyword);
    List<news> topfive();
    List<news> selectAll();
}
