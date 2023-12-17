<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <script type="text/javascript">
        if ("${msg}" != "") {
            alert("${msg}");
        }
    </script>

    <c:remove var="msg"></c:remove>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bright.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addBook.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <title></title>
</head>
<script type="text/javascript">
    function allClick() {
        //取得全选复选框的选中未选 中状态
        var ischeck=$("#all").prop("checked");
        //将此状态赋值给每个商品列表里的复选框
        $("input[name=ck]").each(function () {
            this.checked=ischeck;
        });
    }

    function ckClick() {
        //取得所有name=ck的被选中的复选框
        var fiveLength=$("input[name='ck']").length;
//取得所有name=ck的复选框
        var checkedLength=$("input[name='ck']:checked").length;
        //比较
        if(fiveLength == checkedLength){
            $("#all").prop("checked",true);
        }else
        {
            $("#all").prop("checked",false);
        }
    }
</script>
<body>
<div id="brall">
    <div id="nav">
        <p>商品管理</p>
    </div>
<%--    用于条件查询--%>
    <div id="condition" style="text-align: center">
        <form id="myform">
            商品名称：<input name="pname" id="pname">&nbsp;&nbsp;&nbsp;
            商品类型：<select name="typeid" id="typeid">
            <option value="-1">请选择</option>
            <c:forEach items="${typeList}" var="pt">
                <option value="${pt.typeId}">${pt.typeName}</option>
            </c:forEach>
        </select>&nbsp;&nbsp;&nbsp;
            价格：<input name="lprice" id="lprice">-<input name="hprice" id="hprice">
            <input type="button" value="查询" onclick="condition()">
        </form>
    </div>
    <br>
    <div id="table">

        <c:choose>
            <c:when test="${info.list.size()!=0}">

                <div id="top">
<%--                    勾选全选框，该页所有商品都将被选中，可以联合批量删除一起使用--%>
                    <input type="checkbox" id="all" onclick="allClick()" style="margin-left: 50px">&nbsp;&nbsp;全选
<%--                    点击新增商品后，跳转到/admin/addproduct.jsp新增商品页面--%>
                        <a href="${pageContext.request.contextPath}/admin/addproduct.jsp">

                        <input type="button" class="btn" id="btn1"
                               value="新增商品">
                    </a>
<%--                    点击批量删除按钮，会触发deleleBatch()，若已选中多个商品，那么这些商品都会被删除--%>
                    <input type="button" class="btn" id="btn1"
                           value="批量删除" onclick="deleteBatch()">
                </div>
                <!--显示分页后的商品-->
                <div id="middle">
                    <table class="table table-bordered table-striped">
                        <tr>
                            <th></th>
                            <th>商品名</th>
                            <th>商品介绍</th>
                            <th>定价（元）</th>
                            <th>商品图片</th>
                            <th>商品数量</th>
                            <th>操作</th>
                        </tr>
<%--                        用c:forEach展示分页后的商品--%>
                        <c:forEach items="${info.list}" var="p">
                            <tr>
                                <td valign="center" align="center">
                                    <input type="checkbox" name="ck" id="ck" value="${p.pId}" onclick="ckClick()"></td>
                                <td>${p.pName}</td>
                                <td>${p.pContent}</td>
                                <td>${p.pPrice}</td>
                                <td><img width="55px" height="45px"
                                         src="${pageContext.request.contextPath}/image_big/${p.pImage}"></td>
                                <td>${p.pNumber}</td>
                                <td>
<%--                                    点击编辑以后触发one()，根据传入的商品ID和页码对商品进行编辑--%>
                                    <button type="button" class="btn"
                                            onclick="one(${p.pId},${info.pageNum})">编辑
                                    </button>
        <%--点击删除以后触发del()，根据传入的商品ID和页码对商品进行删除--%>
                                    <button type="button" class="btn" id="mydel"
                                            onclick="del(${p.pId},${info.pageNum})">删除
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <!--分页栏-->
                    <div id="bottom">
                        <div>
                            <nav aria-label="..." style="text-align:center;">
                                <ul class="pagination">
                                    <li>
                                        <a href="javascript:ajaxsplit(${info.prePage})" aria-label="Previous">

                                            <span aria-hidden="true">«</span></a>
                                    </li>
                                    <c:forEach begin="1" end="${info.pages}" var="i">
                                        <c:if test="${info.pageNum==i}">
                                            <li>

                                                <a href="javascript:ajaxsplit(${i})"
                                                   style="background-color: grey">${i}</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${info.pageNum!=i}">
                                            <li>
                                                <a href="javascript:ajaxsplit(${i})">${i}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                    <li>
                                        <a href="javascript:ajaxsplit(${info.nextPage})" aria-label="Next">
                                            <span aria-hidden="true">»</span></a>
                                    </li>
                                    <li style=" margin-left:150px;color: #0e90d2;height: 35px; line-height: 35px;">总共&nbsp;&nbsp;&nbsp;<font
                                            style="color:orange;">${info.pages}</font>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <c:if test="${info.pageNum!=0}">
                                            当前&nbsp;&nbsp;&nbsp;<font
                                            style="color:orange;">${info.pageNum}</font>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        </c:if>
                                        <c:if test="${info.pageNum==0}">
                                            当前&nbsp;&nbsp;&nbsp;<font
                                            style="color:orange;">1</font>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        </c:if>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <h2 style="width:1000px; text-align: center;color: darkred;margin-top: 80px">暂时没有符合条件的商品！</h2>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>

<script type="text/javascript">
    function mysubmit() {
        $("#myform").submit();
    }

    //批量删除
    function deleteBatch() {

            //取得所有被选中删除商品的pid
            var checks=$("input[name=ck]:checked");
            var str="";
            var id="";
            if(checks.length==0){//还未选中商品
                alert("请选择将要删除的商品！");
            }else{
                // 有选中的商品，则取出每个选中商品的ID，拼接提交的ID的数据
                if(confirm("您确定删除这"+checks.length+"条商品吗？")){
                //拼接ID
                    $.each(checks,function (index, item) {
                        id=$(item).val();
                        alert(id);
                        if(id!=null)
                            str += id+",";
                    });
                    //发送请求到服务器端
                    $.ajax({
                        url:"${pageContext.request.contextPath}/prod/deleteBatch.action",
                        data:{"pids":str},
                        type:"post",
                        dataType:"text",
                        success:function (msg){
                            alert(msg);
                            //刷新页面上显示商品数据的容器
                            $("#table").load("http://localhost:8080/admin/product.jsp #table")
                        }
                    });
                }
        }
    }
    //单个删除
    function del(pid,page) {
        if (confirm("确定要删除该商品吗")) {
          //向服务器提交删除请求
            var pname=$("#pname").val();
            var typeid=$("#typeid").val();
            var lprice=$("#lprice").val();
            var hprice=$("#hprice").val();
            $.ajax({
                url:"${pageContext.request.contextPath}/prod/delete.action",
                data:{"pid":pid,"page":page,"pname":pname,"typeid":typeid,"lprice":lprice,"hprice":hprice},
                type:"post",
                dateType:"text",
                success:function (msg){
                    alert(msg);
                    $("#table").load("http://localhost:8080/admin/product.jsp #table");
                }
            });

        }
    }

    function one(pid, page) { //注意pid要与one方法中参数的名称一致，来完成数据的自动注入
        var pname=$("#pname").val();
        var typeid=$("#typeid").val();
        var lprice=$("#lprice").val();
        var hprice=$("#hprice").val();
        var str="?pid="+pid+"&page="+page+"&pname="+pname+"&typeid="+typeid+"&lprice="+lprice+"&hprice="+hprice;
        location.href = "${pageContext.request.contextPath}/prod/one.action"+str;
    }
</script>

<script type="text/javascript">
    <!--分页的AJAX实现-->
    function ajaxsplit(page) {
        var pname=$("#pname").val();
        var typeid=$("#typeid").val();
        var lprice=$("#lprice").val();
        var hprice=$("#hprice").val();
        //异步ajax分页请求
        $.ajax({
        url:"${pageContext.request.contextPath}/prod/ajaxsplit.action",
            data:{"page":page,"pname":pname,"typeid":typeid,"lprice":lprice,"hprice":hprice},
            type:"post",
            success:function () {
                //重新加载分页显示的组件table
                $("#table").load("http://localhost:8080/admin/product.jsp #table");
            }
        })
    };

    //条件查询商品
    function condition(){
        var pname=$("#pname").val();
        var typeid=$("#typeid").val();
        var lprice=$("#lprice").val();
        var hprice=$("#hprice").val();
        $.ajax({
           type:"post",
            url:"${pageContext.request.contextPath}/prod/ajaxsplit.action",
            data:{"pname":pname,"typeid":typeid,"lprice":lprice,"hprice":hprice},
            success:function (){
                //刷新用来显示商品的容器
                $("#table").load("http://localhost:8080/admin/product.jsp #table");
            }
        });
    }
</script>

</html>