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
import model.Customer;
import model.Role;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Benjamin
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
public class Register extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String message = request.getAttribute("message") != null ? request.getAttribute("message").toString() : "";
        request.setAttribute("message", message);
        request.getRequestDispatcher("web-register.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String message = "";
        try {
            GetParameter getParam = new GetParameter(); // class used to get parameters from a request.
            AccountDAO dao = new AccountDAO();

            String username = getParam.getField(request, "email", true); // if cannot get field email then throw exception.

            Account account = dao.findByUsername(username); // find account username (is email).
            if (account != null) {
                /* Account already in use */
                message = "Tài khoản đã được sử dụng";
            } else {
                /* Account does not exist */
                String fullname = GetParameter.getField(request, "fullname", true);
                String password = GetParameter.getField(request, "password", true);
                String phonenumber = GetParameter.getField(request, "phonenumber", true);
                String address = GetParameter.getField(request, "address", true);
                String dateOfBirth = GetParameter.getField(request, "dateOfBirth", true);
                String gender = GetParameter.getField(request, "gender", true);
                Validation validation = new Validation();
                // validation input field
                Customer customer = new Customer();
                customer.setFullname(validation.isString(fullname, 5, 50, "Độ dài của tên phải lớn hơn 5 và nhỏ hơn 50 kí tự", true));
                if (!validation.isValidPhoneNumber(phonenumber)) {
                    throw new Exception("Số điện thoại phải là số và có độ dài 10->12 kí tự");
                }
                customer.setPhone(phonenumber);
                customer.setGender(validation.isBoolean(gender, "Giới tính sai"));
                customer.setDateOfBirth(validation.isDate(dateOfBirth, "Sai ngày sinh"));
                customer.setImage("");
                customer.setAddress(validation.isString(address, 5, 255, "Độ dài của địa chỉ phải lớn hơn 5 và nhỏ hơn 255 kí tự", true));

                EmailSender mail = new EmailSender(); // class used to send emails to customer register
                HttpSession session = request.getSession();
                String token = RandomStringUtils.randomAlphanumeric(64); // create random tokens consisting of letters and numbers (include 40 character)

                account = new Account(username, HashGeneratorUtils.generateSHA256(password), new Role(3), true); // set username, password, role is customer and status true
                account.setValidator(token); // save token to account to use account verification session
                customer.setAccount(account);

                String body = "Xin chào " + username + ". Bạn vừa mới đăng ký tài khoản tại SHOPLAP, "
                        + "vui lòng xác minh tài khoản của bạn thông qua liên kết sau: http://localhost:8080/se1621-net_g5_onlineshop/verification?action=register&token=" + token + " .Link này chỉ khả dụng trong vòng 5 phút";

                if (mail.sendMailConfirm(username, "Please confirm your account", body)) { // if send message to gmail of customer success
                    session.setAttribute("verifyCustomer", customer); // initialize the verifyCustomer property on the session to check if the user 
                    // doesn't click on the link for 5 minutes, it will expire
                    session.setMaxInactiveInterval(300); // set max time is 300 seconds is 5 minutes
                    message = "Vui lòng mở gmail để xác minh tài khoản của bạn. Link xác thực trong vòng 5 phút";
                } else {
                    message = "Lỗi server không gửi được mail. Thử lại lần sau";
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            message = "Đã có lỗi xảy ra (Kiểm tra lại các dữ liệu nhập)";
        }
        request.setAttribute("message", message);
        processRequest(request, response);
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
