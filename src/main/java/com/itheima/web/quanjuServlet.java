package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.*;
import com.itheima.service.HelpService;
import com.itheima.service.NewsService;
import com.itheima.service.PetService;
import com.itheima.service.impl.HelpServiceimpl;
import com.itheima.service.impl.NewsServiceimpl;
import com.itheima.service.impl.PetServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/quantised/*")
public class quanjuServlet extends BaseServlet {
    private PetService petService=new PetServiceImpl();
    private HelpService HelpService=new HelpServiceimpl();
    private NewsService NewsService=new NewsServiceimpl();
    public void qsearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受文章数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        guanjianci gjc=JSON.parseObject(params,guanjianci.class);
        String keyword=gjc.getKeyword();
        System.out.println(keyword);
        List<help> helps=HelpService.hquanju(keyword);
        List<pet> pets=petService.pquanju(keyword);
        List<news> newss=NewsService.nquanju(keyword);
        System.out.println(helps);
        System.out.println(pets);
        System.out.println(newss);
        List<List> quanjushuzu=new ArrayList<>();
        quanjushuzu.add(helps);
        quanjushuzu.add(pets);
        quanjushuzu.add(newss);
        System.out.println(quanjushuzu);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(quanjushuzu);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
}
