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
                    <strong>Данные были успешно обновлены!</strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isUserUpdated");%>
        </c:if>
        <c:if test="${isUserUpdated == false}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Данные не были обновлены!</strong>
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
                                        <div><fmt:message key="name"/>: <c:out value="${user.name}"/></div>
                                        <div><fmt:message key="surname"/>: <c:out value="${user.surname}"/></div>
                                        <div><fmt:message key="login"/>: <c:out value="${user.login}"/></div>
                                        <div>Email: <c:out value="${user.email}"/></div>
                                        <div><fmt:message key="birth.date"/>: <c:out value="${user.birthDate}"/></div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="container-fluid">
                                    <a href="${updateProfilePageCommand}">
                                        <button type="submit" class="buy-button">
                                            Изменить
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
