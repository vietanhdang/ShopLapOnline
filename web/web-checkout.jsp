<%-- 
    Document   : web-checkout
    Created on : Jun 13, 2022, 11:05:43 PM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Checkout</title>
        <%@include file="parts/head.jsp" %>
        <style>
            .error{
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="main-wrapper">
            <%@include file="parts/header.jsp" %>
            <div class="breadcrumb-area bg-gray">
                <div class="container">
                    <div class="breadcrumb-content text-center">
                        <ul>
                            <li>
                                <a href="home">Home</a>
                            </li>
                            <li class="active">Checkout </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="checkout-main-area pt-120 pb-120">
                <div class="container">
                    <div class="checkout-wrap pt-30">
                        <form id="form-checkout" action="Checkout" method="post">
                            <div class="row">
                                <div class="col-lg-5">
                                    <div class="billing-info-wrap mr-50">
                                        <h3>Chi tiết thanh toán</h3>
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="billing-info mb-20">
                                                    <label>Họ và tên <abbr class="required" title="required"> * </abbr></label>
                                                    <input type="text" name="fullname" value="${customer.getFullname()}">
                                                </div>
                                            </div>

                                            <div class="col-lg-12">
                                                <div class="billing-info mb-20">
                                                    <label>Địa chỉ <abbr class="required" title="required"> * </abbr></label>
                                                    <input class="billing-address" name="address" type="text" value="${customer.getAddress()}">
                                                </div>
                                            </div>
                                            <div class="col-lg-12 col-md-12">
                                                <div class="billing-info mb-20">
                                                    <label>Số điện thoại <abbr class="required" title="required"> * </abbr></label>
                                                    <input type="text" name="phone" value="${customer.getPhone()}">
                                                </div>
                                            </div>
                                            <div class="col-lg-12 col-md-12">
                                                <div class="billing-info mb-20">
                                                    <label>Địa chỉ email<abbr class="required" title="required"> * </abbr></label>
                                                    <input type="text" name="email" value="${customer.getAccount().getUsername()}">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="additional-info-wrap">
                                            <label>Ghi chú đơn hàng</label>
                                            <textarea placeholder="Ghi chú về đơn đặt hàng của bạn, ví dụ: những lưu ý đặc biệt khi giao hàng." name="noteorder"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-7">
                                    <div class="your-order-area">
                                        <h3>Hóa đơn của bạn</h3>
                                        <div class="your-order-wrap gray-bg-4">
                                            <table class="table">
                                                <thead>
                                                    <tr>
                                                        <th scope="col">Product</th>
                                                        <th scope="col">Quantity</th>
                                                        <th scope="col" style="width: 35%">Total</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <c:forEach items="${sessionScope.CART.getItems()}" var="cart">
                                                        <tr>
                                                            <th scope="row">${cart.getProduct().getProductName()}</th>

                                                            <td>
                                                                ${cart.getQuantity()}
                                                            </td>
                                                            <td>
                                                                <fmt:formatNumber
                                                                    value="${cart.getQuantity()* cart.getProduct().getUnitPrice()}"
                                                                    type="number"/> &dstrok;
                                                            </td>
                                                        </tr>
                                                    <div hidden="">
                                                        ${totalQuantity = totalQuantity + cart.getQuantity()}
                                                    </div>
                                                </c:forEach>
                                                <tr class="table-active text-danger">
                                                    <th scope="row">Total</th>
                                                    <th scope="row">${totalQuantity}</th>
                                                    <th scope="row">
                                                        <fmt:formatNumber
                                                            value="${sessionScope.CART.getOrderTotal()}"
                                                            type="number"/> &dstrok;
                                                    </th>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <div class="payment-method">
                                                <h4>Phương thức thanh toán</h4>
                                                <div class="pay-top sin-payment">
                                                    <input id="payment_method_1" class="input-radio" type="radio" value="1" checked="checked" name="payment_method">
                                                    <label for="payment_method_1"> Thanh toán khi nhận hàng </label>
                                                    <div class="payment-box payment_method_bacs">
                                                        <p>Phí thu hộ: đ0 VND. Ưu đãi về phí vận chuyển (nếu có) áp dụng cả với phí thu hộ.</p>
                                                    </div>
                                                </div>
                                                <div class="pay-top sin-payment sin-payment-3">
                                                    <input id="payment-method-4" class="input-radio" type="radio" value="0" name="payment_method">
                                                    <label for="payment-method-4">Thẻ tín dụng/ thẻ ghi nợ<img alt="" src="assets/images/icon-img/payment.png"></label>
                                                    <div class="payment-box payment_method_bacs">
                                                        <p>Chức năng đang được cập nhật</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <button style="width:100%" type="submit"  class="btn btn-outline-danger">Đặt hàng</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <%@include file="parts/footer.jsp" %>
            <%@include file="parts/javascripts.jsp" %>

        </div>
    </body>
</html>

