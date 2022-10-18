package com.itheima.service;

import com.itheima.entity.oh;

import java.util.List;

public interface OhService {
    oh selectByid(int hid);
    void deleteByid(int hid);
    void add(oh oh);
    oh selectByteleandhid(int hid,String otele);
    List<oh> selectBytele(String otele);
    void update(oh oh);
}
