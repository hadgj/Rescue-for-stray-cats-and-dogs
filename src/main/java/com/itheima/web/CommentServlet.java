package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.PageBean;
import com.itheima.entity.comment;
import com.itheima.service.CommentService;
import com.itheima.service.impl.CommentServiceimpl;
import com.itheima.util.SensitiveWordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/comment/*")
public class CommentServlet extends BaseServlet {
    private CommentService commentService=new CommentServiceimpl();
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        comment comment= JSON.parseObject(params,comment.class);
        SensitiveWordUtil.init();//词库初始化，将所有数据库敏感词取出
        //替换投诉内容中的敏感词
        String commentstr = SensitiveWordUtil.replaceSensitiveWord(comment.getMessage(), '*', SensitiveWordUtil.MinMatchTYpe);
        //将过滤后的投诉内容重新复制
        comment.setMessage(commentstr);
        commentService.add(comment);
        response.getWriter().write("success");
    }

    public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受宠物数据
        BufferedReader br = request.getReader();
        String params = br.readLine();
        int[] ids = JSON.parseObject(params, int[].class);
        commentService.deleteByids(ids);
        response.getWriter().write("success");
    }

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收参数 当前页码和每页展示条数 url?currentPage=1&pageSize=5
        String _currentPage=request.getParameter("currentPage");
        String _pageSize=request.getParameter("pageSize");
        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSize);
        BufferedReader br=request.getReader();
        String params=br.readLine();
        comment comment= JSON.parseObject(params,comment.class);
        //2.调用service查询
        PageBean<comment> pageBean=commentService.selectByPageAndCondition(currentPage,pageSize,comment);

        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pageBean);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _id = request.getParameter("id");
        int id = Integer.parseInt(_id);
        commentService.deleteByid(id);
        response.getWriter().write("success");
    }

    public void selectAllByNid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nid = request.getParameter("nid");
        int id = Integer.parseInt(nid);
        List<comment> comments=commentService.selectAllByNid(id);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(comments);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
}
