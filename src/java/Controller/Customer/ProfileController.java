/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Customer;

import Utils.FileManagement;
import Utils.GetParameter;
import Utils.HashGeneratorUtils;
import Utils.Validation;
import com.google.gson.Gson;
import dal.AccountDAO;
import dal.TransactionDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Admin;
import model.Order;
import model.Customer;
import model.Sale;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import model.OrderDetails;

/**
 *
 * @author Benjamin
 */
@WebServlet(name = "account", urlPatterns = {"/account"})
@MultipartConfig
public class ProfileController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // set content type
        response.setCharacterEncoding("UTF-8"); // set character encoding
        String message = ""; // message for notify user
        try {
            GetParameter getParam = new GetParameter(); // class used to get parameters from a request.
            Validation validation = new Validation(); // class used to validate value
            String action = getParam.getField(request, "action", false); // get action param value
            HttpSession session = request.getSession();
            TransactionDAO transactionDAO = new TransactionDAO();
            AccountDAO userDAO = new AccountDAO();
            Account user = (Account) session.getAttribute("ACCOUNTLOGGED"); // get logged account
            request.setAttribute("user", user); // save user info
            if (user.getRole().getRoleID() != 3) {
                action = "show_details";
            }
            request.setAttribute("action", action); // save action
            if (request.getAttribute("accountProfile") == null) { // if request not has account profile info yet
                switch (user.getRole().getRoleID()) { // get account role
                    case 1:
                        Admin admin = userDAO.getAdminByAccount(user); // get admin info
                        if (admin != null) { // if get customer info success
                            request.setAttribute("accountProfile", admin);
                        } else {
                            message = "Không lấy được profile admin";
                        }
                        break;
                    case 2:
                        Sale sale = userDAO.getSaleByAccount(user); // get sale info
                        if (sale != null) { // if get customer info success
                            request.setAttribute("accountProfile", sale);
                        } else {
                            message = "Không lấy được profile sales";
                        }
                        break;
                    case 3:
                        Customer customer = userDAO.getCustomerByAccount(user); // get customer info
                        if (customer != null) { // if get customer info success
                            request.setAttribute("accountProfile", customer);
                        } else {
                            message = "Không lấy được profile customer";
                        }
                        break;
                }
            }
            if (user.getRole().getRoleID() == 3) { // only customer has order history
                ArrayList<Order> orders = new ArrayList<>(); // user order list
                String responeDisplay = ""; // respone notify for actions
                /* if system already has user order list */
                if (request.getAttribute("orders") != null) {
                    orders = (ArrayList<Order>) request.getAttribute("orders");
                } else {
                    /* get user order history in database */
                    orders = transactionDAO.findAllOrderByUser(user);
                }
                /* if user go to order details */
                if (action != null && action.equals("orderDetails") && request.getParameter("orderId") != null) {
                    /* check and get valid order id */
                    int orderId = validation.isInt(request.getParameter("orderId"), "Invalid orderId");
                    int find = transactionDAO.findOrderInList(orders, orderId);
                    /* if the order chosen has been found in user order history */
                    if (find != -1) {
                        /* get order detail and save all order info */
                        request.setAttribute("order", orders.get(find));
                        List<OrderDetails> orderDetails = transactionDAO.getDetailsInOrder(orderId);
                        System.out.println("check order detail   " + orderDetails.isEmpty());
                        request.setAttribute("orderDetails", orderDetails);
                    } else {
                        message = "Không tìm thấy đơn hàng";
                    }
                }
                /* if user want to cancel an order */
                if (action != null && action.equals("cancel_order") && request.getParameter("orderId") != null) {
                    /* check and get valid order id */
                    int orderId = validation.isInt(request.getParameter("orderId"), "Invalid orderId");
                    int find = transactionDAO.findOrderInList(orders, orderId);
                    /* 
                     * if the order chosen has been found in user order history and still be 
                     * able to cancelled
                     */
                    if (find != -1 && orders.get(find).getStatusId() == 1) {
                        /* if insert status cancel for order in database success */
                        if (transactionDAO.addOrderStatus(orderId, 6) != -1) {
                            /* update order status in list */
                            orders.get(find).setStatusId(6);
                            orders.get(find).setStatus("Ðã hủy đơn hàng");
                            responeDisplay = "cancelSuccess";
                        }
                    }
                }
                /* if user confirm already receive an order */
                if (action != null && action.equals("receive_order") && request.getParameter("orderId") != null) {
                    /* check and get valid order id */
                    int orderId = validation.isInt(request.getParameter("orderId"), "Invalid orderId");
                    int find = transactionDAO.findOrderInList(orders, orderId);
                    /* 
                     * if the order chosen has been found in user order history and 
                     * it status show been delivering
                     */
                    if (find != -1 && orders.get(find).getStatusId() == 4) {
                        /* if insert status receive products for order in database success */
                        if (transactionDAO.addOrderStatus(orderId, 5) != -1) {
                            /* update order status in list */
                            orders.get(find).setStatusId(5);
                            orders.get(find).setStatus("Ðã nhận được hàng");
                            responeDisplay = "receiveSuccess";
                        }
                    }
                }
                request.setAttribute("orders", orders); // save order history
                response.setStatus(HttpServletResponse.SC_OK); // set response status OK
                response.getWriter().write(new Gson().toJson(responeDisplay)); // send success notify
            }
        } catch (Exception e) {
            message = e.getMessage();
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); // set response status Error
            response.getWriter().write(e.getMessage()); // send failed notify
        }
        request.setAttribute("message", message);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        request.getRequestDispatcher("web-account-info.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // set content type
        request.setCharacterEncoding("UTF-8"); // set character encoding
        processRequest(request, response);
        AccountDAO userDAO = new AccountDAO();
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("ACCOUNTLOGGED"); // get logged account
        Validation validation = new Validation(); // class used to validate value
        if (request.getParameter("change_profileInfo") != null) { // if user change profile details
            try {
                String fullname = GetParameter.getField(request, "fullname", true);
                String phonenumber = GetParameter.getField(request, "phonenumber", true);
                String address = GetParameter.getField(request, "address", true);
                String dateOfBirthString = GetParameter.getField(request, "dateOfBirth", true);
                String genderString = GetParameter.getField(request, "gender", true);

                fullname = validation.isString(fullname, 5, 50, "Length of name must be  5 - 50 character", true);
                if (!validation.isValidPhoneNumber(phonenumber)) { // if phone number invalid
                    throw new Exception("Phone number must be a number and length is 10->12");
                }
                address = validation.isString(address, 5, 255, "Length of address must be 5 - 255 character", true);
                boolean gender = validation.isBoolean(genderString, "error gender");
                Date dateOfBirth = validation.isDate(dateOfBirthString, "error date of birth");

                request.setAttribute("alert", "Cập nhật thông tin cá nhân thất bại");
                int role = user.getRole().getRoleID(); // get account role
                switch (role) {
                    case 3:
                        Customer customer = new Customer(fullname, address, phonenumber, gender, dateOfBirth, user);
                        if (userDAO.updateCustomerInfo(customer)) { // if update customer info success
                            request.setAttribute("alert", "Cập nhật thông tin cá nhân thành công");
                            request.setAttribute("accountProfile", customer);
                        }
                        break;
                    case 1:
                        Admin admin = new Admin(fullname, address, phonenumber, gender, dateOfBirth, user);
                        if (userDAO.updateAdminInfo(admin)) { // if update admin info success
                            request.setAttribute("alert", "Cập nhật thông tin cá nhân thành công");
                            request.setAttribute("accountProfile", admin);
                        }
                        break;
                    case 2:
                        Sale sale = new Sale(fullname, address, phonenumber, gender, dateOfBirth, user);
                        if (userDAO.updateSaleInfo(sale)) { // if update sale info success
                            request.setAttribute("alert", "Cập nhật thông tin cá nhân thành công");
                            request.setAttribute("accountProfile", sale);
                        }
                        break;
                }
            } catch (Exception e) {
                request.setAttribute("alert", "Cập nhật thông tin cá nhân thất bại");
                System.out.println(e.getMessage());
            }
        } else if (request.getParameter("change_password") != null) { // if user change password
            String current_pwd = request.getParameter("current_pwd");
            String current_to_hash = HashGeneratorUtils.generateSHA256(current_pwd); // encoding version of current password
            String new_pwd = request.getParameter("new_pwd");
            String confirm_pwd = request.getParameter("confirm_pwd");
            /*if user input the same to new password, confirm password and input correct current password*/
            if (new_pwd.equals(confirm_pwd) && user.getPassword().equals(current_to_hash)) {
                user.setPassword(HashGeneratorUtils.generateSHA256(new_pwd)); // encoding new password
                if (userDAO.updatePassword(user) != 0) { // if update password success
                    request.setAttribute("alert", "Cập nhật mật khẩu thành công");
                }
            } else {
                request.setAttribute("alert", "Cập nhật mật khẩu thất bại do bạn nhập sai mật khẩu hiện tại");
            }
        } else if (request.getParameter("change_avatar") != null) {
            String image = changeAvatar(request, response);
            request.setAttribute("alert", "Cập nhật avatar thất bại");
            if (image != null) {
                int role = user.getRole().getRoleID(); // get account role
                switch (role) {
                    case 3:
                        Customer customer = (Customer) request.getAttribute("accountProfile");
                        customer.setImage(image);
                        if (userDAO.updateAvatar(user.getId(), role, image) == role) { // if update customer avatar success
                            request.setAttribute("alert", "Cập nhật avatar thành công. Vui lòng load lại trang");
                            request.setAttribute("accountProfile", customer);
                        }
                        session.setAttribute("CUSTOMER", customer);
                        break;
                    case 1:
                        Admin admin = (Admin) request.getAttribute("accountProfile");
                        admin.setImage(image);
                        if (userDAO.updateAvatar(user.getId(), role, image) == role) { // if update admin avatar success
                            request.setAttribute("alert", "Cập nhật avatar thành công. Vui lòng load lại trang");
                            request.setAttribute("accountProfile", admin);
                        }
                        session.setAttribute("ADMIN", admin);
                        break;
                    case 2:
                        Sale sale = (Sale) request.getAttribute("accountProfile");
                        sale.setImage(image);
                        if (userDAO.updateAvatar(user.getId(), role, image) == role) { // if update sale avatar success
                            request.setAttribute("alert", "Cập nhật avatar thành công. Vui lòng load lại trang");
                            request.setAttribute("accountProfile", sale);
                        }
                        session.setAttribute("SALE", sale);
                        break;
                }
            }
        }
        String action = request.getParameter("action"); // get action from request after been checked in proccessRequest
        TransactionDAO transactionDAO = new TransactionDAO();
        if (action.equals("updateReview")) { // if user want to update comment
            try {
                String orderDetailIdString = GetParameter.getField(request, "orderDetailId", true);
                String userIdString = GetParameter.getField(request, "userId", true);
                String commentString = GetParameter.getField(request, "comment", false);
                String ratingString = GetParameter.getField(request, "rating", true);

                int orderDetailId = validation.isInt(orderDetailIdString, "Invalid orderDetailId"); // get checked order detail id
                int userId = validation.isInt(userIdString, "Invalid userId"); // get checked user id
                String comment = validation.isString(commentString, 0, 50, "Invalid comment", true); // get checked comment
                /* get checked rating */
                int rating = validation.isInt(ratingString, "Invalid rating");
                if (rating > 5 || rating < 1) {
                    throw new Exception("Invalid rating");
                }
                /* if update review success */
                if (transactionDAO.updateUserOrderDetailReview(orderDetailId, userId, comment, rating)) {
                    response.setStatus(HttpServletResponse.SC_OK); // set response status OK
                    response.getWriter().write("updateSuccess"); // send success message
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); // set response status Error
                response.getWriter().write(e.getMessage()); // send failed notify
            }
        }
        if (action.equals("insertReview")) { // if user want to update comment
            try {
                String orderDetailIdString = GetParameter.getField(request, "orderDetailId", true);
                String userIdString = GetParameter.getField(request, "userId", true);
                String commentString = GetParameter.getField(request, "comment", false);
                String ratingString = GetParameter.getField(request, "rating", true);

                int orderDetailId = validation.isInt(orderDetailIdString, "Invalid orderDetailId"); // get checked order detail id
                int userId = validation.isInt(userIdString, "Invalid userId"); // get checked user id
                String comment = validation.isString(commentString, 0, 50, "Invalid comment", true); // get checked comment
                /* get checked rating */
                int rating = validation.isInt(ratingString, "Invalid rating");
                if (rating > 5 || rating < 1) {
                    throw new Exception("Invalid rating " + rating);
                }
                /* if insert review success */
                if (transactionDAO.insertUserOrderDetailReview(orderDetailId, userId, comment, rating)) {
                    response.setStatus(HttpServletResponse.SC_OK); // set response status OK
                    response.getWriter().write(new Gson().toJson("insertSuccess")); // send success message
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); // set response status Error
                response.getWriter().write(e.getMessage()); // send failed notify
            }
        }
        request.getRequestDispatcher("web-account-info.jsp").forward(request, response);
    }

    private String changeAvatar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("multipart/form-data"); // set content type for file data
            String folder_account = GetParameter.getFolderImage(request, "account");
            System.out.println("folder name   " + folder_account);
            Part imagePart = request.getPart("avatarImage");
            String image = FileManagement.uploadFile(imagePart, folder_account);
            response.setContentType("text/html;charset=UTF-8"); // set back content type
            return image;
        } catch (IOException | ServletException e) {
            System.out.println("Fail change avatar!!! \n" + e);
        }
        return null;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
