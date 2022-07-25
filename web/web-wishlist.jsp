<%-- 
    Document   : web-wishlist
    Created on : Jun 14, 2022, 3:18:07 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách yêu thích của bạn</title>
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
                                <a href="index.html">Home</a>
                            </li>
                            <li class="active">Danh sách yêu thích </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="cart-main-area pt-115 pb-120">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                            <c:if test="${sessionScope.WISHLIST == null || sessionScope.WISHLIST.getFavourites().isEmpty()}">
                                <h3 class="cart-page-title text-center">Bạn chưa có sản phẩm yêu thích</h3>
                                <div class="cart-shiping-update-wrapper d-flex justify-content-center">
                                    <div class="cart-shiping-update">
                                        <a href="products">
                                            <i class="fa fa-shopping-cart"></i>
                                            <span> Xem các sản phẩm </span>
                                        </a>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${sessionScope.WISHLIST != null && !sessionScope.WISHLIST.getFavourites().isEmpty()}">
                                <form action="#">
                                    <div class="table-content table-responsive cart-table-content">
                                        <table>
                                            <thead>
                                                <tr>
                                                    <th>Hình ảnh sản phẩm</th>
                                                    <th>Tên sản phẩm</th>
                                                    <th>Giá sản phẩm</th>
                                                    <th colspan="2">Hành động</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${sessionScope.WISHLIST.getFavourites()}" var="fav">
                                                    <tr>
                                                        <td class="product-thumbnail">
                                                            <a href="#"><img src="assets/images/product/${fav.getProductImage()}" alt=""></a>
                                                        </td>
                                                        <td class="product-name">
                                                            <a href="<%=request.getContextPath()%>/productDetail?productId=${fav.getProductId()}">${fav.getProductName()}</a>
                                                        </td>
                                                        <td class="product-price-cart"><span class="amount"><fmt:formatNumber
                                                                    value="${fav.getUnitPrice()}"
                                                                    type="number"/> &dstrok; </span></td>
                                                        <td class="product-wishlist-cart">
                                                            <a title="Add to Cart" onclick="addToCart(${fav.getProductId()})">Thêm vào giỏ hàng</a>
                                                        </td>
                                                        <td class="product-remove">
                                                            <a onclick="deleteProductInWishlist(${fav.getProductId()})"><i
                                                                    class="icon_close"></i></a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="parts/footer.jsp" %>
        <%@include file="parts/javascripts.jsp" %>
        <script src="assets/js/plugins/pagination.min.js"></script>
    </body>
</html>
