<%-- 
    Document   : web-register
    Created on : Mar 2, 2022, 12:35:23 AM
    Author     : Benjamin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <title> Forgot Password</title>
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
                                <a href="home">Home</a>
                            </li>
                            <li class="active">Forgot Password</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="login-register-area pt-20 pb-120">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-7 col-md-12 ml-auto mr-auto">
                            <div class="login-register-wrapper">
                                <div class="login-register-tab-list nav">
                                    <h3> Forgot Password </h3>
                                </div>
                                <div class="tab-content">
                                    <div id="lg1" class="tab-pane active">
                                        <div class="login-form-container">
                                            <p class="text-danger">${message}</p>
                                            <div class="login-register-form">
                                                <c:if test="${sessionScope.resetPasswordOn ==null}">
                                                    <form method="post" id="login_register_check" action="reset-password">
                                                        <input type="text" name="email" placeholder="Email"
                                                               id="email" required >
                                                        <div class="button-box">
                                                            <div class="login-footer text-center">
                                                                <button type="submit" name="btn" value="reset">Send</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </c:if>
                                                <c:if test="${sessionScope.resetPasswordOn !=null}">
                                                    <form method="post" id="login_register_check" action="reset-password">
                                                        <input type="password" name="password"
                                                               placeholder="Enter your new password" id="password" required>
                                                        <input type="password" name="repassword"
                                                               placeholder="Re-Password" id="repassword" required>
                                                        <div class="button-box">
                                                            <div class="login-footer text-center">
                                                                <button type="submit" name="btn" value="changePassword">Update password</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </c:if>
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
            /*--- check valid input form register and login ----*/
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
                        equalTo: "#password"
                    }
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
                        required: "Vui lòng nhập mật khẩu",
                        minlength: "Mật khẩu phải có ít nhất 6 ký tự",
                        equalTo: "Mật khẩu không trùng khớp"
                    }
                },
                submitHandler: function (form) {
                    form.submit();
                },
            });
        </script>
    </body>
</html>