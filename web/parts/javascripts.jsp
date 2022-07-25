<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!-- All JS is here
============================================ -->

<script src="<%=request.getContextPath()%>/assets/js/vendor/modernizr-3.6.0.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/vendor/jquery-3.5.1.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/vendor/jquery-migrate-3.3.0.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/vendor/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/slick.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/jquery.syotimer.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/jquery.nice-select.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/wow.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/jquery-ui-touch-punch.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/jquery-ui.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/magnific-popup.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/sticky-sidebar.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/easyzoom.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/scrollup.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>

<!-- Main JS -->
<script src="<%=request.getContextPath()%>/assets/js/main.js"></script>
<script>
    $(window).on("load", function () {
        // makes sure the whole site is loaded
        var status = $("#status");
        var preloader = $("#preloader");
        var body = $("body");
        status.fadeOut(); // will first fade out the loading animation
        preloader.delay(0).fadeOut("fast"); // will fade out the white DIV that covers the website.
        body.delay(0).css({overflow: "visible"});
        var vidDefer = document.getElementsByTagName("iframe");
        for (var i = 0; i < vidDefer.length; i++) {
            if (vidDefer[i].getAttribute("data-src")) {
                vidDefer[i].setAttribute("src", vidDefer[i].getAttribute("data-src"));
            }
        }
    });
</script>
<script>
    const formatNumber = (value) => {
        return value.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
    };
    $('#cartButton').click(function () {
        $.ajax({
            url: '<%= request.getContextPath()%>/Cart',
            type: 'POST',
            data: {
                action: 'getCart'
            },
            success: function (data) {
                data.items.forEach(item => {
                    $('#cart-list').append(
                            '<li class="single-product-cart d-flex align-items-center">' +
                            '<div class="cart-img">' +
                            '<a href=""><img style="width: 80px; height: 80px" src="assets/images/product/' + item['product']['previewImage'] + '" alt=""></a>' +
                            '</div>' +
                            '<div class="cart-title row">' +
                            '<h4 class="col-12"><a href="">' + item['product']['productName'] + '</a></h4>' +
                            '</h4>' +
                            '<span class="col-12">Giá: ' + formatNumber(item['product']['unitPrice']) + 'đ </span>' +
                            '<span class="col-12">Số lượng: ' + item['quantity'] + '</span>' +
                            '</div>' +
                            '<div class="cart-delete"> <a class="cart-delete tooltip-style-2" title="Xóa sản phẩm trong giỏ hàng" onclick="deleteProductInCart(' + "('deleteProduct')" + ',' + item['product']['id'] + ')"><i class="fa fa-close"></i></a>' +
                            '</li>');
                });
                $('#totalOrder').html(formatNumber(data['orderTotal']) + 'đ');
            },
            error: function (data) {
                $('#cartContent').html('<h4>Không có sản phẩm nào trong giỏ hàng</h4>');
            }
        });
    });
    /*
     *  Delete product in cart by event and product id
     * */
    const deleteProductInCart = (mode, id) => {
        swal({
            title: mode === "deleteProduct" ? "Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?" : "Bạn có chắc chắn muốn xóa toàn bộ sản phẩm khỏi giỏ hàng?",
            text: "Sản phẩm này sẽ không được hiển thị trong giỏ hàng",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                $.ajax({
                    url: '<%= request.getContextPath()%>/Cart',
                    type: 'POST',
                    data: {
                        action: mode,
                        productId: id
                    },
                    success: function (data) {
                        swal("Xóa thành công!", {
                            icon: "success",
                            timer: 1000,
                        }).then(() => {
                            // reload window to refresh cart
                            window.location.reload();
                        });

                    }
                });
            }
        });
    };
    const addToCart = (id, condition) => {
        $.ajax({
            url: '<%= request.getContextPath()%>/Cart',
            type: 'POST',
            data: {
                productId: id,
                action: 'addToCart'
            },
            success: function (data) {
                $('#productCount').text(data['items'].length);
                if (condition === 'checkout') {
                    window.location.href = '<%= request.getContextPath()%>/Checkout';
                    return;
                }
                swal({
                    title: "Thành công",
                    text: "Sản phẩm đã được thêm vào giỏ hàng",
                    icon: "success",
                    timer: 5000,
                    buttons: [
                        'Tiếp tục mua hàng',
                        'Đến giỏ hàng',
                    ],
                }).then((value) => {
                    if (value) {
                        window.location.href = '<%= request.getContextPath()%>/Cart';
                    }
                });
            },
            error: function (data) {
                swal({
                    title: "Không thành công",
                    text: data.responseText,
                    icon: "error",
                    timer: 2000,
                    timerProgressBar: true,
                    button: "OK",
                });
            }
        });
    };
    const deleteProductInWishlist = (id) => {
        swal({
            title: "Bạn có chắc chắn muốn bỏ Yêu thích sản phẩm này?",
            text: "Sản phẩm này sẽ không được hiển thị trong Yêu thích",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                $.ajax({
                    url: '<%= request.getContextPath()%>/wishlist',
                    type: 'POST',
                    data: {
                        action: 'deleteProductInWishlist',
                        productId: id
                    },
                    success: function () {
                        swal("Xóa thành công!", {
                            icon: "success",
                            timer: 1000,
                        }).then(() => {
                            // reload window to refresh wishlist
                            window.location.reload();
                        });
                    },
                    error: function (data) {
                        swal("Xóa thất bại", {
                            text: data.responseText,
                            icon: "error",
                            timer: 2000,
                            timerProgressBar: true,
                            button: "OK",
                        })
                    }
                });
            }
        });
    };
    const addToWishlist = (id) => {
        $.ajax({
            url: '<%= request.getContextPath()%>/wishlist',
            type: 'POST',
            data: {
                productId: id,
                action: 'addToWishlist'
            },
            success: function (data) {
                $('#wishlistCount').text(data);
                swal({
                    title: "Thành công",
                    text: "Sản phẩm đã được thêm vào danh sách yêu thích",
                    icon: "success",
                    timer: 5000,
                    buttons: [
                        'Tiếp tục mua hàng',
                        'Đến Yêu thích'
                    ]
                }).then((value) => {
                    if (value) {
                        window.location.href = '<%= request.getContextPath()%>/wishlist';
                    }
                });
            },
            error: function (data) {
                swal({
                    title: "Không thành công",
                    text: data.responseText,
                    icon: "error",
                    timer: 2000,
                    timerProgressBar: true,
                    button: "OK",
                });
            }
        });
    };
</script>
