<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shop.pojo.ProductInfo" %>

<html>
<head>
  <title>购物车</title>
  <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
  <script>
    $(document).ready(function () {
      loadCart();

      function loadCart() {
        $.get("${pageContext.request.contextPath}/shopping-cart/cart", function (data) {
          displayCart(data);
        });
      }

      function displayCart(cartItems) {
        // 清除先前的内容
        $('#cartContent').empty();

        // 检查购物车是否非空
        if (cartItems.length > 0) {
          // 创建表格以显示购物车商品
          var table = $('<table border="1"></table>');
          table.append('<thead><tr><th>商品ID</th><th>商品名称</th><th>商品内容</th><th>商品价格</th><th>操作</th></tr></thead>');
          var tbody = $('<tbody></tbody>');

          // 遍历购物车商品，将行附加到表格中
          $.each(cartItems, function (index, item) {
            var row = $('<tr><td>' + item.pId + '</td><td>' + item.pName + '</td><td>' + item.pContent + '</td><td>' + item.pPrice + '</td><td>' +
                    '<form action="${pageContext.request.contextPath}/shopping-cart/remove-from-cart" method="post">' +
                    '<input type="hidden" name="index" value="' + index + '"/>' +
                    '<input type="submit" value="移除"/>' +
                    '</form>' +
                    '</td></tr>');
            tbody.append(row);
          });

          table.append(tbody);
          $('#cartContent').append(table);

          // 在购物车展示后，异步获取详细信息
          fetchProductDetails(cartItems);
        } else {
          // 如果购物车为空，显示消息
          $('#cartContent').append('<p>您的购物车是空的。</p>');
        }
      }

      // 异步获取详细信息
      function fetchProductDetails(cartItems) {
        $.ajax({
          url: "${pageContext.request.contextPath}/shopping-cart/view",
          type: "GET",
          contentType: "application/json",
          data: { "cartItems": JSON.stringify(cartItems) },
          success: function (data) {
            displayProductDetails(data);
          },
          error: function (error) {
            console.log("Error fetching product details: " + error);
          }
        });
      }

      // 显示详细信息
      function displayProductDetails(productDetails) {
        // 清除先前的内容
        $('#productDetails').empty();

        // 创建商品详情表格
        var detailsTable = $('<table border="1"></table>');
        detailsTable.append('<thead><tr><th>商品ID</th><th>商品名称</th><th>商品内容</th><th>商品价格</th></tr></thead>');
        var detailsTbody = $('<tbody></tbody>');

        // 遍历商品详细信息，将行附加到商品详情表格中
        $.each(productDetails, function (index, item) {
          var detailsRow = $('<tr><td>' + item.pId + '</td><td>' + item.pName + '</td><td>' + item.pContent + '</td><td>' + item.pPrice + '</td></tr>');
          detailsTbody.append(detailsRow);
        });

        detailsTable.append(detailsTbody);
        $('#productDetails').append(detailsTable);
      }

      // 处理刷新按钮点击的示例
      $(document).on('click', '#refreshCartBtn', function () {
        loadCart();
      });
    });
  </script>
</head>
<body>

<h2>您的购物车</h2>

<div id="cartContent">
  <!-- 购物车内容将在此加载 -->
</div>

<button id="refreshCartBtn">刷新购物车</button>

<h2>商品详情</h2>

<div id="productDetails">
  <!-- 商品详情将在此加载 -->
</div>

</body>
</html>
