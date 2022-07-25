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
        <title>Admin - Quản lý người dùng</title>
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
                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách người dùng</b></a></li>
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
                                        <i class="fas fa-plus"></i>Tạo mới nhân viên
                                    </button>
                                </div>
                            </div>
                            <table class="table table-hover table-bordered" id="sampleTable">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên người dùng</th>
                                        <th>Địa chỉ email/đăng nhập</th>
                                        <th>Địa chỉ</th>
                                        <th>Điện thoại</th>
                                        <th>Ảnh</th>
                                        <th>Giới tính</th>
                                        <th>Ngày Sinh</th>
                                        <th>Vai trò</th>
                                        <th>Trạng thái</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listCustomers}" var="c">
                                        <tr>
                                            <td>${c.account.id}</td>
                                            <td>${c.fullname}</td>
                                            <td>${c.account.username}</td>
                                            <td>${c.address}</td>
                                            <td>${c.phone}</td>
                                            <td><img src="<%=request.getContextPath()%>/assets/images/account/${c.image}" alt="" width="100px;" height="100px" class="img-reponsive"></td>
                                            <td>${c.gender == true? "Nam" : "Nữ"}</td>
                                            <td>${c.dateOfBirth}</td>
                                            <td>${c.account.role.name}</td>
                                            <td>${c.account.status?"Hoạt động":"Ngưng hoạt động"}</td>
                                            <td>
                                                <button class="btn btn-primary " type="button" title="${!c.account.status?"active":"in-active"}"
                                                        onclick="changeStatus(${c.account.id},${c.account.status})">${!c.account.status?"active":"in-active"}
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:forEach items="${listSales}" var="s">
                                        <tr>
                                            <td>${s.account.id}</td>
                                            <td>${s.fullname}</td>
                                            <td>${s.account.username}</td>
                                            <td>${s.address}</td>
                                            <td>${s.phone}</td>
                                            <td><img src="../assets/images/account/${s.image}" alt="" width="100px;" height="100px" class="img-reponsive"></td>
                                            <td>${s.gender == true? "Nam" : "Nữ"}</td>
                                            <td>${s.dateOfBirth}</td>
                                            <td>${s.account.role.name}</td>
                                            <td>${s.account.status?"Hoạt động":"Không hoạt động"}</td>
                                            <td>
                                                <button class="btn btn-primary " type="button" title="${!s.account.status?"active":"in-active"}"
                                                        onclick="changeStatus(${s.account.id},${s.account.status})">${!s.account.status?"active":"in-active"}
                                                </button>
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
        <form action="<%=request.getContextPath()%>/admin/users/add" method="post"  enctype="multipart/form-data">
            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" style="color: #000000" id="exampleModalLabel">Tạo mới nhân viên</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="fullname" class="col-form-label">Tên nhân viên</label>
                                <input type="text" name="fullname" class="form-control" id="fullname" required>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-form-label">Email nhân viên</label>
                                <input type="email" name="email" class="form-control" id="email" required>
                            </div>
                            <div class="form-group">
                                <label for="gender" class="col-form-label">Giới tính</label>
                                <select name="gender">
                                    <option value="male" selected>Nam</option>
                                    <option value="female">Nữ</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="DoB" class="col-form-label">Ngày sinh</label>
                                <input type="date" name="DoB" class="form-control" id="DoB" required>
                            </div>
                            <div class="form-group">
                                <label for="image" class="col-form-label">Hình ảnh:</label>
                                <input type="file" name="image" accept="image/png,image/jpg" class="form-control-file" id="image" required>
                                <div class="form-group">
                                    <img class="img-thumbnail" style="width: 100px" src="" id="img"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="address" class="col-form-label">Địa chỉ</label>
                                <input type="text" name="address" class="form-control" id="address" required>
                            </div>
                            <div class="form-group">
                                <label for="phone" class="col-form-label">Điện thoại:</label>
                                <input type="phone" name="phone" class="form-control" id="phone" required>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" id="closeModal" data-dismiss="modal">Đóng</button>
                                <button type="submit" class="btn btn-primary">Tạo</button>
                            </div>
                        </div>
                    </div>
                </div>
        </form>
        <c:if test="${sessionScope.message != null}">
            <script>
                alert("${sessionScope.message}");
                <%request.getSession().removeAttribute("message");%>
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

                $("#sampleTable").DataTable();
                $('#all').click(function (e) {
                    $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
                    e.stopImmediatePropagation();
                });
                ;

                function changeStatus(id, status) {
//                $.ajax({
//                    url: "user/edit",
//                    data: {id: id, stt: status},
//                    type: "POST"
//                });
                    window.location.href = "<%=request.getContextPath()%>/admin/users/edit?id=" + id + "&stt=" + status;
                }

                $("#image").change(function () {
                    var file = $(this)[0].files[0];
                    console.log(file.type);
                    var patterImage = new RegExp("image/*");
                    if (!patterImage.test(file.type)) {
                        alert("Please choose image!");
                    } else {
                        var fileReader = new FileReader();
                        fileReader.readAsDataURL(file);
                        fileReader.onload = function (e) {
                            $("#img").attr("src", e.target.result);
                        };
                    }
                });

        </script>
    </body>
</html>
