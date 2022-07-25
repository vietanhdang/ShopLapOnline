<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<div id="preloader">
    <img class="logo" src="<%=request.getContextPath()%>/assets/images/logo/logo.png" alt="">
    <div id="status">
        <span></span>
        <span></span>
    </div>
</div>
<header class="header-area">
    <div class="container">
        <div class="header-large-device">
            <div class="header-top header-top-ptb-1 border-bottom-1">
                <div class="row">
                    <div class="col-xl-4 col-lg-5">
                        <div class="header-offer-wrap">
                            <p><i class="icon-paper-plane"></i> GIAO HÀNG miễn phí toàn quốc
                                <span></span>
                            </p>
                        </div>
                    </div>
                    <div class="col-xl-8 col-lg-7">
                        <div class="header-top-right">
                            <div class="same-style-wrap">
                                <div class="same-style same-style-border track-order">
                                    <c:choose>
                                        <c:when test="${sessionScope.ACCOUNTLOGGED !=null }">
                                            <c:if test="${ACCOUNTLOGGED.role.roleID == 1}">
                                                Xin chào ADMIN - ${sessionScope.ACCOUNTLOGGED.getUsername()}
                                                <a href="<%=request.getContextPath()%>/admin/dashboard"><i class="fa fa-external-link"
                                                                             aria-hidden="true"></i> Tới trang quản
                                                    lý</a>
                                                <a href="<%=request.getContextPath()%>/logout"><i class="fa fa-sign-out"></i> Đăng xuất</a>
                                            </c:if>
                                            <c:if test="${ACCOUNTLOGGED.role.roleID == 2}">
                                                Xin chào Sales - ${sessionScope.ACCOUNTLOGGED.getUsername()}
                                                <a href="<%=request.getContextPath()%>/sale/dashboard"><i class="fa fa-external-link"
                                                                             aria-hidden="true"></i> Tới trang quản lý
                                                    bán hàng</a>
                                                <a href="<%=request.getContextPath()%>/logout"><i class="fa fa-sign-out"></i> Đăng xuất</a>
                                            </c:if>
                                            <c:if test="${ACCOUNTLOGGED.role.roleID == 3}">
                                                Xin chào ${sessionScope.ACCOUNTLOGGED.getUsername()}
                                                <a href="<%=request.getContextPath()%>/account"><i class="fa fa-external-link" aria-hidden="true"></i>
                                                    Tới trang cá nhân</a>
                                                <a href="<%=request.getContextPath()%>/logout"><i class="fa fa-sign-out"></i> Đăng xuất</a>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="<%=request.getContextPath()%>/login">Đăng nhập</a> / <a href="<%=request.getContextPath()%>/register">Đăng ký</a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="social-style-1 social-style-1-mrg">
                                <a href="https://www.facebook.com/anhdv47/"><i class="icon-social-twitter"></i></a>
                                <a href="https://www.facebook.com/anhdv47/"><i class="icon-social-facebook"></i></a>
                                <a href="https://www.facebook.com/anhdv47/"><i class="icon-social-instagram"></i></a>
                                <a href="https://www.facebook.com/anhdv47/"><i class="icon-social-youtube"></i></a>
                                <a href="https://www.facebook.com/anhdv47/"><i class="icon-social-pinterest"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="header-bottom">
                <div class="row align-items-center">
                    <div class="col-xl-2 col-lg-2">
                        <div class="logo">
                            <a href="home"><img src="<%=request.getContextPath()%>/assets/images/logo/logo.png" alt="logo"></a>
                        </div>
                    </div>
                    <div class="col-xl-8 col-lg-7">
                        <div class="main-menu main-menu-padding-1 main-menu-lh-1">
                            <nav>
                                <ul>
                                    <li><a href="<%=request.getContextPath()%>/home">Trang chủ </a>
                                    </li>
                                    <li><a href="<%=request.getContextPath()%>/products">Sản phẩm </a>
                                        <ul class="mega-menu-style mega-menu-mrg-1">
                                            <li>
                                                <ul>
                                                    <li>
                                                        <a class="dropdown-title">Danh mục</a>
                                                        <ul>
                                                            <c:forEach items="${sessionScope.categories}" var="c">
                                                                <li>
                                                                    <a href="<%=request.getContextPath()%>/products?categoryId=${c.getId()}">${c.getCategoryName()}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-title" href="#">Laptop Thương hiệu</a>
                                                        <ul class="row">
                                                            <c:forEach items="${sessionScope.brands}" var="b">
                                                                <li class="col-md-6"><a
                                                                        href="<%=request.getContextPath()%>/products?brandId=${b.getId()}">${b.getBrandName()}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <a href="<%=request.getContextPath()%>/home"><img src="<%=request.getContextPath()%>/assets/images/logo/logo.png"
                                                                            style="width: 200px" alt=""></a>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        <a>Danh mục</a>
                                        <ul class="sub-menu-style">
                                            <li><a href="<%=request.getContextPath()%>/Cart">Giỏ hàng</a></li>
                                            <li><a href="<%=request.getContextPath()%>/Checkout">Thanh toán</a></li>
                                            <li><a href="<%=request.getContextPath()%>/account">Tài khoản</a></li>
                                            <li><a href="<%=request.getContextPath()%>/wishlist">Danh sách yêu thích</a></li>
                                        </ul>
                                    </li>
                                    <li><a  id="go-to-footer">Liên hệ </a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    <div class="col-xl-2 col-lg-3">
                        <div class="header-action header-action-flex header-action-mrg-right">
                            <div class="same-style-2 header-search-1">
                                <a class="search-toggle" href="#">
                                    <i class="icon-magnifier s-open"></i>
                                    <i class="icon_close s-close"></i>
                                </a>
                                <div class="search-wrap-1">
                                    <form action="<%=request.getContextPath()%>/products">
                                        <input placeholder="Nhập sản phẩm bạn muốn tìm kiếm?" type="text" name="search">
                                        <button class="button-search"><i class="icon-magnifier"></i></button>
                                    </form>
                                </div>
                            </div>
                            <c:if test="${sessionScope.ACCOUNTLOGGED !=null && sessionScope.ACCOUNTLOGGED.role.roleID == 3}">
                                <div class="same-style-2">
                                    <a href="<%=request.getContextPath()%>/account"><i class="icon-user"></i></a>
                                </div>
                                <div class="same-style-2">
                                    <a href="<%=request.getContextPath()%>/wishlist"><i class="icon-heart"></i><span
                                            class="pro-count red" id="wishlistCount">${sessionScope.WISHLIST!=null?sessionScope.WISHLIST.getFavourites().size():0}</span></a>
                                </div>
                            </c:if>
                            <div class="same-style-2 header-cart">
                                <a class="cart-active" href="" id="cartButton">
                                    <i class="icon-basket-loaded"></i><span class="pro-count red"
                                                                            id="productCount">${sessionScope.CART!=null?sessionScope.CART.getItems().size():0}</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<!-- mini cart start -->
<div class="sidebar-cart-active">
    <div class="sidebar-cart-all">
        <a class="cart-close"><i class="icon_close"></i></a>
        <div class="cart-content" id="cartContent">
            <h3>Giỏ hàng</h3>
            <ul id="cart-list">

            </ul>
            <div class="cart-total">
                <h4>Tổng tiền: <span id="totalOrder"><fmt:formatNumber
                            value="${sessionScope.CART.getOrderTotal()}"
                            type="number"/> &dstrok;</span></h4>
            </div>
            <div class="cart-checkout-btn">
                <a class="btn-hover cart-btn-style" href="<%=request.getContextPath()%>/Cart">Xem chi tiết trong giỏ hàng</a>
                <a class="no-mrg btn-hover cart-btn-style" href="<%=request.getContextPath()%>/Checkout">Đặt hàng</a>
            </div>
        </div>
    </div>
</div>