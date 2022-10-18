package com.itheima.mapper;

import com.itheima.entity.lanmu;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface LanmuMapper {
     //查询所有
     @Select("select * from lanmu")
     List<lanmu> selectAll();
     //修改
     @Update("update lanmu set lanmuli=#{lanmuli} where id=#{id}")
     void update(lanmu lanmu);
     //单条删除
     @Delete("delete from lanmu where id=#{id}")
     void deleteByid(@Param("id") int id);
     //批量删除
     void deleteByids(@Param("ids") int[] ids);
     //新增
     @Insert("insert into lanmu values (null,#{lanmuli})")
     void add(lanmu lanmu);
}
