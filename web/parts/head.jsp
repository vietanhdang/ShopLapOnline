<%-- 
    Document   : head
    Created on : Mar 2, 2022, 1:24:19 AM
    Author     : Benjamin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!-- All CSS is here
============================================ --><meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="robots" content="noindex, follow"/>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/vendor/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/vendor/signericafat.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/vendor/cerebrisans.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/vendor/simple-line-icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/vendor/elegant.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/vendor/linear-icon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/plugins/nice-select.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/plugins/easyzoom.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/plugins/slick.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/plugins/animate.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/plugins/magnific-popup.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/plugins/jquery-ui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/images/favicon.png">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
      integrity="sha512-Fo3rlrZj/k7ujTnHg4CGR2D7kSs0v4LLanw2qksYuRlEzO+tcaEPQogQ0KaoGN26/zrn20ImR1DfuLWnOo7aBA=="
      crossorigin="anonymous" referrerpolicy="no-referrer"/>
<style>
    .swal-modal {
        opacity: 0;
        pointer-events: none;
        background-color: #fff;
        text-align: center;
        border-radius: .357rem;
        position: static;
        margin: 20px auto;
        display: inline-block;
        vertical-align: middle;
        -webkit-transform: scale(1);
        transform: scale(1);
        -webkit-transform-origin: 50% 50%;
        transform-origin: 50% 50%;
        z-index: 10001;
        /* border-top: 5px solid rgb(0, 28, 64); */
        transition: opacity .2s, -webkit-transform .3s;
        transition: transform .3s, opacity .2s;
        transition: transform .3s, opacity .2s, -webkit-transform .3s;
    }

    .swal-overlay {

        background-color: rgba(0, 0, 0, 0.781);

    }

    .swal-text {
        color: black;
        text-align: center;
        font-weight: 500;
        font-size: 15px !important;
    }

    .swal-button {
        background: rgb(191 255 184) !important;
        border-radius: .357rem;
        box-shadow: none !important;
        color: rgb(13 136 0) !important;
        border: none;
    }

    .swal-button--cancel {
        color: #d21010 !important;
        border: none;
        background-color: #ffbdbd !important;
    }

    .swal-icon--success__hide-corners {
        width: 5px;
        height: 90px;
        background-color: #fff;
        padding: 1px;
        position: absolute;
        left: 28px;
        top: 8px;
        z-index: 1;
        -webkit-transform: rotate(
            -45deg
            );
        transform: rotate(
            -45deg
            );
    }

    .swal-icon--success__line {
        background-color: rgb(78 208 5);
        height: 2px;
    }

    .swal-icon--success__ring {
        border: 2px solid rgb(78 208 5);
    }

    .swal-icon--success__ring {
        border: 2px solid rgb(78 208 5);
    }

    .swal-button:not([disabled]):hover {
        background: transparent;
        color: white;
    }

    .swal-title {
        color: rgb(0, 0, 0);
    }

    .swal-icon {
        width: 80px;
        height: 80px;
        border-width: 2px !important;
        border-style: solid;
        border-radius: 50%;
        padding: 0;
        position: relative;
        box-sizing: content-box;
        margin: 20px auto;
    }

    .swal-icon--error {
        border-color: #d20303 !important;
        -webkit-animation: animateErrorIcon .5s;
        animation: animateErrorIcon .5s;
    }

    .swal-icon--error__line {
        position: absolute;
        height: 2px !important;
        width: 47px;
        background-color: #d20303 !important;
        display: block;
        top: 37px;
        border-radius: .357rem;
    }

    .swal-icon--info {
        border-color: rgb(0, 28, 64) !important;
    }

    .swal-icon--info:after, .swal-icon--info:before {
        background-color: rgb(0, 28, 64);
    }

    .swal-modal {
        border-radius: .357rem;
    }
    #preloader {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #f8f9fa;
        /* change if the mask should have another color then white */
        z-index: 99999;
        text-align: center;
        /* makes sure it stays on top */
    }

    #preloader img {
        margin-top: 100px;
    }

    @media (max-width: 767px) {
        #preloader img {
            margin-bottom: 50px;
        }
    }

    #status {
        position: relative;
        width: 70px;
        height: 70px;
        top: 35%;
        margin: 0 auto;
        right: 35px;
    }

    #status span {
        position: absolute;
        border-radius: 999px;
    }

    #status span:nth-child(1) {
        border: 5px solid #dcf836;
        border-top: 5px solid transparent;
        width: 70px;
        height: 70px;
        animation: spin1 2s infinite linear;
    }

    #status span:nth-child(2) {
        border: 5px solid #dd003f;
        border-top: 5px solid transparent;
        top: 20px;
        left: 20px;
        width: 30px;
        height: 30px;
        animation: spin2 1s infinite linear;
        margin-left: 35px;
    }

    @keyframes spin1 {
        0% {
            transform: rotate(360deg);
            opacity: 1;
        }

        50% {
            transform: rotate(180deg);
            opacity: 0.75;
        }

        100% {
            transform: rotate(0deg);
            opacity: 1;
        }
    }

    @keyframes spin2 {
        0% {
            transform: rotate(0deg);
            opacity: 0.75;
        }

        50% {
            transform: rotate(180deg);
            opacity: 1;
        }

        100% {
            transform: rotate(360deg);
            opacity: 0.75;
        }
    }
</style>


