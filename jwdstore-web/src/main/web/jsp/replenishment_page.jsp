<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<c:url value="/controller?command=payment_page" var="paymentPageCommand"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="balance.replenishment"/></title>
    <c:import url="header.jsp"/>
    <main>
        <c:if test="${paymentResult == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="balance.has.been.replenished"/> </strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("paymentResult");%>
        </c:if>
        <c:if test="${isInvalidBalance == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="you.dont.have.enough.funds"/></strong> <fmt:message key="top.up.balance"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isInvalidBalance");%>
        </c:if>
        <c:if test="${paymentResult == false}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="something.went.wrong"/></strong> <fmt:message key="try.again"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("paymentResult");%>
        </c:if>
        <div class="container-fluid">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xl-5"></div>
                    <div class="card replenishment-card">
                        <div class="card-header">
                            <p class="h4 bold replenishment-card-header"><fmt:message key="balance.replenishment"/> </p>
                        </div>
                        <div class="card-body replenishment-card-body">
                            <p><fmt:message key="current.balance"/> <c:out value="${balance}"/> <fmt:message key="currency.sign"/></p><br>
                            <p><fmt:message key="enter.amount"/> </p>
                            <form action="${paymentPageCommand}" method="post" id="replenishment-inputs">
                                <div class="input-group mb-3">
                                    <input id="amount" type="number" step="0.10" min="0" placeholder="0,00"
                                           class="form-control" aria-label="Amount" name="amount">
                                    <div class="input-group-append">
                                        <span class="input-group-text"><fmt:message key="currency.sign"/></span>
                                    </div>
                                </div>
                                <button disabled type="submit" id="replenishment-button" class="btn btn-secondary replenishment-button"><fmt:message key="pay"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="<c:url value="/js/replenishmentValidator.js"/>"></script>
    <c:import url="footer.jsp"/>
