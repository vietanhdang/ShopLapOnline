<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>
<style>
    #preloader{
        display: none;
    }
</style>
<html lang="en">
    <head>
        <title>Lỗi</title>
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
                                <a href="<%=request.getContextPath()%>/home">Trang chủ</a>
                            </li>
                            <li class="active">Lỗi</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="pt-20 pb-120">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="error-page-content text-center">
                                <h1>404</h1>
                                <h2>Không tìm thấy trang</h2>
                                <p>Xin lỗi, trang bạn yêu cầu không tồn tại.</p>
                                <a href="<%=request.getContextPath()%>/home" class="btn btn-primary">Trở về trang chủ</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="parts/footer.jsp" %>
        </div>
    </body>
</html>
