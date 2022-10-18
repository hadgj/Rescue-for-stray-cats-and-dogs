package com.itheima.mapper;

import com.itheima.entity.lanmuliitem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface LanmuliitemMapper {
    //查询所有
    @Select("select * from lanmuliitem where parentid=#{parentid}")
    List<lanmuliitem> selectByParentid(@Param("parentid") int parentid);
    //分页查询
    List<lanmuliitem> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("lanmuliitem") lanmuliitem lanmuliitem);
    //分页查询查总数
    int selectTotalCountByCondition(lanmuliitem lanmuliitem);
    //修改
    @Update("update lanmuliitem set parentid=#{parentid},itemname=#{itemname},itemques=#{itemques},itemansw=#{itemansw},itempic=#{itempic} where id=#{id}")
    void update(lanmuliitem lanmuliitem);
    //单条删除
    @Delete("delete from lanmuliitem where id=#{id}")
    void deleteByid(@Param("id") int id);
    //批量删除
    void deleteByids(@Param("ids") int[] ids);
    //新增
    @Insert("insert into lanmuliitem values (null,#{parentid},#{itemname},#{itemques},#{itemansw},#{itempic})")
    void add(lanmuliitem lanmuliitem);
}
