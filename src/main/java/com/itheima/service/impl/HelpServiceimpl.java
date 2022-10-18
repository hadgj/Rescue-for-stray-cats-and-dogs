package com.itheima.service.impl;

import com.itheima.entity.PageBean;
import com.itheima.entity.help;
import com.itheima.mapper.HelpMapper;
import com.itheima.service.HelpService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class HelpServiceimpl implements HelpService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public PageBean<help> selectByPageAndCondition(int currentPage, int pageSize, help help) {
        SqlSession sqlSession=factory.openSession();
        HelpMapper mapper=sqlSession.getMapper(HelpMapper.class);
        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        //计算查询条目数
        int size=pageSize;
        //处理apply条件，模糊表达式
        String address=help.getAddress();
        if(address!=null && address.length()>0){
            help.setAddress("%"+address+"%");
        }
        String hdate=help.getHdate();
        if(hdate!=null && hdate.length()>0){
            help.setHdate(hdate);
        }
        String rid= help.getRid();
        if(rid!=null && rid.length()>0){
            help.setRid(rid);
        }
        //5.查询当前页数据
        List<help> rows=mapper.selectByPageAndCondition(begin,size,help);
        //6.查询总记录数
        int totalCount=mapper.selectTotalCountByCondition(help);
        //7.封装PageBean对象
        PageBean<help> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }
    @Override
    public void deleteByids(int[] ids) {
        SqlSession sqlSession=factory.openSession();
        HelpMapper mapper=sqlSession.getMapper(HelpMapper.class);
        mapper.deleteByids(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByid(int id) {
        SqlSession sqlSession=factory.openSession();
        HelpMapper mapper=sqlSession.getMapper(HelpMapper.class);
        mapper.deleteByid(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public help selectByid(int id) {
        SqlSession sqlSession=factory.openSession();
        HelpMapper mapper=sqlSession.getMapper(HelpMapper.class);
        help help=mapper.selectByid(id);
        sqlSession.close();
        return help;
    }

    @Override
    public void add(help help) {
        SqlSession sqlSession=factory.openSession();
        HelpMapper mapper=sqlSession.getMapper(HelpMapper.class);
        mapper.add(help);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void update(help help) {
        SqlSession sqlSession=factory.openSession();
        HelpMapper mapper=sqlSession.getMapper(HelpMapper.class);
        mapper.update(help);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<help> selectByids(int[] ids) {
        SqlSession sqlSession=factory.openSession();
        HelpMapper mapper=sqlSession.getMapper(HelpMapper.class);
        List<help> helps=mapper.selectByids(ids);
        sqlSession.close();
        return helps;
    }

    @Override
    public List<help> selectAllByRid(String rid) {
        SqlSession sqlSession=factory.openSession();
        HelpMapper mapper=sqlSession.getMapper(HelpMapper.class);
        List<help> helps=mapper.selectAllByRid(rid);
        sqlSession.close();
        return helps;
    }

    @Override
    public help youxian(String rid) {
        SqlSession sqlSession=factory.openSession();
        HelpMapper mapper=sqlSession.getMapper(HelpMapper.class);
        help help=mapper.youxian(rid);
        sqlSession.close();
        return help;
    }

    @Override
    public List<help> hquanju(String keyword) {
        SqlSession sqlSession=factory.openSession();
        HelpMapper mapper=sqlSession.getMapper(HelpMapper.class);
        List<help> helps=mapper.hquanju(keyword);
        sqlSession.close();
        return helps;
    }
}
