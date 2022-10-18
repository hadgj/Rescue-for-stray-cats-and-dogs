package com.itheima.service.impl;

import com.itheima.entity.lanmu;
import com.itheima.mapper.LanmuMapper;
import com.itheima.service.LanmuService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class LanmuServiceimpl implements LanmuService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public List<lanmu> selectAll() {
        SqlSession sqlSession=factory.openSession();
        LanmuMapper mapper=sqlSession.getMapper(LanmuMapper.class);
        List<lanmu> lanmus=mapper.selectAll();
        sqlSession.commit();
        sqlSession.close();
        return lanmus;
    }

    @Override
    public void update(lanmu lanmu) {
        SqlSession sqlSession=factory.openSession();
        LanmuMapper mapper=sqlSession.getMapper(LanmuMapper.class);
        mapper.update(lanmu);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByid(int id) {
        SqlSession sqlSession=factory.openSession();
        LanmuMapper mapper=sqlSession.getMapper(LanmuMapper.class);
        mapper.deleteByid(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByids(int[] ids) {
        SqlSession sqlSession=factory.openSession();
        LanmuMapper mapper=sqlSession.getMapper(LanmuMapper.class);
        mapper.deleteByids(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void add(lanmu lanmu) {
        SqlSession sqlSession=factory.openSession();
        LanmuMapper mapper=sqlSession.getMapper(LanmuMapper.class);
        mapper.add(lanmu);
        sqlSession.commit();
        sqlSession.close();
    }
}
