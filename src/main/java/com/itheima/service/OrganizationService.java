package com.itheima.service;

import com.itheima.entity.PageBean;
import com.itheima.entity.organization;

public interface OrganizationService {
    //根据页数和条件查询数据总数并且显示
    PageBean<organization> selectByPageAndCondition(int currentPage, int pageSize, organization organization);
    void deleteByids(String[] ids);
    void deleteByid(String id);
    void update(int status,String tele);
    void upjichu(organization organization);
    organization selectByid(String id);
}
















