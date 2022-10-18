package com.itheima.service.impl;

import com.itheima.entity.PageBean;
import com.itheima.entity.apply;
import com.itheima.mapper.ApplyMapper;
import com.itheima.service.ApplyService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ApplyServiceimpl implements ApplyService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();

    @Override
    public PageBean<apply> selectByPageAndCondition(int currentPage, int pageSize,  apply apply) {
        SqlSession sqlSession=factory.openSession();
        ApplyMapper mapper=sqlSession.getMapper(ApplyMapper.class);
        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        //计算查询条目数
        int size=pageSize;
        //处理apply条件，模糊表达式
       String rtele=apply.getRtele();
        if(rtele!=null && rtele.length()>0){
            apply.setRtele(rtele);
        }
        String applytime= apply.getApplytime();
        if(applytime!=null && applytime.length()>0){
            apply.setApplytime(applytime);
        }
        String pname= apply.getPname();
        if(pname!=null && pname.length()>0){
            apply.setPname("%"+pname+"%");
        }
        Integer apstate= apply.getApstate();
        if(apstate!=null){
            apply.setApstate(apstate);
        }
        //5.查询当前页数据
        List<apply> rows=mapper.selectByPageAndCondition(begin,size,apply);
        //6.查询总记录数
        int totalCount=mapper.selectTotalCountByCondition(apply);
        //7.封装PageBean对象
        PageBean<apply> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }

    @Override
    public void update(String petIsApply, int apstate, int pid) {
        SqlSession sqlSession=factory.openSession();
        ApplyMapper mapper=sqlSession.getMapper(ApplyMapper.class);
        mapper.update(petIsApply,apstate,pid);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByids(int[] ids) {
        SqlSession sqlSession=factory.openSession();
        ApplyMapper mapper=sqlSession.getMapper(ApplyMapper.class);
        mapper.deleteByids(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByid(int id) {
        SqlSession sqlSession=factory.openSession();
        ApplyMapper mapper=sqlSession.getMapper(ApplyMapper.class);
        mapper.deleteByid(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void add(apply apply) {
        SqlSession sqlSession=factory.openSession();
        ApplyMapper mapper=sqlSession.getMapper(ApplyMapper.class);
        mapper.add(apply);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<apply> selectByRtele(String rtele) {
        SqlSession sqlSession=factory.openSession();
        ApplyMapper mapper=sqlSession.getMapper(ApplyMapper.class);
        List<apply> applies=mapper.selectByRtele(rtele);
        sqlSession.close();
        return applies;
    }

    @Override
    public void updateapply(apply apply) {
        SqlSession sqlSession=factory.openSession();
        ApplyMapper mapper=sqlSession.getMapper(ApplyMapper.class);
        mapper.updateapply(apply);
        sqlSession.commit();
        sqlSession.close();
    }

}
