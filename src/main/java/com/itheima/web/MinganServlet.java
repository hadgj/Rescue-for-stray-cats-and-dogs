package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.PageBean;
import com.itheima.entity.mingan;
import com.itheima.service.MinganService;
import com.itheima.service.impl.MinganServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(value = "/mingan/*")
public class MinganServlet extends BaseServlet {
 private MinganService minganService=new MinganServiceimpl();

 public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  //1. 接收参数 当前页码和每页展示条数 url?currentPage=1&pageSize=5
  String _currentPage=request.getParameter("currentPage");
  String _pageSize=request.getParameter("pageSize");
  int currentPage=Integer.parseInt(_currentPage);
  int pageSize=Integer.parseInt(_pageSize);
  BufferedReader br=request.getReader();
  String params=br.readLine();
  mingan mingan= JSON.parseObject(params,mingan.class);
  PageBean<mingan> pageBean= minganService.selectByPageAndCondition(currentPage,pageSize,mingan);
  //2. 将集合转换为JSON数据   序列化
  String jsonString = JSON.toJSONString(pageBean);
  //3. 响应数据
  response.setContentType("text/json;charset=utf-8");
  response.getWriter().write(jsonString);
 }

 public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  //1.接受宠物数据
  String _id=request.getParameter("id");
  int id=Integer.parseInt(_id);
  minganService.deleteByid(id);
  response.getWriter().write("success");
 }

 public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  //1.接受宠物数据
  BufferedReader br=request.getReader();
  String params=br.readLine();
  int[] ids= JSON.parseObject(params,int[].class);
  minganService.deleteByids(ids);
  response.getWriter().write("success");
 }

 public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  //1.接受宠物数据
  BufferedReader br=request.getReader();
  String params=br.readLine();
  String[] list= JSON.parseObject(params,String[].class);
  for(int i=0;i<list.length;i++){
   mingan mingan1=new mingan();
   mingan1.setStr(list[i]);
   minganService.add(mingan1);
  }
  response.getWriter().write("success");
 }


}
