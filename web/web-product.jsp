<%-- 
    Document   : web-product
    Created on : Jun 7, 2022, 7:09:59 PM
    Author     : vietd
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách các sản phẩm hiện có trong cửa hàng</title>
        <%@include file="parts/head.jsp" %>
        <style>
            #search-product label {
                color: red;
                position: absolute;
                left: 15px;
                top: 55px;
            }

            #price-slider label {
                color: red;
            }
        </style>
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
                            <li class="active">
                                Sản phẩm
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="shop-area pt-120 pb-120">
                <div class="container">
                    <div class="row flex-row-reverse">
                        <div class="col-lg-9">
                            <div class="shop-topbar-wrapper">
                                <div class="shop-topbar-left">
                                    <div class="view-mode nav tooltip-style-2">
                                        <a class="active" href="<%=request.getContextPath()%>/products"
                                           title="Thiết lập lại mặc định" href="#shop-1"><i
                                                class="fa fa-refresh"></i></a>
                                    </div>
                                    <p>Hiển thị ${(productFilter.getCurrentPage()-1)*(productFilter.getRecordPerPage()) +1 }
                                        - ${productFilter.getCurrentPage()*productFilter.getRecordPerPage() > productList.getTotalProduct()? productList.getTotalProduct() : productFilter.getCurrentPage()*productFilter.getRecordPerPage()}
                                        của ${productList.getTotalProduct()} kết quả </p>
                                </div>
                                <div class="product-sorting-wrapper">
                                    <div class="product-shorting shorting-style">
                                        <label>Hiển thị :</label>
                                        <select name="limit" id="limit">
                                            <c:forEach begin="12" end="36" step="12" var="i">
                                                <option value="${i}" ${productFilter.getRecordPerPage() == i ? 'selected': ''}> ${i}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="product-show shorting-style">
                                        <label>Sắp xếp theo :</label>
                                        <select name="sortBy" id="sortBy">
                                            <option value="latest" ${(productFilter.getSortBy() eq "date" && !productFilter.isIsAsc()) ? "selected"
                                                                     : "" }> Mới nhất
                                            </option>
                                            <option value="oldest" ${(productFilter.getSortBy() eq "date" && productFilter.isIsAsc()) ? "selected"
                                                                     : "" }> Cũ nhất
                                            </option>
                                            <option value="priceAsc" ${(productFilter.getSortBy() eq "price" && productFilter.isIsAsc()) ? "selected" : ""
                                                    }> Giá tăng dần
                                            </option>
                                            <option value="priceDesc" ${(productFilter.getSortBy() eq "price" && !productFilter.isIsAsc()) ? "selected"
                                                                        : "" }> Giá giảm dần
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="shop-bottom-area">
                                <c:choose>
                                    <c:when test="${productList.getProducts().size() == 0}">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="alert alert-danger text-center" role="alert">
                                                    <strong>Không có sản phẩm nào phù hợp với yêu cầu của bạn!</strong>
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="tab-content jump">
                                            <div id="shop-1" class="tab-pane active">
                                                <div class="row">
                                                    <c:forEach items="${productList.getProducts()}" var="product">
                                                        <div class="col-xl-4 col-lg-4 col-md-6 col-sm-6 col-12">
                                                            <div class="single-product-wrap mb-35">
                                                                <div class="product-img product-img-zoom mb-15">
                                                                    <a href="<%=request.getContextPath()%>/productDetail?productId=${product.getId()}">
                                                                        <img src="assets/images/product/${product.getPreviewImage()}"
                                                                             alt="">
                                                                    </a>
                                                                    <span class="pro-badge left bg-red">Hiện có ${product.getQuantity()} sản phẩm</span>
                                                                    <div class="product-action-2 tooltip-style-2">
                                                                        <c:if test="${sessionScope.ACCOUNTLOGGED != null && sessionScope.ACCOUNTLOGGED.role.roleID == 3}">
                                                                            <button title="Thêm vào danh sách yêu thích"
                                                                                    onclick="addToWishlist(${product.getId()})">
                                                                                <i
                                                                                    class="icon-heart"></i>
                                                                            </button>

                                                                        </c:if>
                                                                        <button title="Thêm vào giỏ hàng"
                                                                                onclick="addToCart(${product.getId()})"><i
                                                                                class="icon-handbag"></i>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                                <div class="product-content-wrap-2 text-center">
                                                                    <div class="product-rating-wrap">
                                                                        <div class="product-rating">
                                                                            <c:forEach begin="1" end="${product.getRating()}"
                                                                                       step="1">
                                                                                <i class="icon_star"></i>
                                                                            </c:forEach>
                                                                        </div>
                                                                        <span>(${product.getComment()} Đánh giá) (${product.getQuantitySold()} Đã bán)</span>
                                                                    </div>
                                                                    <h3>
                                                                        <a href="<%=request.getContextPath()%>/productDetail?productId=${product.getId()}">${product.getProductName()}</a>
                                                                    </h3>
                                                                    <div class="product-price-2">
                                                                        <span class="new-price">
                                                                            <fmt:formatNumber
                                                                                value="${product.getUnitPrice()}"
                                                                                type="number"/> &dstrok;
                                                                        </span>
                                                                        <span class="old-price">
                                                                            <c:if test="${product.getInitialPrice() !=0}">
                                                                                <fmt:formatNumber
                                                                                    value="${product.getInitialPrice()}"
                                                                                    type="number"/> &dstrok;
                                                                            </c:if>
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                                <div class="product-content-wrap-2 product-content-position text-center">
                                                                    <div class="product-rating-wrap">
                                                                        <div class="product-rating">
                                                                            <c:forEach begin="1" end="${product.getRating()}"
                                                                                       step="1">
                                                                                <i class="icon_star"></i>
                                                                            </c:forEach>
                                                                        </div>
                                                                        <span>(${product.getComment()} Đánh giá) (${product.getQuantitySold()} Đã bán)</span>
                                                                    </div>
                                                                    <h3>
                                                                        <a href="<%=request.getContextPath()%>/productDetail?productId=${product.getId()}">${product.getProductName()}</a>
                                                                    </h3>
                                                                    <div class="product-price-2">
                                                                        <span class="new-price">
                                                                            <fmt:formatNumber
                                                                                value="${product.getUnitPrice()}"
                                                                                type="number"/> &dstrok;
                                                                        </span>
                                                                        <span class="old-price">
                                                                            <c:if test="${product.getInitialPrice() !=0}">
                                                                                <fmt:formatNumber
                                                                                    value="${product.getInitialPrice()}"
                                                                                    type="number"/> &dstrok;
                                                                            </c:if>
                                                                        </span>
                                                                    </div>
                                                                    <div class="pro-add-to-cart">
                                                                        <button title="Add to Cart"
                                                                                onclick="addToCart(${product.getId()}, 'checkout')">
                                                                            Mua ngay
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="pro-pagination-style text-center mt-10" id="pagination">
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-lg-3">
                            <div class="sidebar-wrapper sidebar-wrapper-mrg-right">
                                <div class="sidebar-widget mb-40">
                                    <h4 class="sidebar-widget-title">Tìm kiếm sản phẩm </h4>
                                    <div class="sidebar-search">
                                        <form class="sidebar-search-form" id="search-product">
                                            <input type="text" placeholder="Nhập tên sản phẩm" name="search"
                                                   value="${productFilter.getSearch()}">
                                            <button>
                                                <i class="icon-magnifier"></i>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                                <div class="sidebar-widget shop-sidebar-border mb-35 pt-40">
                                    <h4 class="sidebar-widget-title">Danh mục</h4>
                                    <div class="sidebar-widget-list">
                                        <ul>
                                            <c:forEach items="${sessionScope.categories}" var="category">
                                                <li>
                                                    <div class="sidebar-widget-list-left">
                                                        <label><input type="checkbox" name="categoryId"
                                                                      value="${category.id}"
                                                                      <c:forEach items="${productFilter.getCategoryId()}"
                                                                                 var="categoryIdFilter">
                                                                          ${categoryIdFilter == category.id?'checked':''}
                                                                      </c:forEach>
                                                                      > ${category.categoryName}
                                                        </label>
                                                        <a style="position: absolute; top: 0; right: 0"><span>${category.getTotalProduct()}</span></a>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div class="sidebar-widget shop-sidebar-border mb-35 pt-40">
                                    <h4 class="sidebar-widget-title">Thương hiệu</h4>
                                    <div class="sidebar-widget-list">
                                        <ul>
                                            <c:forEach items="${sessionScope.brands}" var="brand">
                                                <li>
                                                    <div class="sidebar-widget-list-left">

                                                        <label><input type="checkbox" name="brandId"
                                                                      value="${brand.id}"
                                                                      <c:forEach items="${productFilter.getBrandId()}" var="brandIdFilter">
                                                                          ${brandIdFilter == brand.id?'checked':''}
                                                                      </c:forEach>
                                                                      > ${brand.brandName}
                                                        </label>
                                                        <a style="position: absolute; top: 0; right: 0"><span>${brand.getTotalProduct()}</span></a>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div class="sidebar-widget shop-sidebar-border mb-40 pt-40">
                                    <h4 class="sidebar-widget-title">Tìm sản phẩm theo giá: </h4>
                                    <div class="price-filter">
                                        <span>Từ:  <fmt:formatNumber
                                                value="${priceAvaliable[0]}"
                                                type="number"/>&dstrok;  tới
                                            <fmt:formatNumber
                                                value="${priceAvaliable[1]}"
                                                type="number"/>&dstrok;
                                        </span>
                                        <div id="slider-range"></div>
                                        <form class="price-slider-amount" id="price-slider">
                                            <div class="label-input">
                                                <input type="text" id="amountFrom" name="priceFrom"
                                                       placeholder="Hoặc nhập giá từ"
                                                       <c:if test="${productFilter.getPriceFrom() > 0}">
                                                           value="<fmt:formatNumber
                                                               value="${productFilter.getPriceFrom()}"
                                                               type="number"/>"
                                                       </c:if>
                                                       > đ
                                                <input type="text" id="amountTo" name="priceTo"
                                                       placeholder="Hoặc nhập giá đến"
                                                       <c:if test="${productFilter.getPriceTo() > 0}">
                                                           value="<fmt:formatNumber
                                                               value="${productFilter.getPriceTo()}"
                                                               type="number"/>"
                                                       </c:if>
                                                       > đ
                                            </div>
                                            <button type="submit">Tìm kiếm</button>
                                        </form>
                                    </div>
                                </div>
                                <c:forEach items="${attributeAndValue}" var="attributeAndValue">
                                    <div class="sidebar-widget shop-sidebar-border mb-40 pt-40">
                                        <h4 class="sidebar-widget-title">${attributeAndValue.getKey().getName()} </h4>
                                        <div class="sidebar-widget-list">
                                            <ul>
                                                <c:forEach items="${attributeAndValue.getValue()}" var="attributeValue">
                                                    <li>
                                                        <div class="sidebar-widget-list-left">
                                                            <label><input type="checkbox"
                                                                          name="${attributeAndValue.getKey().getFilterName()}"
                                                                          value="${attributeValue.getId()}"

                                                                          <c:forEach items="${productFilter.getSpecifiedAttribute()}"
                                                                                     var="spec">
                                                                              ${spec == attributeValue.getId()?'checked':''}
                                                                          </c:forEach>
                                                                          > ${attributeValue.getName()}
                                                            </label>
                                                            <a style="position: absolute; top: 0; right: 0"><span>${attributeValue.getTotalProduct()}</span></a>
                                                        </div>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="parts/footer.jsp" %>
        <%@include file="parts/javascripts.jsp" %>
        <script src="assets/js/plugins/pagination.min.js"></script>
        <script>
                                                                                    $(document).ready(function () {
                                                                                        let currentPage =${productFilter.getCurrentPage()};
                                                                                        let totalPage = ${productFilter.getTotalPage()};
                                                                                        let values = {};
                                                                                        const loadPage = () => {
                                                                                            // if search is not empty then add it to values
                                                                                            const searchText = $("#search-product input").val();
                                                                                            const amountFrom = $("#amountFrom").val().replace(/\D/g, '');
                                                                                            const amountTo = $("#amountTo").val().replace(/\D/g, '');
                                                                                            if (searchText.length > 1) {
                                                                                                values["search"] = searchText;
                                                                                            }
                                                                                            if (amountFrom.length > 1) {
                                                                                                values["priceFrom"] = amountFrom;
                                                                                            }
                                                                                            if (amountTo.length > 1) {
                                                                                                values["priceTo"] = amountTo;
                                                                                            }
                                                                                            // map all the checkboxes
                                                                                            const checkboxes = $("input:checkbox");
                                                                                            // loop over each checkbox
                                                                                            checkboxes.each(function () {
                                                                                                // if the checkbox is checked
                                                                                                if ($(this).is(":checked")) {
                                                                                                    // get name of the checkbox
                                                                                                    const name = $(this).attr("name");
                                                                                                    // get value of the checkbox
                                                                                                    const value = $(this).val();
                                                                                                    // check if name is already in values object and add it if it isn't
                                                                                                    if (name in values) {
                                                                                                        values[name].push(value);
                                                                                                    } else {
                                                                                                        values[name] = [value];
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                            values["sortBy"] = $("#sortBy option:selected").val();
                                                                                            values["limit"] = $("#limit option:selected").val();
                                                                                            let url = window.location.pathname + "?";
                                                                                            for (const key in values) {
                                                                                                url += key + "=" + values[key] + "&";
                                                                                            }
                                                                                            // // remove the last ampersand
                                                                                            url = url.slice(0, -1);
                                                                                            // redirect to the new url
                                                                                            window.location.href = url;

                                                                                        };
                                                                                        // if sortBy is changed then load the page with the new sortBy
                                                                                        $('select').change(function () {
                                                                                            loadPage(values);
                                                                                        });
                                                                                        $("input:checkbox").click(function (e) {
                                                                                            loadPage(values);
                                                                                        });
                                                                                        // prevent #search-product
                                                                                        $('#search-product').validate({
                                                                                            rules: {
                                                                                                search: {
                                                                                                    required: true,
                                                                                                    minlength: 1,
                                                                                                }
                                                                                            },
                                                                                            messages: {
                                                                                                search: {
                                                                                                    required: "Vui lòng nhập từ khóa",
                                                                                                    minlength: "Vui lòng nhật ít nhất 1 ký tự",
                                                                                                }
                                                                                            }

                                                                                        })
                                                                                        $('#price-slider').validate({
                                                                                            rules: {
                                                                                                priceFrom: {
                                                                                                    required: true,
                                                                                                    number: true
                                                                                                },
                                                                                                priceTo: {
                                                                                                    required: true,
                                                                                                    number: true
                                                                                                }
                                                                                            },
                                                                                            messages: {
                                                                                                priceFrom: {
                                                                                                    required: "Vui lòng nhập giá từ",
                                                                                                    number: "Giá phải là số"
                                                                                                },
                                                                                                priceTo: {
                                                                                                    required: "Vui lòng nhập giá đến",
                                                                                                    number: "Giá phải là số"
                                                                                                }
                                                                                            },
                                                                                            submitHandler: function (form) {
                                                                                                values["priceFrom"] = $("#amountFrom").val().replace(/\D/g, '');
                                                                                                values["priceTo"] = $("#amountTo").val().replace(/\D/g, '');
                                                                                                loadPage(values);
                                                                                            }
                                                                                        });
                                                                                        const paginationFooter = $('#pagination');
                                                                                        paginationFooter.pagination({
                                                                                            dataSource: (done) => {
                                                                                                var dataPaging = [];
                                                                                                for (let i = 0; i < totalPage; i++) {
                                                                                                    dataPaging.push(i + 1);
                                                                                                }
                                                                                                done(dataPaging);
                                                                                            },
                                                                                            pageSize: 1,
                                                                                            pageRange: 1,
                                                                                        });
                                                                                        paginationFooter.addHook('afterPreviousOnClick', function () {
                                                                                            currentPage = currentPage > 1 ? currentPage - 1 : 1;
                                                                                            values["page"] = currentPage;
                                                                                            loadPage(values);
                                                                                        });
                                                                                        paginationFooter.addHook('afterPageOnClick', function (e) {
                                                                                            currentPage = e.target.innerText;
                                                                                            values["page"] = currentPage;
                                                                                            loadPage(values);
                                                                                        });
                                                                                        paginationFooter.addHook('afterNextOnClick', function () {
                                                                                            currentPage = currentPage < totalPage ? currentPage + 1 : totalPage;
                                                                                            values["page"] = currentPage;
                                                                                            loadPage(values);
                                                                                        });
                                                                                        paginationFooter.pagination(currentPage);

                                                                                        const sliderrange = $("#slider-range");
                                                                                        const amountFrom = $("#amountFrom");
                                                                                        const amountTo = $("#amountTo");
                                                                                        const formatNumber = (value) => {
                                                                                            return value.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
                                                                                        };
                                                                                        sliderrange.slider({
                                                                                            range: true,
                                                                                            min: ${priceAvaliable[0]},
                                                                                            max: ${priceAvaliable[1]},
                                                                                            values: [0, 1000],
                                                                                            slide: function (event, ui) {
                                                                                                if (ui.values[0] !== ui.values[1]) {
                                                                                                    $('#search-price').removeAttr('hidden');
                                                                                                    // format the values to vietnamese currency format
                                                                                                    amountFrom.val(formatNumber(ui.values[0]));
                                                                                                    amountTo.val(formatNumber(ui.values[1]));
                                                                                                }
                                                                                            },
                                                                                        });
                                                                                        if (amountFrom.val() !== "" && amountTo.val() !== "") {
                                                                                            sliderrange.slider("values", [amountFrom.val().replace(/\D/g, ''), amountTo.val().replace(/\D/g, '')]);
                                                                                        }
                                                                                    });
        </script>
    </body>

</html>