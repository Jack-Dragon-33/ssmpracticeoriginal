<%--
  Created by IntelliJ IDEA.
  User: 86183
  Date: 2021/12/27
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="jquery/jquery-2.1.1.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
   <script>
       $(function (){
           $("button").click(function (){
               window.history.back()
           })
       })
   </script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">
    <h2 style="text-align: center" class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 尚筹网消息提醒！！！</h2>
    <h3 style="text-align: center">${requestScope.exception.message}</h3><br>
    <button style="width: 100px;margin: 50px auto 0px auto" class="btn btn-lg btn-success btn-block">点我返回</button>

</div>

</body>
</html>