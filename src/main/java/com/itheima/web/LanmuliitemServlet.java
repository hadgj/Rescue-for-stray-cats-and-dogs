package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.PageBean;
import com.itheima.entity.lanmuliitem;
import com.itheima.service.LanmuliitemService;
import com.itheima.service.impl.LanmuliitemServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/LanmuliitemServlet/*")
public class LanmuliitemServlet extends BaseServlet {
private LanmuliitemService lanmuliitemService=new LanmuliitemServiceimpl();

    public void selectByParentid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _parentid=request.getParameter("parentid");
        int parentid=Integer.parseInt(_parentid);
        List<lanmuliitem> lanmuliitems=lanmuliitemService.selectByParentid(parentid);
        String jsonString = JSON.toJSONString(lanmuliitems);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String params=br.readLine();
        lanmuliitem lanmuliitem= JSON.parseObject(params,lanmuliitem.class);
        lanmuliitemService.update(lanmuliitem);
        response.getWriter().write("success");
    }
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String params=br.readLine();
        lanmuliitem lanmuliitem= JSON.parseObject(params,lanmuliitem.class);
        lanmuliitemService.add(lanmuliitem);
        response.getWriter().write("success");
    }
    public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _id=request.getParameter("id");
        int id=Integer.parseInt(_id);
        lanmuliitemService.deleteByid(id);
        response.getWriter().write("success");
    }
    public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String params=br.readLine();
        int[] ids= JSON.parseObject(params,int[].class);
        lanmuliitemService.deleteByids(ids);
        response.getWriter().write("success");
    }

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _currentPage=request.getParameter("currentPage");
        String _pageSize=request.getParameter("pageSize");
        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSize);
        BufferedReader br=request.getReader();
        String params=br.readLine();
        lanmuliitem lanmuliitem= JSON.parseObject(params,lanmuliitem.class);
        PageBean<lanmuliitem> pageBean=lanmuliitemService.selectByPageAndCondition(currentPage,pageSize,lanmuliitem);
        String jsonString = JSON.toJSONString(pageBean);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }


}
