package Controller.Admin.User;

import Utils.FileManagement;
import Utils.GetParameter;
import Utils.HashGeneratorUtils;
import dal.SaleDAO;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Account;
import model.Role;
import model.Sale;

@MultipartConfig
public class AddUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String mess;
        try {
            //get Infor from client
            String email = GetParameter.getField(request, "email", true);
            String fullname = GetParameter.getField(request, "fullname", true);
            boolean gender = GetParameter.getField(request, "gender", true).equals("male");
            String DoB = GetParameter.getField(request, "DoB", true);
            String address = GetParameter.getField(request, "address", true);
            String phone = GetParameter.getField(request, "phone", true);
            String folderImage = GetParameter.getFolderImage(request, "account");
            Part imagePart = GetParameter.getFieldFile(request, "image", true);
            String image = FileManagement.uploadFile(imagePart, folderImage);
            //create new sale to insert, set password default = 123456
            Sale sale = new Sale(fullname, address, phone, image, gender, Date.valueOf(DoB), new Account(email, HashGeneratorUtils.generateSHA256("123456"), new Role(2), false));
            //insert into database
            boolean isInserted = new SaleDAO().insertSaleAccount(sale);
            if (isInserted) { //insert success
                mess = "Tạo nhân viên thành công!";
            } else {
                mess = "Tạo nhân viên thất bại!";
            }

        } catch (Exception ex) { //insert failed
            mess = "Tạo nhân viên thất bại!";
        }
        request.getSession().setAttribute("message", mess);
        response.sendRedirect(request.getContextPath() + "/admin/users");
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
