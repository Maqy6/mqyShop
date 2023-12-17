<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <title></title>
    <%--<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" >--%>
    <style type="text/css">

    </style>
</head>

<body>
<!--整体部分-->
<div id="all">
    <!--上部分-->
    <div id="top">
        <div id="top1">
            <span>Shop</span>
        </div>
        <div id="top2"></div>
        <div id="top3">
            <span>${admin.aName}</span>
        </div>
    </div>
    <!--下部分-->
    <div id="bottom">
        <!--下部分左边-->
        <div id="bleft">
            <div id="ltop">
                <div id="lts">
                    <img src="${pageContext.request.contextPath}/images/users.jpg" /><br />
                    <p style="text-align: center;">用户</p>
                </div>
            </div>
            <div id="lbottom">
                <ul>
                    <a href="${pageContext.request.contextPath}/prod/user-split.action" target="myright" >
                        <li class="two"><span class="glyphicon glyphicon-book" style="color: white;"></span>&nbsp;&nbsp;&nbsp;&nbsp;查看商品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-play" style="color: white;"></span> </li>
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/cart.jsp" target="myright">
                        <li class="one"><span class="glyphicon glyphicon-sort" style="color: white;"></span>&nbsp;&nbsp;&nbsp;&nbsp;购物车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-play" style="color: white;"></span> </li>
                    </a>
                </ul>
            </div>
        </div>
        <!--下部分右边-->
        <div id="bright">
            <iframe frameborder="0" scrolling="yes" name="myright" width="1235px" height="700px" ></iframe>
        </div>
    </div>
</div>
</body>

</html>