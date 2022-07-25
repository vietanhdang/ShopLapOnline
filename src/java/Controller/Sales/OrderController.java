/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Sales;

import Utils.GetParameter;
import Utils.Validation;
import com.google.gson.Gson;
import dal.OrderDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Order;
import model.OrderFilter;

/**
 *
 * @author Admin
 */
//@WebServlet(name = "OrderController", urlPatterns = {"/orders"})
public class OrderController extends HttpServlet {
    private OrderFilter getOrderFilter(HttpServletRequest request) {
        OrderFilter orderFilter = new OrderFilter(); // create a new order filter
        try {
            Validation validation = new Validation(); // create a new validation
            String statusString = GetParameter.getField(request, "status", false); // get order status
            String sortBy = GetParameter.getField(request, "sortBy", false); // get sort by
            String search = GetParameter.getField(request, "search", false); // get search
            String page = GetParameter.getField(request, "page", false); // get page
            String sortSide = GetParameter.getField(request, "sortSide", false); // get sort side ASC/DESC
            if (statusString != null) {
                boolean[] selectStatus = {false, false, false, false, false}; // default 5 order status
                String[] statuses = statusString.trim().split(",");
                for (String status : statuses) {
                    selectStatus[validation.isInt(status.trim(), "Invalid select status")] = true; // change status if it selected
                }
                orderFilter.setSelectStatus(selectStatus); // save select status
            }
            if (search != null && search.length() > 0) {
                /* if search is not null and length is greater than 0 */
                orderFilter.setSearch(search); // set search
            }
            if (page != null) {
                /* if page is not null */
                orderFilter.setCurrentPage(validation.isInt(page, "Error parse page value to number")); // set current page
            }
            if (orderFilter.setSortBy(sortBy)) { // if sort by valid
                orderFilter.setIsAsc(validation.isBoolean(sortSide, "ASC / DESC ?"));
            }
        } catch (Exception e) {
        }
        return orderFilter;
    }
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
        HttpSession session = request.getSession();
        try {
            Account user = (Account) session.getAttribute("ACCOUNTLOGGED"); // get logged account
            if (user.getRole().getRoleID() == 2) { // check logged account is sale
                OrderFilter orderFilter = getOrderFilter(request); // get filter for order list in page
                OrderDAO orderDAO = new OrderDAO();
                ArrayList<Order> orders = orderDAO.getAllNonCancelOrdersForSale(user.getId(), orderFilter); // get non-cancel order list executed by this sale or non-executed
                orderFilter.setTotalOrder(orders.get(orders.size() - 1).getStatusId()); // set number of filter orders by get fake temp order status id
                orderFilter.setTotalPage(orders.get(orders.size() - 1).getSaleId()); // set filter orders total page by get fake temp order sale id
                orders.remove(orders.size() - 1); // delete fake temp order after get it send info
                /* set order filter's order status num */
                for (Order order : orders) {
                    orderFilter.addStatusNum(order.getStatusId(), 1);
                }
                request.setAttribute("orderFilter", orderFilter);
                request.setAttribute("orders", orders);
            }
        } catch (Exception e) {
        }
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
        request.getRequestDispatcher("orders.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8"); // set content type
        request.setCharacterEncoding("UTF-8"); // set character encoding
        if (request.getParameter("cancelOrderId") != null) { // if sale cancel order
            executeOrder(request, response, "cancelOrderId", 3); // set order status to cancel
        }
        if (request.getParameter("acceptOrderId") != null) { // if sale accept order
            executeOrder(request, response, "acceptOrderId", 2); // set order status to accept
        }
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }
    
    /* Sale execute order with actions to change order status */
    private void executeOrder(HttpServletRequest request, HttpServletResponse response, String parameterOrderId, int executeStatus) throws IOException {
        HttpSession session = request.getSession();
        Validation validation = new Validation();
        OrderDAO orderDAO = new OrderDAO();
        try {
            String orderIdString = GetParameter.getField(request, parameterOrderId, true); // get executed order id
            String orderSaleIdString = GetParameter.getField(request, "orderSaleId", true); // get sale id execute order
            int orderId = validation.isInt(orderIdString, "Invalid order id"); // check order id valid
            int orderSaleId = validation.isInt(orderSaleIdString, "Not has sale execute"); // check sale id execute order valid
            Account user = (Account) session.getAttribute("ACCOUNTLOGGED"); // get logged account
            if (orderDAO.addOrderStatusBySale(orderId, orderSaleId, executeStatus, user.getId())) { // if update order status success
                response.setStatus(HttpServletResponse.SC_OK); // set response status OK
                response.getWriter().write(new Gson().toJson("Accept success")); // send success notify
            }
        } catch (Exception e) {
            System.out.println("accept order fail   " + e);
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); // set response status Error
            response.getWriter().write(e.getMessage()); // send failed notify
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
