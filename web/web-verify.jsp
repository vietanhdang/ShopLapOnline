<%-- Document : web-register Created on : Mar 2, 2022, 12:35:23 AM Author : Benjamin --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <title>Verify</title>
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
                            <li class="active">Update Password</li>
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
                                    <h3> Update New Password </h3>
                                </div>
                                <div class="tab-content">
                                    <div id="lg2" class="tab-pane active">
                                        <div class="login-form-container">
                                            <p style="color: red;">${messageError}</p>

                                            <div class="login-register-form">
                                                <form method="post" id="login_register_check">
                                                    <input type="password" name="password"
                                                           placeholder="Password" id="password" required>
                                                    <input type="password" name="repassword"
                                                           placeholder="Re-Password" id="repassword" required>
                                                    <div class="button-box text-center">
                                                        <button type="submit"
                                                                id="registerSubmit">Update</button>
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

                    password: {
                        required: true,
                        minlength: 6,
                    },
                    repassword: {
                        required: true,
                        minlength: 6,
                        equalTo: "#password",
                    },
                },
                messages: {

                    password: {
                        required: "Vui lòng nhập mật khẩu",
                        minlength: "Mật khẩu phải có ít nhất 6 ký tự",
                    },
                    repassword: {
                        required: "Vui lòng nhập lại mật khẩu",
                        minlength: "Mật khẩu phải có ít nhất 6 ký tự",
                        equalTo: "Mật khẩu không trùng khớp",
                    },
                },
                submitHandler: function (form) {
                    form.submit();
                },
            });
        </script>
    </body>

</html>