/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Cart;

import Utils.GetParameter;
import Utils.Validation;
import com.google.gson.Gson;
import dal.ProductDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Product;

/**
 * @author vietd
 */
public class CartController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("web-cart.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json"); // set content type
        response.setCharacterEncoding("UTF-8"); // set character encoding
        HttpSession session = request.getSession(); // get session
        Cart cart = new Cart(); // create a new cart
        if (session.getAttribute("CART") != null) {
            /* if cart in session is not null */
            cart = (Cart) session.getAttribute("CART");
        }
        try {
            String action = GetParameter.getField(request, "action", true); // get action
            if (action.equals("getCart")) {
                /* if action is get cart */
                if (!cart.getItems().isEmpty()) {
                    /* if cart is not empty */
                    response.setStatus(HttpServletResponse.SC_OK); // set status
                    response.getWriter().write(new Gson().toJson(cart)); // write cart to response
                } else {
                    /* if cart is empty */
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND); // set status
                }
                return; // prevent from executing other code
            }
            if (action.equals("deleteAll")) {
                cart.deleteAllCartItem();
                response.setStatus(HttpServletResponse.SC_OK); // set status
                response.getWriter().write(new Gson().toJson(cart)); // write cart to response
                return;
            }
            String productId = GetParameter.getField(request, "productId", true); // get product id
            int pId = new Validation().isInt(productId, "Sản phẩm không tồn tại"); // check product id
            Product product = new ProductDAO().findProductById(pId); // get product
            if (product != null) {
                /* if product is not null */
                if (action.equals("addToCart")) {
                    /* if action is add to cart */
                    cart.addCartItem(product);
                }
                if (action.equals("deleteProduct")) {
                    /* if action is delete product */
                    cart.deleteCartItem(product);
                }
                if (action.equals("updateItemPlus")) {
                    /* if action is update item plus */
                    cart.updateCartItem(product, 1, true, false);
                }
                if (action.equals("updateItemMinus")) {
                    /* if action is update item minus */
                    cart.updateCartItem(product, 1, false, true);
                }
                if (action.equals("updateQuantity")) {
                    /* if action is update quantity */
                    String qty = GetParameter.getField(request, "quantity", true); // get quantity
                    int quantity = new Validation().isInt(qty, "Số lượng phải là một số"); // check quantity
                    if (quantity > 0) {
                        /* if quantity is greater than 0 */
                        cart.updateCartItem(product, quantity, false, false); // update cart item
                    } else {
                        /* if quantity is less than 0 */
                        throw new Exception("Số lượng phải là một số nguyên dương");
                    }
                }
                session.setAttribute("CART", cart); // set cart to session
                response.setStatus(HttpServletResponse.SC_OK); // set status
                response.getWriter().write(new Gson().toJson(cart)); // write cart to response
            } else {
                /* if product is null */
                throw new Exception("Sản phẩm không tồn tại");
            }
        } catch (Exception e) {
            /* if exception occurs */
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); // set status
            response.getWriter().write(e.getMessage()); // write error message to response
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
