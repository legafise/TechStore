<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/img/" var="imgPath"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title>Good page</title>
    <c:import url="header.jsp"/>
    <main>
        <div class="container-fluid name">
            <p class="h2 bold">${good.name}</p>
        </div>
        <div class="container-fluid good-idents">
            <div class="row">
                <div class="col-xl-4">
                    <img src="${imgPath}goods_${good.imgURL}" class="img-fluid img-idents" alt="iphone">
                    <p class="price"><fmt:message key="price"/>: ${good.price} <fmt:message key="currency.sign"/></p>
                    <div class="buttons-idents">
                        <button class="buy-button">
                            <fmt:message key="buy"/>
                        </button>
                        <a href="">
                            <button class="basket-button">
                                <fmt:message key="add.to.cart"/>
                            </button>
                        </a>
                    </div>
                </div>
                <div class="col-xl-6 description">
                    <span class="bold"><fmt:message key="type"/> </span>${good.type} <br>
                    <span class="bold"><fmt:message key="description"/> </span>${good.description}
                </div>
            </div>
        </div>
        <br>
        <div class="container-fluid">
            <p class="h2 bold" align="center">Отзывы</p>
        </div>
        <c:forEach var="review" items="${good.reviews}">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xl-2"></div>
                    <div class="col-xl-8">
                        <div class="card">
                            <div class="card-body">
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-xl-4">
                                            <img src="${imgPath}users_${review.author.profilePictureName}" alt="profile picture"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </main>

    <c:import url="footer.jsp"/>
