package com.itheima.mapper;

import com.itheima.entity.pet;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PetMapper {

    @Select("select * from pet limit 4")
    List<pet> petfour();

    @Select("select * from pet")
    List<pet> selectAll();

    @Select("select * from pet where petID=#{id}")
    pet selectByid(@Param("id") int id);

    @Insert("insert into pet values(null,#{petName},#{petType},#{petSex},#{petAge},#{petDsc},#{petIsApply},#{petAddress},#{pic},#{otele})")
    void add(pet pet);

    void deleteByids(@Param("ids") int[] ids);

    @Delete("delete from pet where petID=#{id}")
    void deleteByid(@Param("id") int id);

    @Select("select * from pet limit #{begin},#{size}")
    List<pet> selectByPage(@Param("begin") int begin,@Param("size") int size);

    @Select("select count(*) from pet")
    int selectTotalCount();


    List<pet> selectByPageAndCondition(@Param("begin") int begin,@Param("size") int size,@Param("pet") pet pet);


    int selectTotalCountByCondition(pet pet);

    @Update("update pet set  petName=#{petName} ,petType=#{petType}, petSex=#{petSex} ,petAge=#{petAge}, petDsc=#{petDsc} ,petIsApply=#{petIsApply} ,petAddress=#{petAddress} ,pic=#{pic},otele=#{otele} where petID=#{petID}")
    void update(pet pet);

    @Select("select petID from pet order by petID desc limit 0,1;")
    int selectnewid();

    @Select("select * from pet where otele=#{otele}")
    List<pet> selectByotele(@Param("otele") String otele);

    @Select("select * from pet where petName like #{keyword} or petType like #{keyword} or petSex like #{keyword} or petAge like #{keyword} or petDsc like #{keyword} or petAddress like #{keyword}")
    List<pet> pquanju(@Param("keyword") String keyword);
}







































