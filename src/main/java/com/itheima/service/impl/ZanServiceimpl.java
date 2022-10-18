package com.itheima.service.impl;

import com.itheima.entity.zan;
import com.itheima.mapper.ZanMapper;
import com.itheima.service.ZanService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ZanServiceimpl implements ZanService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public zan selectByid(String tele, int nid,int status) {
        SqlSession sqlSession=factory.openSession();
        ZanMapper mapper=sqlSession.getMapper(ZanMapper.class);
        zan zan=mapper.selectByid(tele,nid,status);
        sqlSession.close();
        return zan;
    }

    @Override
    public void add(zan zan) {
        SqlSession sqlSession=factory.openSession();
        ZanMapper mapper=sqlSession.getMapper(ZanMapper.class);
        mapper.add(zan);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByid(String tele, int nid,int status) {
        SqlSession sqlSession=factory.openSession();
        ZanMapper mapper=sqlSession.getMapper(ZanMapper.class);
        mapper.deleteByid(tele,nid,status);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<zan> shoucang(String tele, int status) {
        SqlSession sqlSession=factory.openSession();
        ZanMapper mapper=sqlSession.getMapper(ZanMapper.class);
        List<zan> zans=mapper.shoucang(tele,status);
        sqlSession.commit();
        sqlSession.close();
        return zans;
    }
}
