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
        <%@include file="../parts/head.jspf" %>
        <title>Admin - Quản lý sản phẩm </title>
        <style>
            table{
                margin: 0 auto;
                width: 100%;
                clear: both;
                border-collapse: collapse;
                table-layout: fixed; 
                word-wrap:break-word;
            }
        </style>
    </head>
    <body onload="time()" class="app sidebar-mini rtl"> 
        <%@include file="../parts/navbar.jspf" %>
        <%@include file="../parts/sidebar.jspf" %>
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
                            </div>

                            <table class="table table-hover table-bordered" id="productTable">
                                <thead>
                                    <tr>
                                        <th>Mã sản phẩm</th>
                                        <th>Ảnh</th>
                                        <th>Tên sản phẩm</th>
                                        <th>Số lượng</th>
                                        <th>Tình trạng</th>
                                        <th>Giá đang bán</th>
                                        <th>Danh mục</th>
                                        <th>Ngày tạo</th>
                                        <th>Ngày sửa</th>
                                        <th>Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${products}" var="p">
                                        <tr>
                                            <td>${p.getId()}</td>
                                            <td>
                                                <img src="../assets/images/product/${p.getPreviewImage()}" alt="" width="100px;">
                                            </td>
                                            <td>${p.getProductName()}</td>
                                            <td>${p.getQuantity()}</td>
                                            <td>
                                                <span class="badge ${p.getStatus().getId() == 2 ? 'bg-danger' : (p.getStatus().getId() == 1 ? 'bg-success' : 'bg-warning')}">
                                                    ${p.getStatus().getStatusName()}
                                                </span>
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${p.getUnitPrice()}"
                                                                  type="number"/> đ
                                            </td>
                                            <td>
                                                ${p.getCategory().getCategoryName()}
                                            </td>
                                            <td>
                                                <fmt:formatDate type = "both" 
                                                                dateStyle = "short" timeStyle = "short" value = "${p.getCreatedTime()}" /></td>

                                            <td><fmt:formatDate type = "both" 
                                                            dateStyle = "short" timeStyle = "short" value = "${p.getUpdatedTime()}" /></td>
                                            <td>
                                                <button type="button" class="btn btn-secondary btn-sm" data-toggle="modal"
                                                        data-id="${p.getId()}" data-name="${p.getProductName()}"
                                                        data-quantity="${p.getQuantity()}" data-price="${p.getUnitPrice()}"
                                                        data-category="${p.getCategory().getId()}"
                                                        onclick="editProduct(this)" title="Edit quick product ${p.getProductName()}">
                                                    <i class="fas fa-pencil-alt"></i>
                                                </button>
                                                <a class="btn btn-primary btn-sm"
                                                   href="<%=request.getContextPath()%>/admin/product/edit?productId=${p.getId()}"
                                                   title="Sửa"><i class="fas fa-edit"></i>
                                                </a>
                                                <button class="btn btn-info btn-sm" type="button"
                                                        title="Thay đổi trạng thái sản phẩm"
                                                        onclick="changeStatus(${p.getId()},${p.getStatus().getId()})"><i
                                                        class="fas fa-exchange-alt"></i>
                                                </button>
                                                <button class="btn btn-primary btn-sm trash" type="button" title="Xóa"
                                                        onclick="deleteProduct(${p.getId()})">
                                                    <i class="fas fa-trash-alt"></i>
                                                </button>

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
        <!-- Modal change product status -->
        <div class="modal fade" id="modalStatus" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title text-info" id="exampleModalLabel">Thay đổi trạng thái sản phẩm</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="productId" id="productId">
                        <c:forEach items="${productStatus}" var="ps">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="status" id="status${ps.getId()}"
                                       value="${ps.getId()}">
                                <label class="form-check-label" for="status${ps.getId()}">
                                    ${ps.getStatusName()}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" title="Đóng">Đóng</button>
                        <button type="submit" class="btn btn-primary" title="Lưu" onclick="saveStatus()">Lưu</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal edit quick product -->
        <div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <form class="modal-content" id="form-edit" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title text-info">Sửa nhanh thông tin sản phẩm </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="productId" id="productId1">
                        <input type="hidden" name="action" value="editQuickProduct">
                        <div class="form-group">
                            <label for="productName">Tên sản phẩm</label>
                            <input type="text" class="form-control" id="productName" name="productName"
                                   placeholder="Nhập tên sản phẩm">
                        </div>
                        <div class="form-group">
                            <label for="quantity">Số lượng</label>
                            <input type="number" class="form-control" id="quantity" name="quantity"
                                   placeholder="Nhập số lượng">
                        </div>
                        <div class="form-group">
                            <label for="unitPrice">Giá bán</label>
                            <input type="number" class="form-control" id="unitPrice" name="unitPrice"
                                   placeholder="Nhập giá">
                        </div>
                        <div class="form-group">
                            <label for="categoryId">Danh mục</label>
                            <select class="form-control" id="categoryId" name="categoryId">
                                <option value="">Chọn danh mục</option>
                                <c:forEach items="${categories}" var="c">
                                    <option value="${c.getId()}">${c.getCategoryName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" title="Đóng">Đóng</button>
                        <button type="button" class="btn btn-primary" title="Lưu" id="editQuick">Lưu</button>
                    </div>
                </form>
            </div>
        </div>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <%@include file="../parts/commonjs.jspf" %>
        <!-- Page specific javascripts-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
        <!-- Data table plugin-->
        <script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>
        <script src="https://cdn.rawgit.com/rainabba/jquery-table2excel/1.1.0/dist/jquery.table2excel.min.js"></script>

        <script>
                            function deleteProduct(productId) {
                                // delete row from table
                                swal({
                                    title: "Cảnh báo",
                                    text: "Bạn có chắc chắn là muốn xóa sản phẩm này khi đã xóa sản phẩm bạn sẽ xóa tất cả các thông tin liên quan đến sản phẩm này",
                                    buttons: ["Hủy bỏ", "Đồng ý"],
                                }).then((willDelete) => {
                                    if (willDelete) {
                                        $.ajax({
                                            url: "<%=request.getContextPath()%>/admin/product",
                                            type: "POST",
                                            data: {
                                                action: "delete",
                                                productId: productId,
                                            },
                                            success: function (data) {
                                                if (data.status) {
                                                    swal("Thành công", data.message, "success").then(() => location.reload());
                                                } else {
                                                    swal("Thất bại", data.message, "error");
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                            function editProduct(e) {
                                $("#productId1").val($(e).data('id'));
                                $('#productName').val($(e).data('name'));
                                $('#quantity').val($(e).data('quantity'));
                                $('#unitPrice').val($(e).data('price'));
                                $('#categoryId').val($(e).data('category'));
                                $('#modalEdit').modal('show');
                            }
                            $("modalEdit").on("hidden.bs.modal", function () {
                                $("#form-edit").reset();
                            });
                            $("#editQuick").click(function () {
                                $.ajax({
                                    url: '../admin/product',
                                    type: 'POST',
                                    data: $("#form-edit").serialize(),
                                    success: function (data) {
                                        if (data.status) {
                                            swal("Thành công", data.message, "success").then(() => location.reload());
                                        } else {
                                            swal("Thất bại", data.message, "error");
                                        }
                                    }
                                });
                            });
                            function changeStatus(productId, statusId) {
                                $("input[name='status'][value='" + statusId + "']").prop("checked", true);
                                $("input[name='productId']").val(productId);
                                $("#modalStatus").modal("show");
                            }
                            function saveStatus() {
                                var productId = $("input[name='productId']").val();
                                var statusId = $("input[name='status']:checked").val();
                                $.ajax({
                                    url: "../admin/product",
                                    type: "POST",
                                    data: {
                                        action: "changeStatus",
                                        productId: productId,
                                        statusId: statusId
                                    },
                                    success: function (data) {
                                        if (data.status) {
                                            swal("Thành công", "Cập nhật trạng thái sản phẩm thành công", "success").then(() => window.location.reload());
                                        } else {
                                            swal("Thất bại", "Cập nhật trạng thái sản phẩm thất bại", "error");
                                        }
                                    }
                                });
                            }

                            $('#productTable').DataTable({
                                // add colums sample size
                                "columnDefs": [
                                    {width: "7%", "targets": 0},
                                    {width: "15%", targets: 2},
                                    {width: "5%", targets: 3},
                                    {width: "5%", targets: 4},
                                    {width: "5%", targets: 7},
                                    {width: "5%", targets: 8},
                                ],
                                "order": [[8, "desc"]],
                                "language": {
                                    "lengthMenu": "Hiển thị _MENU_ dòng mỗi trang",
                                    "zeroRecords": "Không có dữ liệu",
                                    "info": "Trang _PAGE_ của _PAGES_",
                                    "infoEmpty": "Không có dữ liệu",
                                    "infoFiltered": "(Lọc từ _MAX_ dòng)",
                                    "search": "Tìm kiếm:",
                                    "paginate": {
                                        "previous": "Trang trước",
                                        "next": "Trang sau"
                                    }
                                }
                            });


        </script>
    </body>
</html>
