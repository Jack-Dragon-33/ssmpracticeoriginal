<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/pages/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">

    $(function () {

        // 1.为分页操作准备初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        // 2.调用执行分页的函数，显示分页效果
        generatePage();

        // 3.给查询按钮绑定单击响应函数
        $("#searchBtn").click(function () {

            // ①获取关键词数据赋值给对应的全局变量
            window.keyword = $("#keywordInput").val();

            // ②调用分页函数刷新页面
            generatePage();

        });

        // 4.点击新增按钮打开模态框
        $("#showAddModalBtn").click(function () {

            $("#addModal").modal("show");

        });

        // 5.给新增模态框中的保存按钮绑定单击响应函数
        $("#saveRoleBtn").click(function () {

            // ①获取用户在文本框中输入的角色名称
            // #addModal表示找到整个模态框
            // 空格表示在后代元素中继续查找
            // [name=roleName]表示匹配name属性等于roleName的元素
            var roleName = $.trim($("#rolename").val());
            // ②发送Ajax请求
            $.ajax({
                "url": "role/save.json",
                "type": "post",
                "data": {
                    "name": roleName
                },
                "dataType": "json",
                "success": function (response) {

                    var result = response.operationResult;

                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 将页码定位到最后一页
                        window.pageNum = 99999999;

                        // 重新加载分页数据
                        generatePage();
                    }

                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.operationMessage);
                    }

                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });

            // 关闭模态框
            $("#addModal").modal("hide");

            // 清理模态框
            $("#addModal [name=roleName]").val("");
        });
        $("#updateRoleBtn").click(function (){
            var roleName = $.trim($("#updaterolename").val());
            var roleId = $.trim($("#updateid").val());
            $.ajax({
                "url": "role/update.json",
                "type": "post",
                "data": {
                    "id":roleId,
                    "name": roleName
                },
                "dataType": "json",
                "success": function (response) {

                    var result = response.operationResult;

                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 重新加载分页数据
                        generatePage();
                    }

                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.operationMessage);
                    }

                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            // 关闭模态框
            $("#updateModal").modal("hide");
        })
        // 8.点击确认模态框中的确认删除按钮执行删除
        $("#removeRoleBtn").click(function(){

            // 从全局变量范围获取roleIdArray，转换为JSON字符串
            var requestBody = JSON.stringify(window.roleIdArray);

            $.ajax({
                "url":"role/remove/by/role/id/array.json",
                "type":"post",
                "data":requestBody,
                "contentType":"application/json;charset=UTF-8",
                "dataType":"json",
                "success":function(response){

                    var result = response.operationResult;

                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 重新加载分页数据
                        generatePage();
                    }

                    if(result == "FAILED") {
                        layer.msg("操作失败！"+response.operationMessage);
                    }

                },
                "error":function(response){
                    layer.msg(response.status+" "+response.statusText);
                }
            });

            // 关闭模态框
            $("#confirmModal").modal("hide");
            $("#summaryBox").prop("checked", false);
        });

        // 9.给单条删除按钮绑定单击响应函数
        $("#rolePageBody").on("click",".removeBtn",function(){

            // 从当前按钮出发获取角色名称
            var roleName = $(this).parent().prev().text();

            // 创建role对象存入数组
            var roleArray = [{
                roleId:this.id,
                roleName:roleName
            }];

            // 调用专门的函数打开模态框
            showConfirmModal(roleArray);

        });

        // 10.给总的checkbox绑定单击响应函数
        $("#summaryBox").click(function(){

            // ①获取当前多选框自身的状态
            var currentStatus = this.checked;

            // ②用当前多选框的状态设置其他多选框
            $(".itemBox").prop("checked", currentStatus);

        });

        // 11.全选、全不选的反向操作
        $("#rolePageBody").on("click",".itemBox",function(){

            // 获取当前已经选中的.itemBox的数量
            var checkedBoxCount = $(".itemBox:checked").length;

            // 获取全部.itemBox的数量
            var totalBoxCount = $(".itemBox").length;

            // 使用二者的比较结果设置总的checkbox
            $("#summaryBox").prop("checked", checkedBoxCount == totalBoxCount);

        });

        // 12.给批量删除的按钮绑定单击响应函数
        $("#batchRemoveBtn").click(function(){

            // 创建一个数组对象用来存放后面获取到的角色对象
            var roleArray = [];

            // 遍历当前选中的多选框
            $(".itemBox:checked").each(function(){

                // 使用this引用当前遍历得到的多选框
                var roleId = this.id;

                // 通过DOM操作获取角色名称
                var roleName = $(this).parent().next().text();

                roleArray.push({
                    "roleId":roleId,
                    "roleName":roleName
                });
            });

            // 检查roleArray的长度是否为0
            if(roleArray.length == 0) {
                layer.msg("请至少选择一个执行删除");
                return ;
            }

            // 调用专门的函数打开模态框
            showConfirmModal(roleArray);
        });
        $("#rolePageBody").on("click",".checkBtn",function (){
            $("#assignModal").modal("show");
            window.roleId=this.id
            fillAuthTree();
        })
        $("#assignRoleBtn").click(function (){
            // 声明一个数组，用来存储所有被选取的节点
            var selectedArray=[];
            // 获取 zTreeObj 对象
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
            var checkNodes=zTreeObj.getCheckedNodes(true)
            // 遍历节点将备选取节点的值放到数组中
            for(var i=0;i<checkNodes.length;i++){
                var checkNode=checkNodes[i];
                selectedArray.push(checkNode.id)
            }
            // 定义一个对象将roleId和被选中的Authid以json的格式发送给后端
            var resquestData={"roleId":[window.roleId],"selectArray":selectedArray}
            $.ajax({
                "url":"assign/do/role/assign/auth.json",
                "data":JSON.stringify(resquestData),
                "type":"post",
                "contentType":"application/json;charset=utf-8",
                "dataType":"json",
                "success":function (response){
                    var result=response.operationResult;
                    if(result=="SUCCESS"){
                        layer.msg("操作成功")
                    }
                    if(result == "FAILED") {
                        layer.msg("操作失败！"+response.operationMessage);
                    }
                },
                "error":function (response){
                    layer.msg(response.status+" "+response.statusText);
                }
            })
            $("#assignModal").modal("hide")
        })
    });

</script>
<body>

<%@ include file="/WEB-INF/pages/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/pages/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="glyphicon glyphicon-th"></i> 数据列表
                    </h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float: left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning">
                            <i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float: right; margin-left: 10px;">
                        <i class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button
                            type="button"
                            id="showAddModalBtn" class="btn btn-primary"
                            style="float: right;">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear: both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody"></tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        function updateRole(id) {
            $.ajax({
                "url": "role/get.json",
                "type": "post",
                "data": {"id": id},
                "dataType": "json",
                "success": function (response) {
                    var result = response.operationResult;
                    if (result === "SUCCESS") {
                        $("#updaterolename").val(response.queryData.name)
                        $("#updateid").val(id)
                        $("#updateModal").modal("show");
                    }
                    if (result === "FAILED") {
                        layer.msg("操作失败！" + response.operationMessage);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            })
        }
        //自己的单击删除的思路
    // function deleteById(id){
    //     if(confirm("您确定要删除嘛")){
    //         $.ajax({
    //             "url": "role/delete.json",
    //             "type": "post",
    //             "data": {"id": id},
    //             "dataType": "json",
    //             "success": function (response) {
    //                 var result = response.operationResult;
    //                 if (result === "SUCCESS") {
    //                     layer.msg("操作成功！")
    //                     generatePage()
    //                 }
    //                 if (result === "FAILED") {
    //                     layer.msg("操作失败！" + response.operationMessage);
    //                 }
    //             },
    //             "error": function (response) {
    //                 layer.msg(response.status + " " + response.statusText);
    //             }
    //         })
    //     }
    // }
    </script>
</div>

<%@include file="/WEB-INF/pages/model-role-add.jsp" %>
<%@include file="/WEB-INF/pages/model-role-update.jsp" %>
<%@include file="/WEB-INF/pages/model-role-remove.jsp" %>
<%@include file="/WEB-INF/pages/modal-role-assign-auth.jsp" %>

</body>
</html>