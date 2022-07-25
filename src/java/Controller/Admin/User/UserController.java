package Controller.Admin.User;

import dal.CustomerDAO;
import dal.SaleDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Customer;
import model.Sale;

public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Sale> listSales = new SaleDAO().getAllSales();
        ArrayList<Customer> listCustomers = new CustomerDAO().getAllCustomers();
        request.setAttribute("listSales", listSales);
        request.setAttribute("listCustomers", listCustomers);
        request.getRequestDispatcher("/admin/user/user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
