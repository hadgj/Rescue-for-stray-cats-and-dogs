package com.itheima.service.impl;

import com.itheima.entity.oh;
import com.itheima.mapper.OhMapper;
import com.itheima.service.OhService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class OhServiceimpl implements OhService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public oh selectByid(int hid) {
        SqlSession sqlSession=factory.openSession();
        OhMapper mapper=sqlSession.getMapper(OhMapper.class);
        oh oh=mapper.selectByid(hid);
        sqlSession.close();
        return oh;
    }

    @Override
    public void deleteByid(int hid) {
        SqlSession sqlSession=factory.openSession();
        OhMapper mapper=sqlSession.getMapper(OhMapper.class);
        mapper.deleteByid(hid);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void add(oh oh) {
        SqlSession sqlSession=factory.openSession();
        OhMapper mapper=sqlSession.getMapper(OhMapper.class);
        mapper.add(oh);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public oh selectByteleandhid(int hid, String otele) {
        SqlSession sqlSession=factory.openSession();
        OhMapper mapper=sqlSession.getMapper(OhMapper.class);
        oh oh=mapper.selectByteleandhid(hid,otele);
        sqlSession.commit();
        sqlSession.close();
        return oh;
    }

    @Override
    public List<oh> selectBytele(String otele) {
        SqlSession sqlSession=factory.openSession();
        OhMapper mapper=sqlSession.getMapper(OhMapper.class);
        List<oh> ohs=mapper.selectBytele(otele);
        sqlSession.commit();
        sqlSession.close();
        return ohs;
    }

    @Override
    public void update(oh oh) {
        SqlSession sqlSession=factory.openSession();
        OhMapper mapper=sqlSession.getMapper(OhMapper.class);
        mapper.update(oh);
        sqlSession.commit();
        sqlSession.close();
    }

}
