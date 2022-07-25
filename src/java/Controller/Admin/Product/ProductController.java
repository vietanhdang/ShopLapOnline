/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin.Product;

import dal.CategoryDAO;
import dal.ProductDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Utils.FileManagement;
import Utils.GetParameter;
import model.Message;
import model.Product;
import model.ProductDetails;

/**
 *
 * @author vietd
 */
@WebServlet(name = "ProductController", urlPatterns = {"/admin/product"})
public class ProductController extends HttpServlet {

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
        try {
            ProductDAO productDAO = new ProductDAO();
            CategoryDAO categoryDAO = new CategoryDAO();
            request.setAttribute("productStatus", productDAO.getAllProductStatus());
            request.setAttribute("categories", categoryDAO.findAll());
            request.setAttribute("products", productDAO.getAll());
        } catch (Exception e) {
        }
        request.getRequestDispatcher("/admin/product/product.jsp").forward(request, response);
    }

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
        response.setCharacterEncoding("UTF-8"); // set character encoding
        response.setContentType("application/json"); // set content type
        request.setCharacterEncoding("UTF-8"); // set character encoding
        Message message;
        String action = request.getParameter("action");
        Gson gson = new Gson();
        try {
            if (action != null) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                ProductDAO productDAO = new ProductDAO();
                if (action.equalsIgnoreCase("changeStatus")) {
                    int statusId = Integer.parseInt(request.getParameter("statusId"));
                    if (productDAO.changeStatus(productId, statusId) > 0) {
                        message = new Message(true, "Change status success");
                        response.getWriter().write(gson.toJson(message));

                    } else {
                        message = new Message(false, "Change status failed");
                        response.getWriter().write(gson.toJson(message));
                    }
                }
                if (action.equalsIgnoreCase("editQuickProduct")) {
                    String name = request.getParameter("productName");
                    int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                    int price = Integer.parseInt(request.getParameter("unitPrice"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    if (productDAO.updateProductQuick(productId, name, categoryId, price, quantity) > 0) {
                        message = new Message(true, "Cập nhật thành công");
                        response.getWriter().write(gson.toJson(message));
                    } else {
                        message = new Message(false, "Cập nhật sản phẩm thất bại");
                        response.getWriter().write(gson.toJson(message));
                    }
                }
                if (action.equalsIgnoreCase("delete")) {
                    if (productDAO.checkProductInOrderDetail(productId)) {
                        message = new Message(false, "Sản phẩm đã có trong đơn hàng đã đặt, không thể xóa");
                        response.getWriter().write(gson.toJson(message));
                    } else {
                        ProductDetails productDetails = new ProductDAO().findProductDetailById(productId);
                        String folder_product = GetParameter.getFolderImage(request, "product");
                        String folder_product_details = GetParameter.getFolderImage(request, "product-details");
                        Product product = new ProductDAO().findProductById(productId);
                        if (productDAO.deleteProduct(productId) > 0) {
                            try {
                                for (String image : productDetails.getImages()) {
                                    FileManagement.delete(image, folder_product_details);
                                }
                                FileManagement.delete(product.getPreviewImage(), folder_product);
                            } catch (IOException e) {

                            }
                            message = new Message(true, "Xóa sản phẩm thành công");
                            response.getWriter().write(gson.toJson(message));
                        } else {
                            message = new Message(false, "Xóa sản phẩm thất bại");
                            response.getWriter().write(gson.toJson(message));
                        }
                    }
                }

            }
        } catch (Exception e) {
            message = new Message(false, "Change status failed");
            response.getWriter().write(gson.toJson(message));
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
