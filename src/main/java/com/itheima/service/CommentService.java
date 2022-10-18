package com.itheima.service;

import com.itheima.entity.PageBean;
import com.itheima.entity.comment;

import java.util.List;

public interface CommentService {
    //多选框删除评论
    void deleteByids(int[] ids);
    //分页查询评论
    PageBean<comment> selectByPageAndCondition(int currentPage, int pageSize, comment comment);
    //按照id删除一条评论记录
    void deleteByid(int id);
    void add(comment comment);
    List<comment> selectAllByNid(int nid);
}
