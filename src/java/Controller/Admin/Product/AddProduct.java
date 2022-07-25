/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin.Product;

import Utils.FileManagement;
import Utils.GetParameter;
import Utils.TextProcessing;
import Utils.Validation;
import dal.BrandDAO;
import dal.CategoryDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import model.Brand;
import model.Category;
import model.Message;
import model.Product;
import model.ProductDetails;
import model.ProductStatus;
import model.SpecifiedAttribute;
import model.SpecifiedAttributeValue;

/**
 *
 * @author vietd
 */
@WebServlet(name = "AddProduct", urlPatterns = {"/admin/product/add"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class AddProduct extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        BrandDAO brandDAO = new BrandDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        ProductDAO productDAO = new ProductDAO();
        request.setAttribute("brands", brandDAO.findAll()); // get all brands
        request.setAttribute("categories", categoryDAO.findAll()); // get all categories
        request.setAttribute("productStatus", productDAO.findAllProductStatus()); // get all productStatus
        request.setAttribute("attributes", productDAO.getASpecifiedAttributes()); // get all attributes
        request.getRequestDispatcher("/admin/product/add.jsp").forward(request, response); // forward to add.jsp
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
        try {
            Product product = new Product();
            Validation validation = new Validation();

            String name = GetParameter.getField(request, "name", true); // get name
            product.setProductName(name);

            String quantity = GetParameter.getField(request, "quantity", true); // get quantity
            product.setQuantity(validation.isInt(quantity, "error parse quantity"));

            String status_id = GetParameter.getField(request, "status", true); // get status_id
            product.setStatus(new ProductStatus(validation.isInt(status_id, "error parse status_id")));

            String category_id = GetParameter.getField(request, "categoryId", true); // get category_id
            product.setCategory(new Category(validation.isInt(category_id, "error parse category_id")));

            String brand_id = GetParameter.getField(request, "brandId", true); // get brand_id
            product.setBrand(new Brand(validation.isInt(brand_id, "error parse brand_id")));

            String initial_price = GetParameter.getField(request, "initial_price", false); // get initial_price
            if (initial_price != null) {
                product.setInitialPrice(validation.isInt(initial_price, "error parse")); // set initial_price
            } else {
                product.setInitialPrice(0); // set initial_price = 0
            }
            String original_price = GetParameter.getField(request, "original_price", true); // get original_price
            product.setOriginalPrice(validation.isInt(original_price, "error parse original price"));

            String unit_price = GetParameter.getField(request, "unit_price", true); // get unit_price
            product.setUnitPrice(validation.isInt(unit_price, "error parse"));

            String insurance = GetParameter.getField(request, "insurance", true); // get insurance
            product.setInsurance(validation.isInt(insurance, "error parse"));

            String[] attribute_id = GetParameter.getFields(request, "attribute", true); // get list attribute_id
            String[] attribute_value_id = GetParameter.getFields(request,
                    "specified_attribute", true); // get list attribute_value_id
            List<SpecifiedAttributeValue> specifiedAttributeValues = new ArrayList<>(); // create list
            // specifiedAttributeValues
            for (int i = 0; i < attribute_id.length; i++) {
                String attributeName = "";
                String filterName = "";
                int attributeId = 0;
                int attributeValueId = 0;
                String attributeValueName = "";
                try {
                    attributeId = Integer.parseInt(attribute_id[i]); // if id is number then parse to int
                } catch (NumberFormatException e) {
                    attributeName = attribute_id[i].trim(); // if id is string then set attributeName
                    filterName = TextProcessing.normalizeAttributeQuery(attributeName); // set filterName
                }
                try {
                    attributeValueId = Integer.parseInt(attribute_value_id[i]); // if id is number then parse to int
                } catch (NumberFormatException e) {
                    attributeValueName = attribute_value_id[i].trim(); // if id is string then set attributeValueName
                }
                SpecifiedAttribute specifiedAttribute = new SpecifiedAttribute(attributeName, attributeId, filterName);
                SpecifiedAttributeValue specifiedAttributeValue = new SpecifiedAttributeValue(specifiedAttribute,
                        attributeValueName, attributeValueId);
                specifiedAttributeValues.add(specifiedAttributeValue);
            }
            product.setSpecifiedAttributeValues(specifiedAttributeValues); // set specifiedAttributeValues

            ProductDetails productDetails = new ProductDetails();
            String description = GetParameter.getField(request, "description", false); // get description
            productDetails.setDescription(description);

            String folder_product = GetParameter.getFolderImage(request, "product"); // get folder_product
            String folder_product_details = GetParameter.getFolderImage(request, "product-details"); // get
            // folder_product_details
            List<String> images = FileManagement.uploadFiles(folder_product_details, "image_details", request, true); // upload
            // images
            productDetails.setImages(images);

            Part preview_image = GetParameter.getFieldFile(request, "preview", true); // get preview_image
            String preview_images = FileManagement.uploadFile(preview_image,
                    folder_product); // upload preview_image
            product.setPreviewImage(preview_images);

            productDetails.setProduct(product);
            ProductDAO productDAO = new ProductDAO();
            productDAO.insertProduct(productDetails);
            response.getWriter().write(new Gson().toJson(new Message(true, "Thêm sản phẩm " + name + " thành công")));
        } catch (Exception ex) {
            response.getWriter().write(new Gson().toJson(new Message(false, ex.getMessage())));
        }
    }
}
