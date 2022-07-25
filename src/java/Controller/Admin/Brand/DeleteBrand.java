package Controller.Admin.Brand;

import Utils.GetParameter;
import dal.BrandDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteBrand extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message;
        try {

            int bID = Integer.parseInt(GetParameter.getField(request, "bId", true));
            int isDeleted = new BrandDAO().deleteBrand(bID);
            if (isDeleted != -1) {
                message = "Xóa thành công";
            } else {
                throw new Exception();
            }

        } catch (Exception ex) {
            message = "Xóa thất bại do trong thương hiệu đang có sản phẩm";
        }
        request.getSession().setAttribute("deleteMessage", message);
        response.sendRedirect(request.getContextPath() + "/admin/brand");
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
