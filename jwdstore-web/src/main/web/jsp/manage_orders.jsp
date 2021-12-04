<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<c:url value="/controller?command=update_order_status" var="changeOrderStatusCommand"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title>Управление заказами</title>
    <c:import url="header.jsp"/>
    <main>
        <c:if test="${isOrderUpdated == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Статус заказа успешно изменен!</strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isOrderUpdated");%>
        </c:if>
        <c:if test="${isOrderUpdated == false}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="something.went.wrong"/></strong> <fmt:message key="try.again"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isOrderUpdated");%>
        </c:if>
        <div class="container-fluid name">
            <p class="h2 bold">Управление заказами</p>
        </div>
        <c:choose>
            <c:when test="${empty orders}">
                <div class="container-fluid">
                    <h5 class="basket-good-header">Активных заказов пока нет!</h5>
                </div>
            </c:when>
            <c:when test="${!empty orders}">
                <c:forEach var="order" items="${orders}">
                    <div class="container-fluid">
                        <div class="card order-card-size">
                            <div class="card-header order-info"><fmt:message key="order.number"/> ${order.id}
                                <br/>Адрес доставки: <c:out value="${order.address}"/>
                                <br/><fmt:message key="order.price"/> <c:out value="${order.price}"/> <fmt:message
                                        key="currency.sign"/>
                                <br/>Имя &#47; почта заказчика: <c:out value="${order.customer.name}"/> <c:out
                                        value="${order.customer.surname}"/> &#47; <c:out
                                        value="${order.customer.email}"/>
                            </div>
                            <div class="card-body">
                                <c:forEach var="goodEntry" items="${order.goods.entrySet()}">
                                    <div class="row">
                                        <div class="col-xl-2">
                                            <a href="controller?command=good&goodId=${goodEntry.key.id}"><img
                                                    src="${imgPath}goods_${goodEntry.key.imgName}"
                                                    class="img-fluid img-indents order-img-size" alt="good"></a>
                                        </div>
                                        <div class="col-xl-6 order-info">
                                            <span class="bold"><fmt:message key="good.name"/></span> <a
                                                href="controller?command=good&goodId=${goodEntry.key.id}"><c:out
                                                value="${goodEntry.key.name}"/></a>
                                            <br/> <span class="bold"><fmt:message
                                                key="quantity"/></span> <c:out value="${goodEntry.value}"/>
                                        </div>
                                    </div>
                                    <hr/>
                                </c:forEach>
                                <form method="post" action="${changeOrderStatusCommand}">
                                <div class="row">
                                    <span class="status-message"><fmt:message key="order.status"/></span>
                                        <div class="col-lg-2">
                                            <select class="custom-select" name="status">
                                                <c:forEach var="status" items="${statuses}">
                                                    <option ${order.status.statusContent == status.statusContent ? 'selected' : ''}>${status.statusContent}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <input type="hidden" value="${order.id}" name="orderId">
                                        <button class="btn btn-outline-secondary" type="submit"
                                                id="button-addon2"><fmt:message
                                                key="change"/></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
        </c:choose>
    </main>
    <c:import url="footer.jsp"/>
