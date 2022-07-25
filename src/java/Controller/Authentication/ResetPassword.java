/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Authentication;

import Utils.HashGeneratorUtils;
import Utils.EmailSender;
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
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author vietd
 */
@WebServlet(name = "ResetPassword", urlPatterns = {"/reset-password"})
public class ResetPassword extends HttpServlet {

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
        String url = "web-forgot.jsp"; // request to web-forgot page
        String message = request.getAttribute("message") != null ? request.getAttribute("message").toString() : "Vui lòng nhập tài khoản của bạn";
        try {
            GetParameter getParameter = new GetParameter();
            String btn = GetParameter.getField(request, "btn", false); // get input name btn button from jsp

            if (btn != null) { // if click button name is btn
                Validation validation = new Validation();
                AccountDAO accountDAO = new AccountDAO();
                Account account = new Account();
                HttpSession session = request.getSession();
                if (btn.equals("reset")) {
                    /* if the button is reset, send an email and send a link to verification to verify if the user forgot the password.*/
                    String email = GetParameter.getField(request, "email", true);
                    if (!validation.isValidEmail(email)) {
                        /* check valid input email */
                        throw new Exception("Email không hợp lệ");
                    }
                    account = accountDAO.findByUsername(email); // find account by username is email
                    if (account != null) {
                        /* if account is register */
                        String token = RandomStringUtils.randomAlphanumeric(64); // create random token include 40 character (numbers and letters)
                        account.setValidator(token);  // save token to account to use account verification session
                        EmailSender mail = new EmailSender(); // class used to send emails to email of customer
                        String notice = "Xin chào " + email + ". Bạn vừa mới gửi yêu cầu quên mật khẩu , Vui lòng không cung cấp link này cho bất cứ ai nếu cung cấp thì họ sẽ có thể đổi được mật khẩu của bạn. "
                                + "Vui lòng nhấn vào link dưới đây để đổi mật khẩu : http://localhost:8080/se1621-net_g5_onlineshop/verification?action=reset&token=" + token + "&userId=" + account.getId();
                        if (mail.sendMailConfirm(email, "PLEASE CONFIRM CHANGE PASSWORD", notice)) {
                            accountDAO.updateValidator(token, account.getId());
                            /* if send message to gmail of customer success*/
                            message = "Vui lòng mở gmail để đổi mật khẩu tài khoản của bạn";
                        } else {
                            /* if send message to gmail of customer failed*/
                            message = "Lỗi server không gửi được mail. Thử lại lần sau";
                        }
                    } else {
                        /*if account is not register*/
                        message = "Tài khoản không tồn tại!";
                    }
                }
                if (btn.equals("changePassword")) {
                    /* if the button is pressed to change the password, check if the verification party returns an account property in session to change it */
                    account = (Account) session.getAttribute("resetPasswordOn");
                    if (account != null) {
                        /* if Account object exist in session */
                        String password = GetParameter.getField(request, "password", true);
                        account.setPassword(HashGeneratorUtils.generateSHA256(password)); // hash password to sha256
                        if (accountDAO.updatePassword(account) != -1) {
                            /* update password if return row affected != -1*/
                            session.removeAttribute("resetPasswordOn");
                            accountDAO.updateValidator("", account.getId());
                            message = "Đổi mật khẩu thành công. Đăng nhập ngay";
                            request.setAttribute("message", message);
                            request.getRequestDispatcher("web-login.jsp").forward(request, response);
                            return;
                        } else {
                            /* update password if return row affected ==0 or something error*/
                            message = "Đổi mật khẩu thất bại. Thử lại!";
                        }
                    } else {
                        /* if Account object not exist in session */
                        message = "Link xác thực hết hiệu lực, vui lòng thực hiện lại.";
                    }
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
            Logger.getLogger(ResetPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ResetPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
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
