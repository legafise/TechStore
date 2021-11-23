<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title>${good.name}</title>
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
        <c:if test="${good.reviews.size() == 0}">
            <div class="container-fluid">
                <p class="h2 bold" align="center"><fmt:message key="no.reviews"/></p>
            </div>
        </c:if>
        <c:if test="${good.reviews.size() != 0}">
            <div class="container-fluid">
                <p class="h2 bold" align="center"><fmt:message key="reviews"/></p>
            </div>
            <c:forEach var="review" items="${good.reviews}">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-1"></div>
                        <div class="col-lg-10">
                            <div class="card review">
                                <div class="card-body">
                                    <div class="container-fluid">
                                        <div class="row">
                                            <p class="h4">${review.author.login}</p>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-2">
                                                <img class="review-img"
                                                     src="${imgPath}users_${review.author.profilePictureName}"
                                                     alt="profile picture"/>
                                            </div>
                                            <div class="col-lg-8">
                                                <div class="review-content">${review.content}</div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="rating-mini">
                                                <c:choose>
                                                    <c:when test="${review.rate == 1}">
                                                        <span class="active"></span>
                                                        <span></span>
                                                        <span></span>
                                                        <span></span>
                                                        <span></span>
                                                    </c:when>
                                                    <c:when test="${review.rate == 2}">
                                                        <span class="active"></span>
                                                        <span class="active"></span>
                                                        <span></span>
                                                        <span></span>
                                                        <span></span>
                                                    </c:when>
                                                    <c:when test="${review.rate == 3}">
                                                        <span class="active"></span>
                                                        <span class="active"></span>
                                                        <span class="active"></span>
                                                        <span></span>
                                                        <span></span>
                                                    </c:when>
                                                    <c:when test="${review.rate == 4}">
                                                        <span class="active"></span>
                                                        <span class="active"></span>
                                                        <span class="active"></span>
                                                        <span class="active"></span>
                                                        <span></span>
                                                    </c:when>
                                                    <c:when test="${review.rate == 5}">
                                                        <span class="active"></span>
                                                        <span class="active"></span>
                                                        <span class="active"></span>
                                                        <span class="active"></span>
                                                        <span class="active"></span>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </main>

    <c:import url="footer.jsp"/>
