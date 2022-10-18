package com.itheima.service;

import com.itheima.entity.PageBean;
import com.itheima.entity.apply;

import java.util.List;

public interface ApplyService {
    //根据页数和条件查询数据总数并且显示
    PageBean<apply> selectByPageAndCondition(int currentPage, int pageSize, apply apply);
    //根据领养信息，同时改变宠物的领养状态
    void update(String petIsApply,int apstate,int pid);
    void deleteByids(int[] ids);
    void deleteByid(int id);
    void add(apply apply);
    List<apply> selectByRtele(String rtele);
    void updateapply(apply apply);
}
