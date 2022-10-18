package com.itheima.service.impl;

import com.itheima.entity.PageBean;
import com.itheima.entity.news;
import com.itheima.mapper.NewsMapper;
import com.itheima.service.NewsService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class NewsServiceimpl implements NewsService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public PageBean<news> selectByPageAndCondition(int currentPage, int pageSize, news news) {
        SqlSession sqlSession=factory.openSession();
        NewsMapper mapper=sqlSession.getMapper(NewsMapper.class);
        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        //计算查询条目数
        int size=pageSize;
        //处理pet条件，模糊表达式
        String newsTitle=news.getNewsTitle();
        if(newsTitle!=null && newsTitle.length()>0){
            news.setNewsTitle("%"+newsTitle+"%");
        }
        String newsDate=news.getNewsDate();
        if(newsDate!=null && newsDate.length()>0){
            news.setNewsDate(newsDate+"%");
        }
        //5.查询当前页数据
        List<news> rows=mapper.selectByPageAndCondition(begin,size,news);
        //6.查询总记录数
        int totalCount=mapper.selectTotalCountByCondition(news);
        //7.封装PageBean对象
        PageBean<news> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }

    @Override
    public void add(news news) {
        SqlSession sqlSession=factory.openSession();
        NewsMapper mapper=sqlSession.getMapper(NewsMapper.class);
        mapper.add(news);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByids(int[] ids) {
        SqlSession sqlSession=factory.openSession();
        NewsMapper mapper=sqlSession.getMapper(NewsMapper.class);
        mapper.deleteByids(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteByid(int id) {
        SqlSession sqlSession=factory.openSession();
        NewsMapper mapper=sqlSession.getMapper(NewsMapper.class);
        mapper.deleteByid(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void update(news news) {
        SqlSession sqlSession=factory.openSession();
        NewsMapper mapper=sqlSession.getMapper(NewsMapper.class);
        mapper.update(news);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public news selectByid(int id) {
        SqlSession sqlSession=factory.openSession();
        NewsMapper mapper=sqlSession.getMapper(NewsMapper.class);
        news news=mapper.selectByid(id);
        sqlSession.close();
        return news;
    }

    @Override
    public List<news> topten() {
        SqlSession sqlSession=factory.openSession();
        NewsMapper mapper=sqlSession.getMapper(NewsMapper.class);
        List<news> newss=mapper.topten();
        sqlSession.close();
        return newss;
    }

    @Override
    public List<news> selectByids(int[] ids) {
        SqlSession sqlSession=factory.openSession();
        NewsMapper mapper=sqlSession.getMapper(NewsMapper.class);
        List<news> newss=mapper.selectByids(ids);
        sqlSession.close();
        return newss;
    }

    @Override
    public List<news> nquanju(String keyword) {
        SqlSession sqlSession=factory.openSession();
        NewsMapper mapper=sqlSession.getMapper(NewsMapper.class);
        List<news> newss=mapper.nquanju(keyword);
        sqlSession.close();
        return newss;
    }

    @Override
    public List<news> topfive() {
        SqlSession sqlSession=factory.openSession();
        NewsMapper mapper=sqlSession.getMapper(NewsMapper.class);
        List<news> newss=mapper.topfive();
        sqlSession.close();
        return newss;
    }

    @Override
    public List<news> selectAll() {
        SqlSession sqlSession=factory.openSession();
        NewsMapper mapper=sqlSession.getMapper(NewsMapper.class);
        List<news> newss=mapper.selectAll();
        sqlSession.close();
        return newss;
    }
}
