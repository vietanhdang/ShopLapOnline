<%-- 
    Document   : web-account-info
    Created on : Mar 2, 2022, 8:53:40 PM
    Author     : Benjamin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Account</title>
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
                            <li class="active">Tài khoản</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="my-account-wrapper pt-120 pb-120">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <!-- My Account Page Start -->
                            <div class="myaccount-page-wrapper">
                                <div class="row">
                                    <div class="col-lg-3 col-md-4">
                                        <div class="myaccount-tab-menu nav" role="tablist">
                                            <c:if test="${user.getRole().getRoleID() == 3}">
                                                <a href="account?action=order" class="${action eq 'order'? "active" :""}"><i class="fa fa-cart-arrow-down"></i> Danh sách đơn hàng</a>
                                                <a href="account?action=payment"class="${action eq 'payment'? "active" :""}" ><i class="fa fa-credit-card"></i> Phương thức thanh toán</a>
                                            </c:if>
                                            <a href="account?action=show_details" class="${action eq 'show_details'? "active" :""}"><i class="fa fa-user"></i> Thông tin tài khoản</a>
                                            <a href="logout"><i class="fa fa-sign-out"></i> Đăng xuất</a>
                                        </div>
                                    </div>
                                    <div class="col-lg-9 col-md-8">
                                        <div class="tab-content">
                                            <p style="color: red;">${message}</p>
                                            <c:if test="${(empty action or action eq 'order') and user.getRole().getRoleID() == 3}">
                                                <c:if test="${orders.size()>0}">
                                                    <div id="orders">
                                                        <div class="myaccount-content">
                                                            <h3>Đơn hàng</h3>
                                                            <div class="myaccount-table table-responsive text-center">
                                                                <table class="table table-bordered">
                                                                    <thead class="thead-light">
                                                                        <tr>
                                                                            <th>Mã đơn hàng</th>
                                                                            <th>Ngày mua hàng</th>
                                                                            <th>Trạng thái</th>
                                                                            <th>Tổng tiền</th>
                                                                            <th>Chi tiết</th>
                                                                            <th>Hành động</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach items="${orders}" var="o">
                                                                            <tr>
                                                                                <td> ${o.getId()}</td>
                                                                                <td> ${o.getCreatedTime()}</td>
                                                                                <td> ${o.getStatus()}</td>
                                                                                <td> <fmt:formatNumber
                                                                                        value="${o.getTotalTransaction()}"
                                                                                        type="number" /> &dstrok;</td>
                                                                                <td>
                                                                                    <a href="account?action=orderDetails&orderId=${o.getId()}" class="check-btn sqr-btn">Xem</a>
                                                                                </td>
                                                                                <td>
                                                                                    <c:if test="${o.getStatusId() == 1}">
                                                                                        <a onclick="cancelOrder(${o.getId()})"><button class="cancel-btn sqr-btn cancel-order" data-id= "${o.getId()}" >Hủy</button></a>
                                                                                    </c:if>
                                                                                    <c:if test="${o.getStatusId() == 4}">
                                                                                        <a onclick="receiveOrder(${o.getId()})"><button class="cancel-btn sqr-btn receive-order" data-id= "${o.getId()}" >Đã nhận được hàng</button></a>
                                                                                    </c:if>
                                                                                    <c:if test="${o.getStatusId() == 5}">
                                                                                        <a href="account?action=orderDetails&orderId=${o.getId()}"><button class="cancel-btn sqr-btn">Đánh giá sản phẩm</button></a>
                                                                                    </c:if>
                                                                                    <c:if test="${o.getStatusId() == 6}">
                                                                                        <p class="cancel-btn sqr-btn">Đã hủy</p>
                                                                                    </c:if>
                                                                                </td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <c:if test="${orders.size()==0}">
                                                    <div id="orders">
                                                        <div class="myaccount-content">
                                                            <h3>Chưa có đơn hàng nào được mua <a href="products" class="text-danger">Mua ngay</a></h3>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${(action eq 'orderDetails') and user.getRole().getRoleID() == 3}">
                                                <div id="orders">
                                                    <div class="myaccount-content">
                                                        <h3><a href="account"><i class="fa fa-arrow-left" aria-hidden="true"></i></a> Chi tiết đơn hàng</h3>
                                                        <div class="row">
                                                            <div class="col-lg-12">
                                                                <div class="card">
                                                                    <div class="card-header">
                                                                        <strong>Tên hóa đơn người nhận</strong>
                                                                    </div>
                                                                    <div class="card-body card-block">
                                                                        <div class="form-group">
                                                                            <label class=" form-control-label">Họ và tên</label>
                                                                            <input type="text" class="form-control" readonly="" value="${order.getFullName()}">
                                                                        </div>
                                                                        <div class="form-group">
                                                                            <label class=" form-control-label">Số điện thoại</label>
                                                                            <input type="text" class="form-control" readonly="" value="${order.getPhone()}">
                                                                        </div>
                                                                        <div class="form-group">
                                                                            <label class=" form-control-label">Địa chỉ giao hàng</label>
                                                                            <textarea class="form-control" readonly="" >${order.getAddress()}</textarea>
                                                                        </div>
                                                                        <div class="form-group">
                                                                            <label class=" form-control-label">Email</label>
                                                                            <input type="text" class="form-control" readonly="" value="${order.getEmail()}">
                                                                        </div>
                                                                        <div class="form-group">
                                                                            <label class=" form-control-label">Ghi chú</label>
                                                                            <textarea class="form-control" readonly="" >${order.getNotes()}</textarea>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-12">
                                                                <div class="card">
                                                                    <div class="card-header"><strong>Sản phẩm</strong></div>
                                                                    <div class="card-body card-block">
                                                                        <div class="myaccount-table table-responsive text-center">
                                                                            <table class="table table-bordered">
                                                                                <thead class="thead-light">
                                                                                    <tr>
                                                                                        <th>Sản phẩm</th>
                                                                                        <th>Số lượng</th>
                                                                                        <th>Giá tiền/1sp</th>
                                                                                        <th>Tổng</th>
                                                                                            <c:if test="${order.getStatusId() == 5}">
                                                                                            <th>Đánh giá sản phẩm</th>
                                                                                            </c:if>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                    <c:forEach items="${orderDetails}" var="o">
                                                                                        <tr>
                                                                                            <td><a style="float: left" href="productDetail?productId=${o.getProductId()}">${o.getProductName()}</a></td>
                                                                                            <td>${o.getQuantity()}</td>
                                                                                            <td><fmt:formatNumber
                                                                                                    value="${o.getPrice()}"
                                                                                                    type="number" /> &dstrok;</td>
                                                                                            <td><fmt:formatNumber
                                                                                                    value="${o.getPrice()*o.getQuantity()}"
                                                                                                    type="number" /> &dstrok;</td>
                                                                                                <c:if test="${order.getStatusId() == 5}">
                                                                                                <td>
                                                                                                    <c:if test="${o.isComment()}">
                                                                                                        <button class="view_review" data-id = "${o.getId()}">Xem đánh giá</button>
                                                                                                    </c:if>
                                                                                                    <c:if test="${!o.isComment()}">
                                                                                                        <button class="review_product" data-id = "${o.getId()}">Chưa đánh giá</button>
                                                                                                    </c:if>
                                                                                                </td>
                                                                                            </c:if>
                                                                                        </tr>
                                                                                    </c:forEach>
                                                                                </tbody>
                                                                            </table>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <!-- Single Tab Content Start -->
                                            <c:if test="${(action eq 'payment') and user.getRole().getRoleID() == 3}">
                                                <div>
                                                    <div class="myaccount-content">
                                                        <h3>Phương thức thanh toán</h3>
                                                        <p class="saved-message">Tính năng đang được cập nhật.</p>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${(empty action and user.getRole().getRoleID() != 3) or action eq 'show_details'}">
                                                <div id="account-info" role="tabpanel">
                                                    <div class="myaccount-content">
                                                        <h3>Chi tiết tài khoản  <span class="text-success">${alert}</span></h3>
                                                        <div class="account-details-form">
                                                            <div class="single-input-item">
                                                                <label for="email" class="required">Email</label>
                                                                <input type="email" name="email" value="${user.getUsername()}" readonly=""/>
                                                            </div>
                                                            <form action="account?action=show_details&change_avatar=change" id="change_avatar" method="post" enctype="multipart/form-data">
                                                                <fieldset>
                                                                    <div class="single-input-item">
                                                                        </br><legend>Avatar</legend>
                                                                        <input type="file" id="Image-edit" value="${accountProfile.getImage()}" name="avatarImage" accept="image/png,image/jpg">
                                                                        <div class="form-group">
                                                                            <img class="img-thumbnail" style="width: 100px" src="<%=request.getContextPath()%>/assets/images/account/${accountProfile.getImage()}" id="img-avatar"/>
                                                                        </div>
                                                                    </div>
                                                                    <div class="single-input-item">
                                                                        <input type="submit" style="width: 100px; font-size: 15px" id="avatar-button" value="Thay đổi" disabled></input>
                                                                    </div>
                                                                </fieldset>
                                                            </form>
                                                            <form action="account?action=show_details&change_profileInfo=change" id="change_profileInfo" method="post">
                                                                <fieldset>
                                                                    <div class="single-input-item">
                                                                        </br><legend>Thông tin cá nhân</legend>
                                                                        <label for="fullname" class="required">Họ tên</label>
                                                                        <input type="text" ${user.getRole().getRoleID() == 3 ? "" :"disabled"} id="fullname" name="fullname" value="${accountProfile.getFullname()}" />
                                                                        <label for="address" class="required">Địa chỉ</label>
                                                                        <input type="text" id="address" name="address" value="${accountProfile.getAddress()}" ${user.getRole().getRoleID() == 3 ? "" :"disabled"}/>
                                                                        <label for="phonenumber" class="required">Số điện thoại</label>
                                                                        <input type="number" id="phonenumber" name="phonenumber" value="${accountProfile.getPhone()}" ${user.getRole().getRoleID() == 3 ? "" :"disabled"}/>
                                                                        <label for="gender" class="required" ${user.getRole().getRoleID() == 3 ? "" :"disabled"}>Giới tính</label>
                                                                        <select name="gender" id="gender" id="gender" ${user.getRole().getRoleID() == 3 ? "" :"disabled"}>
                                                                            <option value="true">Male</option>
                                                                            <option value="false"<c:if test="${!accountProfile.isGender()}">selected</c:if>>Female</option>
                                                                            </select>
                                                                            <label for="dateOfBirth" class="required">Ngày sinh</label>
                                                                            <input type="date" id="dateOfBirth" name="dateOfBirth" ${user.getRole().getRoleID() == 3 ? "" :"disabled"} value="${accountProfile.getDateOfBirth()}"/>
                                                                    </div>
                                                                    <div class="single-input-item">
                                                                        <button class="check-btn sqr-btn ">Cập nhật</button>
                                                                    </div>
                                                                </fieldset>
                                                            </form>
                                                            <form action="account?action=show_details&change_password=change" id="change_password" method="post">
                                                                <fieldset>
                                                                    <legend>Mật khẩu</legend>
                                                                    <div class="single-input-item">
                                                                        <label for="current-pwd" class="required">Mật khẩu hiện tại</label>
                                                                        <input type="password" id="current-pwd" name="current_pwd" />
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="col-lg-6">
                                                                            <div class="single-input-item">
                                                                                <label for="new-pwd" class="required">Mật khẩu mới</label>
                                                                                <input type="password" id="new-pwd" name="new_pwd" id="new_pwd"/>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-lg-6">
                                                                            <div class="single-input-item">
                                                                                <label for="confirm-pwd" class="required">Nhập lại mật khẩu mới</label>
                                                                                <input type="password" id="confirm-pwd" name="confirm_pwd" />
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </fieldset>
                                                                <div class="single-input-item">
                                                                    <button class="check-btn sqr-btn ">Lưu thay đổi</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div> 
                                            </c:if>
                                        </div>
                                    </div> 
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="cofirm_modal" tabindex="-1" role="dialog"
                 aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered w-25" role="document">
                    <div class="modal-content text-center ">
                        <div class="modal-body">
                            <div class="modal-notice-add-product-to-cart-success">
                                <div id="modal-head-icon">
                                </div>
                                <div id="modal-body-message" class="mt-4">
                                </div>
                                <div id="modal-show-button" class="mt-4 d-flex justify-content-around">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="review-modal" tabindex="-1" role="dialog"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered w-25" role="document">
                <div class="modal-content text-center ">
                    <div class="modal-body">
                        <div class="dec-review-bottom">
                            <div class="ratting-form-wrapper">
                                <span>Đánh giá sản phẩm</span>
                                <div class="ratting-form">
                                    <form method="post" id="update_review">
                                        <div class="row">
                                            <div class="col-12"><img id="previewImage" style="width: 100px; height: 100px"></div>
                                            <div class="col-12"><p id="productName"></p></div>
                                            <div class="col-lg-12 mt-3">
                                                <label>Chất lượng sản phẩm</label>
                                                <div class="star-box-wrap justify-content-center">
                                                    <input type="radio" name="rating" value="5" id="5"><label for="5">☆</label>
                                                    <input type="radio" name="rating" value="4" id="4"><label for="4">☆</label>
                                                    <input type="radio" name="rating" value="3" id="3"><label for="3">☆</label>
                                                    <input type="radio" name="rating" value="2" id="2"><label for="2">☆</label>
                                                    <input type="radio" name="rating" value="1" id="1"><label for="1">☆</label>
                                                </div>
                                                <div class="col-md-12">
                                                    <div class="rating-form-style mb-20">
                                                        <label for="yourReview">Bình luận</label>
                                                        <textarea name="yourReview" id="comment"></textarea>
                                                    </div>
                                                </div>
                                                <div class="col-md-12">
                                                    <div class="rating-form-style mb-20">
                                                        <label>Ngày đánh giá: </label>
                                                        <span id="createdTime"></span>
                                                    </div>
                                                </div>
                                                <div class="col-md-12">
                                                    <div class="rating-form-style mb-20">
                                                        <label>Ngày sửa: </label>
                                                        <span id="updatedTime"></span>
                                                    </div>
                                                </div>
                                                <div class="col-lg-12">
                                                    <div class="form-submit">
                                                        <input type="submit" value="Sửa" id="edit_review">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="review-insert" tabindex="-1" role="dialog"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered w-25" role="document">
                <div class="modal-content text-center ">
                    <div class="modal-body">
                        <div class="dec-review-bottom">
                            <div class="ratting-form-wrapper">
                                <span>Thêm đánh giá sản phẩm</span>
                                <p>Bình luận của bạn sẽ được hiển thị với tên của bạn</p>
                                <div class="ratting-form">
                                    <form method="post" id="update_review_form">
                                        <div class="row">
                                            <div class="col-lg-12 mt-3">
                                                <label>Chất lượng sản phẩm</label>
                                                <div class="star-box-wrap justify-content-center">
                                                    <input type="radio" name="rating1" value="5" id="51" checked><label for="51">☆</label>
                                                    <input type="radio" name="rating1" value="4" id="41"><label for="41">☆</label>
                                                    <input type="radio" name="rating1" value="3" id="31"><label for="31">☆</label>
                                                    <input type="radio" name="rating1" value="2" id="21"><label for="21">☆</label>
                                                    <input type="radio" name="rating1" value="1" id="11"><label for="11">☆</label>
                                                </div>
                                                <div class="col-md-12">
                                                    <div class="rating-form-style mb-20">
                                                        <label for="yourReview">Bình luận</label>
                                                        <textarea name="yourReview" id="comment1"></textarea>
                                                    </div>
                                                </div>
                                                <div class="col-lg-12">
                                                    <div class="form-submit">
                                                        <input type="submit" value="Bình luận" id="insert_review">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="parts/footer.jsp" %>
        <%@include file="parts/javascripts.jsp" %>
        <script src="assets/js/plugins/plugins.min.js"></script>
        <script>
                                                                                            // check message url ordersuccess
                                                                                            var url = window.location.href;
                                                                                            if (url.indexOf("ordersuccess") > -1) {
                                                                                                $('#cofirm_modal').modal('show');
                                                                                                $('#modal-head-icon').html('<img style="width: 100px; height: 100px" src="assets/images/icon-img/confirmation-icon-29.jpg" alt="">');
                                                                                                $('#modal-body-message').html('<h3 class="text-success">Đơn hàng của bạn đã đặt thành công</h3>');
                                                                                                $('#modal-show-button').html('<button class="btn btn-secondary" style="font-size: 0.9rem" data-dismiss="modal">Quay lại</button>');
                                                                                                $('#cofirm_modal').on('hidden.bs.modal', function () {
                                                                                                    window.location.href = "account";
                                                                                                });
                                                                                            }
                                                                                            $(document).ready(function () {
                                                                                                $('.review_product').click(function () {
                                                                                                    $('#review-insert').modal('show');
                                                                                                    let orderDetailId = $(this).data('id');
                                                                                                    let userId = "${sessionScope.ACCOUNTLOGGED.getId()}";
                                                                                                    $('#insert_review').click(function () {
                                                                                                        var rating = $('input[name=rating1]:checked').val();
                                                                                                        var comment = $('#comment1').val();
                                                                                                        $.ajax({
                                                                                                            url: "account?action=insertReview",
                                                                                                            type: "POST",
                                                                                                            data: {
                                                                                                                orderDetailId: orderDetailId,
                                                                                                                userId: userId,
                                                                                                                rating: rating,
                                                                                                                comment: comment
                                                                                                            },
                                                                                                            success: function (data) {
                                                                                                                swal({
                                                                                                                    title: "Bình luận thành công",
                                                                                                                    icon: "success",
                                                                                                                    timer: 2000,
                                                                                                                    timerProgressBar: true,
                                                                                                                    button: "OK"
                                                                                                                });
                                                                                                                window.location.reload();
                                                                                                            }
                                                                                                        });
                                                                                                    });
                                                                                                });
                                                                                                $('.view_review').click(function () {
                                                                                                    var orderDetailId = $(this).data('id');
                                                                                                    var userId = "${sessionScope.ACCOUNTLOGGED.getId()}";
                                                                                                    // ajax to get review
                                                                                                    $.ajax({
                                                                                                        url: "<%=request.getContextPath()%>/api/order/getReview?orderDetailId=" + orderDetailId + "&userId=" + userId,
                                                                                                        type: "GET",
                                                                                                        success: function (data) {
                                                                                                            if (data !== null) {
                                                                                                                data = JSON.parse(data);
                                                                                                                // show modal review
                                                                                                                $('#review-modal').modal('show');
                                                                                                                $("#previewImage").attr("src", "assets/images/product/" + data.previewImage);
                                                                                                                $("#productName").html(data.productName);
                                                                                                                var rating = data.rating;
                                                                                                                $("#" + rating).prop("checked", true);
                                                                                                                $("#createdTime").html(data.createdTime);
                                                                                                                $("#updatedTime").html(data.updatedTime);
                                                                                                                $("#comment").html(data.comment);
                                                                                                                // get data form  update_review and edit review
                                                                                                                // post form via ajax
                                                                                                                $('#update_review').submit(function (e) {
                                                                                                                    e.preventDefault();
                                                                                                                    var comment = $("#comment").val();
                                                                                                                    var rating = $("input[name='rating']:checked").val();
                                                                                                                    $.ajax({
                                                                                                                        url: "account?action=updateReview",
                                                                                                                        type: "POST",
                                                                                                                        data: {
                                                                                                                            orderDetailId: orderDetailId,
                                                                                                                            userId: userId,
                                                                                                                            comment: comment,
                                                                                                                            rating: rating
                                                                                                                        },
                                                                                                                        success: function (data) {
                                                                                                                            swal({
                                                                                                                                title: "Cập nhật thành công",
                                                                                                                                icon: "success",
                                                                                                                                timer: 2000,
                                                                                                                                timerProgressBar: true,
                                                                                                                                button: "OK"
                                                                                                                            });
                                                                                                                        },
                                                                                                                        error: function (data) {
                                                                                                                            swal({
                                                                                                                                title: "Không thành công",
                                                                                                                                icon: "error",
                                                                                                                                timer: 2000,
                                                                                                                                timerProgressBar: true,
                                                                                                                                button: "OK"
                                                                                                                            });
                                                                                                                        }
                                                                                                                    });
                                                                                                                });
                                                                                                            }
                                                                                                        }
                                                                                                    });
                                                                                                });
                                                                                                $("#update_review_form").validate({
                                                                                                    rules: {
                                                                                                        yourReview: {
                                                                                                            maxlength: 500
                                                                                                        }
                                                                                                    },
                                                                                                    messages: {
                                                                                                        yourReview: {
                                                                                                            maxLength: "Đánh giá quá dài"
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                                $("#change_profileInfo").validate({
                                                                                                    rules: {
                                                                                                        fullname: {
                                                                                                            required: true,
                                                                                                            maxlength: 50,
                                                                                                            minlength: 5
                                                                                                        },
                                                                                                        phonenumber: {
                                                                                                            required: true,
                                                                                                            maxlength: 12,
                                                                                                            minlength: 10
                                                                                                        },
                                                                                                        address: {
                                                                                                            required: true,
                                                                                                            maxlength: 255,
                                                                                                            minlength: 5
                                                                                                        },
                                                                                                        dateOfBirth: {
                                                                                                            required: true
                                                                                                        }
                                                                                                    },
                                                                                                    messages: {
                                                                                                        fullname: {
                                                                                                            required: "Vui lòng nhập tên",
                                                                                                            maxlength: "Tên có độ dài 5 - 50 ký tự",
                                                                                                            minlength: "Tên có độ dài 5 - 50 ký tự"
                                                                                                        },
                                                                                                        phonenumber: {
                                                                                                            required: "Vui lòng nhập số điện thoại",
                                                                                                            maxlength: "Số điện thoại phải gồm 10 - 12 chữ số",
                                                                                                            minlength: "Số điện thoại phải gồm 10 - 12 chữ số"
                                                                                                        },
                                                                                                        address: {
                                                                                                            required: "Vui lòng nhập địa chỉ",
                                                                                                            maxlength: "Tên có độ dài 5 - 255 ký tự",
                                                                                                            minlength: "Tên có độ dài 5 - 255 ký tự"

                                                                                                        },
                                                                                                        dateOfBirth: {
                                                                                                            required: "Vui lòng nhập ngày sinh"
                                                                                                        }
                                                                                                    },
                                                                                                    submitHandler: function (form) {
                                                                                                        form.submit();
                                                                                                    }
                                                                                                });
                                                                                                // check valid form id change_password
                                                                                                $('#change_password').validate({
                                                                                                    rules: {
                                                                                                        current_pwd: {
                                                                                                            required: true,
                                                                                                            minlength: 6
                                                                                                        },
                                                                                                        new_pwd: {
                                                                                                            required: true,
                                                                                                            minlength: 6
                                                                                                        },
                                                                                                        confirm_pwd: {
                                                                                                            required: true,
                                                                                                            minlength: 6,
                                                                                                            equalTo: "#new_pwd"
                                                                                                        }
                                                                                                    },
                                                                                                    messages: {
                                                                                                        current_pwd: {
                                                                                                            required: "Vui lòng nhập mật khẩu hiện tại",
                                                                                                            minlength: "Mật khẩu phải có ít nhất 6 ký tự"
                                                                                                        },
                                                                                                        new_pwd: {
                                                                                                            required: "Vui lòng nhập mật khẩu mới",
                                                                                                            minlength: "Mật khẩu phải có ít nhất 6 ký tự"
                                                                                                        },
                                                                                                        confirm_pwd: {
                                                                                                            required: "Vui lòng nhập lại mật khẩu mới",
                                                                                                            minlength: "Mật khẩu phải có ít nhất 6 ký tự",
                                                                                                            equalTo: "Mật khẩu không trùng khớp"
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                                /*--- check valid input form add address ----*/
                                                                                                $("#add_address_check").validate({
                                                                                                    rules: {
                                                                                                        fullname: {
                                                                                                            required: true,
                                                                                                        },
                                                                                                        address: {
                                                                                                            required: true,
                                                                                                        },
                                                                                                        phone: {
                                                                                                            required: true,
                                                                                                            number: true,
                                                                                                        },
                                                                                                    },
                                                                                                    messages: {
                                                                                                        fullname: {
                                                                                                            required: "Vui lòng nhập tên đầy đủ",
                                                                                                        },
                                                                                                        address: {
                                                                                                            required: "Vui lòng nhập địa chỉ",
                                                                                                        },
                                                                                                        phone: {
                                                                                                            required: "Vui lòng nhập đúng số điện thoại",
                                                                                                            number: "Vui lòng nhập đúng số điện thoại",

                                                                                                        },
                                                                                                    },
                                                                                                    submitHandler: function (form) {
                                                                                                        form.submit();
                                                                                                    },
                                                                                                });
                                                                                            });
                                                                                            const cancelOrder = (id) => {
                                                                                                swal({
                                                                                                    title: "Bạn có chắc chắn muốn hủy đơn hàng này?",
                                                                                                    icon: "warning",
                                                                                                    buttons: true,
                                                                                                    dangerMode: true,
                                                                                                }).then((willDelete) => {
                                                                                                    if (willDelete) {
                                                                                                        console.log(id);
                                                                                                        $.ajax({
                                                                                                            url: 'account?action=cancel_order&orderId=' + id,
                                                                                                            type: 'GET',
                                                                                                            success: function (data) {
                                                                                                                swal("Hủy thành công!", {
                                                                                                                    icon: "success",
                                                                                                                    timer: 1000,
                                                                                                                }).then(() => {
                                                                                                                    // reload window to refresh order
                                                                                                                    window.location.reload();
                                                                                                                });
                                                                                                            },
                                                                                                            error: function (data) {
                                                                                                                swal("Hủy thất bại!", {
                                                                                                                    icon: "error",
                                                                                                                    timer: 1000,
                                                                                                                }).then(() => {
                                                                                                                    // reload window to refresh order
                                                                                                                    window.location.reload();
                                                                                                                });
                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                });
                                                                                            };
                                                                                            const receiveOrder = (id) => {
                                                                                                swal({
                                                                                                    title: "Bạn đã nhận được đơn hàng này?",
                                                                                                    icon: "warning",
                                                                                                    buttons: true,
                                                                                                    dangerMode: true,
                                                                                                }).then((willReceive) => {
                                                                                                    if (willReceive) {
                                                                                                        console.log(id);
                                                                                                        $.ajax({
                                                                                                            url: 'account?action=receive_order&orderId=' + id,
                                                                                                            type: 'GET',
                                                                                                            success: function (data) {
                                                                                                                swal("Thay đổi thành công!", {
                                                                                                                    icon: "success",
                                                                                                                    timer: 1000,
                                                                                                                }).then(() => {
                                                                                                                    // reload window to refresh order
                                                                                                                    window.location.reload();
                                                                                                                });
                                                                                                            },
                                                                                                            error: function (data) {
                                                                                                                swal("Hệ thống lưu thất bại", {
                                                                                                                    icon: "error",
                                                                                                                    timer: 1000,
                                                                                                                }).then(() => {
                                                                                                                    // reload window to refresh order
                                                                                                                    window.location.reload();
                                                                                                                });
                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                });
                                                                                            };

                                                                                            $("#Image-edit").change(function () {
                                                                                                var file = $(this)[0].files[0];
                                                                                                console.log(file.type);
                                                                                                var patterImage = new RegExp("image/*");
                                                                                                if (!patterImage.test(file.type)) {
                                                                                                    alert("Please choose image!");
                                                                                                    $("#img-avatar").attr("src", "client-1.png");
                                                                                                    $("#avatar-button").attr("disabled", true);
                                                                                                    $("#avatar-button").attr("style", "width: 100px; font-size: 15px");
                                                                                                } else {
                                                                                                    var fileReader = new FileReader();
                                                                                                    fileReader.readAsDataURL(file);
                                                                                                    fileReader.onload = function (e) {
                                                                                                        $("#img-avatar").attr("src", e.target.result);
                                                                                                        $("#avatar-button").attr("disabled", false);
                                                                                                        $("#avatar-button").attr("style", "width: 100px; background-color: red; color: white; font-size: 15px");
                                                                                                    };
                                                                                                }
                                                                                            });
        </script>
    </body>
</html>
