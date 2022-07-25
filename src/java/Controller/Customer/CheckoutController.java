/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Customer;

import Controller.Cart.Item;
import Controller.Cart.Cart;
import Utils.EmailSender;
import Utils.GetParameter;
import Utils.Validation;
import dal.CustomerDAO;
import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Customer;
import model.Order;
import model.OrderDetails;

/**
 *
 * @author hoang
 */
public class CheckoutController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("CART");
        if (session.getAttribute("CART") == null || cart.getItems().isEmpty()) { // check session of cart if this session ==  null out.println alert in jsp
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Giỏ hàng của bạn đang trống. Xin mời bạn mua hàng');");
            out.println("location='products';"); //  redirect to products Page
            out.println("alert('Giỏ hàng của bạn đang trống');");
            out.println("</script>");
        } else {
            CustomerDAO customerDAO = new CustomerDAO(); // call constructor CustomerDao
            Account account = (Account) request.getSession().getAttribute("ACCOUNTLOGGED"); // call account of object Account and assign session of ACCOUNTLOGGED  
            Customer customer = customerDAO.GetInforCustomerForScreenCheckout(account.getId()); // Get infor customer by ID of account login and assign customer
            request.setAttribute("customer", customer);
            request.getRequestDispatcher("web-checkout.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        OrderDAO orderDAO = new OrderDAO();
        HttpSession session = request.getSession();
        GetParameter getParameter = new GetParameter();
        Validation validation = new Validation();
        try {
            String fullName = getParameter.getField(request, "fullname", true); // get value fullname from input field full name
            String address = getParameter.getField(request, "address", true); // get value address from input field address
            String phone = getParameter.getField(request, "phone", true);// get value phone from input field phone
            boolean checkPhone = validation.isValidPhoneNumber(phone);
            if (!checkPhone) {
                throw new Exception("Số điện thoại không đúng định dạng");
            }
            String email = getParameter.getField(request, "email", true);
            boolean checkEmail = validation.isValidEmail(email);
            if (!checkEmail) {
                throw new Exception("Email không đúng định dạng");
            }
            String noteOrder = request.getParameter("noteorder");// get value note order from input field note order
            int payMethod = Integer.parseInt(request.getParameter("payment_method")); // get value pay method from input radio field pay method
            Cart cart = (Cart) session.getAttribute("CART"); // get cart from session cart
            List<OrderDetails> orderDetails = new ArrayList<>(); // initialization list order details
            StringBuilder body = new StringBuilder();
            long subtotal = 0;

            body.append("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    <!-- CSS only -->\n"
                    + "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css\"\n"
                    + "        integrity=\"sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn\" crossorigin=\"anonymous\">\n"
                    + "    <title>Document</title>\n"
                    + "<style>\n"
                    + "table, th, td {\n"
                    + "  border:1px solid black;\n"
                    + "}\n"
                    + "</style>"
                    + "</head>\n"
                    + "\n"
                    + "<body>\n"
                    + "<h3>Details of products that you have purchased:</h3>"
                    + "    <table class=\"table table-striped table-dark\">\n"
                    + "        <thead>\n"
                    + "            <tr>\n"
                    + "                <th scope=\"col\">ProductID</th>\n"
                    + "                <th scope=\"col\">ProductName</th>\n"
                    + "                <th scope=\"col\">Quantity</th>\n"
                    + "                <th scope=\"col\">Total</th>\n"
                    + "            </tr>\n"
                    + "        </thead>\n"
                    + "        <tbody>");
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat vn = NumberFormat.getInstance(localeVN);
            for (Item item : cart.getItems()) {
                body.append("<tr>\n"
                        + "                <th scope=\"row\">" + item.getProduct().getId() + "</th>\n"
                        + "                <td>" + item.getProduct().getProductName() + "</td>\n"
                        + "                <td>" + item.getQuantity() + "</td>\n"
                        + "                <td>" + vn.format(item.getQuantity() * item.getProduct().getUnitPrice()) + " VNĐ</td>\n"
                        + "</tr>"
                );
                orderDetails.add(new OrderDetails(item.getProduct().getId(), item.getQuantity(), item.getPrice()));
                subtotal += item.getQuantity() * item.getProduct().getUnitPrice();
            }
            body.append("  </tbody>\n"
                    + "    </table>\n"
                    + "</body>\n"
                    + "\n"
                    + "</html>");
            Account account = (Account) request.getSession().getAttribute("ACCOUNTLOGGED");
            Order order = new Order(fullName, address, phone, email, noteOrder, cart.getOrderTotal(), payMethod);
            if (orderDAO.orderProduct(order, orderDetails, account.getId())) {
                session.removeAttribute("CART");
                EmailSender emailSender = new EmailSender();
                body.append("<h3>SubTotal: ").append(vn.format(subtotal)).append(" VNĐ</h3>");
                emailSender.sendMailConfirm(account.getUsername(), "Thank you for shopping at ShopLap", body.toString());
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Bạn đã đặt hàng thành công');");
                out.println("location='account';");
                out.println("</script>");
            } else {
                throw new Exception("Đặt hàng không thành công");
            }
        } catch (Exception e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('" + e.getMessage() + "');");
            out.println("location='Checkout';");
            out.println("</script>");
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
