package com.itheima.service;

import com.itheima.entity.Regular;
import com.itheima.entity.admin;
import com.itheima.entity.organization;

public interface UsersService {
    Regular selectregular(String tele,String password);

    organization selectorganization(String tele,String password);

    admin selectadmin(String tele,String password);
//    注册的时候使用，根据手机号查id存不存在即可
    Regular selectByregulartele(String tele);

    organization selectByorganizationtele(String tele);



    void addregular(Regular Regular);


    void addorganization(organization organization);
}
