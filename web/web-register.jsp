<%-- Document : web-register Created on : Mar 2, 2022, 12:35:23 AM Author : Benjamin --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>
<html class="no-js" lang="en">
    <head>
        <title>Register</title>
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
                            <li class="active">Đăng ký</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="login-register-area pt-50 pb-120">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-7 col-md-12 ml-auto mr-auto">
                            <div class="login-register-wrapper">
                                <div class="login-register-tab-list nav">
                                    <h3> Đăng ký </h3>
                                </div>
                                <div class="tab-content">
                                    <div id="lg2" class="tab-pane active">
                                        <div class="login-form-container">
                                            <p style="color: red;">${message}</p>
                                            <div class="login-register-form">
                                                <form method="post" id="login_register_check" action="register">
                                                    <input type="text" name="email" placeholder="Enter your email"
                                                           id="email" value="${username}" required>
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <input type="password" name="password"
                                                                   placeholder="Enter your password" id="password" required>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <input type="password" name="repassword"
                                                                   placeholder="Re-Password" id="repassword" required>
                                                        </div>
                                                    </div>
                                                    <input type="text" required name="fullname" id="fullname" placeholder="Enter your full name">
                                                    <div class="row">
                                                        <div class="col-md-3 d-flex align-items-end">
                                                            <select name="gender" id="gender">
                                                                <option value="true">Male</option>
                                                                <option value="false">Female</option>
                                                            </select>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <input type="date" name="dateOfBirth"
                                                                   id="dateOfBirth" required >
                                                        </div>
                                                        <div class="col-md-5">
                                                            <input type="number" required id="phonenumber" name="phonenumber" placeholder="Enter your phone number">
                                                        </div>
                                                    </div>

                                                    <input type="text" name="address" required placeholder="Enter your address" id="address">
                                                    <div class="button-box d-flex justify-content-between">
                                                        <button type="submit"
                                                                id="registerSubmit">Đăng ký</button>
                                                        <a href="login" style="color: green">Bạn đã có tài khoản?</a>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="parts/footer.jsp" %>
        </div>

        <%@include file="parts/javascripts.jsp" %>
        <script>
            $("#login_register_check").validate({
                rules: {
                    email: {
                        required: true,
                        email: true,
                    },
                    password: {
                        required: true,
                        minlength: 6,
                    },
                    repassword: {
                        required: true,
                        minlength: 6,
                        equalTo: "#password",
                    },
                    fullname: {
                        required: true,
                    },
                    phonenumber: {
                        required: true,
                        maxlength: 10,
                    },
                    address: {
                        required: true,
                    },
                    dateOfBirth: {
                        required: true,
                    },
                },
                messages: {
                    email: {
                        required: "Vui lòng nhập email",
                        email: "Email không đúng định dạng",
                    },
                    password: {
                        required: "Vui lòng nhập mật khẩu",
                        minlength: "Mật khẩu phải có ít nhất 6 ký tự",
                    },
                    repassword: {
                        required: "Vui lòng nhập lại mật khẩu",
                        minlength: "Mật khẩu phải có ít nhất 6 ký tự",
                        equalTo: "Mật khẩu không trùng khớp",
                    },
                    fullname: {
                        required: "Vui lòng nhập tên",
                    },
                    phonenumber: {
                        required: "Vui lòng nhập số điện thoại",
                        minlength: "Số điện thoại phải gồm 10 chữ số",
                    },
                    address: {
                        required: "Vui lòng nhập địa chỉ",

                    },
                    dateOfBirth: {
                        required: "Vui lòng nhập ngày sinh",
                    },
                },
                submitHandler: function (form) {
                    form.submit();
                },
            });
        </script>
    </body>

</html>