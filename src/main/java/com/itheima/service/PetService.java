package com.itheima.service;

import com.itheima.entity.PageBean;
import com.itheima.entity.pet;

import java.util.List;

public interface PetService {
    List<pet> selectAll();
    void add(pet pet);
    void deleteByids(int[] ids);
    PageBean<pet> selectByPage(int currentPage,int pageSize);
    PageBean<pet> selectByPageAndCondition(int currentPage,int pageSize,pet pet);
    void deleteByid(int id);
    void update(pet pet);
    pet selectByid(int id);
    int selectnewid();
    List<pet> selectByotele(String otele);
    List<pet> pquanju(String keyword);
    List<pet> petfour();
}
