<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/css/style.css" var="cssPath"/>
<c:url value="/img/plus_in_circle.png" var="plusImg"/>
<c:url value="/controller?command=catalog" var="catalogPath"/>
<c:url value="/controller?command=basket" var="basketPath"/>
<c:url value="/controller?command=change_language" var="changeLanguageCommand"/>
<c:url value="/controller?command=authorization_page" var="authorizationPageCommand"/>
<c:url value="/controller?command=registration_page" var="registrationPageCommand"/>
<c:url value="/controller?command=log_out" var="logOutCommand"/>
<c:url value="/controller?command=profile" var="profilePageCommand"/>
<c:url value="/controller?command=orders_page" var="ordersPageCommand"/>
<c:url value="/controller?command=replenishment_page" var="replenishmentPageCommand"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<link rel="stylesheet" href="${cssPath}">
</head>
<body>
<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <a class="navbar-brand" href="${catalogPath}" title="<fmt:message key="to.main"/>">TechStore</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
        <ul class="navbar-nav">
            <c:if test="${role != 'guest'}">
                <li class="nav-item active">
                    <a class="nav-link" href="${replenishmentPageCommand}"><fmt:message key="balance"/> ${balance} <fmt:message key="currency.sign"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"></a>
                </li>
            </c:if>
            <li class="nav-item ${currentPage == 'catalog' ? 'active' : ''}">
                <a class="nav-link" href="${catalogPath}"><fmt:message key="catalog"/></a>
            </li>
            <li class="nav-item ${currentPage == 'basket' ? 'active' : ''}">
                <a class="nav-link" href="${basketPath}"><fmt:message key="basket"/></a>
            </li>
            <c:choose>
                <c:when test="${role == 'guest'}">
                    <li class="nav-item ${currentPage == 'registration' ? 'active' : ''}">
                        <a class="nav-link" href="${registrationPageCommand}"><fmt:message key="registration"/></a>
                    </li>
                    <li class="nav-item ${currentPage == 'sing_in' ? 'active' : ''}">
                        <a class="nav-link" href="${authorizationPageCommand}"><fmt:message key="sign.in"/></a>
                    </li>
                </c:when>
                <c:when test="${role != 'guest'}">
                    <li class="nav-item">
                        <a class="nav-link ${currentPage == 'profile' ? 'active' : ''}"
                           href="${profilePageCommand}"><fmt:message key="profile"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${currentPage == 'orders' ? 'active' : ''}"
                           href="${ordersPageCommand}"><fmt:message key="orders"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${logOutCommand}"><fmt:message key="log.out"/></a>
                    </li>
                </c:when>
            </c:choose>
            <li class="nav-item">
                <a class="nav-link" href=""></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href=""></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href=""></a>
            </li>
            <li class="nav-item">
                <form action="${changeLanguageCommand}" method="post">
                    <select class="custom-select nav-selector" id="locale" name="locale" onchange="submit()">
                        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>RU</option>
                        <option value="en_US" ${locale == 'en_US' ? 'selected' : ''}>EN</option>
                    </select>
                </form>
            </li>
            <li class="nav-item">
                <a class="nav-link" href=""></a>
            </li>
        </ul>
    </div>
</nav>
