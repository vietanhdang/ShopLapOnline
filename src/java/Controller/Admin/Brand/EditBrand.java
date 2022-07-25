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
public class EditBrand extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bId = Integer.parseInt(GetParameter.getField(request, "bId", true));
            String bName = GetParameter.getField(request, "bName", true);
            String bDesciption = GetParameter.getField(request, "bDesciption", false);
            BrandDAO brandDAO = new BrandDAO();
            Brand brand = brandDAO.findById(bId);
            brand.setBrandName(bName);
            brand.setDescription(bDesciption);
            Part bImagePart = request.getPart("bImage");
            if (bImagePart.getSize() != 0) {
                String folderBrand = GetParameter.getFolderImage(request, "brand-logo");
                String bImage = FileManagement.uploadFile(bImagePart, folderBrand);
                brand.setImage(bImage);
            }
            boolean isUpdated = brandDAO.updateBrand(brand);
            if (!isUpdated) {
                throw new Exception();
            }
            response.sendRedirect(request.getContextPath() + "/admin/brand");
        } catch (Exception ex) {
            response.getWriter().write("error");
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
