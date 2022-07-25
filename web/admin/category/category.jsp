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
        <title>Admin - Quản lý thể loại</title>
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
                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách thể loại</b></a></li>
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
                                        <i class="fas fa-plus"></i>Tạo mới thể loại
                                    </button>
                                </div>
                            </div>
                            <table class="table table-hover table-bordered" id="sampleTable">
                                <thead>
                                    <tr>
                                        <th width="10"><input type="checkbox" id="all"></th>
                                        <th>Mã thể loại</th>
                                        <th>Tên thể loại</th>
                                        <th>Ảnh</th>
                                        <th width="500">Mô tả</th>
                                        <th>Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listCategories}" var="c">
                                        <tr>
                                            <td width="10"><input type="checkbox" name="check1" value="1"></td>
                                            <td>${c.id}</td>
                                            <td>${c.categoryName}</td>
                                            <td><img src="../assets/images/category/${c.categoryImage}" alt="" width="100px;">
                                            </td>
                                            <td>${c.categoryDescription}</td>
                                            <td>
                                                <button class="btn btn-primary btn-sm trash" type="button" title="Xóa"
                                                        onclick="deleteCategory(this, ${c.id})"><i class="fas fa-trash-alt"></i>
                                                </button>
                                                <a class="btn btn-primary btn-sm" title="Sửa" data-toggle="modal" data-target="#editModal${c.id}"><i class="fas fa-edit"></i></a>
                                                <form action="<%=request.getContextPath()%>/admin/category/edit" method="post"  enctype="multipart/form-data">
                                                    <div class="modal fade" id="editModal${c.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                        <div class="modal-dialog" role="document">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" style="color: #000000" id="exampleModalLabel">Chỉnh sửa thể loại</h5>
                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="form-group">
                                                                        <label for="cId" class="col-form-label">ID:</label>
                                                                        <input type="text" name="cId" class="form-control" id="cId" value="${c.id}" readonly>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="cName" class="col-form-label">Tên thể loại:</label>
                                                                        <input type="text" id="cName"  name="cName" class="form-control" value="${c.categoryName}" required>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="Image-edit" class="col-form-label">Hình ảnh:</label>
                                                                        <input type="file" id="Image-edit${c.id}" onchange="changeImg(${c.id})" value="${c.categoryImage}" name="cImage" accept="image/png,image/jpg" class="form-control-file">
                                                                        <div class="form-group">
                                                                            <img class="img-thumbnail" style="width: 100px" src="../assets/images/category/${c.categoryImage}" id="img-category-edit${c.id}"/>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="message-text" class="col-form-label">Mô tả:</label>
                                                                        <textarea class="form-control" id="cDesciption" name="cDesciption" id="message-text">${c.categoryDescription}</textarea>
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
        <form action="<%=request.getContextPath()%>/admin/category/add" method="post"  enctype="multipart/form-data">
            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" style="color: #000000" id="exampleModalLabel">Tạo mới thể loại</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="cName" class="col-form-label">Tên thể loại:</label>
                                <input type="text" name="cName" class="form-control" id="cName" required>
                            </div>
                            <div class="form-group">
                                <label for="cImage" class="col-form-label">Hình ảnh:</label>
                                <input type="file" name="cImage" accept="image/png,image/jpg" class="form-control-file" id="cImage" required>
                                <div class="form-group">
                                    <img class="img-thumbnail" style="width: 100px" src="" id="img-category"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="message-text" class="col-form-label">Mô tả:</label>
                                <textarea class="form-control" name="cDesciption" id="message-text"></textarea>
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
                <%request.getSession().removeAttribute("deleteMessage");%>
            </script>
        </c:if>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <%@include file="../parts/commonjs.jspf" %>
        <!-- Page specific javascripts-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
        <!-- Data table plugin-->
        <script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>
        <script src="https://cdn.rawgit.com/rainabba/jquery-table2excel/1.1.0/dist/jquery.table2excel.min.js"></script>

        <script>
                $('#sampleTable').DataTable();
                function deleteCategory(r, categoryId) {
                    // delete row from table
                    swal({
                        title: "Cảnh báo",
                        text: "Bạn có chắc chắn là muốn xóa thể loại này?",
                        buttons: ["Hủy bỏ", "Đồng ý"]
                    })
                            .then((willDelete) => {
                                if (willDelete) {
                                    window.location.href = "<%=request.getContextPath()%>/admin/category/delete?cId=" + categoryId;
                                }
                            });
                }

                $('#all').click(function (e) {
                    $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
                    e.stopImmediatePropagation();
                });

                $("#cImage").change(function () {
                    var file = $(this)[0].files[0];
                    console.log(file.type);
                    var patterImage = new RegExp("image/*");
                    if (!patterImage.test(file.type)) {
                        alert("Please choose image!");
                    } else {
                        var fileReader = new FileReader();
                        fileReader.readAsDataURL(file);
                        fileReader.onload = function (e) {
                            $("#img-category").attr("src", e.target.result);
                        };
                    }
                });

                function changeImg(cId) {
                    var input = document.querySelector('#Image-edit' + cId + '');
                    var file = input.files[0];
                    console.log(file.type);
                    var patterImage = new RegExp("image/*");
                    if (!patterImage.test(file.type)) {
                        alert("Please choose image!");
                    } else {
                        var fileReader = new FileReader();
                        fileReader.readAsDataURL(file);
                        fileReader.onload = function (ex) {
                            $('#img-category-edit' + cId + '').attr("src", ex.target.result);
                        };
                    }
                }
        </script>
    </body>
</html>
