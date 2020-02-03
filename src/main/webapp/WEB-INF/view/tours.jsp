<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                            <a href="<c:url value="/logout" />">Выйти</a>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="row">
                <h3 class="col-md-12 text-center">
                    Список актуальных туров
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
                    <th>Описание</th>
                    <th>Место</th>
                    <th>Дата начала</th>
                    <th>Дата окончания</th>
                    <th>Осталось мест</th>
                    <th>Количество билетов</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${allTours}" var="tour">
                    <c:if test="${!tourService.isPassedTour(tour)}">
                        <form name="${tour.id_tour}+selectForm" class="form-horizontal" action="/orderTourByUser"
                              method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <tr>
                                <input type="text" name="id_tour" value="${tour.id_tour}" class="hidden">
                                <td>${tour.name}</td>
                                <td>${tour.description}</td>
                                <td>${tour.location}</td>
                                <td><fmt:formatDate value="${tour.start_date}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${tour.end_date}" pattern="yyyy-MM-dd"/></td>
                                <c:set var="freePlaces" value="${tourService.getNumberOfFreePlaces(tour)}"/>
                                <td> ${freePlaces}</td>
                                <c:set var="selectItem" value="0"/>
                                <td>
                                    <select name="selectNumberOfTicketsByUserID${tour.id_tour}" class="selectpicker">
                                        <option value="0" selected="selected">0</option>
                                        <c:forEach begin="1" end="${freePlaces}" var="selectItem">
                                            <option value="${selectItem}">${selectItem}</option>
                                        </c:forEach>
                                        <c:set var="id">${tour.id_tour}</c:set>
                                        <c:if test="${(mapSelectedTickets[id] != 0) && (mapSelectedTickets[id] != '') && (mapSelectedTickets[id] != null)}">
                                            <option selected disabled
                                                    hidden> ${mapSelectedTickets[id]} </option>
                                        </c:if>
                                    </select>
                                </td>
                                <td>
                                    <c:set var="checkTourInUserOrder"
                                           value="${tourService.checkTourInUserOrders(curUser,tour)}"/>
                                    <c:choose>
                                        <c:when test="${checkTourInUserOrder}">
                                            <span>Вы уже заказали</span>
                                        </c:when>
                                        <c:when test="${freePlaces==0}">
                                            <span>Мест нет</span>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="submit" class="btn btn-primary btn-xs" value="Забронировать">
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </form>
                    </c:if>
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