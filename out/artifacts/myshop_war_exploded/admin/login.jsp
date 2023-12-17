<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
		<title></title>
		<style>
			#top {
				text-align: center;
			}

			#top span {
				display: inline-block;
				margin-top: 10px; /* 可根据需要调整上边距 */
				font-size: 24px; /* 可根据需要调整字体大小 */
			}

			#login {
				position: absolute;
				top: 30%;
				left: 50%;
				transform: translate(-50%, -50%);
			}
		</style>
	</head>

	<body>
		<div id="login">
			<div id="top">
				<span>Login</span>
			</div>
			<div id="bottom">
				<form action="${pageContext.request.contextPath}/admin/login.action" method="post">
					<table border="0px" id="table">
						<tr>
							<td class="td1">用户名：</td>
							<td><input type="text" value="user1" placeholder="Username" class="td2" name="name"></td>
						</tr>
						<tr>
							<td></td>
							<td><span id="nameerr"></span></td>
						</tr>
						<tr>
							<td class="td1">密码：</td>
							<td><input type="password" value="123456" placeholder="Password" class="td2" name="pwd"></td>
						</tr>
						<tr>
							<td></td>
							<td><span id="pwderr"></span></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="登录" class="td3">
							</td>
						</tr>
					</table>
				</form>
				${errmsg}
			</div>

		</div>
	</body>

</html>