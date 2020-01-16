<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Tours</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/main.css">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <nav class="navbar navbar-inverse" role="navigation">
                <div class="navbar-header">
                    </button> <a class="navbar-brand active">Tour Service</a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="/tours">Туры</a>
                        </li>
                        <li>
                            <a href="/orders">Заказы</a>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="/profile"> <c:out value="${pageContext.request.remoteUser}"/> </a>
                        </li>
                        <li>
                            <a href="<c:url value="/logout" />" type="button" class="btn btn-danger btn-xs">Выйти</a>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="row">
                <h3 class="col-md-12 text-center">
                    Информация профиля: ${user.email}
                </h3>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
        </div>
        <div class="col-md-5">
            <form class="form-horizontal" action="/profile" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-group">
                    <label class="control-label col-xs-3" for="f_name">Имя:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" id="f_name" name="first_name" value=${user.first_name}>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-3" for="l_name">Фамилия:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" id="l_name" name="last_name" value=${user.last_name}>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-3" for="birth">Дата рождения:</label>
                    <div class="col-xs-9">
                        <input type="date" class="form-control" id="birth" name="birthday" value=${user.birthday}>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-9">
                        <input type="submit" class="btn btn-primary" value="Сохранить изменения">
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-4">
        </div>
    </div>
</div>
<footer class="navbar-static-bottom navbar-inverse">
    <p class="text-center">&copy; Tour Service телефон:8(908)-777-77-77</p>
</footer>
</body>
</html>