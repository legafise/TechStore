<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/css/style.css" var="cssPath"/>
<c:url value="/controller?command=catalog" var="catalogPath"/>
<c:url value="/controller?command=change_language" var="changeLanguageCommand"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1,   shrink-to-fit=no">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
      integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
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
            <li class="nav-item ${currentPage == 'catalog' ? 'active' : ''}">
                <a class="nav-link" href="${catalogPath}"><fmt:message key="catalog"/></a>
            </li>
            <li class="nav-item ${currentPage == 'basket' ? 'active' : ''}">
                <a class="nav-link" href=""><fmt:message key="basket"/></a>
            </li>
            <li class="nav-item ${currentPage == 'registration' ? 'active' : ''}">
                <a class="nav-link" href=""><fmt:message key="registration"/></a>
            </li>
            <li class="nav-item ${currentPage == 'sing_in' ? 'active' : ''}">
                <a class="nav-link" href=""><fmt:message key="sing.in"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href=""></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href=""></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href=""></a>
                ${uri}
            </li>
            <li class="nav-item">
                <form action="${changeLanguageCommand}" method="post">
                    <select class="form-control nav-selector" id="locale" name="locale" onchange="submit()">
                        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>RU</option>
                        <option value="en_US" ${locale == 'en_US' ? 'selected' : ''}>EN</option>
                    </select>
                </form>
            </li>
        </ul>
    </div>
</nav>
