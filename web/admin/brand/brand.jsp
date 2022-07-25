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
        <title>Admin - Quản lý thương hiệu</title>
        <style>

            /* The Close Button */
            .close {
                color: #aaaaaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }

            .close:hover,
            .close:focus {
                color: #000;
                text-decoration: none;
                cursor: pointer;
            }
        </style>
    </head>
    <body onload="time()" class="app sidebar-mini rtl">
        <%@include file="../parts/navbar.jspf" %>
        <%@include file="../parts/sidebar.jspf" %>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a><b>Danh sách thương hiệu</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button">
                                <div class="col-sm-2">
                                    <button type="button" class="btn btn-add btn-sm" title="Thêm" data-toggle="modal" data-target="#addModal">
                                        <i class="fas fa-plus"></i>Tạo mới thương hiệu
                                    </button>
                                </div>

                            </div>
                            <table class="table table-hover table-bordered" id="sampleTable">
                                <thead>
                                    <tr>
                                        <th width="10"><input type="checkbox" id="all"></th>
                                        <th>Mã thương hiệu</th>
                                        <th>Tên thương hiệu</th>
                                        <th>Ảnh</th>
                                        <th width="500">Mô tả</th>
                                        <th>Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listBrands}" var="b">
                                        <tr>
                                            <td width="10"><input type="checkbox" name="check1" value="1"></td>
                                            <td>${b.id}</td>
                                            <td>${b.brandName}</td>
                                            <td><img src="<%=request.getContextPath()%>/assets/images/brand-logo/${b.image}" alt="" width="100px;">
                                            </td>
                                            <td>${b.description}</td>
                                            <td>
                                                <button class="btn btn-primary btn-sm trash" type="button" title="Xóa"
                                                        onclick="deleteBrand(this, ${b.id})"><i class="fas fa-trash-alt"></i>
                                                </button>
                                                <a class="btn btn-primary btn-sm" title="Sửa" data-toggle="modal" data-target="#editModal${b.id}"><i class="fas fa-edit"></i></a>
                                                <form action="<%=request.getContextPath()%>/admin/brand/edit" method="post"  enctype="multipart/form-data">
                                                    <div class="modal fade" id="editModal${b.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                        <div class="modal-dialog" role="document">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" style="color: #000000" id="exampleModalLabel">Chỉnh sửa thương hiệu</h5>
                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="form-group">
                                                                        <label for="bId" class="col-form-label">ID:</label>
                                                                        <input type="text" name="bId" class="form-control" id="bId" value="${b.id}" readonly>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="bName" class="col-form-label">Tên thương hiệu:</label>
                                                                        <input type="text" id="bName"  name="bName" class="form-control" id="bName" value="${b.brandName}" required>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="Image-edit" class="col-form-label">Hình ảnh:</label>
                                                                        <input type="file" id="Image-edit${b.id}" onchange="changeImg(${b.id})" value="${b.image}" name="bImage" accept="image/png,image/jpg" class="form-control-file">
                                                                        <div class="form-group">
                                                                            <img class="img-thumbnail" style="width: 100px" src="../assets/images/brand-logo/${b.image}" id="img-brand-edit${b.id}"/>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="message-text" class="col-form-label">Mô tả:</label>
                                                                        <textarea class="form-control" id="bDesciption" name="bDesciption" id="message-text">${b.description}</textarea>

                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-secondary" id="closeModal" data-dismiss="modal">Đóng</button>
                                                                        <button type="submit" class="btn btn-primary">Tạo</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                </form>
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
        <!-- Modal -->
        <form action="<%=request.getContextPath()%>/admin/brand/add" method="post"  enctype="multipart/form-data">
            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" style="color: #000000" id="exampleModalLabel">Tạo mới thương hiệu</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="bName" class="col-form-label">Tên thương hiệu:</label>
                                <input type="text" name="bName" class="form-control" id="bName" required>
                            </div>
                            <div class="form-group">
                                <label for="bImage" class="col-form-label">Hình ảnh:</label>
                                <input type="file" name="bImage" accept="image/png,image/jpg" oninvalid="abc" class="form-control-file" id="bImage" required>
                                <div class="form-group">
                                    <img class="img-thumbnail" style="width: 100px" src="" id="img-brand"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="message-text" class="col-form-label">Mô tả:</label>
                                <textarea class="form-control" name="bDesciption" id="message-text"></textarea>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" id="closeModal" data-dismiss="modal">Đóng</button>
                                <button type="submit" class="btn btn-primary">Tạo</button>
                            </div>
                        </div>
                    </div>
                </div>
        </form>
        <c:if test="${sessionScope.deleteMessage != null}">
            <script>
                alert("${sessionScope.deleteMessage}");
                <%request.getSession().removeAttribute("deleteMessage"); %>
            </script>
        </c:if>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <%@include file="../parts/commonjs.jspf" %>
        <!-- Page specific javascripts-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
        <!-- Data table plugin-->
        <script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>
        <script>
            function deleteBrand(r, brandId) {
                // delete row from table
                swal({
                    title: "Cảnh báo",
                    text: "Bạn có chắc chắn là muốn xóa thương hiệu này?",
                    buttons: ["Hủy bỏ", "Đồng ý"]
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                window.location.href = "<%=request.getContextPath()%>/admin/brand/delete?bId="+brandId;
                                
                            }
                        });
            }

                                                                            $('#all').click(function (e) {
                                                                                $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
                                                                                e.stopImmediatePropagation();
                                                                            });

                                                                            $("#bImage").change(function () {
                                                                                var file = $(this)[0].files[0];
                                                                                console.log(file.type);
                                                                                var patterImage = new RegExp("image/*");
                                                                                if (!patterImage.test(file.type)) {
                                                                                    alert("Please choose image!");
                                                                                } else {
                                                                                    var fileReader = new FileReader();
                                                                                    fileReader.readAsDataURL(file);
                                                                                    fileReader.onload = function (e) {
                                                                                        $("#img-brand").attr("src", e.target.result);
                                                                                    };
                                                                                }
                                                                            });

            function changeImg(bId) {
                var input = document.querySelector('#Image-edit' + bId + '');
                var file = input.files[0];
                console.log(file.type);
                var patterImage = new RegExp("image/*");
                if (!patterImage.test(file.type)) {
                    alert("Please choose image!");
                } else {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL(file);
                    fileReader.onload = function (ex) {
                        $('#img-brand-edit' + bId + '').attr("src", ex.target.result);
                    };
                }
            }
        </script>
    </body>
</html>
