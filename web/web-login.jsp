<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">
    <head>
        <title>Đăng nhập</title>
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
                            <li class="active">Đăng nhập</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="login-register-area pt-20 pb-120">
                <div class="container">
                    <div class="row">
                        <div class="col-12">
                            <div class="login-register-wrapper">
                                <div class="login-register-tab-list nav">
                                    <h3 > Đăng Nhập </h3>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 m-auto">
                            <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                                <ol class="carousel-indicators">
                                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                                    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                                </ol>
                                <div class="carousel-inner">
                                    <div class="carousel-item active">
                                        <img class="d-block w-100" src="https://cdn.tgdd.vn/Products/Images/44/249151/Slider/vi-vn-msi-gaming-ge66-raider-11uh-i7-259vn-1.jpg" alt="First slide">
                                    </div>
                                    <div class="carousel-item">
                                        <img class="d-block w-100" src="https://cdn.tgdd.vn/Products/Images/44/249151/Slider/vi-vn-msi-gaming-ge66-raider-11uh-i7-259vn-1.jpg" alt="Second slide">
                                    </div>
                                    <div class="carousel-item">
                                        <img class="d-block w-100" src="https://macone.vn/wp-content/uploads/2019/04/colors_blue__imac2021-1024x683.jpeg" alt="Third slide">
                                    </div>
                                </div>
                                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-6 ml-auto mr-auto">
                            <div class="login-register-wrapper">
                                <div class="tab-content">
                                    <div id="lg1" class="tab-pane active">
                                        <div class="login-form-container">
                                            <p style="color: orange">${message}</p>
                                            <div class="login-register-form">
                                                <form method="post" id="login_register_check" action="login">
                                                    <input type="hidden" name="returnUrl" value="${returnUrl}"/>
                                                    <input type="text" name="email" placeholder="Email"
                                                           id="email" required value="${email}">
                                                    <input type="password" name="password"
                                                           placeholder="Password" id="password" required>
                                                    <div class="button-box">
                                                        <div class="login-toggle-btn">
                                                            <input type="checkbox" name="remember" id="remember">
                                                            <label for="remember">Ghi nhớ đăng nhập</label>
                                                            <a href="reset-password">Quên mật khẩu?</a>
                                                        </div>
                                                        <div class="login-footer d-flex justify-content-between">
                                                            <button type="submit" name="btn-login" value="login">Đăng nhập</button>
                                                            <a href="register" style="color: red">Bạn không có tài khoản?</a>
                                                        </div>
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

                },
                submitHandler: function (form) {
                    form.submit();
                },
            });
        </script>
    </body>
</html>