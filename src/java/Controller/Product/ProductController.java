/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Product;

import Utils.GetParameter;
import Utils.Validation;
import dal.ProductDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProductList;
import model.SpecifiedAttribute;
import model.SpecifiedAttributeValue;

/**
 * @author vietd
 */
@WebServlet(name = "Product", urlPatterns = {"/products"})
public class ProductController extends HttpServlet {

    protected ProductFilter processQueryParam(HttpServletRequest request) throws Exception {

        ProductFilter productFilter = new ProductFilter(); // create a new product filter
        Validation validation = new Validation(); // create a new validation
        String brandId = GetParameter.getField(request, "brandId", false); // get brand id
        String categoryId = GetParameter.getField(request, "categoryId", false); // get category id
        String sortBy = GetParameter.getField(request, "sortBy", false); // get sort by
        String search = GetParameter.getField(request, "search", false); // get search
        String limit = GetParameter.getField(request, "limit", false); // get limit
        String page = GetParameter.getField(request, "page", false); // get page
        String priceFrom = GetParameter.getField(request, "priceFrom", false); // get price from
        String priceTo = GetParameter.getField(request, "priceTo", false); // get price to
        int numOfAttribute = 0; // number of attribute
        if (priceFrom != null && priceTo != null) {
            /* if price from and price to is not null */
            int priceFm = validation.isInt(priceFrom, "Error pasrse price from"); // check price from is int
            int priceT = validation.isInt(priceTo, "Error pasrse price to"); // check price to is int
            if (priceFm <= priceT) {
                /* if price from is less than or equal to price to */
                productFilter.setPriceFrom(priceFm); // set price from
                productFilter.setPriceTo(priceT); // set price to
            }
        }
        if (search != null && search.length() > 1) {
            /* if search is not null and length is greater than 1 */
            productFilter.setSearch(search); // set search
        }
        if (limit != null) {
            /* if limit is not null */
            productFilter.setRecordPerPage(validation.isInt(limit, "Error pasrse limit value to number")); // set record per page
        }
        if (page != null) {
            /* if page is not null */
            productFilter.setCurrentPage(validation.isInt(page, "Error pasrse page value to number")); // set current page
        }
        if (categoryId != null) {
            /* if category id is not null */
            productFilter.setCategoryId(categoryId); // set category id
        }
        if (brandId != null) {
            /* if brand id is not null */
            productFilter.setBrandId(brandId); // set brand id
        }
        StringBuilder sb = new StringBuilder();

        for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            if (entry.getKey().contains("filter_laptop")) {
                numOfAttribute += 1;
                sb.append(entry.getValue()[0]).append(",");
            }
        }
        // check if last and first character is comma
        if (sb.length() > 0) {
            /* if length is greater than 0  then remove last character */
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        if (sortBy != null) {
            /* if sort by is not null */
            if (sortBy.equals("oldest")) {
                /* if sort by is oldest */
                productFilter.setIsAsc(true);
            }
            if (sortBy.equals("priceAsc") || sortBy.equals("priceDesc")) {
                /* if sort by is price ascending or descending */
                productFilter.setSortBy("price"); // set sort by
                if (sortBy.equals("priceAsc")) {
                    /* if sort by is price ascending */
                    productFilter.setIsAsc(true);
                }
            }
        }
        productFilter.setSpecifiedAttribute(sb.toString()); // set specified attribute
        productFilter.setNumOfAttributeFitler(numOfAttribute); // set number of attribute filter
        return productFilter; // return product filter
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
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
        ProductDAO productDAO = new ProductDAO(); // create a new product dao
        ProductFilter productFilter = null; // create a new product filter
        String message = ""; // create a new message
        try {
            productFilter = processQueryParam(request); // process query param
        } catch (Exception ex) {
            message = ex.getMessage(); // set message
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex); // log error
        }
        Map<SpecifiedAttribute, List<SpecifiedAttributeValue>> attributeAndValue = productDAO.findAllSpecifiedAttributeAndValue(); // create a new map
        ProductList productList = new ProductList(); // create a new product list
        try {
            productList = productDAO.findProductByFilter(productFilter); // get product by filter
            productFilter.setTotalRecord(productList.getTotalProduct()); // set total record
        } catch (SQLException e) {
            message = e.getMessage();
        }
        request.setAttribute("priceAvaliable", productDAO.findMaxMinPrice()); // set price avaliable
        request.setAttribute("productList", productList); // set product list
        request.setAttribute("attributeAndValue", attributeAndValue); // set attribute and value
        request.setAttribute("productFilter", productFilter); // set product filter
        request.getRequestDispatcher("web-product.jsp").forward(request, response); // forward to web-product.jsp
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
