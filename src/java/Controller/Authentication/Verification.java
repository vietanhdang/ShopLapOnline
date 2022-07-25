/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Authentication;

import Utils.GetParameter;
import Utils.Validation;
import dal.AccountDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Customer;

/**
 *
 * @author Benjamin
 */
@WebServlet(name = "verification", urlPatterns = {"/verification"})
public class Verification extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = "login"; // request url to [Login Controller] if verification successful
        String message = "";
        try {
            HttpSession session = request.getSession();

            String action = GetParameter.getField(request, "action", true);
            String token = GetParameter.getField(request, "token", true);
            Validation validate = new Validation();
            AccountDAO userDAO = new AccountDAO();

            if (action.equals("register")) {
                if (session.getAttribute("verifyCustomer") != null) { // check in session if verifyCustomer's property is still valid
                    Customer customer = (Customer) session.getAttribute("verifyCustomer"); // get customer account
                    if (customer.getAccount().getValidator().trim().equals(token.trim())) {
                        // token in the account is stored in the session. If equal then insert to DB

                        if (userDAO.insertCustomerAccount(customer) != -1) { // if insert success
                            session.removeAttribute("verifyCustomer"); // remove account in session 
                            message = "Xác minh tài khoản thành công. Đăng nhập ngay";
                            url = "login"; // request to Login controller
                        } else {// if insert failed
                            message = "Lỗi đăng ký";
                            url = "register"; // request to Register controller
                        }
                    }
                } else {// check in session if verifyCustomer's property is not valid
                    message = "Link xác thực hết hiệu lực, vui lòng đăng ký lại.";
                    url = "register"; // request to Register controller
                }
            }

            if (action.equals("reset")) {// if action from reset password
                String userId = GetParameter.getField(request, "userId", true);
                int uId = validate.isInt(userId, "Sai token");
                Account account = userDAO.checkValidatorToken(token.trim(), uId);
                if (account != null) { // check in session if resetPassword property is still valid
                    // token in the account is stored in the session. If equal request to reset-password page
                    message = "Đổi mật khẩu của bạn trong vòng 5 phút";
                    session.setAttribute("resetPasswordOn", account);
                    session.setMaxInactiveInterval(300); // Set session time up to 5 minutes to change password on resetPasswordOn
                    url = "reset-password"; // request to reset-password controller
                } else {
                    message = "Sai token";
                    url = "reset-password"; // request to reset-password controller
                }
            }
        } catch (Exception e) {
            message = e.getMessage();
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher(url).forward(request, response);
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

        try {
            processRequest(request, response);

        } catch (Exception ex) {
            Logger.getLogger(Verification.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
