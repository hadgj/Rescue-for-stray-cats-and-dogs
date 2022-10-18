<%--
  Created by IntelliJ IDEA.
  User: 刘超
  Date: 2022/5/2
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>文件上传实例 - 菜鸟教程</title>
</head>
<body>
<h1>文件上传实例 - 菜鸟教程</h1>
<form method="post" action="/web-demo/UploadServlet/upload" enctype="multipart/form-data">
    选择一个文件:
    <input type="file" name="uploadFile" multiple/>
    <br/><br/>
    <input type="submit" value="上传" />
</form>
</body>
</html>
