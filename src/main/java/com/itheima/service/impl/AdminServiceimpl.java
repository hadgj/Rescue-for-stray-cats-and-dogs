package com.itheima.service.impl;

import com.itheima.entity.admin;
import com.itheima.mapper.AdminMapper;
import com.itheima.service.AdminService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class AdminServiceimpl implements AdminService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public void update(admin admin) {
        SqlSession sqlSession=factory.openSession();
        AdminMapper mapper=sqlSession.getMapper(AdminMapper.class);
        mapper.update(admin);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public admin selectbytele(String tele) {
        SqlSession sqlSession=factory.openSession();
        AdminMapper mapper=sqlSession.getMapper(AdminMapper.class);
        admin admin=mapper.selectbytele(tele);
        return  admin;
    }
}
