package com.itheima.mapper;

import com.itheima.entity.oh;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OhMapper {
    @Select("select * from oh where hid=#{hid}")
    oh selectByid(@Param("hid") int hid);
    @Delete("delete from oh where hid=#{hid}")
    void deleteByid(@Param("hid") int hid);
    @Insert("insert into oh values (null,#{otele},#{hid},#{ohstate})")
    void add(oh oh);
    @Select("select * from oh where hid=#{hid} and otele=#{otele}")
    oh selectByteleandhid(@Param("hid") int hid,@Param("otele") String otele);
    @Select("select * from oh where otele=#{otele}")
    List<oh> selectBytele(@Param("otele") String otele);
    @Update("update oh set otele=#{otele},hid=#{hid},ohstate=#{ohstate} where hid=#{hid}")
    void update(oh oh);
}
