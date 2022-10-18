package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.PageBean;
import com.itheima.entity.news;
import com.itheima.service.NewsService;
import com.itheima.service.impl.NewsServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(value = "/news/*")
public class NewsServlet extends BaseServlet {
    private NewsService NewsService=new NewsServiceimpl();

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收参数 当前页码和每页展示条数 url?currentPage=1&pageSize=5
        String _currentPage=request.getParameter("currentPage");
        String _pageSize=request.getParameter("pageSize");
        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSize);
        BufferedReader br=request.getReader();
        String params=br.readLine();
        news news= JSON.parseObject(params,news.class);
        System.out.println(news);
        PageBean<news> pageBean= NewsService.selectByPageAndCondition(currentPage,pageSize,news);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pageBean);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        news news= JSON.parseObject(params,news.class);
        NewsService.add(news);
        response.getWriter().write("success");
    }

    public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受文章ID数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        int[] ids= JSON.parseObject(params,int[].class);
        NewsService.deleteByids(ids);
        response.getWriter().write("success");
    }

    public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受宠物数据
        String _id=request.getParameter("newsID");
        int id=Integer.parseInt(_id);
        NewsService.deleteByid(id);
        response.getWriter().write("success");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受文章数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        news news= JSON.parseObject(params,news.class);
        NewsService.update(news);
        response.getWriter().write("success");
    }

    public void selectByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _id=request.getParameter("newsID");
        int id=Integer.parseInt(_id);
        news news=NewsService.selectByid(id);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(news);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void topten(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<news> newss1=NewsService.selectAll();
        Date day=new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i=0;i<newss1.size();i++){
            Date d2 = null;
            try {
                d2 = format.parse(newss1.get(i).getNewsDate());
                long diff = day.getTime()-d2.getTime();
                long t = diff / (60 * 60 * 1000) % 24;
                t=t+2;
                BigDecimal redu= BigDecimal.valueOf(newss1.get(i).getNewsZan()/Math.pow(t,3.0/2));
                newss1.get(i).setNewsRedu(redu);
                System.out.println(newss1.get(i).getNewsRedu());
                NewsService.update(newss1.get(i));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<news> newss2=NewsService.topten();
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(newss2);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);

    }

    public void selectByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String params=br.readLine();
        int[] ids= JSON.parseObject(params,int[].class);
        List<news> newss=NewsService.selectByids(ids);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(newss);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void topfive(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<news> newss=NewsService.topfive();
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(newss);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

}
