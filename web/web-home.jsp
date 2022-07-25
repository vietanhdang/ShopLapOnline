<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home</title>
        <%@include file="parts/head.jsp" %>
    </head>
    <body>
        <div class="main-wrapper">
            <%@include file="parts/header.jsp" %>
            <div class="slider-area bg-gray-8">
                <div class="container">
                    <div class="hero-slider-active-2 nav-style-1 nav-style-1-modify-2 nav-style-1-blue">
                        <c:forEach items="${sliders}" var="slider">
                            <div class="single-hero-slider single-hero-slider-hm9 single-animation-wrap">
                                <div class="row slider-animated-1">
                                    <div class="col-lg-5 col-md-5 col-12 col-sm-6" style="padding-right: 5%">
                                        <div class="hero-slider-content-6 slider-content-hm9">
                                            <h5 class="animated">Sản phẩm mới</h5>
                                            <h1 class="animated">${slider.getTitle()}</h1>
                                            <p class="animated">${slider.getDescription()}</p>
                                            <div class="btn-style-1">
                                                <a class="animated btn-1-padding-4 btn-1-blue btn-1-font-14" href="productDetail?productId=${slider.getProduct().getId()}">Khám phá ngay</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-7 col-md-7 col-12 col-sm-6">
                                        <div class="hm9-hero-slider-img">
                                            <img class="animated" src="./assets/images/slider/${slider.getImageUrl()}" alt="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="about-us-area pt-60 pb-60">
                <div class="container">
                    <div class="about-us-content-3 text-center">
                        <h3>Chào mừng bạn đã đến với <span>ShopLap</span></h3>
                        <p>Chúng tôi có hơn ${totalProduct} sản phẩm laptop khác nhau phù hợp với mục đích mua sắm của bạn</p>
                    </div>
                </div>
            </div>
            <div class="product-categories-area pt-70 pb-70">
                <div class="container">
                    <div class="section-title-btn-wrap mb-25">
                        <div class="section-title-8">
                            <h2>Popular Brands</h2>
                        </div>
                        <div class="btn-style-9">
                            <a href="shop.html">All Product</a>
                        </div>
                    </div>
                    <div class="section-wrap-1">
                        <div class="product-categories-slider-1 nav-style-3">
                            <c:forEach items="${sessionScope.brands}" var="brand">
                                <div class="product-plr-1">
                                    <div class="single-product-wrap">
                                        <div class="product-img product-img-border-transparent mb-15">
                                            <a href="shop?brandId=${brand.getId()}">
                                                <img src="./assets/images/brand-logo/${brand.getImage()}" alt="">
                                            </a>
                                        </div>
                                        <div class="product-content-categories-2 product-content-orange text-center">
                                            <h5 class="font-width-dec"><a href="shop?brandId=${brand.getId()}">${brand.getBrandName()}</a></h5>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="banner-product-wrap pb-70">
                <div class="container">
                    <div class="section-title-btn-wrap mb-25">
                        <div class="section-title-8">
                            <h2>Sản phẩm bán chạy</h2>
                        </div>
                    </div>
                </div>
            </div>
            <c:forEach items="${inforTop5ProductBestSelling}" var="product">
                <div class="banner-product-wrap pb-70">
                    <div class="container">
                        <div class="section-wrap-3">
                            <div class="row">
                                <div class="col-lg-4 col-md-6">
                                    <div class="banner-wrap">
                                        <div class="banner-img banner-img-zoom">
                                            <a href="product-details.html"><img src="assets/images/product/${product.getPreviewImage()}" alt=""></a>
                                        </div>
                                        <div class="banner-content-23 text-center">
                                            <c:choose>
                                                <c:when test="${product.getQuantity() == 0}">
                                                    <div class="banner-btn-3">
                                                        <a>Hết hàng</a>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="banner-btn-3">
                                                        <a onclick="addToCart(${product.getId()}, 'checkout')">Mua ngay</a>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-8 col-md-6">
                                    <div class="product-area product-area-padding">
                                        <div class="section-title-btn-wrap mb-25">
                                            <div class="section-title-8">
                                                <h2>${product.getProductName()}</h2>
                                            </div>
                                        </div>
                                        <div class="tab-content jump">
                                            <div id="product-9" class="tab-pane active">
                                                <div class="product-plr-1">
                                                    <div class="single-product-wrap">
                                                        <div class="product-content-wrap-3">
                                                            <h3 class="mrg-none bold"><a class="orange"><p>Số lượng:</p> ${product.getQuantity()}</a></h3>
                                                            <h3 class="mrg-none bold"><a class="orange"><p>Nhãn hàng:</p> ${product.getBrand().getBrandName()}</a></h3>
                                                            <h3 class="mrg-none bold"><a class="orange"><p>Thể loại:</p> ${product.getCategory().getCategoryName()} </a></h3>
                                                            <h3 class="mrg-none bold"><a class="orange"><p>Trạng thái:</p> ${product.getStatus().getStatusName()} </a></h3>
                                                            <h3 class="mrg-none bold">
                                                                <a class="orange">
                                                                    <p>Price: </p>
                                                                    <fmt:formatNumber
                                                                        value="${product.getUnitPrice()}"
                                                                        type="number"/> &dstrok;
                                                                </a></h3>

                                                        </div>
                                                        <div class="product-content-wrap-3 product-content-position-2 pro-position-2-padding-dec">
                                                            <div class="pro-add-to-cart-2">
                                                                <button title="Add to Cart" onclick="addToCart(${product.getId()})">Thêm giỏ hàng</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <div class="deal-area">
                <div class="container">
                    <div class="deal-bg-color">
                        <div class="row align-items-center">
                            <div class="col-lg-4 col-md-6">
                                <div class="deal-content-2 pl-50">
                                    <span>hot deal</span>
                                    <h2>
                                        <span>50% Giảm giá</span> Apple Mac Mini M1 2020 - 13 Inchs
                                    </h2>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6">
                                <div class="deal-content-2 pl-35">
                                    <p>Mac mini với chip Apple M1. CPU nhanh hơn đến 3x, GPU nhanh hơn đến 6x và Neural Engine mạnh mẽ với công
                                        nghệ máy học nhanh hơn đến 15x.* Cho hiệu năng khủng mà kích cỡ không đổi.</p>
                                    <div class="deal-btn-2">
                                        <a href="products?productId=124903733">Xem trong shop</a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-5 col-md-12">
                                <div class="deal-img">
                                    <a href="product-details.html"><img src="https://salt.tikicdn.com/cache/w1200/media/catalog/producttmp/61/e5/20/47dae3303efd455e2af742b0e4ec3269.jpg" alt=""></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="product-area pt-115 pb-120">
                <div class="container">
                    <div class="section-title-6 section-title-6-xs mb-60 text-center">
                        <h2>Gợi ý hôm nay cho bạn</h2>
                    </div>
                    <div class="row">
                        <c:forEach items="${products}" var="product">
                            <div class="custom-col-5">
                                <div class="single-product-wrap mb-60">
                                    <div class="product-img product-img-zoom mb-15">
                                        <a href="productDetail?productId=${product.getId()}">
                                            <img src="./assets/images/product/${product.getPreviewImage()}" alt="">
                                        </a>
                                        <div
                                            class="product-action-2 tooltip-style-2">
                                            <button type="button" title="Thêm vào giỏ hàng" onclick="addToCart(${product.getId()})"><i class="fa fa-shopping-cart" aria-hidden="true"></i></button>
                                                <c:if test="${sessionScope.loggedCustomer!=null}">
                                                <button type="button" title="Thêm vào danh sách yêu thích" class="addToFavourite" data-id="${product.getId()}"><i
                                                        class="icon-heart"></i></button>
                                                </c:if>
                                            <button title="Thêm vào so sánh sản phẩm"><i
                                                    class="icon-refresh"></i></button>
                                        </div>
                                    </div>
                                    <div class="product-content-wrap-3">
                                        <h3 class="mrg-none"><a class="blue" href="productDetail?productId=${product.getId()}">${product.getProductName()}</a></h3>
                                        <span style="color:red">Hiện có ${product.getQuantity()} sản phẩm</span>
                                        <div class="product-rating-wrap-2">
                                            <div class="product-rating-4">
                                                <c:forEach begin="1" end="${product.getRatingCount()}" step="1">
                                                    <i class="icon_star"></i>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="product-price-4">
                                            <span class="new-price">
                                                <fmt:formatNumber value = "${product.getUnitPrice()}" type="number"/> &dstrok;
                                            </span>
                                            <span class="old-price">
                                                <c:if test="${product.getInitialPrice() !=0}">
                                                    <fmt:formatNumber
                                                        value="${product.getInitialPrice()}"
                                                        type="number"/> &dstrok;
                                                </c:if>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="product-content-wrap-3 product-content-position-2 pro-position-2-padding-dec">
                                        <span style="color:red">Hiện có ${product.getQuantity()} sản phẩm</span>
                                        <h3 class="mrg-none"><a class="blue" href="productDetail?productId=${product.getId()}">${product.getProductName()}</a></h3>
                                        <div class="product-rating-wrap-2">
                                            <div class="product-rating-4">
                                                <c:forEach begin="1" end="${product.getRatingCount()}" step="1">
                                                    <i class="icon_star"></i>
                                                </c:forEach>
                                            </div>
                                            <span>(${product.getOrderCount()})</span>
                                        </div>
                                        <div class="product-price-4">
                                            <span>
                                                <fmt:formatNumber value = "${product.getUnitPrice()}" type="number"/> &dstrok;
                                            </span>
                                        </div>
                                        <div class="pro-add-to-cart-2">
                                            <button title="Buy Now">
                                                <a style="color:white" onclick="addToCart(${product.getId()}, 'checkout')">Mua ngay</a> 
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="more-product-btn text-center">
                        <a href="products">Xem thêm nhiều hơn</a>
                    </div>
                </div>
            </div>
            <%@include file="parts/footer.jsp" %>
        </div>
        <%@include file="parts/javascripts.jsp" %>
    </body>
</html>
