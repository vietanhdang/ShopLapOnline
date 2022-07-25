package Controller.Authentication;

import Controller.Customer.Wishlist;
import Utils.HashGeneratorUtils;
import Utils.GetParameter;
import dal.AccountDAO;
import dal.FavouriteDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import org.apache.commons.lang3.RandomStringUtils;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String returnUrl = request.getParameter("returnUrl");
        request.setAttribute("returnUrl", returnUrl);
        request.getRequestDispatcher("web-login.jsp").forward(request, response);//forward to jsp page
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        try {
            String returnUrl = request.getParameter("returnUrl");
            AccountDAO accountDAO = new AccountDAO();
            String email = GetParameter.getField(request, "email", true);
            Account account = accountDAO.findByUsername(email.trim());
            if (account == null) {//email is not exist in database
                message = "tài khoản không tồn tại";
            } else {
                if (!account.isStatus()) {
                    message = "tài khoản đang bị tạm khóa! vui lòng liên hệ admin để biết thêm chi tiết";
                } else {
                    String password = GetParameter.getField(request, "password", true);
                    if (!HashGeneratorUtils.generateSHA256(password).equals(account.getPassword())) {//wrong passwood
                        message = "Sai mật khẩu";
                        request.setAttribute("email", email);
                    } else {
                        boolean remember = GetParameter.getField(request, "remember", false) != null;
                        if (remember) { //user want to remember
                            String randomAlphanumeric = RandomStringUtils.randomAlphanumeric(64);
                            String hashedValidator = HashGeneratorUtils.generateSHA256(randomAlphanumeric);
                            Cookie cookieSelector = new Cookie("laptop-username", account.getUsername());
                            cookieSelector.setMaxAge(604800); //set cookie for 1 week
                            Cookie cookieValidator = new Cookie("laptop-user-validator", randomAlphanumeric);
                            cookieValidator.setMaxAge(604800);
                            response.addCookie(cookieSelector);
                            response.addCookie(cookieValidator);
                            accountDAO.updateAccountCookie(account.getId(), hashedValidator);//set cookie for account
                        }
                        request.getSession().setAttribute("ACCOUNTLOGGED", account);
                        /* if user has role is customer */
                        if (account.getRole().getRoleID() == 3) {
                            /* get customer's wishlist from database */
                            request.getSession().setAttribute("WISHLIST", new Wishlist(
                                    new FavouriteDAO().getFavouritesByUserId(account.getId())));
                        }
                        if (returnUrl != null && !returnUrl.isEmpty()) {
                            response.sendRedirect(request.getContextPath() + returnUrl);
                        } else {
                            response.sendRedirect("home");
                        }
                        return;
                    }
                }
            }
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("web-login.jsp").forward(request, response);//forward to jsp page

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
