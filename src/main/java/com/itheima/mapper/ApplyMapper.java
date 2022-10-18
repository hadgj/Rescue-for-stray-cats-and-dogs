package com.itheima.mapper;


import com.itheima.entity.apply;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ApplyMapper {
    //根据条件查询数据总数
    int selectTotalCountByCondition(apply apply);
    //根据页数和条件查询数据总数并且显示
    List<apply> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("apply") apply apply);
    //根据领养信息，同时改变宠物的领养状态
    @Update("update pet p,apply a set p.petIsApply=#{petIsApply},a.apstate=#{apstate} WHERE p.petID=#{pid} and a.pid=#{pid}")
    void update(@Param("petIsApply") String petIsApply,@Param("apstate") int apstate,@Param("pid") int pid);
    //批量删除和单独删除
    //根据选中数据批量删除
    void deleteByids(@Param("ids") int[] ids);
    //删除选中行ID
    @Delete("delete from apply where apid=#{id}")
    void deleteByid(@Param("id") int id);

    //提交领养申请，执行添加操作
    @Insert("insert into apply values(null,#{rtele},#{pid},#{apmessage},#{applytime},#{rusername},#{pname},#{apstate},#{ptype})")
    void add(apply apply);

    @Select("select * from apply where rtele=#{rtele}")
    List<apply> selectByRtele(@Param("rtele") String rtele);

    @Update("update apply set rtele=#{rtele},pid=#{pid},apmessage=#{apmessage},rusername=#{rusername},pname=#{pname},apstate=#{apstate},ptype=#{ptype} where apid=#{apid}")
    void updateapply(apply apply);
}
