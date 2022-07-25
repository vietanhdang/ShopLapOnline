<%-- 
    Document   : product
    Created on : May 25, 2022, 3:50:57 PM
    Author     : vietd
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="parts/head.jspf" %>
    <title>Admin - Quản lý sản phẩm</title>

</head>
<body onload="time()" class="app sidebar-mini rtl">
<%@include file="parts/navbar.jspf" %>
<%@include file="parts/sidebar.jspf" %>
<main class="app-content">
    <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
            <li class="breadcrumb-item active"><a href="#"><b>Danh sách sản phẩm</b></a></li>
        </ul>
        <div id="clock"></div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="tile-body">
                    <div class="row element-button">
                        <div class="col-sm-2">

                            <a class="btn btn-add btn-sm" href="<%=request.getContextPath()%>/admin/product/add"
                               title="Thêm"><i
                                    class="fas fa-plus"></i>
                                Tạo mới sản phẩm</a>
                        </div>
                        <div class="col-sm-2">
                            <a class="btn btn-delete btn-sm nhap-tu-file" type="button" title="Nhập"
                               onclick="myFunction(this)"><i
                                    class="fas fa-file-upload"></i> Tải từ file</a>
                        </div>

                        <div class="col-sm-2">
                            <a class="btn btn-delete btn-sm print-file" type="button" title="In"
                               onclick="myApp.printTable()"><i
                                    class="fas fa-print"></i> In dữ liệu</a>
                        </div>
                        <div class="col-sm-2">
                            <a class="btn btn-delete btn-sm print-file js-textareacopybtn" type="button"
                               title="Sao chép"><i
                                    class="fas fa-copy"></i> Sao chép</a>
                        </div>

                        <div class="col-sm-2">
                            <button class="btn btn-excel btn-sm" onclick="exportExcel()" href="" title="In"><i
                                    class="fas fa-file-excel"></i>
                                Xuất
                                Excel
                            </button>
                        </div>
                        <div class="col-sm-2">
                            <a class="btn btn-delete btn-sm pdf-file" type="button" title="In"
                               onclick="myFunction(this)"><i
                                    class="fas fa-file-pdf"></i> Xuất PDF</a>
                        </div>
                        <div class="col-sm-2">
                            <a class="btn btn-delete btn-sm" type="button" title="Xóa" onclick="myFunction(this)"><i
                                    class="fas fa-trash-alt"></i> Xóa tất cả </a>
                        </div>
                    </div>
                    <table class="table table-hover table-bordered" id="productTable">
                        <thead>
                        <tr>
                            <th>Mã sản phẩm</th>
                            <th>Tên sản phẩm</th>
                            <th>Ảnh</th>
                            <th>Số lượng</th>
                            <th>Tình trạng</th>
                            <th>Giá tiền</th>
                            <th>Danh mục</th>
                            <th>Chức năng</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${products}" var="p">
                            <tr>
                                <td>${p.getId()}</td>
                                <td>${p.getProductName()}</td>
                                <td><img src="../assets/images/product/${p.getPreviewImage()}" alt="" width="100px;">
                                </td>
                                <td>${p.getQuantity()}</td>
                                <td><span class="badge bg-success">Còn hàng</span></td>
                                <td><fmt:formatNumber value="${p.getUnitPrice()}"
                                                      type="number"/> đ
                                </td>
                                <td>${p.getCategory().getCategoryName()}</td>
                                <td>
                                    <button class="btn btn-primary btn-sm trash" type="button" title="Xóa"
                                            onclick="deleteProduct(this, ${p.getId()})"><i class="fas fa-trash-alt"></i>
                                    </button>
                                    <a class="btn btn-primary btn-sm"
                                       href="<%=request.getContextPath()%>/admin/product/edit?productId=${p.getId()}"
                                       title="Sửa"><i class="fas fa-edit"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<%@include file="parts/commonjs.jspf" %>
<!-- Page specific javascripts-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<!-- Data table plugin-->
<script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>
<script src="https://cdn.rawgit.com/rainabba/jquery-table2excel/1.1.0/dist/jquery.table2excel.min.js"></script>

<script type="text/javascript">
    // $('#productTable').DataTable();
    // if click btn-excel then export table to excel
    function exportExcel() {

        $("#productTable").table2excel({
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "myFileName",
            fileext: ".xls",
            exclude_img: true,
            exclude_links: true,
            exclude_inputs: true
        });
    }

    // change width column table
    var table = $('#productTable').DataTable() ;

    //Thời Gian
    function time() {
        var today = new Date();
        var weekday = new Array(7);
        weekday[0] = "Chủ Nhật";
        weekday[1] = "Thứ Hai";
        weekday[2] = "Thứ Ba";
        weekday[3] = "Thứ Tư";
        weekday[4] = "Thứ Năm";
        weekday[5] = "Thứ Sáu";
        weekday[6] = "Thứ Bảy";
        var day = weekday[today.getDay()];
        var dd = today.getDate();
        var mm = today.getMonth() + 1;
        var yyyy = today.getFullYear();
        var h = today.getHours();
        var m = today.getMinutes();
        var s = today.getSeconds();
        m = checkTime(m);
        s = checkTime(s);
        nowTime = h + " giờ " + m + " phút " + s + " giây";
        if (dd < 10) {
            dd = '0' + dd
        }
        if (mm < 10) {
            mm = '0' + mm
        }
        today = day + ', ' + dd + '/' + mm + '/' + yyyy;
        tmp = '<span class="date"> ' + today + ' - ' + nowTime +
            '</span>';
        document.getElementById("clock").innerHTML = tmp;
        clocktime = setTimeout("time()", "1000", "Javascript");

        function checkTime(i) {
            if (i < 10) {
                i = "0" + i;
            }
            return i;
        }
    }
</script>
<script>
    function deleteProduct(r, productId) {
        // delete row from table
        swal({
            title: "Cảnh báo",
            text: "Bạn có chắc chắn là muốn xóa sản phẩm này?",
            buttons: ["Hủy bỏ", "Đồng ý"],
        })
            .then((willDelete) => {
                if (willDelete) {
                    var i = r.parentNode.parentNode.rowIndex;
                    document.getElementById("productTable").deleteRow(i);
                    $.ajax({
                        url: "<%=request.getContextPath()%>/api/admin/product/delete/" + productId,
                        type: "DELETE",
                        success: function (result) {
                            swal("Xóa thành công!", {
                                icon: "success",
                            });
                        },
                        error: function (result) {
                            swal("Xóa thất bại!", {
                                icon: "error",
                            });
                        }
                    });
                }
            });
    }

    // $(function () {
    //     $(".trash").click(function (event) {
    //         var target = $(this).attr('data-pId');

    //     });
    // });
    $('#all').click(function (e) {
        $('#productTable tbody :checkbox').prop('checked', $(this).is(':checked'));
        e.stopImmediatePropagation();
    });
</script>
</body>
</html>
