package com.itheima.service.impl;

import com.itheima.entity.PageBean;
import com.itheima.entity.ro;
import com.itheima.mapper.RoMapper;
import com.itheima.service.RoService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class RoServiceimpl implements RoService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public void add(ro ro) {
        SqlSession sqlSession=factory.openSession();
        RoMapper mapper=sqlSession.getMapper(RoMapper.class);
        mapper.add(ro);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void update(ro ro) {
        SqlSession sqlSession=factory.openSession();
        RoMapper mapper=sqlSession.getMapper(RoMapper.class);
        mapper.update(ro);
        sqlSession.commit();
        sqlSession.close();
    }


    @Override
    public void deleteByid(int id) {
        SqlSession sqlSession=factory.openSession();
        RoMapper mapper=sqlSession.getMapper(RoMapper.class);
        mapper.deleteByid(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public int youxiaojinggao(int bstatus, String btele) {
        SqlSession sqlSession=factory.openSession();
        RoMapper mapper=sqlSession.getMapper(RoMapper.class);
        int num=mapper.youxiaojinggao(bstatus,btele);
        sqlSession.commit();
        sqlSession.close();
        return num;
    }

    @Override
    public List<ro> jinggao(int bstatus, String btele) {
        SqlSession sqlSession=factory.openSession();
        RoMapper mapper=sqlSession.getMapper(RoMapper.class);
        List<ro> ros=mapper.jinggao(bstatus,btele);
        sqlSession.commit();
        sqlSession.close();
        return ros;
    }

    @Override
    public ro fengjin(int bstatus, String btele) {
        SqlSession sqlSession=factory.openSession();
        RoMapper mapper=sqlSession.getMapper(RoMapper.class);
        ro ro=mapper.fengjin(bstatus, btele);
        sqlSession.commit();
        sqlSession.close();
        return ro;
    }

    @Override
    public PageBean<ro> selectByPageAndCondition(int currentPage, int pageSize, ro ro) {
        SqlSession sqlSession=factory.openSession();
        RoMapper mapper=sqlSession.getMapper(RoMapper.class);
        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        //计算查询条目数
        int size=pageSize;
        String btele=ro.getBtele();
        if(btele!=null && btele.length()>0){
            ro.setBtele(btele);
        }
        Integer bstatus=ro.getBstatus();
        if(bstatus!=null){
            ro.setBstatus(bstatus);
        }
        //5.查询当前页数据
        List<ro> rows=mapper.selectByPageAndCondition(begin,size,ro);
        //6.查询总记录数
        int totalCount=mapper.selectTotalCountByCondition(ro);
        //7.封装PageBean对象
        PageBean<ro> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }

    @Override
    public void deleteByids(int[] ids) {
        SqlSession sqlSession=factory.openSession();
        RoMapper mapper=sqlSession.getMapper(RoMapper.class);
        mapper.deleteByids(ids);
        sqlSession.commit();
        sqlSession.close();
    }


}
