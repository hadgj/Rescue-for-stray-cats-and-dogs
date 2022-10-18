package com.itheima.mapper;

import com.itheima.entity.chat;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChatMapper {
    //发送留言，增加一条数据
    @Insert("insert into chat values (null,#{bstatus},#{btele},#{cstatus},#{ctele},#{message})")
    void add(chat chat);
    //展示留言是一个账号的留言的集合
    @Select("select * from chat where bstatus=#{bstatus} and btele=#{btele}")
    List<chat> selectchats(@Param("bstatus") int bstatus,@Param("btele") String btele);
}
