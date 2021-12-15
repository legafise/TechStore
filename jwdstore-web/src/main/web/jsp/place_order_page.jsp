<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<c:url value="/controller?command=place_order" var="placeOrderCommand"/>
<c:set value="${goods}" scope="session" var="goods"/>
<c:set value="${price}" scope="session" var="price"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="ordering"/> </title>
    <c:import url="header.jsp"/>
    <main>
        <c:if test="${isInvalidAddress == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="invalid.address.message"/> </strong> <fmt:message key="try.again"/>!
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isInvalidAddress");%>
        </c:if>
        <div class="container-fluid name">
            <p class="h2 bold"><fmt:message key="ordering"/> </p>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-3"></div>
                <div class="col-lg-6">
                    <div class="card card-size place-order-card">
                        <h4 align="center" class="card-header"><fmt:message key="fill.reqired.information"/> </h4>
                        <div class="card-body order-card-indents">
                            <form method="post" action="${placeOrderCommand}" id="order-inputs">
                                <h5 align="center"><fmt:message key="address"/> </h5>
                                <input class="form-control" id="address" type="text" name="address" placeholder="<fmt:message key="enter.address"/> "><br/>
                                <hr/>
                                <h5 align="center"><fmt:message key="ordering.goods"/> :</h5><br/>
                                <c:forEach var="goodEntry" items="${goods.entrySet()}">
                                    <div class="row">
                                        <div class="col-xl-3">
                                            <a href="controller?command=good&goodId=${goodEntry.key.id}"><img
                                                    src="${imgPath}goods_${goodEntry.key.imgName}"
                                                    class="img-fluid img-indents place-order-img-size" alt="good"></a>
                                        </div>
                                        <div class="col-xl-6 order-info">
                                            <span class="bold"><fmt:message key="good.name"/></span> <a
                                                href="controller?command=good&goodId=${goodEntry.key.id}"> <c:out value="${goodEntry.key.name}"/></a>
                                            <br/> <span class="bold"><fmt:message
                                                    key="quantity"/></span>  <c:out value="${goodEntry.value}"/>
                                        </div>
                                    </div>
                                    <hr/>
                                </c:forEach>
                                <br/>
                                <input type="hidden" value="${price}" name="price">
                                <h5 align="center"><fmt:message key="order.price"/>  <c:out value="${price}"/> <fmt:message
                                        key="currency.sign"/></h5>
                                <button type="submit" id="place-order-button" disabled class="btn basket-buy-button place-order-button"><fmt:message key="place.order"/>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="<c:url value="/js/placeOrderValidator.js"/>"></script>
<c:import url="footer.jsp"/>