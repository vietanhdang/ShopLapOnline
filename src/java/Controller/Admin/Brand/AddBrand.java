package Controller.Admin.Brand;

import Utils.FileManagement;
import Utils.GetParameter;
import dal.BrandDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Brand;

@MultipartConfig
public class AddBrand extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //get brand information from jsp page
            String bName = GetParameter.getField(request, "bName", true);
            String bDesciption = GetParameter.getField(request, "bDesciption", false);
            String folderBrand = GetParameter.getFolderImage(request, "brand-logo");
            Part bImagePart = GetParameter.getFieldFile(request, "bImage", true);
            //upload image to image folder
            String bImage = FileManagement.uploadFile(bImagePart, folderBrand);
            Brand brand = new Brand(bName, bDesciption, bImage);
            //insert into database
            int brandId = new BrandDAO().createBrand(brand);
            if (brandId == -1) {//insert false
                throw new Exception("insert false");
            }
            //insert successfully
            response.sendRedirect(request.getContextPath() + "/admin/brand");
        } catch (Exception ex) {
            response.getWriter().print(ex.getMessage());
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
