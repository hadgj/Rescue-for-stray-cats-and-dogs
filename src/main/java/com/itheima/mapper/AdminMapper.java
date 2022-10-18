package com.itheima.mapper;


import com.itheima.entity.admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdminMapper {
    //编辑完的更新操作
    @Update("update admin set  tele=#{tele} ,sex=#{sex}, age=#{age} ,pic=#{pic}, username=#{username} where tele=#{tele}")
    void update(admin admin);

    @Select("select * from admin where tele=#{tele}")
    admin selectbytele(@Param("tele") String tele);
}
