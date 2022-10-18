package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.admin;
import com.itheima.service.AdminService;
import com.itheima.service.impl.AdminServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(value = "/admin/*")
public class AdminServlet extends BaseServlet {
    private AdminService AdminService=new AdminServiceimpl();

    public void getUsername(HttpServletRequest request, HttpServletResponse response)throws Exception{
        //先从session中获取到之前存在session中的用户信息，然后通过ObjectMapper输出返回一个json数据给html页面，由页面去解析这个json数据
        admin admin=(admin)request.getSession().getAttribute("admin");
        if(admin!=null){
            String jsonString = JSON.toJSONString(admin);
            System.out.println(jsonString);
            //3. 响应数据
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(jsonString);
        }
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受文章数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        admin admin= JSON.parseObject(params,admin.class);
        AdminService.update(admin);
        response.getWriter().write("success");
    }

    public void selectbytele(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tele=request.getParameter("tele");
        admin admin=AdminService.selectbytele(tele);
        String jsonString = JSON.toJSONString(admin);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
}
