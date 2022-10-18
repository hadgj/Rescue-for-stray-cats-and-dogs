package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.zan;
import com.itheima.service.ZanService;
import com.itheima.service.impl.ZanServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/zan/*")
public class ZanServlet extends BaseServlet {
  private ZanService zanService=new ZanServiceimpl();
    public void selectByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tele=request.getParameter("tele");
        String _nid=request.getParameter("nid");
        int nid=Integer.parseInt(_nid);
        String _status=request.getParameter("status");
        int status=Integer.parseInt(_status);
        zan zan=zanService.selectByid(tele,nid,status);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(zan);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tele=request.getParameter("tele");
        String _nid=request.getParameter("nid");
        int nid=Integer.parseInt(_nid);
        String _status=request.getParameter("status");
        int status=Integer.parseInt(_status);
        zanService.deleteByid(tele,nid,status);
        response.getWriter().write("success");
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        zan zan= JSON.parseObject(params,zan.class);
        zanService.add(zan);
        response.getWriter().write("success");
    }

    public void shoucang(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tele=request.getParameter("tele");
        String _status=request.getParameter("status");
        int status=Integer.parseInt(_status);
        List<zan> zans=zanService.shoucang(tele,status);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(zans);

        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
}
