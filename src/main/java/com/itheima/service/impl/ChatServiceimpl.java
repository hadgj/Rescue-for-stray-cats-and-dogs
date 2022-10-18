package com.itheima.service.impl;

import com.itheima.entity.chat;
import com.itheima.mapper.ChatMapper;
import com.itheima.service.ChatService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ChatServiceimpl implements ChatService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public void add(chat chat) {
        SqlSession sqlSession=factory.openSession();
        ChatMapper mapper=sqlSession.getMapper(ChatMapper.class);
        mapper.add(chat);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<chat> selectchats(int bstatus, String btele) {
        SqlSession sqlSession=factory.openSession();
        ChatMapper mapper=sqlSession.getMapper(ChatMapper.class);
        List<chat> chats=mapper.selectchats(bstatus,btele);
        sqlSession.commit();
        sqlSession.close();
        return chats;
    }
}
