package com.itheima.mapper;

import com.itheima.entity.Regular;
import com.itheima.entity.admin;
import com.itheima.entity.organization;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UsersMapper {
    @Select("select * from regular where tele=#{tele} and password=#{password}")
    Regular selectregular(@Param("tele") String tele,@Param("password") String password);

    @Select("select * from organization where tele=#{tele} and password=#{password}")
    organization selectorganization(@Param("tele") String tele, @Param("password") String password);

    @Select("select * from admin where tele=#{tele} and password=#{password}")
    admin selectadmin(@Param("tele") String tele, @Param("password") String password);

    @Select("select * from regular where tele =#{tele}")
    Regular selectByregulartele(String tele);

    @Select("select * from organization where tele =#{tele}")
    organization selectByorganizationtele(String tele);


    @Insert("insert into regular values(#{tele},#{password},#{sex},#{age},#{address},#{pic},#{num},#{status},#{username})")
    void addregular(Regular Regular);

    @Insert("insert into organization values(#{tele},#{password},#{address},#{username},#{status},#{pic})")
    void addorganization(organization organization);

}
