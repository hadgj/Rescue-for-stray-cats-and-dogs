package com.itheima.service;

import com.itheima.entity.admin;

public interface AdminService {
    void update(admin admin);
    admin selectbytele(String tele);
}
