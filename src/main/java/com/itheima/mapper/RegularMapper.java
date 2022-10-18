package com.itheima.mapper;

import com.itheima.entity.Regular;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RegularMapper {
    //根据条件查询数据总数
    int selectTotalCountByCondition(Regular Regular);
    //根据页数和条件查询数据总数并且显示
    List<Regular> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("Regular") Regular Regular);
    //根据选中数据批量删除
    void deleteByids(@Param("ids") String[] ids);
    //删除选中行ID
    @Delete("delete from regular where tele=#{id}")
    void deleteByid(@Param("id") String id);
    @Update("update regular set  password=#{password} ,sex=#{sex}, age=#{age} ,address=#{address}, pic=#{pic} ,num=#{num} ,status=#{status} ,username=#{username} where tele=#{tele}")
    void update(Regular Regular);
    @Select("select * from regular where tele=#{id}")
    Regular selectByid(@Param("id") String id);


    @Select("select * from regular order by num desc")
    List<Regular> selectAllByNum();
}
