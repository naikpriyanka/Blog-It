<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link type="text/css" rel="stylesheet" href="css/stylesheet.css"/>
<head>
<!--FOLLOWING DIV IS FOR THE HEADER: HOME AND LOGOUT BUTTONS GO TO THE RIGHT-->
<div id="HEADER">
	
	<a id="HEADERTEXT" href=""><div>BLOG IT</div></a>
	<!-- <a class="HEADERBUTT" href=""><div>LOGOUT</div></a> -->
	<!-- <a class="HEADERBUTT" href=""><div>HOME</div></a> -->
</div>
</head>
<body>
<div id="main-container">
<div id="LOGINBUTTON">
		<h2 style="color:white; top-margin:4px;padding:4px;">Login</h2>
		<form action="LoginServlet" method="post">
			<div class="textlabel">
				<label style="margin:2px; padding:2px;"for="username">Username</label><br/>
				<label style="margin:2px; padding:2px;"for="password">Password</label><br/>
			</div>
			<div class="textinput">
				<input type="text" name="username" id="username" maxlength="50" placeholder="Your username" style="float:left; width: 130px;"/><br/>
				<input type="password" name="password" id="password" maxlength="50" placeholder="Your password" style="float:left; width: 130px;"/><br/>
			</div>
			<br/>
			<br/>
			<button type="submit" class="btn-primary">Enter</button>
		</form>
		<hr />
		<small style="text-align:center;">You must login to start posting</small>
		<div id="register">
			<label style="margin:2px; padding:2px;"for="register"><a href="register.jsp">Register here</a></label><br/>
		</div>
</div>
</div>
<div id="footer">
	<!-- <ul class="pop-up">
		<li>Ramani</li><hr/>
		<li>Priyanka</li><hr/>
		<li>Manjusha</li><hr/>
		<li>Sharwari</li>
	</ul> -->
	<a href="" id="Dev" class="footertabs"><div>Developers</div></a>
	<a href=""  class="footertabs"><div>About</div></a>
</div>
</body>
</html>