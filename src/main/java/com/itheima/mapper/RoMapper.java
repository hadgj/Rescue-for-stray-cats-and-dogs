package com.itheima.mapper;


import com.itheima.entity.ro;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoMapper {
    //用户在前台投诉的时候用，增加一条投诉
    @Insert("insert into ro values (null,#{bstatus},#{btele},#{message},#{jtele},#{pic},#{jstate},#{jstatus})")
    void add(ro ro);
    //审核的时候用，管理员在后台把jstate的状态改成1，2，3，4
    @Update("update ro set bstatus=#{bstatus} ,btele=#{btele}, message=#{message}, jtele=#{jtele},pic=#{pic},jstate=#{jstate},jstatus=#{jstatus} where id=#{id}")
    void update(ro ro);
    //删除投诉，可能用不到
    @Delete("delete from ro where id=#{id}")
    void deleteByid(@Param("id") int id);
    //查询一个账号的警告处理的个数，当个数大于3个的时候，账号状态变为4，这个账号不能登录
    @Select("select count(*) from ro where bstatus=#{bstatus} and btele=#{btele} and jstate=2")
    int youxiaojinggao(@Param("bstatus") int bstatus,@Param("btele") String btele);
    //查询一个账号的警告处理的详情，就是登陆之后，要在开始先弹出在个人中心查看警告的弹窗，然后在个人中心我的警告查看我收到的警告的列表
    @Select("select * from ro where bstatus=#{bstatus} and btele=#{btele} and jstate=2 or jstate=3")
    List<ro> jinggao(@Param("bstatus") int bstatus, @Param("btele") String btele);
    //查询一个账号的封禁处理的详情,如果有的话，这个账号不能登录
    @Select("select * from ro where bstatus=#{bstatus} and btele=#{btele} and jstate=3")
    ro fengjin(@Param("bstatus") int bstatus, @Param("btele") String btele);
    //多选框删除投诉
    void deleteByids(@Param("ids") int[] ids);
    //分页查询投诉
    List<ro> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("ro") ro ro);
    //查询投诉记录总数
    int selectTotalCountByCondition(ro ro);
}
