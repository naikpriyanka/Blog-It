<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>
<title> BLOG IT | Register </title>
<link rel="stylesheet" href="css/stylesheet2.css">
</head>

<body>
<div id="header"> 
	<div id="title">
		<label for="title">BLOG IT </label>
	</div>
</div>

<div id="main-region" class="container">
	<br>
		<h1><center>Register </center> </h1>
		<form action="RegisterServlet" method="post" role="form" >
			<table cellpadding="10" align="center">
				<tr class="form-group">
				<td>
					<label for="fullname">Fullname </label><span class="text-danger"> *</span>
				</td>
				<td>
					<input type="text" name="fullname" id="fullname" maxlength="70" class="form-control required" placeholder="Your fullname" />
				</td>
				</tr>
				<tr class="form-group">
				<td>
					<label for="gender">Gender </label><span class="text-danger"> *</span>
				</td>
				<td>
					<input type="radio" name="gender" id="gender" class="form-control" value="M" class="form-control required" /> Male
					<input type="radio" name="gender" id="gender" class="form-control" value="F" class="form-control required"/> Female
				</td>
				</tr>
				<tr class="form-group">
				<td>
					<label for="place">Place </label><span class="text-danger"> *</span>
				</td>
				<td>
					<input type="text" name="place" id="place" maxlength="50" class="form-control required" placeholder="Your place" />
				</td>
				</tr>
				<tr class="form-group">
				<td>
					<label for="dob">Date of Birth </label><span class="text-danger"> *</span>
				</td>
				<td>
					<input type="text" name="dob" id="dob" maxlength="50" class="form-control required" placeholder="DD-MM-YYYY" />
				</td>
				</tr>
				<tr class="form-group">
				<td>
					<label for="username">Username </label><span class="text-danger"> *</span>
				</td>
				<td>
					<input type="text" name="username" id="username" maxlength="50" class="form-control required" placeholder="Your username" />
				</td>
				</tr>
				<tr class="form-group">
				<td>
					<label for="enter password">Enter Password </label><span class="text-danger"> *</span>
				</td>
				<td>
					<input type="password" name="epassword" id="epassword" maxlength="50" class="form-control required" />
				</td>
				</tr>
				<tr class="form-group">
				<td >
					<label for="confirm password">Confirm Password </label><span class="text-danger"> *</span>
				</td>
				<td>
					<input type="password" name="cpassword" id="cpassword" maxlength="50" class="form-control required" />
				</td>
				</tr>
				<tr>
				<td>
					<button type="submit" class="btn btn-primary" id="registerbtn">Register</button>
				</td>
				</tr>
			</table>
		</form>
		<center><span class="text-danger" >*</span> Mandatory fields</center>
		</div>
</div>
</body>
<script src="js/vendor/jquery.js"></script>
<script src="js/vendor/bootstrap.min.js"></script>
<script>
$(function() {
	var invalidSubmission = false;
	$('.required').blur(function() {
		var errorTextObject = $(this).parent().find('small.text-danger');
		if($(this).val().trim() == '' && errorTextObject.length == 0 ) {
			invalidSubmission = true;
			var label = $(this).parent().find('label').html();
			$(this).parent().append('<small class="text-danger">'+ label +' is mandatory</small>');
		}
		
	}).change(function() {
		invalidSubmission = false;

		if($(this).val().trim() != '') {
		    $(this).parent().find('small.text-danger').fadeOut(function() { $(this).remove(); });
		}
	});
	
	$('form').submit( function (event) {
		$('button').focus();
		if (invalidSubmission) {
			event.preventDefault();
		}
	});
	
	$('#title').focus();

});

</script>

</html>