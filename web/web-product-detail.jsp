<%-- 
    Document   : web-product-detail
    Created on : Jun 11, 2022, 9:53:47 PM
    Author     : pc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Sản phẩm ${productDetails.getProduct().getProductName()}</title>
        <link rel="shortcut icon" type="image/x-icon" href="assets/images/favicon.png">
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
                            <li class="active">Chi tiết sản phẩm</li>
                        </ul>
                    </div>
                </div>
            </div>
            <c:if test="${message != null}">
                <div class="cart-main-area pt-115 pb-120">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                <h3 class="cart-page-title text-center">${message}</h3>
                                <div class="cart-shiping-update-wrapper d-flex justify-content-center">
                                    <div class="cart-shiping-update">
                                        <a href="products">
                                            <i class="fa fa-shopping-cart"></i>
                                            <span> Xem các sản phẩm </span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${message == null}">
                <div class="product-details-area pt-120 pb-115">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-6 col-md-6">
                                <div class="product-details-tab">
                                    <div class="product-dec-right pro-dec-big-img-slider">

                                        <div class="easyzoom-style">
                                            <div class="easyzoom easyzoom--overlay is-ready">
                                                <a href="assets/images/product/${productDetails.getProduct().getPreviewImage()}">
                                                    <img src="assets/images/product/${productDetails.getProduct().getPreviewImage()}" alt="">
                                                </a>
                                            </div>
                                            <a class="easyzoom-pop-up img-popup" href="assets/images/product/${productDetails.getProduct().getPreviewImage()}"><i class="icon-size-fullscreen"></i></a>
                                        </div>
                                        <c:forEach items="${productDetails.getImages()}" var="image">
                                            <div class="easyzoom-style">
                                                <div class="easyzoom easyzoom--overlay">
                                                    <a href="assets/images/product-details/${image}">
                                                        <img src="assets/images/product-details/${image}" alt="">
                                                    </a>
                                                </div>
                                                <a class="easyzoom-pop-up img-popup" href="assets/images/product-details/${image}"><i class="icon-size-fullscreen"></i></a>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <div class="product-dec-left product-dec-slider-small-2 product-dec-small-style2">
                                        <div class="product-dec-small active">
                                            <img src="assets/images/product/${productDetails.getProduct().getPreviewImage()}" alt="">
                                        </div>
                                        <c:forEach items="${productDetails.getImages()}" var="image">
                                            <div class="product-dec-small">
                                                <img src="assets/images/product-details/${image}" alt="">
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6">
                                <div class="product-details-content pro-details-content-mrg">
                                    <h2>${productDetails.getProduct().getProductName()}</h2>
                                    <div class="product-ratting-review-wrap">
                                        <div class="product-ratting-digit-wrap">
                                            <div class="product-ratting">
                                                <c:forEach begin="1" end="${productDetails.getProduct().getRating()}" step="1">
                                                    <i class="icon_star"></i>
                                                </c:forEach>
                                                <div class="product-digit">
                                                    <span>${productDetails.getProduct().getRating()}.0</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="product-review-order">
                                            <span>(${productDetails.getComments().size()} Đánh giá) (${productDetails.getProduct().getQuantitySold()} Đã bán)</span>
                                        </div>
                                    </div>
                                    <div class="pro-details-price">
                                        <span class="new-price"><fmt:formatNumber
                                                value="${productDetails.getProduct().getUnitPrice()}"
                                                type="number"/> &dstrok;
                                        </span>
                                        <span class="old-price">
                                            <c:if test="${productDetails.getProduct().getInitialPrice() !=0}">
                                                <fmt:formatNumber
                                                    value="${productDetails.getProduct().getInitialPrice()}"
                                                    type="number"/> &dstrok;
                                            </c:if>
                                        </span>
                                    </div>
                                    <div class="product-details-meta">
                                        <ul>
                                            <li><span style="display: inline">Thương hiệu: </span> <a href="products?brandId=${productDetails.getProduct().getBrand().getId()}">
                                                    ${productDetails.getProduct().getBrand().getBrandName()}</a></li>
                                            <li><span>Danh mục: </span> <a href="products?categoryId=${productDetails.getProduct().getCategory().getId()}">
                                                    ${productDetails.getProduct().getCategory().getCategoryName()}</a></li>
                                            <li><span>Hiện có: </span> <fmt:formatNumber value="${productDetails.getProduct().getQuantity()}" type="number"></fmt:formatNumber></li>
                                            <li><span>Tình trạng: </span> ${productDetails.getProduct().getStatus().getStatusName()}</li>
                                        </ul>
                                    </div>
                                    <div class="pro-details-action-wrap">
                                        <div class="pro-details-add-to-cart">
                                            <a title="Thêm vào giỏ hàng" onclick="addToCart(${productDetails.getProduct().getId()})">Thêm vào giỏ hàng</a>
                                        </div>
                                        <div class="pro-details-action">
                                            <c:if test="${sessionScope.ACCOUNTLOGGED != null && sessionScope.ACCOUNTLOGGED.role.roleID == 3}">
                                                <a title="Thêm vào danh sách yêu thích" 
                                                   onclick="addToWishlist(${productDetails.getProduct().getId()})">
                                                    <i class="icon-heart"></i>
                                                </a>
                                            </c:if>
                                            <a title="Add to Compare" href="#"><i class="icon-refresh"></i></a>
                                            <a class="social" title="Social" href="#"><i class="icon-share"></i></a>
                                            <div class="product-dec-social">
                                                <a class="facebook" title="Facebook" href="#"><i class="icon-social-facebook"></i></a>
                                                <a class="twitter" title="Twitter" href="#"><i class="icon-social-twitter"></i></a>
                                                <a class="instagram" title="Instagram" href="#"><i class="icon-social-instagram"></i></a>
                                                <a class="pinterest" title="Pinterest" href="#"><i class="icon-social-pinterest"></i></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="description-review-wrapper pb-110">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="dec-review-topbar nav mb-45">
                                    <a class="active" data-toggle="tab" href="#des-details1">Description</a>
                                    <c:if test="${productDetails.getProduct().getSpecifiedAttributeValues() != null 
                                                  && !productDetails.getProduct().getSpecifiedAttributeValues().isEmpty()}">
                                          <a data-toggle="tab" href="#des-details2">Specification</a>
                                    </c:if>
                                    <a data-toggle="tab" href="#des-details3">Reviews and Rating </a>
                                </div>
                                <div class="tab-content dec-review-bottom">
                                    <div id="des-details1" class="tab-pane active">
                                        <div class="description-wrap">
                                            ${productDetails.getDescription().toString()}
                                        </div>
                                    </div>
                                    <div id="des-details2" class="tab-pane">
                                        <div class="specification-wrap table-responsive">
                                            <table>
                                                <tbody>
                                                    <c:forEach items="${productDetails.getProduct().getSpecifiedAttributeValues()}" var="attr">
                                                        <tr>
                                                            <td class="title width1">${attr.getSpecifiedAttribute().getName()}</td>
                                                            <td>${attr.getName()}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div id="des-details3" class="tab-pane">
                                        <div class="review-wrapper">
                                            <h2>${productDetails.getComments().size()} đánh giá cho ${productDetails.getProduct().getProductName()}</h2>
                                            <c:forEach items="${productDetails.getComments()}" var="comment">
                                                <div class="single-review">
                                                    <div class="review-img">
                                                        <img src="assets/images/account/${comment.getPreviewImage()}" alt="" height="60" width="60">
                                                    </div>
                                                    <div class="review-content">
                                                        <div class="review-top-wrap">
                                                            <div class="review-name">
                                                                <h5><span>${comment.getFullname()}</span> - ${comment.getUpdatedTime()}</h5>
                                                            </div>
                                                            <div class="review-rating">
                                                                <c:forEach begin="1" end="${comment.getRating()}" step="1">
                                                                    <i class="yellow icon_star"></i>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                        <p>${comment.getComment()}</p>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
        <%@include file="parts/footer.jsp" %>
        <%@include file="parts/javascripts.jsp" %>
        <script src="assets/js/plugins/pagination.min.js"></script>
    </body>
</html>
