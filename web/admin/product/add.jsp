<%-- 
    Document   : add
    Created on : Jul 22, 2022, 12:09:38 AM
    Author     : vietd
--%>

<%@page import="java.util.List" %>
<%@page import="model.Category" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../parts/head.jspf" %>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/admin/vendor/select2/select2.min.css">
        <title>Admin - Thêm sản phẩm </title>
        <style>
            .valid-feedback{
                display: block;
            }
        </style>
    </head>
    <body onload="time()" class="app sidebar-mini rtl">
        <%@include file="../parts/navbar.jspf" %>
        <%@include file="../parts/sidebar.jspf" %>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="#"><b>Tạo sản phẩm</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button">
                                <div class="col-sm-2">
                                    <a class="btn btn-add btn-sm" href="<%=request.getContextPath()%>/admin/product"
                                       title="Quay lại"><i class="fa fa-arrow-left"></i> Quay lại danh sách sản phẩm</a>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <form class="row mb-2"  id="form-add-product" method="post">
                <div class="col-lg-8">
                    <!-- Circle Buttons -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Thông tin sản phẩm</h6>
                        </div>
                        <div class="card-body row">
                            <div class="col-lg-12">
                                <div class="form-group row">
                                    <div class="col-md-6">
                                        <div class="preview-image">
                                            <label for="preview-img">Hình ảnh</label>
                                            <img id="preview-img" src="" alt="" id="preview-img" class="img-thumbnail"
                                                 style="display: none; width: 100px; height: 100px;">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="preview">Hình ảnh sản phẩm</label>
                                        <div class="input-group">
                                            <div class="custom-file">
                                                <input type="file" class="custom-file-input" id="preview" name="preview" >
                                                <label class="custom-file-label" for="preview">Chọn hình ảnh</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name">Tên sản phẩm</label>
                                    <input type="text" class="form-control" id="name" name="name" placeholder="Tên sản phẩm" >
                                </div>
                                <div class="form-group row">
                                    <div class="col-md-4">
                                        <label for="original_price">Giá nhập</label>
                                        <input type="text" class="form-control" id="original_price" name="original_price"
                                               placeholder="Giá nhập" >
                                    </div>
                                    <div class="col-md-4">
                                        <label for="initial_price">Giá khởi tạo (nếu có)</label>
                                        <input type="text" class="form-control" id="initial_price" name="initial_price"
                                               placeholder="Giá khởi tạo" >
                                    </div>
                                    <div class="col-md-4">
                                        <label for="unit_price">Giá bán</label>
                                        <input type="text" class="form-control" id="unit_price" name="unit_price"
                                               placeholder="Giá bán" >
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-md-6">
                                        <label for="quantity">Số lượng</label>
                                        <input type="text" class="form-control" id="quantity" name="quantity"
                                               placeholder="Số lượng" >
                                    </div>
                                    <div class="col-md-6">
                                        <label for="insurance">Bảo hành</label>
                                        <input type="text" class="form-control" id="insurance" name="insurance"
                                               placeholder="Bảo hành" >
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <table class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th scope="col" style="width: 5%">#</th>
                                                <th style="width: 40%">Tên thuộc tính</th>
                                                <th style="width: 40%">Giá trị</th>
                                                <th style="width: 15%" class="text-center">Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody id="table-attribute">
                                            <tr>
                                                <td class="text-center">1</td>
                                                <td>
                                                    <select class="form-control select2 attribute" name="attribute" >
                                                        <option value="">Chọn thuộc tính</option>
                                                        <c:forEach var="attribute" items="${attributes}">
                                                            <option value="${attribute.id}">${attribute.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <select class="form-control select2" name="specified_attribute" >
                                                    </select>
                                                </td>
                                                <td class="text-center">
                                                    <button type="button" onclick="removeAttr(this)" class="btn btn-danger btn-sm">
                                                        Xóa
                                                    </button>

                                                </td>
                                            </tr>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <td colspan="4" class="text-right">
                                                    <p class="text-danger"><u>Lưu ý:</u> 
                                                     để hiển thị được các giá trị thuộc tính hiện có, bạn cần phải chọn thuộc tính trước (Nếu các giá trị thuộc tính không hiển thị trong danh sách, bạn cần phải xóa các thuộc tính đó và thêm lại. Nếu thuộc tính không tồn tại bạn có thể thêm mới bằng cách gõ thuộc tính mới sau đó nhấn enter)</p>
                                                    <a href="javascript:void(0)" class="btn btn-success"
                                                       onclick="addAttribute()">Thêm thuộc tính</a>
                                                </td>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <div class="form-group">
                                    <label for="description">Description</label>
                                    <textarea class="form-control" name="description" rows="3" id="description"
                                              name="description"></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="status">Trạng thái</label>
                                    <select class="form-control" id="status1" name="status">
                                        <c:forEach var="status" items="${productStatus}">
                                            <option value="${status.id}">${status.statusName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="card shadow mb-4">
                        <div class="card-header py-3 bg-danger">
                            <h6 class="m-0 font-weight-bold text-primary">Danh mục sản phẩm</h6>
                        </div>
                        <div class="card-body">
                            <div class="form-group">
                                <label for="category">Danh mục</label>
                                <select class="form-control select2" id="category" name="categoryId" >
                                    <option value="">Chọn danh mục</option>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.id}">${category.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="card shadow mb-4">
                        <div class="card-header py-3 bg-success">
                            <h6 class="m-0 font-weight-bold text-primary">Thương hiệu</h6>
                        </div>
                        <div class="card-body">
                            <div class="form-group">
                                <label for="brand">Thương hiệu</label>
                                <select class="form-control select2" id="brand" name="brandId" >
                                    <option value="">Chọn thương hiệu</option>
                                    <c:forEach var="brand" items="${brands}">
                                        <option value="${brand.id}">${brand.brandName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Hình ảnh chi tiết</h6>
                        </div>
                        <div class="card-body row">
                            <table class="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col" style="width: 5%">#</th>
                                        <th style="width: 40%">Hình ảnh</th>
                                        <th style="width: 40%">Hình ảnh</th>
                                        <th style="width: 15%" class="text-center">Hành động</th>
                                    </tr>
                                </thead>
                                <tbody id="table-image">
                                    <tr>
                                        <td class="text-center">1</td>
                                        <td>
                                            <div class="detail-image">
                                                <img src="" alt="" class="img-thumbnail"
                                                     style="width: 100px; height: 100px;display:none">
                                            </div>
                                        </td>
                                        <td>
                                            <input type="file" class="form-control" name="image_details" onchange="readURL(this)"
                                                   placeholder="Hình ảnh">

                                        </td>
                                        <td class="text-center">
                                            <button type="button" onclick="removeAttr(this)" class="btn btn-danger btn-sm">Xóa
                                            </button>
                                        </td>
                                    </tr>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colspan="4" class="text-right">
                                            <button type="button" class="btn btn-success btn-sm" onclick="addImage()">Thêm hình
                                                ảnh
                                            </button>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary" id="save">
                        <i class="fa fa-save"></i> Save
                    </button>
                </div>
            </form>
        </main>
        <%@include file="../parts/commonjs.jspf" %>
        <script src="../js/ckeditor/ckeditor.js"></script>
        <script src="../vendor/select2/select2.min.js"></script>
        <script src="../vendor/jquery-validate/jquery.validate.min.js"></script>
        <script>
                                                function readURL(e) {
                                                    var imgNearest = $(e).closest('tr').find('.detail-image img');
                                                    imgNearest.attr('src', URL.createObjectURL(e.files[0]));
                                                    imgNearest.show();

                                                }
                                                function addImage() {
                                                    let total = $('#table-image tr').length;
                                                    let html = `<tr>
                                                <td class="text-center">${total+1}</td>
                                                <td>
                                                    <div class="detail-image">
                                                        <img src="" alt="" class="img-thumbnail" style="width: 100px; height: 100px;display:none">
                                                    </div>
                                                </td>
                                                <td>
                                                    <input type="file" class="form-control"  name="image_details" onchange="readURL(this)" placeholder="Hình ảnh">
                                            
                                                </td>
                                                <td class="text-center">
                                                    <button type="button" onclick="removeAttr(this)" class="btn btn-danger btn-sm" >Xóa</button>
                                                </td>
                                            </tr>`;
                                                    $('#table-image').append(html);
                                                    reOrderAttr('table-image');

                                                }
                                                function removeAttr(e) {
                                                    $(e).closest('tr').remove();
                                                    reOrderAttr('table-attribute');
                                                    reOrderAttr('table-image');
                                                }
                                                // re-order attribute
                                                function reOrderAttr(tableName) {
                                                    var i = 1;
                                                    $('#' + tableName + ' tr').each(function () {
                                                        $(this).find('td:first').text(i);
                                                        i++;
                                                    });
                                                }
                                                function addAttribute() {
                                                    let total = $('#table-attribute tr').length;
                                                    let html = `<tr>
                                                            <td class="text-center">` + 1 + `</td>
                                                            <td>
                                                                <select class="form-control select2 attribute" name="attribute">
                                                                    <option value="">Chọn thuộc tính</option>
                                                                <c:forEach var="attribute" items="${attributes}">
                                                                        <option value="${attribute.id}">${attribute.name}</option>
                                                                 </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td>
                                                                <select class="form-control select2" name="specified_attribute">
                                                                </select>
                                                            </td>
                                                            <td class="text-center">
                                                                <button type="button" class="btn btn-danger btn-sm" onclick="removeAttr(this)">Xóa</button>
                                                            </td>
                                                        </tr>`;

                                                    $('#table-attribute').append(html);
                                                    reOrderAttr('table-attribute');
                                                    $('.select2').select2({
                                                        tags: true,
                                                    });
                                                }
                                                // remove attribute
                                                $(document).on('change', '.attribute', function () {
                                                    let total = $('#table-attribute tr').length;
                                                    var element = $(this);
                                                    var attributeId = element.val();
                                                    let isDuplicate = false;
                                                    let valueElement = $(this).closest('tr').find('td:nth-child(3) select');
                                                    if (total > 1) {
                                                        for (let i = 1; i < total; i++) {
                                                            let attributeIdElement = $('#table-attribute tr:nth-child(' + i + ')').find('td:nth-child(2) select');
                                                            if (attributeIdElement.val() == attributeId) {
                                                                swal("Lỗi", "Thuộc tính đã tồn tại", "error").then(() => {
                                                                    element.val('');
                                                                    valueElement.html('');
                                                                });
                                                                return false;
                                                            }
                                                        }
                                                    }
                                                    valueElement.empty();
                                                    fetch("<%= request.getContextPath()%>/api/product/attribute/" + attributeId, {
                                                        method: "GET"
                                                    }).then(function (response) {
                                                        return response.json();
                                                    }).then(function (data) {
                                                        $.each(data, function (index, value) {
                                                            valueElement.append("<option value='" + value.id + "'>" + value.name + "</option>");
                                                        });
                                                    });
                                                });
                                                $(document).ready(function () {
                                                    $('.select2').select2({
                                                        tags: true,
                                                    });
                                                });
                                                CKEDITOR.replace('description');
                                                $("#preview").change(function () {
                                                    $("#preview-img").attr("src", URL.createObjectURL(this.files[0]));
                                                    $("#preview-img").css("display", "inherit");

                                                });

                                                $("#form-add-product").validate({
                                                    rules: {
                                                        name: {
                                                            required: true,
                                                            minlength: 3,
                                                            maxlength: 255
                                                        },
                                                        original_price: {
                                                            required: true,
                                                            number: true,
                                                            min: 0
                                                        },
                                                        initial_price: {
                                                            required: true,
                                                            number: true,
                                                            min: 0
                                                        },
                                                        unit_price: {
                                                            required: true,
                                                            number: true,
                                                            min: 0
                                                        },
                                                        quantity: {
                                                            required: true,
                                                            number: true,
                                                            min: 0
                                                        },
                                                        insurance: {
                                                            required: true,
                                                            number: true,
                                                            min: 0
                                                        },

                                                        description: {
                                                            required: true,
                                                        },
                                                        categoryId: {
                                                            required: true,
                                                        },
                                                        brandId: {
                                                            required: true,
                                                        },
                                                        image_details: {
                                                            required: true,
                                                        },
                                                        attribute: {
                                                            required: true,
                                                        },
                                                        specified_attribute: {
                                                            required: true,
                                                        },
                                                        preview: {
                                                            required: true,
                                                        }
                                                    },
                                                    messages: {
                                                        name: {
                                                            required: "Vui lòng nhập tên sản phẩm",
                                                            minlength: "Tên sản phẩm phải có ít nhất 3 ký tự",
                                                            maxlength: "Tên sản phẩm không được vượt quá 255 ký tự"
                                                        },
                                                        original_price: {
                                                            required: "Vui lòng nhập giá gốc",
                                                            number: "Giá gốc phải là số",
                                                            min: "Giá gốc phải lớn hơn 0"
                                                        },
                                                        initial_price: {
                                                            required: "Vui lòng nhập giá khởi điểm",
                                                            number: "Giá khởi điểm phải là số",
                                                            min: "Giá khởi điểm phải lớn hơn 0"
                                                        },
                                                        unit_price: {
                                                            required: "Vui lòng nhập giá đơn vị",
                                                            number: "Giá đơn vị phải là số",
                                                            min: "Giá đơn vị phải lớn hơn 0"
                                                        },
                                                        quantity: {
                                                            required: "Vui lòng nhập số lượng",
                                                            number: "Số lượng phải là số",
                                                            min: "Số lượng phải lớn hơn 0"
                                                        },
                                                        insurance: {
                                                            required: "Vui lòng nhập bảo hành",
                                                            number: "Bảo hành phải là số",
                                                            min: "Bảo hành phải lớn hơn 0"
                                                        },
                                                        description: {
                                                            required: "Vui lòng nhập mô tả",
                                                        },
                                                        categoryId: {
                                                            required: "Vui lòng chọn danh mục",
                                                        },
                                                        brandId: {
                                                            required: "Vui lòng chọn nhãn hiệu",
                                                        },
                                                        image_details: {
                                                            required: "Vui lòng chọn ảnh chi tiết",
                                                            accept: "Ảnh chi tiết không hợp lệ"
                                                        },
                                                        attribute: {
                                                            required: "Vui lòng chọn thuộc tính",
                                                        },
                                                        specified_attribute: {
                                                            required: "Vui lòng chọn thuộc tính",
                                                        },
                                                        preview: {
                                                            required: "Vui lòng chọn ảnh đại diện",
                                                            accept: "Ảnh đại diện không hợp lệ"
                                                        }
                                                    },
                                                    errorElement: "em",
                                                    errorPlacement: function (error, element) {
                                                        // Thêm class `invalid-feedback` cho field đang có lỗi
                                                        error.addClass("invalid-feedback");
                                                        if (element.prop("type") === "checkbox") {
                                                            error.insertAfter(element.parent("label"));
                                                        } else {
                                                            error.insertAfter(element);
                                                        }
                                                    },
                                                    success: function (label, element) {
                                                        // Thêm class `valid-feedback` cho field và label có thành công
                                                        $(element).addClass("valid-feedback").removeClass("is-invalid");
                                                        $(label).addClass("valid-feedback").removeClass("invalid-feedback");
                                                    },
                                                    highlight: function (element, errorClass, validClass) {
                                                        $(element).addClass("is-invalid").removeClass("valid-feedback");
                                                        $(element).parent().find(".invalid-feedback").remove();
                                                    },
                                                    unhighlight: function (element, errorClass, validClass) {
                                                        $(element).addClass("valid-feedback").removeClass("is-invalid");
                                                        $(element).parent().find(".invalid-feedback").remove();
                                                    },
                                                    submitHandler: function (form) {
                                                        let description = CKEDITOR.instances.description.getData();
                                                        $("#description").val(description);
                                                        let formData = new FormData($(form)[0]);
                                                        $.ajax({
                                                            url: "<%=request.getContextPath()%>/admin/product/add",
                                                            type: "POST",
                                                            data: formData,
                                                            contentType: false,
                                                            cache: false,
                                                            processData: false,
                                                            success: function (data) {
                                                                if (data.status) {
                                                                    swal("Thành công", data.message, "success").then(() => window.location.href = "<%=request.getContextPath()%>/admin/product");
                                                                } else {
                                                                    swal("Thất bại", data.message, "error");
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
        </script>

    </body>
</html>
