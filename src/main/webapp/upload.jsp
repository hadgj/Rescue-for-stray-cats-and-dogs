<%--
  Created by IntelliJ IDEA.
  User: 刘超
  Date: 2022/5/2
  Time: 2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>文件上传实例 - 菜鸟教程</title>
    <script src="js/axios-0.18.0.js"></script>
</head>
<body>



    选择图片：<input type="file" id="input" name="input" multiple="multiple" />
<button type="button" onclick="onc()">提交</button>
<script type="text/javascript">
    function onc(){
        var files=document.getElementById("input");
        var filesdata=files.files;
        console.log(filesdata)
        //页面加载完成之后，发送异步数据请求，查询数据
        axios({method:"post",
            url:"http://localhost:9090/web-demo/UploadServlet",
            data:filesdata
        }).then(function (resp){

        })
    }
</script>
</body>
</html>
