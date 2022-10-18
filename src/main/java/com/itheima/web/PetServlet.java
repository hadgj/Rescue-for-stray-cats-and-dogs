package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.PageBean;
import com.itheima.entity.pet;
import com.itheima.service.PetService;
import com.itheima.service.impl.PetServiceImpl;
import com.itheima.util.SensitiveWordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/pet/*")
public class PetServlet extends BaseServlet {
    private PetService petService=new PetServiceImpl();
    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 调用Service查询
        List<pet> pets = petService.selectAll();

        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pets);

        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        pet pet= JSON.parseObject(params,pet.class);
        SensitiveWordUtil.init();//词库初始化，将所有数据库敏感词取出
        //替换投诉内容中的敏感词
        String petstr = SensitiveWordUtil.replaceSensitiveWord(pet.getPetDsc(), '*', SensitiveWordUtil.MinMatchTYpe);
        //将过滤后的投诉内容重新复制
        pet.setPetDsc(petstr);
        petService.add(pet);
        response.getWriter().write("success");
    }

    public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        int[] ids= JSON.parseObject(params,int[].class);
        petService.deleteByids(ids);
        response.getWriter().write("success");
    }

    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收参数 当前页码和每页展示条数 url?currentPage=1&pageSize=5
        String _currentPage=request.getParameter("currentPage");
        String _pageSize=request.getParameter("pageSize");
        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSize);
        //2.调用service查询
        PageBean<pet> pageBean=petService.selectByPage(currentPage,pageSize);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pageBean);
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
        pet pet= JSON.parseObject(params,pet.class);
        //2.调用service查询
        PageBean<pet> pageBean=petService.selectByPageAndCondition(currentPage,pageSize,pet);

        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pageBean);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受宠物数据
        String _id=request.getParameter("petID");
        int id=Integer.parseInt(_id);
        petService.deleteByid(id);
        response.getWriter().write("success");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        pet pet= JSON.parseObject(params,pet.class);
        petService.update(pet);
        response.getWriter().write("success");
    }

    public void selectByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _id=request.getParameter("petID");
        int id=Integer.parseInt(_id);
        //1. 调用Service查询
        pet pet = petService.selectByid(id);

        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pet);

        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectnewid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id=petService.selectnewid();
        //3. 响应数据
        response.getWriter().write(id+"");
    }

    public void selectByotele(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String otele=request.getParameter("otele");
        List<pet> pets=petService.selectByotele(otele);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pets);

        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void petfour(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1. 调用Service查询
        List<pet> pets=petService.petfour();
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pets);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);

    }

}




































