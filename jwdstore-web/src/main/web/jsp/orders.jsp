<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="orders"/></title>
    <c:import url="header.jsp"/>
    <main>
        <div class="container-fluid name">
            <p class="h2 bold"><fmt:message key="your.orders"/></p>
        </div>
        <c:choose>
            <c:when test="${isEmptyOrderList == true}">
                <div class="container-fluid">
                    <h5 class="basket-good-header"><fmt:message key="no.orders.yet"/></h5>
                </div>
            </c:when>
            <c:when test="${isEmptyOrderList == false}">
                <c:forEach var="order" items="${orderList}">
                    <div class="container-fluid">
                        <div class="card card-size">
                            <h5 class="card-header"><fmt:message key="order.number"/> ${order.id}
                                <br/><fmt:message key="order.status"/> ${order.status.statusContent}
                                <br/><fmt:message key="order.price"/> ${order.price} <fmt:message key="currency.sign"/></h5>
                            <div class="card-body">
                                <c:forEach var="goodEntry" items="${order.goods.entrySet()}">
                                    <div class="row">
                                        <div class="col-xl-2">
                                            <a href="controller?command=good&goodId=${goodEntry.key.id}"><img src="${imgPath}goods_${goodEntry.key.imgURL}" class="img-fluid img-indents order-img-size" alt="good"></a>
                                        </div>
                                        <div class="col-xl-6 order-info">
                                            <span class="bold"><fmt:message key="good.name"/></span> <a href="controller?command=good&goodId=${goodEntry.key.id}">${goodEntry.key.name}<a/>
                                            <br/> <span class="bold"><fmt:message key="quantity"/></span> ${goodEntry.value}
                                        </div>
                                    </div>
                                    <hr/>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
        </c:choose>
    </main>
    <c:import url="footer.jsp"/>
