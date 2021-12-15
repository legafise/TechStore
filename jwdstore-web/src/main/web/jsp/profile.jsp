<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<c:url value="/controller?command=update_profile_page" var="updateProfilePageCommand"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="profile"/></title>
    <c:import url="header.jsp"/>
    <main>
        <c:if test="${isUserUpdated == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="data.was.updated.successfully"/> </strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isUserUpdated");%>
        </c:if>
        <c:if test="${isUserUpdated == false}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="data.was.not.updated"/> </strong> <fmt:message key="user.with.such.data.is.exist"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isUserUpdated");%>
        </c:if>
        <div class="container-fluid name">
            <div class="container-fluid name">
                <div class="row">
                    <div class="col-xl-4"></div>
                    <div class="card user-info-card">
                        <div class="card-header">
                            <p class="h2 bold" align="center"><fmt:message key="profile"/></p>
                        </div>
                        <div class="card-body">
                            <span class="user-info">${user.login}</span>
                            <div class="row">
                                <div class="col-xl-6">
                                    <img src="${imgPath}users_${user.profilePictureName}"
                                         alt="Ваше фото" class="img-fluid img-indents profile-img"/>
                                </div>
                                <div class="col-xl-6">
                                    <p class="h4 bold"><fmt:message key="personal.information"/></p>
                                    <div class="user-info">
                                        <div><span class="bold"><fmt:message key="name"/></span>: <c:out value="${user.name}"/></div>
                                        <div><span class="bold"><fmt:message key="surname"/></span>: <c:out value="${user.surname}"/></div>
                                        <div><span class="bold"><fmt:message key="login"/></span>: <c:out value="${user.login}"/></div>
                                        <div><span class="bold">Email</span>: <c:out value="${user.email}"/></div>
                                        <div><span class="bold"><fmt:message key="birth.date"/></span>: <c:out value="${user.birthDate}"/></div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="container-fluid">
                                    <a href="${updateProfilePageCommand}">
                                        <button type="submit" class="btn btn-outline-secondary">
                                            <fmt:message key="change"/>
                                        </button>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <c:import url="footer.jsp"/>
