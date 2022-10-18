package com.itheima.mapper;

import com.itheima.entity.news;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface NewsMapper {
    //根据条件查询数据总数
    int selectTotalCountByCondition(news news);
    //根据页数和条件查询数据总数并且显示
    List<news> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("news") news news);
    //新增一条数据
    @Insert("insert into news values(null,#{newsTitle},#{newsContent},#{newsDate},#{newsImagePath},#{newsIsTop},#{newsZan},#{newsRedu})")
    void add(news news);
    //根据选中数据批量查询
    List<news> selectByids(@Param("ids") int[] ids);
    //根据选中数据批量删除
    void deleteByids(@Param("ids") int[] ids);
    //删除选中行ID
    @Delete("delete from news where newsID=#{id}")
    void deleteByid(@Param("id") int id);
    //编辑完的更新操作
    @Update("update news set  newsTitle=#{newsTitle} ,newsContent=#{newsContent}, newsDate=#{newsDate} ,newsImagePath=#{newsImagePath}, newsIsTop=#{newsIsTop} ,newsZan=#{newsZan},newsRedu=#{newsRedu} where newsID=#{newsID}")
    void update(news news);
    @Select("select * from news where newsID=#{id}")
    news selectByid(@Param("id") int id);

    @Select("select * from news order by newsRedu desc limit 10")
    List<news> topten();

    @Select("select * from news where newsTitle like #{keyword} or newsContent like #{keyword} or newsDate like #{keyword}")
    List<news> nquanju(@Param("keyword") String keyword);


    //取五条热点新闻按点赞量排行limit0 5
    @Select("select * from news order by newsZan desc limit 5")
    List<news> topfive();

    @Select("select * from news")
    List<news> selectAll();
}






























