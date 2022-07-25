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
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

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
@WebServlet(name = "EditProduct", urlPatterns = {"/admin/product/edit"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class EditProduct extends HttpServlet {

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
            GetParameter getParameter = new GetParameter(); // get parameter from request
            Validation validation = new Validation(); // validate data
            ProductDAO productDAO = new ProductDAO(); // get productDAO
            int productId = validation.isInt(getParameter.getField(request, "productId", true),
                    "Cannot parse productId"); // get productId from request
            ProductDetails details = productDAO.findAllInfoProductById(productId); // get productDetails by productId
            if (details == null) {
                request.setAttribute("message", "null");
            } else {
                BrandDAO brandDAO = new BrandDAO();
                CategoryDAO categoryDAO = new CategoryDAO();
                request.setAttribute("brands", brandDAO.findAll());
                request.setAttribute("categories", categoryDAO.findAll());
                request.setAttribute("productStatus", productDAO.findAllProductStatus());
                request.setAttribute("attributes", productDAO.getASpecifiedAttributes()); // get all attributes
                request.setAttribute("productSpecifiedValue",
                        productDAO.getSpecifiedAttributeValueOfProduct(productId));
                request.setAttribute("productDetails", details);
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/product");
            return;
        }
        request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response); // forward to add.jsp
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

            Validation validation = new Validation();
            String productId = GetParameter.getField(request, "productId", true);

            ProductDetails productDetails = new ProductDAO()
                    .findAllInfoProductById(validation.isInt(productId, "Lỗi mã sản phẩm"));
            Product product = productDetails.getProduct();
            if (productDetails == null) {
                throw new Exception("Không tìm thấy sản phẩm");
            }

            String name = GetParameter.getField(request, "name", true); // get name
            product.setProductName(name);

            String quantity = GetParameter.getField(request, "quantity", true); // get quantity
            product.setQuantity(validation.isInt(quantity, "Lỗi số lượng"));

            String status_id = GetParameter.getField(request, "status", true); // get status_id
            product.setStatus(new ProductStatus(validation.isInt(status_id, "Lỗi trạng thái")));

            String category_id = GetParameter.getField(request, "categoryId", true); // get category_id
            product.setCategory(new Category(validation.isInt(category_id, "Lỗi danh mục")));

            String brand_id = GetParameter.getField(request, "brandId", true); // get brand_id
            product.setBrand(new Brand(validation.isInt(brand_id, "Lỗi thương hiệu")));

            String initial_price = GetParameter.getField(request, "initial_price", false); // get initial_price
            if (initial_price != null) {
                product.setInitialPrice(validation.isInt(initial_price, "Lỗi giá khởi tạo"));
            } else {
                product.setInitialPrice(0); // set initial_price = 0
            }
            String original_price = GetParameter.getField(request, "original_price", true); // get original_price
            product.setOriginalPrice(validation.isInt(original_price, "Lỗi giá gốc"));

            String unit_price = GetParameter.getField(request, "unit_price", true); // get unit_price
            product.setUnitPrice(validation.isInt(unit_price, "Lỗi giá bán"));

            String insurance = GetParameter.getField(request, "insurance", true); // get insurance
            product.setInsurance(validation.isInt(insurance, "Lỗi bảo hành"));

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

            String description = GetParameter.getField(request, "description", false); // get description
            productDetails.setDescription(description);
            String folder_product = GetParameter.getFolderImage(request, "product"); // get folder_product
            List<String> preview = FileManagement.uploadFiles(folder_product, "preview", request, false);
            if (!preview.isEmpty()) {
                try {
                    FileManagement.delete(product.getPreviewImage(), folder_product);
                } catch (IOException e) {

                }
                product.setPreviewImage(preview.get(0));
            }
            String folder_product_details = GetParameter.getFolderImage(request, "product-details"); // get
            String[] image_details_text = GetParameter.getFields(request, "image_details_text", false); // get
            // images_details_text
            List<String> images = new ArrayList<>(); // create list images
            List<String> imagesUploadHasSaved = FileManagement.uploadFiles(folder_product_details, "image_details", request, false); // list images upload has saved
            if (image_details_text != null) { // if images_details_text is not null
                images.addAll(Arrays.asList(image_details_text));
            }
            if (images.isEmpty() && imagesUploadHasSaved.isEmpty()) { // if images is empty and imagesUploadHasSaved is
                // empty
                throw new Exception("Cần phải có ít nhất một hình ảnh chi tiết");
            }
            images.addAll(imagesUploadHasSaved); // add imagesUploadHasSaved to images
            for (String image : productDetails.getImages()) {
                if (!images.contains(image)) { // if old image not in new image
                    try {
                        FileManagement.delete(image, folder_product_details); // delete old image
                    } catch (IOException e) {

                    }
                }
            }
            System.out.println(images);
            productDetails.setImages(images);
            productDetails.setProduct(product);
            ProductDAO productDAO = new ProductDAO();
            productDAO.editProduct(productDetails);
            response.getWriter().write(new Gson().toJson(new Message(true, "Sửa sản phẩm " + name + " thành công")));
        } catch (Exception ex) {
            response.getWriter().write(new Gson().toJson(new Message(false, ex.getMessage())));
        }
    }
}
