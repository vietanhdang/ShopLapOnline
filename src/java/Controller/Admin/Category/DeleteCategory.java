package Controller.Admin.Category;

import Utils.GetParameter;
import dal.CategoryDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCategory extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message;
        try {

            int cID = Integer.parseInt(GetParameter.getField(request, "cId", true));
            int isDeleted = new CategoryDAO().deleteCategory(cID);
            if (isDeleted != -1) {
                message = "Xóa thể loại thành công";
            } else {
                throw new Exception();
            }

        } catch (Exception ex) {
            message = "Xóa thất bại do trong thể loại này đang có sản phẩm";
        }
        request.getSession().setAttribute("deleteMessage", message);
        response.sendRedirect(request.getContextPath() + "/admin/category");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
