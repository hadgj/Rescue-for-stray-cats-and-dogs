package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.Regular;
import com.itheima.entity.admin;
import com.itheima.entity.organization;
import com.itheima.service.UsersService;
import com.itheima.service.impl.UsersServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/loginandregi/*")
public class loginandregiServlet extends BaseServlet {
    private UsersService UsersService=new UsersServiceimpl();
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //1.获取电话和密码还有身份status
        String tele=request.getParameter("tele");
        String password=request.getParameter("password");
        Integer status= Integer.valueOf(request.getParameter("status"));
        //2.调用sevice查询
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer=response.getWriter();
        if(status==1){
            admin admin= UsersService.selectadmin(tele,password);
            if(admin!=null){
                //管理员登陆成功
                //将登陆成功后的admin对象，存储到session
                HttpSession session=request.getSession();
                session.setAttribute("admin",admin);
                //response.sendRedirect("admin.html");
                //告诉前台用户名或密码错误
                response.getWriter().write("success1");
            }else{
                //告诉前台用户名或密码错误
                response.getWriter().write("error");
            }
        }else if(status==2){
            Regular regular=UsersService.selectregular(tele,password);
            if(regular!=null){
                HttpSession session=request.getSession();
                session.setAttribute("regular",regular);
                //普通用户登陆成功
                if(regular.getStatus()==4)
                {
                    response.getWriter().write("fengjin");
                }
                else
                {
                    response.getWriter().write("success");
                }
            }else{
                //告诉前台用户名或密码错误
                response.getWriter().write("error");
            }
        }else{
            organization organization=UsersService.selectorganization(tele,password);
            if(organization!=null){
                HttpSession session=request.getSession();
                session.setAttribute("organization",organization);
                //救助机构登陆成功
                if(organization.getStatus()==4)
                {
                    response.getWriter().write("fengjin");
                }
                else
                {
                    response.getWriter().write("success");
                }
            }else{
                //告诉前台用户名或密码错误
                response.getWriter().write("error");
            }
        }
    }

    public void radd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String params=br.readLine();
        Regular regular=JSON.parseObject(params,Regular.class);
            //调用service，根据电话查询用户对象
            Regular r=UsersService.selectByregulartele(regular.getTele());
            if(r==null)
            {
                //用户名不存在，添加用户
                UsersService.addregular(regular);
                response.getWriter().write("success");
            }
            else
            {
                response.getWriter().write("error");
            }

    }

    public void oadd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        organization organ= JSON.parseObject(params,organization.class);
            //调用service，根据电话查询用户对象
            organization o=UsersService.selectByorganizationtele(organ.getTele());
            if(o==null)
            {
                UsersService.addorganization(organ);
                response.getWriter().write("success");
            }
            else
            {
                response.getWriter().write("error");
            }
    }
}
