<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Sale - Trang chủ</title>
        <%@include file="../parts/head.jspf" %>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.css">
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
            <div class="row">
                <!--Left-->
                <div class="col-md-12 col-lg-6">
                    <div class="row">
                        <!-- col-6 -->
                        <div class="col-md-6">
                            <div class="widget-small primary coloured-icon"><i class='icon bx bxs-user-account fa-3x'></i>
                                <div class="info">
                                    <h4>Tổng khách hàng</h4>
                                    <p><b>${countCustomers} khách hàng</b></p>
                                    <p class="info-tong">Tổng số khách hàng được quản lý.</p>
                                </div>
                            </div>
                        </div>
                        <!-- col-6 -->
                        <div class="col-md-6">
                            <div class="widget-small info coloured-icon"><i class='icon bx bxs-data fa-3x'></i>
                                <div class="info">
                                    <h4>Tổng sản phẩm</h4>
                                    <p><b>${countProducts} sản phẩm</b></p>
                                    <p class="info-tong">Tổng số sản phẩm được quản lý.</p>
                                </div>
                            </div>
                        </div>
                        <!-- col-6 -->
                        <div class="col-md-6">
                            <div class="widget-small warning coloured-icon"><i class='icon bx bxs-shopping-bags fa-3x'></i>
                                <div class="info">
                                    <h4>Tổng đơn hàng</h4>
                                    <p><b>${totalOrderProccessed} đơn hàng</b></p>
                                    <p class="info-tong">Tổng số hóa đơn bán hàng trong tháng.</p>
                                </div>
                            </div>
                        </div>
                        <!-- col-6 -->
                        <div class="col-md-6">
                            <div class="widget-small danger coloured-icon"><i class='icon bx bxs-error-alt fa-3x'></i>
                                <div class="info">
                                    <h4>Sắp hết hàng</h4>
                                    <p><b>${countOutOfStock} sản phẩm</b></p>
                                    <p class="info-tong">Số sản phẩm cảnh báo hết cần nhập thêm.</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="tile">
                                <h3 class="tile-title">Khách hàng mới</h3>
                                <div>
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Tên khách hàng</th>
                                                <th>Ngày sinh</th>
                                                <th>Số điện thoại</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${newCustomers}" var ="nc">
                                                <tr>
                                                    <td>${nc.getAccount().getId()}</td>
                                                    <td>${nc.getFullname()}</td>
                                                    <td>${nc.getDateOfBirth()}</td>
                                                    <td><span class="tag tag-success">${nc.getPhone()}</span></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                            </div>
                        </div>
                        <!-- / col-12 -->
                    </div>
                </div>
                <div class="col-md-12 col-lg-6">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="tile">
                                <h3 class="tile-title">Tình trạng đơn hàng</h3>
                                <div>
                                    <table class="table table-bordered" id="orderstable">
                                        <thead>
                                            <tr>
                                                <th>ID đơn hàng</th>
                                                <th>Tên khách hàng</th>
                                                <th>Tổng tiền</th>
                                                <th>Trạng thái</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items ="${orders}" var="order" >
                                                <tr>
                                                    <td>${order.getOrderId()}</td>
                                                    <td>${order.getFullName()}</td>
                                                    <td>

                                                        <fmt:formatNumber value="${order.getTotalTransaction()}"
                                                                          type="number"/> &dstrok;

                                                    </td>
                                                    <td>
                                                        <c:if test="${order.getOrderStatusId() == 1}">
                                                            <span class="badge bg-info">
                                                                ${order.getStatus()}
                                                            </span>
                                                        </c:if>
                                                        <c:if test="${order.getOrderStatusId() == 2}">
                                                            <span class="badge bg-primary">
                                                                ${order.getStatus()}
                                                            </span>
                                                        </c:if>
                                                        <c:if test="${order.getOrderStatusId() == 3}">
                                                            <span class="badge bg-secondary">
                                                                ${order.getStatus()}
                                                            </span>
                                                        </c:if>
                                                        <c:if test="${order.getOrderStatusId() == 4}">
                                                            <span class="badge bg-success">
                                                                ${order.getStatus()}
                                                            </span>
                                                        </c:if>
                                                        <c:if test="${order.getOrderStatusId() == 5}">
                                                            <span class="badge bg-danger">
                                                                ${order.getStatus()}
                                                            </span>
                                                        </c:if>
                                                        <c:if test="${order.getOrderStatusId() == 6}">
                                                            <span class="badge bg-warning">
                                                                ${order.getStatus()}
                                                            </span>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--END right-->
            </div>

        </main>
        <%@include file="../parts/commonjs.jspf" %>
        <!--===============================================================================================-->
        <script type="text/javascript" src="js/plugins/chart.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
        <!--===============================================================================================-->
        <script type ="text/javascript">
        $(document).ready(function () {
            $('#orderstable').DataTable({
                "order": [[0, "desc"]],
            });
        });
        </script>
    </body>
</html>
