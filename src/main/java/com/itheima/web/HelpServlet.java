package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.entity.PageBean;
import com.itheima.entity.Regular;
import com.itheima.entity.help;
import com.itheima.entity.oh;
import com.itheima.service.HelpService;
import com.itheima.service.OhService;
import com.itheima.service.RegularService;
import com.itheima.service.impl.HelpServiceimpl;
import com.itheima.service.impl.OhServiceimpl;
import com.itheima.service.impl.RegularServiceimpl;
import com.itheima.util.SensitiveWordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet(value = "/help/*")
public class HelpServlet extends BaseServlet {
    private HelpService HelpService=new HelpServiceimpl();
    private OhService ohService=new OhServiceimpl();
    private RegularService RegularService=new RegularServiceimpl();
    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收参数 当前页码和每页展示条数 url?currentPage=1&pageSize=5
        String _currentPage=request.getParameter("currentPage");
        String _pageSize=request.getParameter("pageSize");
        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSize);
        BufferedReader br=request.getReader();
        String params=br.readLine();
        help help= JSON.parseObject(params,help.class);
        PageBean<help> pageBean= HelpService.selectByPageAndCondition(currentPage,pageSize,help);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pageBean);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String params=br.readLine();
        int[] ids= JSON.parseObject(params,int[].class);
        HelpService.deleteByids(ids);
        response.getWriter().write("success");
    }

    public void deleteByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _id=request.getParameter("id");
        int id=Integer.parseInt(_id);
        HelpService.deleteByid(id);
        response.getWriter().write("success");
    }

    public void selectByid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quanxian;
        String _id=request.getParameter("id");
        int id=Integer.parseInt(_id);
        help help= HelpService.selectByid(id);
        String tele=request.getParameter("tele");
        System.out.println(id);
        System.out.println(tele);
        oh oh=ohService.selectByteleandhid(id,tele);
        if(oh==null)
        {
            //权限只能是123，1是看到处理中2是看到已完成3是按钮开始处理
            oh oh1=ohService.selectByid(id);
            if(oh1==null){
            //权限是3
                quanxian=3;
            }else if(oh1.getOhstate()==2){
                quanxian=1;
            }else{
                quanxian=2;
            }
        }
        else
        {
            //权限是45，4是看到两个按钮一个是终止救助，一个是完成救助按钮，5是看到已完成信息
            if(oh.getOhstate()==2){
                quanxian=4;
            }else{
                quanxian=5;
            }
        }
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(help);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString+quanxian);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        help help= JSON.parseObject(params,help.class);
        SensitiveWordUtil.init();//词库初始化，将所有数据库敏感词取出
        //替换投诉内容中的敏感词
        String helpstr = SensitiveWordUtil.replaceSensitiveWord(help.getHdesc(), '*', SensitiveWordUtil.MinMatchTYpe);
        //将过滤后的投诉内容重新复制
        help.setHdesc(helpstr);
        HelpService.add(help);
        response.getWriter().write("success");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1.接受宠物数据
        BufferedReader br=request.getReader();
        String params=br.readLine();
        help help= JSON.parseObject(params,help.class);
        System.out.println(help);
        HelpService.update(help);
        response.getWriter().write("success");
    }

    public void selectByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tele=request.getParameter("otele");
        List<oh> ohs=ohService.selectBytele(tele);
        System.out.println(ohs);
        System.out.println(ohs.size());
        int[] ids=new int[ohs.size()];
        for(int i=0;i<ohs.size();i++){
            ids[i]=ohs.get(i).getHid();
        }
        System.out.println(ids);
        List<help> helps=HelpService.selectByids(ids);
        //2. 将集合转换为JSON数据   序列化
       String jsonString = JSON.toJSONString(helps);
        //3. 响应数据
       response.setContentType("text/json;charset=utf-8");
      response.getWriter().write(jsonString);
    }

    public void selectAllByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid=request.getParameter("rid");
        List<help> helps=HelpService.selectAllByRid(rid);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(helps);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void youxian(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     List<Regular> rs=RegularService.selectAllByNum();
     help[] helps=new help[rs.size()];
    for(int i=0;i<rs.size();i++){
         helps[i]=HelpService.youxian(rs.get(i).getTele());
     }
        List<help> list = Arrays.asList(helps);
        List<help> collect = list.stream().map(e -> {
            if (e == null) {
                return null;
            }
            return e;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(collect);
        //3. 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
}
