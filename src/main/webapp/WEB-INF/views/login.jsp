<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Login page</title>
    <link type="text/css" href="/resources/css/bootstrap.min.css" rel="stylesheet" >
    <link type="text/css" href="/resources/css/main.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <nav class="navbar navbar-default navbar-inverse" role="navigation">
                <div class="collapse navbar-collapse nav navbar-nav" id="bs-example-navbar-collapse-1">
                    <li>
                        <a class="text-center">Tour Service</a>
                    </li>
                </div>
            </nav>
            <h3 class="text-center">
                Авторизация
            </h3>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
            <p>
                <a class="btn" href="#"></a>
            </p>
        </div>
        <div class="col-md-4">
            <c:url value="/login" var="loginUrl"/>
            <form action="${loginUrl}" method="POST" class="form-horizontal">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label">
                        Email
                    </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="username" name="username">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">
                        Пароль
                    </label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="password" name="password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">
                            Вход
                        </button>
                    </div>
                </div>
            </form>
            <p>
                <a class="btn" href="#"></a>
            </p>
        </div>
        <div class="col-md-4">
            <p>
                <a class="btn" href="#"></a>
            </p>
        </div>
    </div>
</div>
<footer class="navbar-static-bottom navbar-inverse">
    <p class="text-center">&copy; Tour Service телефон:8(908)-777-77-77</p>
</footer>
</body>
</html>