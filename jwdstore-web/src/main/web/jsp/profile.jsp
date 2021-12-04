<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="profile"/></title>
    <c:import url="header.jsp"/>
    <main>
        <main>
            <div class="container-fluid name">
                <div class="container-fluid name">
                    <div class="row">
                        <div class="col-xl-4"></div>
                        <div class="card user-info-card">
                            <div class="card-header">
                                <p class="h2 bold" align="center"><fmt:message key="profile"/> </p>
                            </div>
                            <div class="card-body">
                                <span class="user-info">${user.login}</span>
                                <div class="row">
                                    <div class="col-xl-6">
                                        <img src="${imgPath}users_${user.profilePictureName}"
                                             alt="Ваше фото" class="img-fluid img-indents profile-img"/>
                                    </div>
                                    <div class="col-xl-6">
                                        <p class="h4 bold"><fmt:message key="personal.information"/> </p>
                                        <div class="user-info">
                                            <p><fmt:message key="name"/>:  <c:out value="${user.name}"/></p>
                                            <p><fmt:message key="surname"/>:  <c:out value="${user.surname}"/></p>
                                            <p><fmt:message key="login"/>:  <c:out value="${user.login}"/></p>
                                            <p>Email: <c:out value="${user.email}"/></p>
                                            <p><fmt:message key="birth.date"/>:  <c:out value="${user.birthDate}"/></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </main>
    <c:import url="footer.jsp"/>
