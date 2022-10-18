package com.itheima.mapper;

import com.itheima.entity.zan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ZanMapper {
    @Select("select * from zan where tele=#{tele} and nid=#{nid} and status=#{status}")
    zan selectByid(@Param("tele") String tele, @Param("nid") int nid,@Param("status") int status);

    @Insert("insert into zan values (null,#{tele},#{nid},#{status})")
    void add(zan zan);

    @Delete("delete from zan where tele=#{tele} and nid=#{nid} and status=#{status}")
    void deleteByid(@Param("tele") String tele, @Param("nid") int nid,@Param("status") int status);

    @Select("SELECT * FROM zan WHERE tele=#{tele} and status=#{status}")
    List<zan> shoucang(@Param("tele") String tele,@Param("status") int status);
}
