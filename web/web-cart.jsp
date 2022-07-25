<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Cart</title>
        <%@include file="parts/head.jsp" %>
    </head>
    <body>
        <div class="main-wrapper">
            <%@include file="parts/header.jsp" %>
            <div class="breadcrumb-area bg-gray">
                <div class="container">
                    <div class="breadcrumb-content text-center">
                        <ul>
                            <li>
                                <a href="home">Trang chủ</a>
                            </li>
                            <li class="active">Giỏ hàng</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="cart-main-area pt-50 pb-120">
                <div class="container">
                    <c:if test="${sessionScope.CART == null or sessionScope.CART.getOrderTotal()==0}">
                        <h3 class="cart-page-title text-center">Giỏ hàng của bạn còn trống</h3>
                        <div class="cart-shiping-update-wrapper d-flex justify-content-center">
                            <div class="cart-shiping-update">
                                <a href="products">
                                    <i class="fa fa-shopping-cart"></i>
                                    <span> Mua ngay </span>
                                </a>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.CART.getOrderTotal()>0}">
                        <h1 class="cart-page-title text-center pb-40">Giỏ hàng của bạn hiện tại đang có
                            : ${sessionScope.CART.getItems().size()} sản phẩm</h1>
                        <div class="row">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="table-content table-responsive cart-table-content">
                                    <table class="table table-hover table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Hình ảnh sản phẩm</th>
                                                <th>Tên sản phẩm</th>
                                                <th>Giá sản phẩm</th>
                                                <th>Số lượng</th>
                                                <th>Giá tiền</th>
                                                <th>Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${sessionScope.CART.getItems()}" var="cart">
                                                <tr>
                                                    <td class="product-thumbnail">
                                                        <a href="#"><img style="width: 100%;"
                                                                         src="assets/images/product/${cart.getProduct().getPreviewImage()}"
                                                                         alt=""></a>
                                                    </td>
                                                    <td class="product-name">
                                                        <a href="#" class="row">
                                                            <span class="col-md-12">${cart.getProduct().getProductName()}</span>
                                                            <span class="col-md-12 text-muted">Số lượng còn lại: ${cart.getProduct().getQuantity()}</span>
                                                        </a>
                                                    </td>
                                                    <td class="product-price-cart"><span class="amount"><fmt:formatNumber
                                                                value="${cart.getProduct().getUnitPrice()}"
                                                                type="number"/> &dstrok; </span></td>
                                                    <td class="product-quantity pro-details-quality">
                                                        <div class="cart-plus-minus">
                                                            <div class="dec qtybutton"
                                                                 onclick="updateItemMinus(this,${cart.getProduct().getId()})">-
                                                            </div>
                                                            <input class="cart-plus-minus-box" type="text" name="quantity"
                                                                   value="${cart.getQuantity()}"
                                                                   data-id="${cart.getProduct().getId()}">
                                                            <div class="inc qtybutton"
                                                                 onclick="updateItemPlus(this,${cart.getProduct().getId()})">+
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td class="product-subtotal"><fmt:formatNumber
                                                            value="${cart.getPrice()}"
                                                            type="number"/> &dstrok;
                                                    </td>
                                                    <td class="product-remove">
                                                        <a onclick="deleteProductInCart('deleteProduct',${cart.getProduct().getId()})"><i
                                                                class="icon_close"></i></a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td class="product-thumbnail">

                                                </td>
                                                <td class="product-name">

                                                </td>
                                                <td class="product-price-cart">

                                                </td>
                                                <td class="product-quantity pro-details-quality">
                                                    ${sessionScope.CART.getTotalQuantityOfProduct()}
                                                </td>
                                                <td class="product-subtotal"><fmt:formatNumber
                                                        value="${sessionScope.CART.getOrderTotal()}"
                                                        type="number"/> &dstrok;
                                                </td>
                                                <td class="product-remove">

                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="grand-totall">
                                            <div class="title-wrap">
                                                <h4 class="cart-bottom-title section-bg-gary-cart">Tổng số sản phẩm
                                                    (${sessionScope.CART.getTotalQuantityOfProduct()})</h4>
                                            </div>
                                            <h5></h5>
                                            <h4 class="grand-totall-title">Tổng thanh toán<span><fmt:formatNumber
                                                        value="${sessionScope.CART.getOrderTotal()}"
                                                        type="number"/> &dstrok;</span></h4>
                                            <a href="Checkout">Tiến hành đặt hàng</a>
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="cart-shiping-update-wrapper">
                                            <div class="cart-shiping-update">
                                                <a href="products">Tiếp tục mua sắm</a>
                                            </div>
                                            <div class="cart-clear">
                                                <a onclick="deleteProductInCart('deleteAll', 0)">Xóa giỏ hàng</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <%@include file="parts/footer.jsp" %>
        <%@include file="parts/javascripts.jsp" %>
        <script>

            const updateItem = (productId, act, quantity) => {
                var k = $.Deferred();
                $.ajax({
                    async: false,
                    url: "Cart",
                    type: "POST",
                    data: {
                        productId: productId,
                        action: act,
                        quantity: quantity
                    },
                }).done(function (data) {
                    k.resolve(data);
                }).catch(function (err) {
                    swal({
                        title: "Lỗi",
                        text: err.responseText,
                        icon: "error",
                        button: "OK",
                    });
                });
                return k.promise();
            };
            $('input[name=quantity]').change(function () {
                var quantity = $(this).val();
                var productId = $(this).attr('data-id');
                updateItem(productId, 'updateQuantity', quantity).then(() => {
                    location.reload(); // reload page
                });
            });

            function updateItemMinus(event, productId) {
                updateItem(productId, "updateItemMinus").then(() => {
                    window.location.reload();
                });
            }

            function updateItemPlus(event, productId) {
                updateItem(productId, "updateItemPlus").then(() => {
                    // load current page
                    window.location.reload();
                });
            }</script>
    </body>
</html>
