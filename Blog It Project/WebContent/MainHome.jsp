<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="ramani.Post"%>
<%@page import="java.util.Date" %>
<%@page import="java.sql.SQLException"%>
<%@page import="ramani.DBHandler"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="ramani.PostNotFoundException"%>
<%@page import="java.sql.Connection" %>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.text.DateFormat" %>
<%@page import="ramani.Friend" %>

<%
	String errorMessage = null;
	
	String userNameSession = session.getAttribute("userNameSession")==null?null:session.getAttribute("userNameSession").toString();
	List<Post> allPosts = DBHandler.getAllPosts(userNameSession);
	
	if (allPosts==null) {
		errorMessage = "Something went wrong and we can't show you any posts. Please visit again.";
	} else {
		if (allPosts.size()==0) {
			errorMessage = "There are no posts to show!";
		}
	}
	//System.out.println("In Home.jsp " + userNameSession);
	SimpleDateFormat year = new SimpleDateFormat("yyyy");
	SimpleDateFormat dayMonth = new SimpleDateFormat("dd MMM");
	System.out.println("FRND USERNAME:" + userNameSession);
	List<Friend> allfriends = DBHandler.getAllfriends(userNameSession);
	List<Friend> allnonfriends = DBHandler.SuggestFriends(userNameSession);
	//List<Friend> allnonfriends = DBHandler.getAllfriends(userNameSession);
%>

<!DOCTYPE html>
<html>
<head>
<title>
	BLOG IT | Post
</title>
<link rel="stylesheet" href="css/stylesheet1.css"/>
</head>

<body>

<div id="HEADER"> 
	<div id="HEADERTEXT">BLOG IT </div>
	<a class="HEADERBUTT" href="LogoutServlet" ><div>Logout </div></a>
</div>
<div id="LEFTPANEL">
		<div id="Profile_label">My Profile</div> 
<%
try 
{
  Class.forName("oracle.jdbc.OracleDriver");

  Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","twitterproject", "branch");
  
  //String userNameSession = session.getAttribute("userNameSession")==null?null:session.getAttribute("userNameSession").toString(); 
  
  String SELECT_PROFILE = "SELECT * from profile where username = ?";

  PreparedStatement s = con.prepareStatement(SELECT_PROFILE);
  
  s.setString(1, userNameSession);
  System.out.println("Username in leftpanel:" + userNameSession);
  
  ResultSet rs = s.executeQuery();
  
  while (rs.next()) {
	  String fullname = rs.getString(1);
	  String gender = rs.getString(2);
	  Date dob = rs.getDate(3);
	  String place = rs.getString(4);
	System.out.println("In leftpanel: " + fullname+ gender + place);
%>	
	<br/>
	<div class="Profile_field">
		<div class="labelques">Name : </div>
		<div class="labelans">
		<%
			out.print(fullname);	
		 %>
		 </div>
	</div>
	<br>
	<div class="Profile_field">
		<div  class="labelques">Gender : </div>
		<div class="labelans">
		<%
			out.print(gender);	
		 %>
		 </div>
	</div>
	<br>
	<div class="Profile_field">
		<div  class="labelques">DoB : </div>
		<div class="labelans">
		<%
			out.print(dob);	
		 %>
		 </div>
	</div>
	<br>
	<div class="Profile_field">
		<div  class="labelques">Place : </div>
		<div class="labelans">
		<%
			out.print(place);	
		 %>
		 </div>
	</div>
<div>
<%
  }
  rs.close();
  s.close();
  con.close();
}
catch (ClassNotFoundException e1) {
  System.out.println(e1.toString());
}
catch (SQLException e2) {
  System.out.println(e2.toString());
}
catch (Exception e3) {
  System.out.println(e3.toString());
}
%>
</div>
</div>
<div id="NEWPOST">	
	<form action="AddPostServlet" method="post">
		<textarea name ="body" rows="2" cols="97"placeholder="Write your post!!!"  id="post" onkeyup="textCounter(this,'counter',140)"/></textarea>
		<br/>
		<button type="submit" name="Post" id="postbutton">Post</button>
		<div style="margin-left:10px; font-family:Verdana; font-size:12px; display:inline-block;">
		<label for="left" id="leftC">Characters Left:</label>
		<input  disabled maxlength="3" value="140" id="counter" size="1">
		</div>
	</form>
</div>
<div id="RIGHTPANEL">
	<div id="container">
		<div id = "friend_label">FRIENDS</div>
		<div id="friend_list_box">
			
			<%
			if (errorMessage == null) {
				for (Friend friend: allfriends) {
			%>
			<div id="friend_body">
	        <%
	           	String fri = "";
	           	fri = friend.getfriendName();
	           		
	           	out.print(fri);
	        %>
	        </div>
		    <% }} else  { %>
		    <h1>Oops!</h1>
		    <p><%=errorMessage%></p>
		    <% } %>
		</div>
	<br/>
		
		<div id="suggest_label">SUGGESTIONS</div>
		<div id="suggestion_list">	
				<%
					if (errorMessage == null) {
					for (Friend friend: allnonfriends) {
				%>
		    	<div id="suggest_body">
					<div id="suggest_friend_name">
					
	              	<%
	            		String fri = "";
	            	   	fri = friend.getfriendName();
	           		   	out.print(fri);
	            	%>   	
		        	</div>
			        <div id="addbutton">
			        <a style="text-decoration:none; color:black;"href="addFriendservlet?friendName=<%=friend.getfriendName()%>">Add</a>
			        </div>
				</div>
			        <%}}
					else { %>
			        <h1>Oops!</h1>
			        <p><%=errorMessage%></p>
			        <% } %>
		</div>
	
	<br/>

</div>

</div>
<div id="main-region">
		<%
		if (errorMessage == null) {
			for (Post post: allPosts) {
		%>
		<div id="post-box">
			<div id="bodyrow">
	              	<%
	            	String body = "";
	            	String author= "";
	            	//String date = "";
	            	//DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	            	if (post.getBody().length() > 500) {
	            		post.getBody().subSequence(0, 500);
	            	} else {
	            		body = post.getBody();
	           		}
	            	author = post.getAuthor();
	            	//date = df.format(post.getCreatedOn());
	            	out.print(body);
	            	
	            	//out.print(date);
	            	%>
	        </div>
	        <div id="author-row">
	        <%out.print(author);%>
	        </div>
    	</div>
        <% }} else  { %>
        <h1>Oops!</h1>
        <p><%=errorMessage%></p>
        <% } %>
</div>

<script>
	function increaseHeight()
	{
   		Document.getElementById('LEFTPANEL').style.height = Document.getElementById('POSTCONTAINER').style.height;
	}
	window.onload= increaseHheight();
	function textCounter(field,field2,maxlimit)
	{
		var countfield = document.getElementById(field2);
		if(field.value.length > maxlimit)
		{
			field.value = field.value.substring(0,maxlimit);
			return false;
		}
		else
		{
			countfield.value = maxlimit - field.value.length;
		}
	}
</script>

</body>
</html>