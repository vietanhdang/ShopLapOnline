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
public class AddCategory extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String cName = GetParameter.getField(request, "cName", true);
            String cDesciption = GetParameter.getField(request, "cDesciption", false);
            String folderCategory = GetParameter.getFolderImage(request, "category");
            Part cImagePart = GetParameter.getFieldFile(request, "cImage", true);
            String bImage = FileManagement.uploadFile(cImagePart, folderCategory);
            Category category = new Category(cName, cDesciption, bImage);
            boolean isInserted = new CategoryDAO().createCategory(category);
            if (!isInserted) {
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
