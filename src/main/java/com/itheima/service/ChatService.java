package com.itheima.service;

import com.itheima.entity.chat;

import java.util.List;

public interface ChatService {
    void add(chat chat);
    List<chat> selectchats(int bstatus,String btele);
}
