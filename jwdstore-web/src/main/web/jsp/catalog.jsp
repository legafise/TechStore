<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<c:url value="/controller?command=add_good_page" var="addGoodPageCommand"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="catalog"/></title>
    <c:import url="header.jsp"/>

    <main>
        <c:if test="${authorizationResult == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="positive.authorization.result"/> </strong> <fmt:message
                        key="positive.authorization.result.info"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("authorizationResult");%>
        </c:if>
        <c:if test="${isGoodAdded == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="good.added"/> </strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isGoodAdded");%>
        </c:if>
        <c:if test="${isGoodRemoved == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="good.removed"/> </strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isGoodRemoved");%>
        </c:if>
        <c:if test="${logOutResult == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="positive.log.out.result"/> </strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("logOutResult");%>
        </c:if>
        <div class="container-fluid name">
            <p class="h2 bold"><fmt:message key="catalog"/></p>
        </div>
        <div class="container-fluid">
            <c:if test="${role == 'moder' || role == 'admin'}">
                <a href="${addGoodPageCommand}">
                    <button class="more-button">
                        <fmt:message key="add.good"/>
                    </button>
                </a>
            </c:if>
            <div class="row">
                <c:forEach var="good" items="${goodList}">
                    <div class="col-xl-3">

                        <div class="card text-center card-size">
                            <div class="card-body">
                                <h5 class="card-title short-description"><a
                                        href="controller?command=good&goodId=${good.id}"><c:out
                                        value="${good.name}"/></a>
                                </h5>
                                <p class="card-text">
                                    <a href="controller?command=good&goodId=${good.id}">
                                        <img src="${imgPath}goods_${good.imgName}"
                                             class="img-fluid img-indents img-size"
                                             alt="${good.name}">
                                    </a>
                                </p>
                                <a href="controller?command=good&goodId=${good.id}">
                                    <button class="more-button">
                                        <fmt:message key="more"/>
                                    </button>
                                </a>
                            </div>
                            <div class="card-footer text-muted short-description">
                                <a href="controller?command=good&goodId=${good.id}"><fmt:message
                                        key="price"/>: ${good.price} <fmt:message key="currency.sign"/></a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </main>
<c:import url="footer.jsp"/>