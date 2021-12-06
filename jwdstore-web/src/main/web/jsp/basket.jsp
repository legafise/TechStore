<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<c:url value="/controller?command=remove_good_from_basket" var="removeGoodFromBasketCommand"/>
<c:url value="/controller?command=change_good_quantity" var="changeGoodQuantityCommand"/>
<c:url value="/controller?command=clear_basket" var="clearBasketCommand"/>
<c:url value="/controller?command=place_basket_order" var="placeBasketOrder"/>
<c:url value="/controller?command=place_order_by_buy_button" var="placeOrderByBuyButton"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>
<c:set var="totalPrice" value="0"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="basket"/></title>
    <c:import url="header.jsp"/>
    <main class="content">
        <c:if test="${goodUpdatingResult == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="quantity.updated"/></strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("goodUpdatingResult");%>
        </c:if>
        <c:if test="${clearingBasketResult == true}">
        <div class="container-fluid authorization-result">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <strong><fmt:message key="basket.cleaned"/></strong>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
        <%request.getSession().removeAttribute("clearingBasketResult");%>
    </c:if>
        <c:if test="${goodRemovingResult == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="good.removed"/> </strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("goodRemovingResult");%>
        </c:if>
        <c:if test="${goodRemovingResult == false || goodUpdatingResult == false || clearingBasketResult == false}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="something.went.wrong"/></strong> <fmt:message key="try.again"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("goodRemovingResult");%>
            <%request.getSession().removeAttribute("goodUpdatingResult");%>
            <%request.getSession().removeAttribute("clearingBasketResult");%>
        </c:if>
        <div class="container-fluid name">
            <p class="h2 bold"><fmt:message key="basket"/></p>
        </div>
        <c:choose>
            <c:when test="${isBasketEmpty == true}">
                <div class="container-fluid">
                    <h5 class="basket-good-header"><fmt:message key="basket.is.empty"/></h5>
                </div>
            </c:when>
            <c:when test="${isBasketEmpty == false}">
                <div class="container-fluid">
                    <c:forEach var="entry" items="${basketGoods}">
                        <div class="card card-size">
                            <h5 class="card-header basket-good-header"><c:out value="${entry.key.name}"/></h5>
                            <div class="card-body">
                                <div class="row basket-description-text">
                                    <div class="col-xl-3">
                                        <img src="${imgPath}goods_${entry.key.imgName}"
                                             class="img-fluid img-indents img-size" alt="">
                                    </div>
                                    <div class="col-xl-9">
                                        <span class="bold"><fmt:message
                                                key="description"/></span> <c:out value="${entry.key.description}"/>
                                        <br>
                                        <span class="bold"><fmt:message key="type"/></span> <c:out
                                            value="${entry.key.type.name}"/> <br><br>
                                        <form method="post" action="${changeGoodQuantityCommand}">
                                            <div class="row">
                                                <div class="input-group mb-3 quantity-form">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><fmt:message
                                                                key="quantity"/></span>
                                                    </div>
                                                    <input type="hidden" value="${entry.key.id}" name="goodId">
                                                    <input type="number" min="1" step="1" value="${entry.value}"
                                                           class="form-control" name="quantity"
                                                           placeholder="Количество"
                                                           aria-describedby="button-addon2">
                                                    <div class="input-group-append">
                                                        <button class="btn btn-outline-secondary" type="submit"
                                                                id="button-addon2"><fmt:message
                                                                key="change"/></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <span class="price-in-basket"><fmt:message
                                        key="price"/>: ${entry.key.price * entry.value} <fmt:message
                                        key="currency.sign"/> </span>
                                <br>
                                <form action="${placeOrderByBuyButton}" method="post">
                                    <input type="hidden" name="goodId" value="${entry.key.id}">
                                    <input type="hidden" name="quantity" value=${entry.value}>
                                    <button type="submit" class="btn basket-buy-button"><fmt:message
                                            key="buy.basket.good"/></button>
                                </form>
                                <form action="${removeGoodFromBasketCommand}" method="post">
                                    <input type="hidden" value="${entry.key.id}" name="goodId"/>
                                    <button class="btn delete-button"><fmt:message key="remove"/></button>
                                </form>
                            </div>
                            <input type="hidden" value="${totalPrice = totalPrice + entry.key.price * entry.value}">
                        </div>
                    </c:forEach>
                </div>
                <div class="container-fluid basket-description-text">
                    <span class="price-in-basket"><fmt:message key="total.price"/> ${totalPrice} <fmt:message
                            key="currency.sign"/> </span>
                    <div>
                        <form method="post" action="${placeBasketOrder}">
                            <button type="submit" class="btn basket-buy-button"><fmt:message key="to.order"/></button>
                        </form>
                        <form method="post" action="${clearBasketCommand}">
                            <button type="submit" class="btn delete-button"><fmt:message
                                    key="remove.goods.from.basket"/></button>
                        </form>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </main>
<c:import url="footer.jsp"/>