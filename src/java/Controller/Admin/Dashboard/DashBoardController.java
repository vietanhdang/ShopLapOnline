/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin.Dashboard;

import dal.AccountDAO;
import dal.CustomerDAO;
import dal.OrderDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Customer;
import model.Order;

/**
 *
 * @author vietd
 */
public class DashBoardController extends HttpServlet {

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
        ProductDAO pdao = new ProductDAO();
        AccountDAO accountDAO = new AccountDAO();
        OrderDAO orderDAO = new OrderDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        int countProducts = pdao.countAllProduct();
        int countCustomers = accountDAO.countAccount(3);
        int countSales = accountDAO.countAccount(2);
        int countAdmin = accountDAO.countAccount(1);
        int countOfStock = pdao.countOutOfStock(3);
        int totalOrderProccessed = orderDAO.totalOfOrdersProcess();
        ArrayList<Order> orders = orderDAO.getInforOrder();
        ArrayList<Customer> newCustomers = customerDAO.getNewCustomers();
        request.setAttribute("countCustomers", countCustomers);
        request.setAttribute("countAdmin", countAdmin);
        request.setAttribute("countSales", countSales);
        request.setAttribute("countProducts", countProducts);
        request.setAttribute("countOutOfStock", countOfStock);
        request.setAttribute("totalOrderProccessed", totalOrderProccessed);
        request.setAttribute("orders", orders);
        request.setAttribute("newCustomers", newCustomers);
        request.getRequestDispatcher("/admin/dashbord/dashboard.jsp").forward(request, response);
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
