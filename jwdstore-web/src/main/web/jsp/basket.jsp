<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>
<c:set var="totalPrice" value="0"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="basket"/></title>
    <c:import url="header.jsp"/>
    <main class="content">
        <div class="container-fluid name">
            <p class="h2 bold"><fmt:message key="basket"/></p>
        </div>
        <c:choose>
            <c:when test="${isBasketEmpty == true}">
                <div class="container-fluid">
                    <h5 class="basket-good-header"><fmt:message key="basket.is.empty"/></h5>
                </div>
            </c:when>
            <c:when test="${isBasketEmpty == false}">
                <div class="container-fluid">
                    <c:forEach var="entry" items="${basketGoods}">
                        <div class="card card-size">
                            <h5 class="card-header basket-good-header">${entry.key.name}</h5>
                            <div class="card-body">
                                <div class="row basket-description-text">
                                    <div class="col-xl-3">
                                        <img src="${imgPath}goods_${entry.key.imgURL}" class="img-fluid img-indents img-size" alt="">
                                    </div>
                                    <div class="col-xl-9">
                                        <span class="bold"><fmt:message key="description"/></span> ${entry.key.description} <br>
                                        <span class="bold"><fmt:message key="type"/></span> ${entry.key.type} <br>
                                        <span class="bold"><fmt:message key="quantity"/></span> ${entry.value}
                                    </div>
                                </div>
                                <span class="price-in-basket"><fmt:message key="price"/>: ${entry.key.price * entry.value} <fmt:message key="currency.sign"/> </span>
                                <br>
                                <a href="" class="btn buy-button-in-basket"><fmt:message key="buy"/></a>
                                <a href="${removeGoodCommand}${entry.key.id}" class="btn delete-button"><fmt:message key="remove"/></a>
                            </div>
                            <!--${totalPrice = totalPrice + entry.key.price * entry.value}-->
                        </div>
                    </c:forEach>
                </div>
                <div class="container-fluid basket-description-text">
                    <span class="price-in-basket"><fmt:message key="total.price"/> ${totalPrice} <fmt:message key="currency.sign"/> </span>
                    <div>
                        <a href="" class="btn buy-button-in-basket"><fmt:message key="to.order"/></a>
                        <a href="${clearBasketCommand}" class="btn delete-button"><fmt:message key="remove.goods.from.basket"/></a>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </main>
<c:import url="footer.jsp"/>