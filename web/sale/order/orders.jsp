<%-- 
    Document   : orders
    Created on : Jul 13, 2022, 3:36:54 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách đơn hàng</title>
    <%@include file="../../parts/head.jsp" %>
    <%@include file="../parts/head.jspf" %>
</head>
<body onload="time()" class="app sidebar-mini rtl">
<%@include file="../parts/navbar.jspf" %>
<%@include file="../parts/sidebar.jspf" %>
<main class="app-content">
    <div class="row">
        <div class="col-md-12">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb">
                    <li class="breadcrumb-item"><a href="#"><b>Bảng điều khiển</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
        </div>
    </div>
    <p style="color: red;">${message}</p>
    <c:if test="${sessionScope.ACCOUNTLOGGED !=null && sessionScope.ACCOUNTLOGGED.role.roleID == 2}">
        <div class="row">
            <div class="col-lg-3">
                <div class="col-md-12">
                    <div class="tile">
                        <h3 class="tile-title">Trạng thái đơn hàng</h3>
                        <div>
                            <div class="sidebar-widget">
                                <div class="sidebar-widget mb-40">
                                    <div class="sidebar-widget mb-35">
                                        <div class="sidebar-widget-list">
                                            <ul>
                                                <li>
                                                    <div class="sidebar-widget-list-left">
                                                        <label><input type="checkbox" name="status" value="0"
                                                            ${orderFilter.getSelectStatus()[0] ? 'checked' : ''}>Đang xử
                                                            lý</label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="sidebar-widget-list-left">
                                                        <label><input type="checkbox" name="status" value="1"
                                                            ${orderFilter.getSelectStatus()[1] ? 'checked' : ''}>Chấp
                                                            nhận đơn hàng</label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="sidebar-widget-list-left">
                                                        <label><input type="checkbox" name="status" value="2"
                                                            ${orderFilter.getSelectStatus()[2] ? 'checked' : ''}>Từ chối
                                                            đơn
                                                            hàng</label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="sidebar-widget-list-left">
                                                        <label><input type="checkbox" name="status" value="3"
                                                            ${orderFilter.getSelectStatus()[3] ? 'checked' : ''}>Đang
                                                            giao
                                                            hàng</label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="sidebar-widget-list-left">
                                                        <label><input type="checkbox" name="status" value="4"
                                                            ${orderFilter.getSelectStatus()[4] ? 'checked' : ''}>Hoàn
                                                            thành</label>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-9">
                <div class="col-md-12">
                    <div class="tile">
                        <h3 class="tile-title">Tìm kiếm đơn hàng</h3>
                        <div>
                            <div class="sidebar-widget">
                                <div class="sidebar-widget mb-40">
                                    <div class="sidebar-search">
                                        <form class="sidebar-search-form" id="search-order">
                                            <input type="text" placeholder="Nhập mã hoặc tên khách hàng" name="search"
                                                   value="${orderFilter.getSearch()}">
                                            <button>
                                                <i class="icon-magnifier"></i>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="shop-topbar-wrapper">
                                <div class="shop-topbar-left">
                                    <div class="view-mode nav tooltip-style-2">
                                        <a class="active" href="<%=request.getContextPath()%>/sale/orders"
                                           title="Thiết lập lại mặc định"><i class="fa fa-refresh"></i></a>
                                    </div>
                                    <c:if test="${orderFilter.getTotalOrder() > 0}">
                                        <p>Hiển
                                            thị ${(orderFilter.getCurrentPage()-1)*orderFilter.getOrderPerPage() +1 }
                                            - ${orderFilter.getCurrentPage()*orderFilter.getOrderPerPage() > orderFilter.getTotalOrder()?
                                                orderFilter.getTotalOrder() : orderFilter.getCurrentPage()*orderFilter.getOrderPerPage()}
                                            của ${orderFilter.getTotalOrder()} kết quả </p>
                                    </c:if>
                                    <c:if test="${orderFilter.getTotalOrder() <= 0}">
                                        <div class="alert bg-danger text-center m-0">
                                            <strong>Không tìm thấy đơn hàng</strong>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="product-sorting-wrapper">
                                    <div class="product-show shorting-style">
                                        <label>Sắp xếp theo :</label>
                                        <select name="sortBy" id="sortBy">
                                            <option value="HistoryTime" ${(orderFilter.getSortBy() eq "HistoryTime") ? "selected" : "" }>
                                                Lịch sử hoạt động
                                            </option>
                                            <option value="OrderDate" ${(orderFilter.getSortBy() eq "OrderDate") ? "selected" : "" }>
                                                Ngày đặt đơn hàng
                                            </option>
                                            <option value="Order_ID" ${(orderFilter.getSortBy() eq "Order_ID") ? "selected" : "" }>
                                                Mã đơn hàng
                                            </option>
                                            <option value="Fullname" ${(orderFilter.getSortBy() eq "Fullname") ? "selected" : "" }>
                                                Tên khách hàng
                                            </option>
                                            <option value="TotalTransaction" ${(orderFilter.getSortBy() eq "TotalTransaction") ? "selected" : "" }>
                                                Tổng giá đơn hàng
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <button id="sortSide" value="${orderFilter.isIsAsc()}" class="btn btn-primary btn-sm">
                                    <c:if test="${orderFilter.isIsAsc()}"><i class="fa fa-sort-asc"
                                                                             title="Tăng dần"></i></c:if>
                                    <c:if test="${!orderFilter.isIsAsc()}"><i class="fa fa-sort-desc"
                                                                              title="Giảm dần"></i></c:if>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${orderFilter.getTotalOrder() > 0}">
            <div class="col-md-12">
                <div class="tile">
                    <h3 class="tile-title">Danh sách đơn hàng</h3>
                    <div>
                        <c:if test="${orders.size()>0}">
                            <div id="orders">
                                <div class="myaccount-content p-0">
                                    <div class="myaccount-table table-responsive text-center">
                                        <table class="table table-bordered">
                                            <thead class="thead-light">
                                            <tr>
                                                <th>Mã đơn hàng</th>
                                                <th>Khách hàng</th>
                                                <th>Ngày mua hàng</th>
                                                <th>Tình trạng</th>
                                                <th>Tổng tiền</th>
                                                <th>Chi tiết</th>
                                                <th>Hành động</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${orders}" var="o">
                                                <tr>
                                                    <td> ${o.getId()}</td>
                                                    <td> ${o.getFullName()}</td>
                                                    <td> ${o.getCreatedTime()}</td>
                                                    <td> ${o.getStatus()}</td>
                                                    <td><fmt:formatNumber
                                                            value="${o.getTotalTransaction()}"
                                                            type="number"/> &dstrok;
                                                    </td>
                                                    <td>
                                                        <button onclick="getOrderDetail(${o.getId()})"
                                                                class="check-btn sqr-btn btn-primary"
                                                                id="detail-order-${o.getId()}">Hiện
                                                        </button>
                                                    </td>
                                                    <td>
                                                        <c:if test="${o.getStatusId() == 1}">
                                                            <button class="cancel-btn sqr-btn btn-danger"
                                                                    onclick="cancelOrder(${o.getId()}, ${o.getSaleId()})">
                                                                Từ
                                                                chối
                                                            </button>
                                                            <button class="cancel-btn sqr-btn btn-success"
                                                                    onclick="acceptOrder(${o.getId()}, ${o.getSaleId()})">
                                                                Chấp thuận
                                                            </button>
                                                        </c:if>
                                                        <c:if test="${o.getStatusId() == 3}">
                                                            <p class="cancel-btn sqr-btn">Đã từ chối</p>
                                                        </c:if>
                                                        <c:if test="${o.getStatusId() == 2}">
                                                            <button class="cancel-btn sqr-btn btn-success"
                                                                    onclick="deliveryOrder(${o.getId()}, ${o.getSaleId()})">
                                                                Chưa giao hàng
                                                            </button>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr id="detail-modal-${o.getId()}" style="display: none">
                                                    <td colspan="7">
                                                        <div class="myaccount-content p-0">
                                                            <h3>Chi tiết đơn hàng số ${o.getId()} của khách
                                                                hàng ${o.getFullName()} </h3>
                                                            <div class="row">
                                                                <div class="col-lg-12">
                                                                    <div class="card">
                                                                        <div class="card-header">
                                                                            <strong>Tên hóa đơn người nhận</strong>
                                                                        </div>
                                                                        <div class="card-body card-block">
                                                                            <div class="form-group col-md-6"
                                                                                 style="float: left">
                                                                                <label class=" form-control-label">Số
                                                                                    điện
                                                                                    thoại</label>
                                                                                <input type="text" class="form-control"
                                                                                       readonly=""
                                                                                       value="${o.getPhone()}">
                                                                            </div>
                                                                            <div class="form-group col-md-6"
                                                                                 style="float: right">
                                                                                <label class=" form-control-label">Email</label>
                                                                                <input type="text" class="form-control"
                                                                                       readonly=""
                                                                                       value="${o.getEmail()}">
                                                                            </div>
                                                                            <div class="form-group col-md-6"
                                                                                 style="float: left">
                                                                                <label class=" form-control-label">Địa
                                                                                    chỉ
                                                                                    giao
                                                                                    hàng</label>
                                                                                <textarea class="form-control"
                                                                                          readonly="">${o.getAddress()}</textarea>
                                                                            </div>
                                                                            <div class="form-group col-md-6"
                                                                                 style="float: right">
                                                                                <label class=" form-control-label">Ghi
                                                                                    chú</label>
                                                                                <textarea class="form-control"
                                                                                          readonly="">${o.getNotes()}</textarea>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-12">
                                                                    <div class="card">
                                                                        <div class="card-header"><strong>Sản
                                                                            phẩm</strong>
                                                                        </div>
                                                                        <div class="card-body card-block">
                                                                            <div class="myaccount-table table-responsive text-center"
                                                                                 id="order-product-${o.getId()}">
                                                                                <table class="table table-bordered">
                                                                                    <thead class="thead-light">
                                                                                    <tr>
                                                                                        <th>Sản phẩm</th>
                                                                                        <th>Số lượng</th>
                                                                                        <th>Giá tiền/1sp</th>
                                                                                        <th>Tổng</th>
                                                                                    </tr>
                                                                                    </thead>
                                                                                    <tbody id="detail-body-${o.getId()}"></tbody>
                                                                                </table>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="pro-pagination-style text-center mt-10" id="pagination"
                                     style="padding-bottom: 50px">
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:if>
    </c:if>
    <%@include file="../../parts/javascripts.jsp" %>
    <script src="assets/js/plugins/pagination.min.js"></script>
    <script src="assets/js/vendor/vendor.min.js"></script>
    <script src="assets/js/plugins/plugins.min.js"></script>
    <script>
        var detailOn = -1;
        const getOrderDetail = (id) => {
            $.ajax({
                url: "<%=request.getContextPath()%>/api/order/getOrderDetail",
                type: "GET",
                data: {
                    orderId: id
                },
                success: function (data) {
                    if (data !== null && data.toString() !== '[]') {
                        data = JSON.parse(data);
                        $('#detail-body-' + id + '').empty();
                        data.forEach((item) => {
                            let text = "<tr>" + "<td><a style='float: left' href='productDetail?productId=" + item.productId + "'>" + item.productName + "</a></td>"
                                + "<td>" + item.quantity + "</td>"
                                + "<td>" + formatNumber(item.price) + " &dstrok;</td>"
                                + "<td>" + formatNumber(item.price * item.quantity) + " &dstrok;</td>";
                            $('#detail-body-' + id + '').append(text);
                        });
                    } else {
                        $('#order-product-' + id + '').html("Không có chi tiết các sản phẩm của đơn hàng");
                        $('#order-product-' + id + '').attr("class", "alert alert-danger");
                    }
                    $('#detail-modal-' + id + '').attr("style", "display: ");
                    $('#detail-order-' + id + '').attr("onclick", "hiddenOrderDetail(" + id + ")");
                    $('#detail-order-' + id + '').html('Ẩn');
                    if (detailOn !== -1) {
                        hiddenOrderDetail(detailOn);
                    }
                    detailOn = id;
                },
                error: function () {
                    swal({
                        title: "Không thành công",
                        text: "unknown",
                        icon: "error",
                        timer: 2000,
                        timerProgressBar: true,
                        button: "OK",
                    });
                }
            });
        };
        const hiddenOrderDetail = (id) => {
            $('#detail-modal-' + id + '').attr("style", "display: none");
            $('#detail-order-' + id + '').attr("onclick", "getOrderDetail(" + id + ")");
            $('#detail-order-' + id + '').html('Hiện');
            detailOn = -1;
        };
        const cancelOrder = (orderId, saleId) => {
            swal({
                title: "Bạn có chắc chắn muốn từ chối đơn hàng này?",
                icon: "warning",
                buttons: true,
                dangerMode: true
            }).then((willCancel) => {
                if (willCancel) {
                    $.ajax({
                        url: "<%= request.getContextPath()%>/sale/orders",
                        type: "POST",
                        data: {
                            cancelOrderId: orderId,
                            orderSaleId: saleId
                        },
                        success: function () {
                            swal({
                                title: "Từ chối thành công",
                                icon: "success",
                                timer: 2000,
                                timerProgressBar: true,
                                button: "OK"
                            }).then(() => {
                                // reload window to refresh wishlist
                                window.location.reload();
                            });
                        },
                        error: function () {
                            swal({
                                title: "Từ chối thất bại",
                                icon: "error",
                                timer: 2000,
                                timerProgressBar: true,
                                button: "OK"
                            });
                        }
                    });
                }
            });
        };
        const acceptOrder = (orderId, saleId) => {
            swal({
                title: "Bạn có chắc chắn muốn chấp nhận đơn hàng này?",
                icon: "warning",
                buttons: true,
                dangerMode: true
            }).then((willAccept) => {
                if (willAccept) {
                    $.ajax({
                        url: "<%= request.getContextPath()%>/sale/orders",
                        type: "POST",
                        data: {
                            acceptOrderId: orderId,
                            orderSaleId: saleId
                        },
                        success: function () {
                            swal({
                                title: "Chấp nhận thành công",
                                icon: "success",
                                timer: 2000,
                                timerProgressBar: true,
                                button: "OK"
                            }).then(() => {
                                // reload window to refresh wishlist
                                window.location.reload();
                            });
                        },
                        error: function () {
                            swal({
                                title: "Chấp nhận thất bại",
                                icon: "error",
                                timer: 2000,
                                timerProgressBar: true,
                                button: "OK"
                            });
                        }
                    });
                }
            });
        };
        const deliveryOrder = (orderId, saleId) => {
            swal({
                title: "Bạn có chắc chắn đã vận chuyển đơn hàng này?",
                icon: "warning",
                buttons: true,
                dangerMode: true
            }).then((willDelivering) => {
                if (willDelivering) {
                    $.ajax({
                        url: "<%= request.getContextPath()%>/sale/orders",
                        type: "POST",
                        data: {
                            deliveryOrderId: orderId,
                            orderSaleId: saleId
                        },
                        success: function () {
                            swal({
                                title: "Xác nhận thành công",
                                icon: "success",
                                timer: 2000,
                                timerProgressBar: true,
                                button: "OK"
                            }).then(() => {
                                // reload window to refresh wishlist
                                window.location.reload();
                            });
                        },
                        error: function () {
                            swal({
                                title: "Xác nhận thất bại",
                                icon: "error",
                                timer: 2000,
                                timerProgressBar: true,
                                button: "OK"
                            });
                        }
                    });
                }
            });
        };
        let currentPage = ${orderFilter.getCurrentPage()};
        let totalPage = ${orderFilter.getTotalPage()};
        let sortSide = ${orderFilter.isIsAsc()};
        let values = {};
        const loadPage = (page) => {
            // if search is not empty then add it to values
            const searchText = $("#search-order input").val();
            if (searchText.length > 0) {
                values["search"] = searchText;
            }
            // map all the checkboxes
            const checkboxes = $("input:checkbox");
            // loop over each checkbox
            checkboxes.each(function () {
                // if the checkbox is checked
                if ($(this).is(":checked")) {
                    // get name of the checkbox
                    const name = $(this).attr("name");
                    // get value of the checkbox
                    const value = $(this).val();
                    // check if name is already in values object and add it if it isn't
                    if (name in values) {
                        values[name].push(value);
                    } else {
                        values[name] = [value];
                    }
                }
            });
            values["sortBy"] = $("#sortBy option:selected").val();
            values["page"] = page;
            values["sortSide"] = $("#sortSide").val();
            let url = window.location.pathname + "?";
            for (const key in values) {
                url += key + "=" + values[key] + "&";
            }
            // // remove the last ampersand
            url = url.slice(0, -1);
            // redirect to the new url
            window.location.href = url;

        };
        // if select order status is changed then load the page
        $("input:checkbox").click(function (e) {
            loadPage(1);
        });
        // if sortBy is changed then load the page with the new sortBy
        $('#sortBy').change(function () {
            loadPage(1);
        });
        // if sort side is changed then load the page with the new sortSide
        $('#sortSide').click(function () {
            $('#sortSide').attr("value", ${!orderFilter.isIsAsc()});
            loadPage(1);
        });
        // set bar for page with current page and total page
        let pageBar = "<ul>";
        // previous button enabled if current page > 1
        pageBar += "<li><a" + ((currentPage > 1) ? " onclick='loadPage(" + (currentPage - 1) + ")'" : "") + ">«</a><li>";
        for (let i = 1; i <= totalPage; i++) {
            // if total page > 10 and current page == final page, add '...' before final page button
            if (totalPage > 10 && i === totalPage - 1 && totalPage === currentPage) {
                pageBar += "<li><a>...</a><li>";
            } // if total page <= 10 or current page < final page, add pages button
            else pageBar += "<li><a" + ((i !== currentPage) ? " onclick='loadPage(" + i + ")'" : " class='active'") + ">" + i + "</a><li>";
            // if total page > 10 and still has (non final and non first 3 page) button not added
            if (i < totalPage && totalPage > 10 && i > 2) {
                // if current page is 1 of first 3 page or is added
                if (currentPage < 4 || i >= currentPage) {
                    // if button previous final is not added
                    if (i < totalPage - 1) pageBar += "<li><a>...</a><li>";
                    i = totalPage - 1;
                }
                // if current page is not added
                else if (i < currentPage) {
                    // if current page is not behind first 3 page, add '...' before current page button
                    if (currentPage !== 4) pageBar += "<li><a>...</a><li>";
                    i = currentPage - 1;
                }
            }
        }
        // next button enabled if current page < final page
        pageBar += "<li><a" + ((currentPage < totalPage) ? " onclick='loadPage(" + (currentPage + 1) + ")'" : "") + ">»</a><li>";
        pageBar += "</ul>";
        $('#pagination').html(pageBar);
    </script>
</main>
<%@include file="../parts/commonjs.jspf" %>
</body>
</html>
