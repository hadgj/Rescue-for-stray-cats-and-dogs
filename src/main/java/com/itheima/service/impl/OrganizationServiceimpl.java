package com.itheima.service.impl;

import com.itheima.entity.PageBean;
import com.itheima.entity.organization;
import com.itheima.mapper.OrganizationMapper;
import com.itheima.service.OrganizationService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class OrganizationServiceimpl implements OrganizationService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public PageBean<organization> selectByPageAndCondition(int currentPage, int pageSize, organization organization) {
        SqlSession sqlSession=factory.openSession();
        OrganizationMapper mapper=sqlSession.getMapper(OrganizationMapper.class);
        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        //计算查询条目数
        int size=pageSize;
        String tele=organization.getTele();
        if(tele!=null && tele.length()>0){
            organization.setTele(tele);
        }
        //5.查询当前页数据
        List<organization> rows=mapper.selectByPageAndCondition(begin,size,organization);
        //6.查询总记录数
        int totalCount=mapper.selectTotalCountByCondition(organization);
        //7.封装PageBean对象
        PageBean<organization> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }

    @Override
    public void deleteByids(String[] ids) {
        SqlSession sqlSession=factory.openSession();
        OrganizationMapper mapper=sqlSession.getMapper(OrganizationMapper.class);
        mapper.deleteByids(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByid(String id) {
        SqlSession sqlSession=factory.openSession();
        OrganizationMapper mapper=sqlSession.getMapper(OrganizationMapper.class);
        mapper.deleteByid(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void update(int status, String tele) {
        SqlSession sqlSession=factory.openSession();
        OrganizationMapper mapper=sqlSession.getMapper(OrganizationMapper.class);
        mapper.update(status,tele);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void upjichu(organization organization) {
        SqlSession sqlSession=factory.openSession();
        OrganizationMapper mapper=sqlSession.getMapper(OrganizationMapper.class);
        mapper.upjichu(organization);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public organization selectByid(String id) {
        SqlSession sqlSession=factory.openSession();
        OrganizationMapper mapper=sqlSession.getMapper(OrganizationMapper.class);
        organization organization= mapper.selectByid(id);
        sqlSession.commit();
        sqlSession.close();
        return organization;
    }
}
