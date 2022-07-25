/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Customer;

import Utils.GetParameter;
import Utils.Validation;
import com.google.gson.Gson;
import dal.FavouriteDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Favourite;
import model.Product;

/**
 *
 * @author pc
 */
public class FavouriteController extends HttpServlet {

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
        Wishlist wishlist = new Wishlist();
        HttpSession session = request.getSession();
        try {
            Account user = (Account) session.getAttribute("ACCOUNTLOGGED"); // get logged account
            if (user.getRole().getRoleID() == 3) { // if user is a customer
                if (session.getAttribute("WISHLIST") != null) { // if system already has user wishlist
                    wishlist = (Wishlist) session.getAttribute("WISHLIST");
                } else {
                    /* get user wishlist from database */
                    wishlist.setFavourites(new FavouriteDAO().getFavouritesByUserId(user.getId()));
                }
            }
        } catch (Exception e) {
        }
        session.setAttribute("WISHLIST", wishlist); // save wishlist
        request.getRequestDispatcher("web-wishlist.jsp").forward(request, response);
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
        response.setContentType("application/json"); // set content type
        response.setCharacterEncoding("UTF-8"); // set character encoding
        HttpSession session = request.getSession();
        Wishlist wishlist = new Wishlist(); // user wishlist
        /* if system already has user wishlist */
        if (session.getAttribute("WISHLIST") != null) {
            wishlist = (Wishlist) session.getAttribute("WISHLIST");
        }
        GetParameter getParameter = new GetParameter(); // class used to get parameters from a request.
        try {
            String action = getParameter.getField(request, "action", true); // get action param value
            String productId = getParameter.getField(request, "productId", true); // check empty id of chosen product
            int pId = new Validation().isInt(productId, "Sản phẩm không tồn tại"); // check id is valid number
            Product product = new ProductDAO().findProductById(pId); // get product by id
            /* if cannot found product by id */
            if (product == null) {
                throw new Exception("Sản phẩm không tồn tại");
            } else if (session.getAttribute("ACCOUNTLOGGED") != null) { // if user account logged
                Account user = (Account) session.getAttribute("ACCOUNTLOGGED"); // get logged account
                if (action.equals("addToWishlist")) { // if user click add a product to wishlist
                    /* New favourite with user id and product info */
                    Favourite favourite = new Favourite();
                    favourite.setUserId(user.getId());
                    favourite.setProductId(pId);
                    favourite.setProductName(product.getProductName());
                    favourite.setProductImage(product.getPreviewImage());
                    favourite.setUnitPrice(product.getUnitPrice());
                    favourite.setUnitInStock(1);
                    wishlist.addFavourite(favourite); // add favourite to wishlist
                }
                if (action.equals("deleteProductInWishlist")) { // if user choose a product deleted from wishlist
                    wishlist.deleteFavourite(user.getId(), pId); // delete favourite from wishlist
                }
                session.setAttribute("WISHLIST", wishlist); // save wishlist
                response.setStatus(HttpServletResponse.SC_OK); // set response status OK
                response.getWriter().write(new Gson().toJson(wishlist.getFavourites().size())); // send wishlist size
            }
        } catch (Exception e) {
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
