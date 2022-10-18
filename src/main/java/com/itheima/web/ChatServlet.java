package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.chat;
import com.itheima.service.ChatService;
import com.itheima.service.impl.ChatServiceimpl;
import com.itheima.util.SensitiveWordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/chat/*")
public class ChatServlet extends BaseServlet {
    private ChatService chatService=new ChatServiceimpl();
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        chat chat= JSON.parseObject(params,chat.class);
        SensitiveWordUtil.init();//词库初始化，将所有数据库敏感词取出
        //替换投诉内容中的敏感词
        String chatstr = SensitiveWordUtil.replaceSensitiveWord(chat.getMessage(), '*', SensitiveWordUtil.MinMatchTYpe);
        //将过滤后的投诉内容重新复制
        chat.setMessage(chatstr);
        chatService.add(chat);
        response.getWriter().write("success");
    }

    public void selectchats(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _bstatus = request.getParameter("bstatus");
        int bstatus = Integer.parseInt(_bstatus);
        String btele = request.getParameter("btele");
        List<chat> chats=chatService.selectchats(bstatus,btele);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(chats);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
}
