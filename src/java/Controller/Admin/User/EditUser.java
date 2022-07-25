/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin.User;

import Utils.GetParameter;
import dal.AccountDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mess;
        try {
            int id = Integer.parseInt(GetParameter.getField(request, "id", true));
            boolean status = GetParameter.getField(request, "stt", true).equals("true");
            boolean isUpdated = new AccountDAO().changeStatus(id, !status);
            if (isUpdated) {
                mess = "thay đổi trạng thái thành công";
            } else {
                mess = "thay đổi trạng thái thát bại";
            }
        } catch (Exception ex) {
            mess = "thay đổi trạng thái thất bại";
        }
        request.getSession().setAttribute("message", mess);
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(GetParameter.getField(request, "id", true));
            boolean status = GetParameter.getField(request, "stt", true).equals("true");
            boolean isUpdated = new AccountDAO().changeStatus(id, status);
            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/admin/users");
            } else {
                response.getWriter().print("failed");
            }
        } catch (Exception ex) {
            Logger.getLogger(EditUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
