package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.PageBean;
import com.itheima.entity.ro;
import com.itheima.service.MinganService;
import com.itheima.service.RoService;
import com.itheima.service.impl.MinganServiceimpl;
import com.itheima.service.impl.RoServiceimpl;
import com.itheima.util.SensitiveWordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/ro/*")
public class RoServlet extends BaseServlet {
    private RoService roService=new RoServiceimpl();
    private MinganService minganService=new MinganServiceimpl();
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        ro ro= JSON.parseObject(params,ro.class);
        SensitiveWordUtil.init();//词库初始化，将所有数据库敏感词取出
        //替换投诉内容中的敏感词
        String tousustr = SensitiveWordUtil.replaceSensitiveWord(ro.getMessage(), '*', SensitiveWordUtil.MinMatchTYpe);
        //将过滤后的投诉内容重新复制
        ro.setMessage(tousustr);
        roService.add(ro);
        response.getWriter().write("success");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        ro ro= JSON.parseObject(params,ro.class);
        roService.update(ro);
        response.getWriter().write("success");
    }

    public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _id = request.getParameter("id");
        int id = Integer.parseInt(_id);
        roService.deleteByid(id);
        response.getWriter().write("success");
    }

    public void youxiaojinggao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _bstatus = request.getParameter("bstatus");
        int bstatus = Integer.parseInt(_bstatus);
        String btele = request.getParameter("btele");
        int num=roService.youxiaojinggao(bstatus,btele);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(num);
    }

    public void jinggao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _bstatus = request.getParameter("bstatus");
        int bstatus = Integer.parseInt(_bstatus);
        String btele = request.getParameter("btele");
        List<ro> ros=roService.jinggao(bstatus,btele);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(ros);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void fengjin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _bstatus = request.getParameter("bstatus");
        int bstatus = Integer.parseInt(_bstatus);
        String btele = request.getParameter("btele");
        ro ro=roService.fengjin(bstatus,btele);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(ro);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收参数 当前页码和每页展示条数 url?currentPage=1&pageSize=5
        String _currentPage=request.getParameter("currentPage");
        String _pageSize=request.getParameter("pageSize");
        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSize);
        BufferedReader br=request.getReader();
        String params=br.readLine();
        ro ro= JSON.parseObject(params,ro.class);
        //2.调用service查询
        PageBean<ro> pageBean=roService.selectByPageAndCondition(currentPage,pageSize,ro);

        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pageBean);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受宠物数据
        BufferedReader br = request.getReader();
        String params = br.readLine();
        int[] ids = JSON.parseObject(params, int[].class);
        roService.deleteByids(ids);
        response.getWriter().write("success");
    }
}
