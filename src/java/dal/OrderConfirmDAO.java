/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.OrderStatus;

/**
 *
 * @author Benjamin
 */
public class OrderConfirmDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<OrderStatus> findAllStatus() {
        ArrayList<OrderStatus> status = new ArrayList<>();
        try {
            String sql = "select * from Orders_Status where order_status_id != 8";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                OrderStatus a = new OrderStatus();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                status.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return status;
    }
}
