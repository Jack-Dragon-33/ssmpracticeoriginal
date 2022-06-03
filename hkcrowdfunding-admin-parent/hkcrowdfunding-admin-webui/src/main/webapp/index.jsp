<%--
  Created by IntelliJ IDEA.
  User: 86183
  Date: 2021/12/27
  Time: 9:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello</title>
<%--    http://localhost:8080/hkcrowdfunding_admin_webui_war/ssm.html--%>
<%--     base 标签必须写在 head 标签内部--%>
<%--     base 标签必须在所有“带具体路径”的标签的前面--%>
<%--     serverName 部分 EL 表达式和 serverPort 部分 EL 表达式之间必须写“:”--%>
<%--     serverPort部分EL表达式和contextPath部分EL表达式之间绝对不能写“/”--%>
<%--     原因：contextPath 部分 EL 表达式本身就是“/”开头--%>
<%--     如果多写一个“/”会干扰 Cookie 的工作机制--%>
<%--     serverPort 部分 EL 表达式后面必须写“/”--%>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script src="jquery/jquery-2.1.1.min.js"></script>

    <script>
        $(function(){
            var array=[1,2,3]
            var response=JSON.stringify(array)
            $("#btn").click(function (){
                $.ajax({
                    "url":"send/array.json",
                    "data":response,
                    "type":"post",
                    "contentType":"application/json;charset=UTF-8",
                    "dataType":"text",
                    "success":function (data){
                        alert(data)
                    }
                })
            })
            $("#btn1").click(function (){
                layer.msg("layer")
            })
        })

    </script>
</head>
<body>
<a href="ssm.html">hello,你好</a>
<button id="btn">发送数组</button>
<button id="btn1">layer弹框</button>
</body>
</html>
