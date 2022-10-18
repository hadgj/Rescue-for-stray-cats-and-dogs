package com.itheima.mapper;


import com.itheima.entity.help;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface HelpMapper {
    //根据条件查询数据总数
    int selectTotalCountByCondition(help help);
    //根据页数和条件查询数据总数并且显示
    List<help> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("help") help help);
    //批量删除和单独删除
    //根据选中数据批量删除
    void deleteByids(@Param("ids") int[] ids);
    //删除选中行ID
    @Delete("delete from help where id=#{id}")
    void deleteByid(@Param("id") int id);
    @Select("select * from help where id=#{id}")
    help selectByid(@Param("id") int id);
    @Insert("insert into help values(null,#{address},#{hdate},#{hdesc},#{pic},#{rid},#{hstate})")
    void add(help help);
   @Update("update help set address=#{address},hdate=#{hdate},hdesc=#{hdesc},pic=#{pic},rid=#{rid},hstate=#{hstate} where id=#{id}")
    void update(help help);

    //根据选中数据批量查询
    List<help> selectByids(@Param("ids") int[] ids);

    @Select("select * from help where rid=#{rid}")
    List<help> selectAllByRid(@Param("rid") String rid);

    @Select("select * from help where rid=#{rid} order by hdate desc limit 0,1")
    help youxian(@Param("rid") String rid);

    @Select("select * from help where address like #{keyword} or hdate like #{keyword} or hdesc like #{keyword}")
    List<help> hquanju(@Param("keyword") String keyword);
}
