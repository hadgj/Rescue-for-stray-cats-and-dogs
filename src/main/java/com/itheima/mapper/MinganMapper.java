package com.itheima.mapper;


import com.itheima.entity.mingan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MinganMapper {
    //新增一条数据
    @Insert("insert into mingan values(null,#{str})")
    void add(mingan mingan);
    //根据条件查询数据总数
    int selectTotalCountByCondition(mingan mingan);
    //根据页数和条件查询数据总数并且显示
    List<mingan> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("mingan") mingan mingan);
    void deleteByids(@Param("ids") int[] ids);
    @Delete("delete from mingan where id=#{id}")
    void deleteByid(@Param("id") int id);
    @Select("select * from mingan")
    List<mingan> selectAll();
}
