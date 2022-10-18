package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.PageBean;
import com.itheima.entity.organization;
import com.itheima.entity.ro;
import com.itheima.service.OrganizationService;
import com.itheima.service.RoService;
import com.itheima.service.impl.OrganizationServiceimpl;
import com.itheima.service.impl.RoServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(value = "/organization/*")
public class OrganizationServlet extends BaseServlet {
  private OrganizationService OrganizationService=new OrganizationServiceimpl();
  private RoService roService=new RoServiceimpl();
  public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //1. 接收参数 当前页码和每页展示条数 url?currentPage=1&pageSize=5
    String _currentPage=request.getParameter("currentPage");
    String _pageSize=request.getParameter("pageSize");
    int currentPage=Integer.parseInt(_currentPage);
    int pageSize=Integer.parseInt(_pageSize);
    BufferedReader br=request.getReader();
    String params=br.readLine();
    organization organization = JSON.parseObject(params,organization.class);
    PageBean<organization> pageBean= OrganizationService.selectByPageAndCondition(currentPage,pageSize,organization);
    //2. 将集合转换为JSON数据   序列化
    String jsonString = JSON.toJSONString(pageBean);
    //3. 响应数据
    response.setContentType("text/json;charset=utf-8");
    response.getWriter().write(jsonString);
  }

  public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BufferedReader br=request.getReader();
    String params=br.readLine();
    String[] ids= JSON.parseObject(params,String[].class);
    OrganizationService.deleteByids(ids);
    response.getWriter().write("success");
  }

  public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String _id=request.getParameter("tele");
    OrganizationService.deleteByid(_id);
    response.getWriter().write("success");
  }

  public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   String status=request.getParameter("status");
   String tele=request.getParameter("tele");
   OrganizationService.update(Integer.parseInt(status),tele);
    response.getWriter().write("success");
  }

  public void getUsername(HttpServletRequest request, HttpServletResponse response)throws Exception{
    //先从session中获取到之前存在session中的用户信息，然后通过ObjectMapper输出返回一个json数据给html页面，由页面去解析这个json数据
    organization organization=(organization)request.getSession().getAttribute("organization");
    if(organization!=null){
      String jsonString = JSON.toJSONString(organization);
      System.out.println(jsonString);
      //3. 响应数据
      response.setContentType("text/json;charset=utf-8");
      response.getWriter().write(jsonString);
    }
  }

  public void upjichu(HttpServletRequest request, HttpServletResponse response)throws Exception{
    //1.接受宠物数据
    BufferedReader br=request.getReader();
    String params=br.readLine();
    organization organization= JSON.parseObject(params,organization.class);
    OrganizationService.upjichu(organization);
    response.getWriter().write("success");
  }

  public void selectByid(HttpServletRequest request, HttpServletResponse response)throws Exception{
    String _id=request.getParameter("tele");
    //1. 调用Service查询
    organization organization = OrganizationService.selectByid(_id);

    //2. 将集合转换为JSON数据   序列化
    String jsonString = JSON.toJSONString(organization);

    //3. 响应数据
    response.setContentType("text/json;charset=utf-8");
    response.getWriter().write(jsonString);
  }

  public void jinyong(HttpServletRequest request, HttpServletResponse response)throws Exception{
    String _id=request.getParameter("tele");
    OrganizationService.update(4,_id);
    //3. 响应数据
    response.setContentType("text/json;charset=utf-8");
    response.getWriter().write("success");
  }

  public void jinggao(HttpServletRequest request, HttpServletResponse response)throws Exception{
    BufferedReader br=request.getReader();
    String params=br.readLine();
    ro ro= JSON.parseObject(params,ro.class);
    String _id=request.getParameter("tele");
    int num=roService.youxiaojinggao(3,_id);
    System.out.println(num);
    if(num>5){
      OrganizationService.update(4,_id);
      roService.update(ro);
      //3. 响应数据
      response.setContentType("text/json;charset=utf-8");
      response.getWriter().write("need");
    }else{
      roService.update(ro);
      //3. 响应数据
      response.setContentType("text/json;charset=utf-8");
      response.getWriter().write("uneed");
    }
  }
}



















