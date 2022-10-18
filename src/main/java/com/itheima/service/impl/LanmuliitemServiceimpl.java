package com.itheima.service.impl;

import com.itheima.entity.PageBean;
import com.itheima.entity.lanmuliitem;
import com.itheima.mapper.LanmuliitemMapper;
import com.itheima.service.LanmuliitemService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class LanmuliitemServiceimpl implements LanmuliitemService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public List<lanmuliitem> selectByParentid(int parentid) {
        SqlSession sqlSession=factory.openSession();
        LanmuliitemMapper mapper=sqlSession.getMapper(LanmuliitemMapper.class);
        List<lanmuliitem> lanmuliitems=mapper.selectByParentid(parentid);
        sqlSession.commit();
        sqlSession.close();
        return lanmuliitems;
    }



    @Override
    public PageBean<lanmuliitem> selectByPageAndCondition(int currentPage, int pageSize, lanmuliitem lanmuliitem) {
        SqlSession sqlSession=factory.openSession();
        LanmuliitemMapper mapper=sqlSession.getMapper(LanmuliitemMapper.class);
        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        //计算查询条目数
        int size=pageSize;
        List<lanmuliitem> rows=mapper.selectByPageAndCondition(begin,size,lanmuliitem);
        //6.查询总记录数
        int totalCount=mapper.selectTotalCountByCondition(lanmuliitem);
        //7.封装PageBean对象
        PageBean<lanmuliitem> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        sqlSession.commit();
        sqlSession.close();
        return pageBean;
    }



    @Override
    public void update(lanmuliitem lanmuliitem) {
        SqlSession sqlSession=factory.openSession();
        LanmuliitemMapper mapper=sqlSession.getMapper(LanmuliitemMapper.class);
        mapper.update(lanmuliitem);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByid(int id) {
        SqlSession sqlSession=factory.openSession();
        LanmuliitemMapper mapper=sqlSession.getMapper(LanmuliitemMapper.class);
        mapper.deleteByid(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByids(int[] ids) {
        SqlSession sqlSession=factory.openSession();
        LanmuliitemMapper mapper=sqlSession.getMapper(LanmuliitemMapper.class);
        mapper.deleteByids(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void add(lanmuliitem lanmuliitem) {
        SqlSession sqlSession=factory.openSession();
        LanmuliitemMapper mapper=sqlSession.getMapper(LanmuliitemMapper.class);
        mapper.add(lanmuliitem);
        sqlSession.commit();
        sqlSession.close();
    }
}
