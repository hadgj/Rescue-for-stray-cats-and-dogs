package com.itheima.service.impl;

import com.itheima.entity.PageBean;
import com.itheima.entity.Regular;
import com.itheima.mapper.RegularMapper;
import com.itheima.service.RegularService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class RegularServiceimpl implements RegularService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public PageBean<Regular> selectByPageAndCondition(int currentPage, int pageSize, Regular Regular) {
        SqlSession sqlSession=factory.openSession();
        RegularMapper mapper=sqlSession.getMapper(RegularMapper.class);
        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        //计算查询条目数
        int size=pageSize;
        //处理apply条件，模糊表达式
        String tele=Regular.getTele();
        if(tele!=null && tele.length()>0){
            Regular.setTele(tele);
        }
        //5.查询当前页数据
        List<Regular> rows=mapper.selectByPageAndCondition(begin,size,Regular);
        //6.查询总记录数
        int totalCount=mapper.selectTotalCountByCondition(Regular);
        //7.封装PageBean对象
        PageBean<Regular> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }

    @Override
    public void deleteByids(String[] ids) {
        SqlSession sqlSession=factory.openSession();
        RegularMapper mapper=sqlSession.getMapper(RegularMapper.class);
        mapper.deleteByids(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByid(String id) {
        SqlSession sqlSession=factory.openSession();
        RegularMapper mapper=sqlSession.getMapper(RegularMapper.class);
        mapper.deleteByid(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void update(Regular Regular) {
        SqlSession sqlSession=factory.openSession();
        RegularMapper mapper=sqlSession.getMapper(RegularMapper.class);
        mapper.update(Regular);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Regular selectByid(String id) {
        SqlSession sqlSession=factory.openSession();
        RegularMapper mapper=sqlSession.getMapper(RegularMapper.class);
        Regular Regular=mapper.selectByid(id);
        sqlSession.commit();
        sqlSession.close();
        return Regular;
    }

    @Override
    public List<Regular> selectAllByNum() {
        SqlSession sqlSession=factory.openSession();
        RegularMapper mapper=sqlSession.getMapper(RegularMapper.class);
        List<Regular> rs=mapper.selectAllByNum();
        sqlSession.commit();
        sqlSession.close();
        return rs;
    }
}
