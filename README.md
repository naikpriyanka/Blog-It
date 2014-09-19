Blog-It
=======

This project is web application where the blogger can write micro – blogs of not more than 140 characters. The blogger could even make friends, view his friends list as well as friend suggestion.


To run Blog-It project : 

1. Set the startup window for the project to start to index.jsp page.
2. If you have your account, enetering correct username and password and clicking on Login button of index.jsp page, you will be directed to MainHome.jsp page.
3. If you are a new user, click on register button to create account.
4. Clicking on register button, you will be directed to Blog-It register.jsp page wherein you can create your account.
5. After clicking on register button, your account will be created and one will be directed to index.jsp to login with the newly created account.
6. Once logged in, on MainHome.jsp page, the user will be able to see his own profile, the friends he is following, friend suggestions to follow them if he knows any one of them and his friend's posts.
7. The blogger can put in his thoughts, ideas, day-to-day experiences, incidences worth noting in post text area provided to share them with his friends.
8. On clicking on the post button, the blooger will be able to see the blogs in posts segment in reverse chronological order depending on the date and time of post.
9. The blogger can follow few more friends by clicking on the add button in front of the username in friend suggestion segment. 
10. Clicking on add button, the friend will get added to blogger's current friend list and now the blogger can even view his blogs.
11. To end the login session, the blogger can click on logout button right there in the right top corner.


System architecture of Project :


Web Browser(Mozilla) --> HTML,CSS,JS,JQuery --> JSP/Servlet & Apache Tomcat --> Server  -->  Database(Oracle 10g)


Prerequisites : 

Creation of Database : 

Use Oracle 10g

1. Create User by typing :   

            create user twitterproject identified by branch;

2. Grant access to user to access database and connect to database by typing :

            grant connect, resource to twitterproject;

3. Connect to the user by typing (It will ask for password type in the above password - branch):

            conn twitterproject
            Enter password:


4. Now create following tables : 


1. Login Table :


create table login
(
username varchar2(50) primary key not null,
password varchar2(20) not null
);


2. Post Table :


create table post
(
postId varchar2(20) primary key not null,
post varchar2(140) not null,
postDate date,
author varchar2(50) 
);


3. Friends Table :


create table friend
(
username varchar2(50) primary key not null
);


4. Friends Posts Table :


create table friendPost
(
postId varchar2(20),
username varchar2(50),
postDate date,
foreign key (postId) references post (postId) on delete cascade,
foreign key (username) references friend (username) on delete cascade,
primary key (postId,username)
);

5. Profile Table :


create table profile
(
fullName varchar2(100),
gender varchar2(10),
dob date,
place varchar2(20),
username varchar2(50) primary key not null,
password varchar2(20),
numberofposts integer
);


Configuration related to Apache Tomcat :

1. Extract any latest version of Apache Tomcat. In my project, I have used Apache Tomcat v7.0.
2. After extraction, in conf folder, open server file in notepad, edit the port nos. 
  Change 8009 => 8019
  Change 8080 => 8181
  Change 8005 => 8015
This should be done in order to remove conflicts on port numbers while running the server from eclipse, as 8080 is already in use by the oracle 10g.
3. Save the file and close.

Configuration in Eclipse to run the project :

1. Use Eclipse Kepler in order to get all the packages related to servers.
2. Click on Window tab in menu bar, in Window --> Open Perspective, click on Server.
3. On the bottom, there is a window for Servers, wherein a note like "click on this link to add on new servers".
4. Click on the link, window will be opened, wherein add the Apache Tomcat v7.0 and give the path of the extracted Apache Tomcat Folder.
5. Click on Finish.
6. In Project Explorer Window, one will be able to see the project with name Servers.
7. Now again Click on Window tab in menu bar, in Window --> Open Perspective, click on Data Source Explorer.
8. On the bottom, there is a window for Data Source Explorer.
9. Right Click on the Database Connection.
10. Click on New.
11. On clicking on new, one window will get opened. 
12. Add a driver for JDBC connectivity.
13. Another window will get opened up. Click on Jar List. In that there is a already a ojdbc.jar file in existence. Click on it and remove it. Again add the jar file by giving in the path from lib folder of oraclexe of ojdbc14.jar. (Something like c:\oraclexe\app\oracle\product\10.2.0\server\jdbc\lib\ojdbc.jar.) Click on OK.
14. Enter in details related to database like username => twitterproject password => branch
To get SID type : 
                select sys_context('userenv','instance_name') from dual; //to check SID of oracle 10g

in oracle 10g command prompt
15. Click on Test Connection. If Ping Succeeded, Click on OK and the project is ready for use. :-)
