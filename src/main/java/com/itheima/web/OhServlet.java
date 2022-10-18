package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.oh;
import com.itheima.service.OhService;
import com.itheima.service.impl.OhServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/oh/*")
public class OhServlet extends BaseServlet {
  private OhService ohService=new OhServiceimpl();
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        oh oh= JSON.parseObject(params,oh.class);
        ohService.add(oh);
        response.getWriter().write("success");
    }

    public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _hid = request.getParameter("hid");
        int hid = Integer.parseInt(_hid);
        ohService.deleteByid(hid);
        response.getWriter().write("success");
    }

    public void selectByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _hid = request.getParameter("hid");
        int hid = Integer.parseInt(_hid);
        oh oh=ohService.selectByid(hid);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(oh);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectByteleandhid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _hid = request.getParameter("hid");
        int hid = Integer.parseInt(_hid);
        String otele = request.getParameter("otele");
        oh oh=ohService.selectByteleandhid(hid,otele);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(oh);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectBytele(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String otele = request.getParameter("otele");
        List<oh> ohs=ohService.selectBytele(otele);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(ohs);

        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        oh oh= JSON.parseObject(params,oh.class);
        ohService.update(oh);
        response.getWriter().write("success");
    }
}
