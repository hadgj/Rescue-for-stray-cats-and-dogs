package com.itheima.service.impl;

import com.itheima.entity.PageBean;
import com.itheima.entity.mingan;
import com.itheima.mapper.MinganMapper;
import com.itheima.service.MinganService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class MinganServiceimpl implements MinganService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();

    @Override
    public PageBean<mingan> selectByPageAndCondition(int currentPage, int pageSize, mingan mingan) {
        SqlSession sqlSession=factory.openSession();
        MinganMapper mapper=sqlSession.getMapper(MinganMapper.class);
        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        //计算查询条目数
        int size=pageSize;
        //处理pet条件，模糊表达式
        String str=mingan.getStr();
        if(str!=null && str.length()>0){
            mingan.setStr("%"+str+"%");
        }
        //5.查询当前页数据
        List<mingan> rows=mapper.selectByPageAndCondition(begin,size,mingan);
        //6.查询总记录数
        int totalCount=mapper.selectTotalCountByCondition(mingan);
        //7.封装PageBean对象
        PageBean<mingan> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }

    @Override
    public void deleteByids(int[] ids) {
        SqlSession sqlSession=factory.openSession();
        MinganMapper mapper=sqlSession.getMapper(MinganMapper.class);
        mapper.deleteByids(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByid(int id) {
        SqlSession sqlSession=factory.openSession();
        MinganMapper mapper=sqlSession.getMapper(MinganMapper.class);
        mapper.deleteByid(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void add(mingan mingan) {
        SqlSession sqlSession=factory.openSession();
        MinganMapper mapper=sqlSession.getMapper(MinganMapper.class);
        mapper.add(mingan);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<mingan> selectAll() {
        SqlSession sqlSession=factory.openSession();
        MinganMapper mapper=sqlSession.getMapper(MinganMapper.class);
        List<mingan> mingans=mapper.selectAll();
        return mingans;
    }


}
