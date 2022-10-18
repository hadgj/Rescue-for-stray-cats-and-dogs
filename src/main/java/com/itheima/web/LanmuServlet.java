package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.lanmu;
import com.itheima.service.LanmuService;
import com.itheima.service.impl.LanmuServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/LanmuServlet/*")
public class LanmuServlet extends BaseServlet {
private LanmuService lanmuService=new LanmuServiceimpl();

    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<lanmu> lanmus=lanmuService.selectAll();
        String jsonString = JSON.toJSONString(lanmus);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String params=br.readLine();
        lanmu lanmu= JSON.parseObject(params,lanmu.class);
        lanmuService.update(lanmu);
        response.getWriter().write("success");
    }
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String params=br.readLine();
        lanmu lanmu= JSON.parseObject(params,lanmu.class);
        if(lanmu!=null){
            lanmuService.add(lanmu);
        }
        response.getWriter().write("success");
    }
    public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _id=request.getParameter("id");
        int id=Integer.parseInt(_id);
        lanmuService.deleteByid(id);
        response.getWriter().write("success");
    }
    public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String params=br.readLine();
        int[] ids= JSON.parseObject(params,int[].class);
        lanmuService.deleteByids(ids);
        response.getWriter().write("success");
    }

}
