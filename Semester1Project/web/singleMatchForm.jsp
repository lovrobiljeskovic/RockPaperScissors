<%-- 
    Document   : index
    Created on : Jan 18, 2017, 2:57:35 PM
    Author     : thomasthimothee
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>

    <head>
        <title>Your new choices</title>
    </head>

    <body>


        <form name="singleMatchForm" method="POST" action="/Semester1Project/finalProjectServlet">
            <input type="hidden" name="formName" value="singleMatchForm" />
            <br>
            Your existing name: <select name="name">
                <c:forEach items="${list}" var="item">
                    <option value="${item}">
                        ${item}
                    </option>
                </c:forEach>
            </select> <br>
            <br>
            Your opponent's name: 
            <select name="name2">
                <c:forEach items="${list}" var="item">
                    <option value="${item}">
                        ${item}
                    </option>
                </c:forEach>
            </select>
            <br>
            <br> 
            <input type="submit" value="submit" />            
        </form>

    </body>

</html>

