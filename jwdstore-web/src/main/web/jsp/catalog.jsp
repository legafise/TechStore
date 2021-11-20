<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/img/" var="imgPath" />
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="languages.keywords" />

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="catalog" /></title>
<c:import url="header.jsp" />
<main>
    <div class="container-fluid name">
        <p class="h2 bold"><fmt:message key="catalog" /></p>
    </div>
    <div class="container-fluid">
        <div class="row">
            <c:forEach var="good" items="${goodList}">
                <div class="col-xl-3">
                    <div class="card text-center card-size">
                        <div class="card-body">
                            <h5 class="card-title short-description"><a href="controller?command=good&goodId=${good.id}&currencyId=${currency.id}">${good.name}</a></h5>
                            <p class="card-text">
                                <a href="controller?command=good&goodId=${good.id}">
                                    <img src="${imgPath}goods_${good.imgURL}" class="img-fluid img-idents img-size" alt="${good.name}">
                                </a>
                            </p>
                            <a href="controller?command=good&goodId=${good.id}">
                                <button class="more-button">
                                    <fmt:message key="more" />
                                </button>
                            </a>
                        </div>
                        <div class="card-footer text-muted short-description">
                            <a href="controller?command=good&goodId=${good.id}"><fmt:message key="price" />: ${good.price} <fmt:message key="currency.sign" /></a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</main>

<c:import url="footer.jsp"/>