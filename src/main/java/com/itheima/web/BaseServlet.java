package com.itheima.web;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 替换HttpServlet,根据请求的最后一段路径来进行方法分发
 */

public class BaseServlet extends HttpServlet {

    //根据请求的最后一段路径来进行方法分发
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        /* 跨域 */
//        /* 允许跨域的主机地址 */
//        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
//        /* 允许跨域的请求头 【这个不加 POST 请求会跨域 因为我这里的POST头加了 】 */
//        resp.setHeader("Access-Control-Allow-Headers", "*");
//        /* 允许跨域的请求方法GET, POST, HEAD 等 */
//        resp.setHeader("Access-Control-Allow-Methods", "*");
//
//        /* 是否携带cookie */
//        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.addHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept");
        resp.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
        resp.addHeader("Access-Control-Max-Age", "0");
        resp.addHeader("Access-Control-Allow-Credentials","true");
        resp.addHeader("XDomainRequestAllowed","1");
        req.setCharacterEncoding("utf-8");
        //1. 获取请求路径
        String uri = req.getRequestURI(); // /brand-case/brand/selectAll
//        System.out.println(uri);
        //2. 获取最后一段路径，方法名
        int index = uri.lastIndexOf('/');
        String methodName = uri.substring(index + 1); //  /selectAll? selectAll?
//        System.out.println(methodName);

        //2. 执行方法
        //2.1 获取BrandServlet /UserServlet 字节码对象 Class
        //System.out.println(this);

        Class<? extends BaseServlet> cls = this.getClass();
        //2.2 获取方法 Method对象
        try {
            Method method = cls.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //2.3 执行方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
