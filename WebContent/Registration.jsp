<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="CSS/Style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>W.B.S.W.</title>
</head>
<body>
<div id=navigation><ul>
<li><a href="Login.jsp"> Login </a></li>
</ul> </div>

<h1>${initParam['WebsiteName']}</h1>
<h2> Register here</h2>

<form action=Register method="post">

User Name: <input type=text name=userName><br>
First Name: <input type=text name=firstName><br>
Last Name: <input type=text name=lastName><br>
Password: <input type=password name=password><br>
Email: <input type=text name=email><br>
<input type=submit value=Register> <br> 

</form>

<br>
<br>

</body>
</html>