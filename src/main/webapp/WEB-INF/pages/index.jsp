<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
	<p>${errorMsg}</p>
	<form action="login" method="post">
		<label for="name">用户</label><input type="text" name="name" id="name"/>
		<label for="password">密码</label><input type="password" name="password" id="password"/>
		<input type="submit" value="登录">
	</form>
</body>
</html>