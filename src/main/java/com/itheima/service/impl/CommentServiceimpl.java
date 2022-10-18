package com.itheima.service.impl;

import com.itheima.entity.PageBean;
import com.itheima.entity.comment;
import com.itheima.mapper.CommentMapper;
import com.itheima.service.CommentService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class CommentServiceimpl implements CommentService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public void deleteByids(int[] ids) {
        SqlSession sqlSession=factory.openSession();
        CommentMapper mapper=sqlSession.getMapper(CommentMapper.class);
        mapper.deleteByids(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public PageBean<comment> selectByPageAndCondition(int currentPage, int pageSize, comment comment) {
        SqlSession sqlSession=factory.openSession();
        CommentMapper mapper=sqlSession.getMapper(CommentMapper.class);
        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        //计算查询条目数
        int size=pageSize;
        String ntitle=comment.getNtitle();
        if(ntitle!=null && ntitle.length()>0){
            comment.setNtitle("%"+ntitle+"%");
        }
        //5.查询当前页数据
        List<comment> rows=mapper.selectByPageAndCondition(begin,size,comment);
        //6.查询总记录数
        int totalCount=mapper.selectTotalCountByCondition(comment);
        //7.封装PageBean对象
        PageBean<comment> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }

    @Override
    public void deleteByid(int id) {
        SqlSession sqlSession=factory.openSession();
        CommentMapper mapper=sqlSession.getMapper(CommentMapper.class);
        mapper.deleteByid(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void add(comment comment) {
        SqlSession sqlSession=factory.openSession();
        CommentMapper mapper=sqlSession.getMapper(CommentMapper.class);
        mapper.add(comment);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<comment> selectAllByNid(int nid) {
        SqlSession sqlSession=factory.openSession();
        CommentMapper mapper=sqlSession.getMapper(CommentMapper.class);
        List<comment> comments=mapper.selectAllByNid(nid);
        sqlSession.commit();
        sqlSession.close();
        return comments;
    }
}
