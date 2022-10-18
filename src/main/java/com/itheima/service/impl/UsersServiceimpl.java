package com.itheima.service.impl;

import com.itheima.entity.Regular;
import com.itheima.entity.admin;
import com.itheima.entity.organization;
import com.itheima.mapper.UsersMapper;
import com.itheima.service.UsersService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UsersServiceimpl implements UsersService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public Regular selectregular(String tele, String password) {
        SqlSession sqlSession=factory.openSession();
        UsersMapper mapper=sqlSession.getMapper(UsersMapper.class);
        Regular Regular=mapper.selectregular(tele,password);
        sqlSession.close();
        return Regular;
    }

    @Override
    public organization selectorganization(String tele, String password) {
        SqlSession sqlSession=factory.openSession();
        UsersMapper mapper=sqlSession.getMapper(UsersMapper.class);
        organization organization=mapper.selectorganization(tele,password);
        sqlSession.close();
        return organization;
    }

    @Override
    public admin selectadmin(String tele, String password) {
        SqlSession sqlSession=factory.openSession();
        UsersMapper mapper=sqlSession.getMapper(UsersMapper.class);
        admin admin=mapper.selectadmin(tele,password);
        sqlSession.close();
        return admin;
    }

    @Override
    public Regular selectByregulartele(String tele) {
        SqlSession sqlSession=factory.openSession();
        UsersMapper mapper=sqlSession.getMapper(UsersMapper.class);
        Regular Regular=mapper.selectByregulartele(tele);
        sqlSession.close();
        return Regular;
    }

    @Override
    public organization selectByorganizationtele(String tele) {
        SqlSession sqlSession=factory.openSession();
        UsersMapper mapper=sqlSession.getMapper(UsersMapper.class);
        organization organization=mapper.selectByorganizationtele(tele);
        sqlSession.close();
        return organization;
    }


    @Override
    public void addregular(Regular Regular) {
        SqlSession sqlSession=factory.openSession();
        UsersMapper mapper=sqlSession.getMapper(UsersMapper.class);
        mapper.addregular(Regular);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void addorganization(organization organization) {
        SqlSession sqlSession=factory.openSession();
        UsersMapper mapper=sqlSession.getMapper(UsersMapper.class);
        mapper.addorganization(organization);
        sqlSession.commit();
        sqlSession.close();
    }
}
