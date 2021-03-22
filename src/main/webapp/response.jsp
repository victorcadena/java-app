<%-- 
    Document   : response
    Created on : Jul 5, 2019, 7:43:09 PM
    Author     : Norma
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.importserver.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
<body>
    
    
    
    <jsp:useBean id="mybean" scope="session" class="com.mycompany.importserver.NameHandler"/>
     <jsp:setProperty property="*" name="mybean"/>  
    <h1>Hello, <jsp:getProperty name="mybean" property="name" /> <jsp:getProperty name="mybean" property="lastname" />!</h1>
    
    <p><%= SaveInFile.saveUserRecord( mybean.getName(), mybean.getLastname(), mybean.getEmail()) %></p>


</body>
</html>
