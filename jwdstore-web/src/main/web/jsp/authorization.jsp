<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>
<c:url value="/controller?command=authorization" var="authorizationCommand"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="authorization"/></title>
    <c:import url="header.jsp"/>
    <main>
        <c:if test="${registrationResult == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="positive.registration.result"/> </strong> <fmt:message key="positive.registration.result.info"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("registrationResult");%>
        </c:if>
        <c:if test="${authorizationResult == false}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="bad.authorization.result"/> </strong> <fmt:message key="bad.authorization.result.info"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("authorizationResult");%>
        </c:if>
        <c:if test="${authorizationInformation == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="you.are.not.authorized"/> </strong> <fmt:message key="you.are.not.authorized.info"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("authorizationInformation");%>
        </c:if>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xl-4"></div>
                <div class="col-xl-4">
                    <div class="card text-center authorization-form-indents">
                        <div class="card-header authorization bold">
                            <fmt:message key="authorization"/>
                        </div>
                        <div class="card-body">
                            <form action="${authorizationCommand}" method="post" id="authorization-inputs">
                                <div class="form-group">
                                    <label for="email" class="authorization">Email</label>
                                    <input type="email" class="form-control" id="email" aria-describedby="emailHelp"
                                           name="email">
                                </div>
                                <div class="form-group">
                                    <label for="password" class="authorization"><fmt:message key="password"/></label>
                                    <input type="password" class="form-control" id="password" name="password">
                                </div>
                                <button type="submit" class="btn btn-secondary authorization authorization-button">
                                    <fmt:message key="sign.in"/>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="<c:url value="/js/authorizationValidator.js"/>"></script>
    <c:import url="footer.jsp"/>
