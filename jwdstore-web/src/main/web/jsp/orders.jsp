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
        <c:if test="${placingOrderResult == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="order.was.created.successfully"/> </strong> <fmt:message key="order.was.created.successfully.info"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("placingOrderResult");%>
        </c:if>
        <c:if test="${placingOrderResult == false}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="something.went.wrong"/></strong> <fmt:message key="try.again"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("placingOrderResult");%>
        </c:if>
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
                        <div class="card order-card-size">
                            <div class="card-header order-info"><fmt:message key="order.number"/> ${order.id}
                                <br/><fmt:message key="delivery.address"/>:  <c:out value="${order.address}"/>
                                <br/><fmt:message key="ordering.date"/> : <c:out value="${order.date}"/>
                                <br/><fmt:message key="order.price"/>  <c:out value="${order.price}"/> <fmt:message key="currency.sign"/>
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
                                            <span class="bold"><fmt:message key="good.name"/>:</span> <a
                                                href="controller?command=good&goodId=${goodEntry.key.id}"><c:out value="${goodEntry.key.name}"/></a>
                                            <br/> <span class="bold"><fmt:message
                                                    key="quantity"/></span> <c:out value="${goodEntry.value}"/>
                                        </div>
                                    </div>
                                    <hr/>
                                </c:forEach>
                                <div class="order-status h4"><c:out value="${order.status.statusContent}"/></div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
        </c:choose>
    </main>
    <c:import url="footer.jsp"/>
