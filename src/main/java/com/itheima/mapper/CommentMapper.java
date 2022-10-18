package com.itheima.mapper;


import com.itheima.entity.comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {
    //多选框删除评论
    void deleteByids(@Param("ids") int[] ids);
    //分页查询评论
    List<comment> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("comment") comment comment);
    //查询评论记录总数
    int selectTotalCountByCondition(comment comment);
    //按照id删除一条评论记录
    @Delete("delete from comment where id=#{id}")
    void deleteByid(@Param("id") int id);
    @Insert("insert into comment values (null,#{nid},#{status},#{tele},#{message},#{shijian},#{ntitle},#{username})")
    void add(comment comment);
    //查询一个新闻下面的所有评论
    @Select("select * from comment where nid=#{nid} order by shijian desc,id desc")
    List<comment> selectAllByNid(@Param("nid") int nid);
}
