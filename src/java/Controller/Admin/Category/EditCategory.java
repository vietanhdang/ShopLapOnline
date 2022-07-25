package Controller.Admin.Category;

import Utils.FileManagement;
import Utils.GetParameter;
import dal.CategoryDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Category;

@MultipartConfig
public class EditCategory extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int cId = Integer.parseInt(GetParameter.getField(request, "cId", true));
            String cName = GetParameter.getField(request, "cName", true);
            String cDesciption = GetParameter.getField(request, "cDesciption", false);
            CategoryDAO categoryDAO = new CategoryDAO();
            Category category = categoryDAO.findById(cId);
            category.setCategoryName(cName);
            category.setCategoryDescription(cDesciption);
            Part cImagePart = request.getPart("cImage");
            if (cImagePart.getSize() != 0) {
                String folderCategory = GetParameter.getFolderImage(request, "category");
                String cImage = FileManagement.uploadFile(cImagePart, folderCategory);
                category.setCategoryImage(cImage);
            }
            boolean isUpdated = categoryDAO.updateCategory(category);
            if (!isUpdated) {
                throw new Exception();
            }
            response.sendRedirect(request.getContextPath() + "/admin/category");
        } catch (Exception ex) {
            response.getWriter().print(ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
