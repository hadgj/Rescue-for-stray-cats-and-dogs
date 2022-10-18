package com.itheima.mapper;


import com.itheima.entity.organization;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrganizationMapper {
    //根据条件查询数据总数
    int selectTotalCountByCondition(organization organization);
    //根据页数和条件查询数据总数并且显示
    List<organization > selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("organization") organization  organization );
    //根据选中数据批量删除
    void deleteByids(@Param("ids") String[] ids);
    //删除选中行ID
    @Delete("delete from organization  where tele=#{id}")
    void deleteByid(@Param("id") String id);
    //审核语句
    @Update("update organization set status=#{status} where tele=#{tele}")
    void update(@Param("status") int status,@Param("tele") String tele);
    @Update("update organization set  password=#{password} ,address=#{address}, pic=#{pic} ,status=#{status} ,username=#{username} where tele=#{tele}")
    void upjichu(organization organization);
    @Select("select * from organization where tele=#{id}")
    organization selectByid(@Param("id") String id);
}












