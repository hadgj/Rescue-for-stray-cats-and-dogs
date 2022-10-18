package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.PageBean;
import com.itheima.entity.apply;
import com.itheima.service.ApplyService;
import com.itheima.service.impl.ApplyServiceimpl;
import com.itheima.util.SensitiveWordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/apply/*")
public class ApplyServlet extends BaseServlet {
    private ApplyService ApplyService=new ApplyServiceimpl();
    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收参数 当前页码和每页展示条数 url?currentPage=1&pageSize=5
        String _currentPage=request.getParameter("currentPage");
        String _pageSize=request.getParameter("pageSize");
        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSize);
        BufferedReader br=request.getReader();
        String params=br.readLine();
        apply apply= JSON.parseObject(params,apply.class);
        PageBean<apply> pageBean= ApplyService.selectByPageAndCondition(currentPage,pageSize,apply);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pageBean);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String petIsApply=request.getParameter("petIsApply");
        Integer apstate= Integer.valueOf(request.getParameter("apstate"));
        Integer pid= Integer.valueOf(request.getParameter("pid"));
        ApplyService.update(petIsApply,apstate,pid);
        response.getWriter().write("success");
    }

    public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String params=br.readLine();
        int[] ids= JSON.parseObject(params,int[].class);
        ApplyService.deleteByids(ids);
        response.getWriter().write("success");
    }

    public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _id=request.getParameter("apid");
        int id=Integer.parseInt(_id);
        ApplyService.deleteByid(id);
        response.getWriter().write("success");
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        apply apply= JSON.parseObject(params,apply.class);
        SensitiveWordUtil.init();//词库初始化，将所有数据库敏感词取出
        //替换投诉内容中的敏感词
        String applystr = SensitiveWordUtil.replaceSensitiveWord(apply.getApmessage(), '*', SensitiveWordUtil.MinMatchTYpe);
        //将过滤后的投诉内容重新复制
        apply.setApmessage(applystr);
        ApplyService.add(apply);
        response.getWriter().write("success");
    }

    public void selectByRtele(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tele=request.getParameter("tele");
        List<apply> applies=ApplyService.selectByRtele(tele);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(applies);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void updateapply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        apply apply= JSON.parseObject(params,apply.class);
        ApplyService.updateapply(apply);
        response.getWriter().write("success");
    }
}
