<%--
  Created by IntelliJ IDEA.
  User: SSDSystem
  Date: 05.02.2020
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <style>
        table td{
            vertical-align:top;
            border:solid 1px #888;
            padding:10px;
        }
    </style>
</head>
<body>
<h1>Оущибка)) : ${statusCode}</h1>
<table>
    <tr>
        <td>Date</td>
        <td>${timestamp}</td>
    </tr>
    <tr>
        <td>Error</td>
        <td>${error}</td>
    </tr>
    <tr>
        <td>Status</td>
        <td>${status}</td>
    </tr>
    <tr>
        <td>Message</td>
        <td>${message}</td>
    </tr>
    <tr>
        <td>Exception</td>
        <td>${exception}</td>
    </tr>
    <tr>
    <td>Trace</td>
    <td>
        <pre>${trace}</pre>
    </td>
</tr>
    <tr>
        <td>statusCode</td>
        <td>
            <pre>${statusCode}</pre>
        </td>
    </tr>
<%--    <tr>--%>
<%--        <td>exceptionGetMessage:</td>--%>
<%--        <td>--%>
<%--            <pre>${exception.getMessage()}</pre>--%>
<%--        </td>--%>
<%--    </tr>--%>
    <tr>
        <td>ex</td>
        <td>
            <pre>${exception}</pre>
        </td>
    </tr>
    <tr>
        <td>header</td>
        <td>
            <pre>${header}</pre>
        </td>
    </tr>
    <tr>
        <td>gheaderValues</td>
        <td>
            <pre>${headerValues}</pre>
        </td>
    </tr>
</table>
</body>
</html>
