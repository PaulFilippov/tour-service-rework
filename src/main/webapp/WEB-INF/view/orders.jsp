<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Orders</title>
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
                    Ваши заявки на участие в турах
                </h3>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Место</th>
                    <th>Описание</th>
                    <th>Дата начала</th>
                    <th>Дата завершения</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userOrders}" var="userOrder">
                    <tr>
                        <td>${userOrder.getTour().getName()}</td>
                        <td>${userOrder.getTour().getLocation()}</td>
                        <td>${userOrder.getTour().getDescription()}</td>
                        <td><fmt:formatDate value="${userOrder.getTour().getStart_date()}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${userOrder.getTour().getEnd_date()}" pattern="yyyy-MM-dd"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${tourService.isPassedTour(userOrder.getTour())}">
                                    <span>Тур прошел</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="/deleteOrder/${userOrder.id_order}" type="button"
                                       class="btn btn-danger btn-xs">Отменить</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<footer class="navbar-static-bottom navbar-inverse">
    <p class="text-center">&copy; Tour Service телефон:8(908)-777-77-77</p>
</footer>
</body>
</html>