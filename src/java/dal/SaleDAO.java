/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Account;
import model.Sale;
import model.Role;

/**
 *
 * @author Admin
 */
public class SaleDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Sale> getAllSales() {
        ArrayList<Sale> listSales = new ArrayList<>();
        try {
            String sql = "SELECT Sale_ID, FullName, [Address], Phone, [Image], Gender, DateOfBirth, \n"
                    + "[Status], UserName, [Password] FROM Accounts a \n"
                    + "INNER JOIN Sales s ON s.Sale_ID = a.Account_ID\n"
                    + "WHERE a.Role_ID = 2";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Sale c = new Sale();
                Account a = new Account(rs.getString("UserName"), rs.getString("Password"), new Role(2, "Nhân viên"), rs.getBoolean("Status"));
                a.setId(rs.getInt("Sale_ID"));
                c.setAccount(a);
                c.setFullname(rs.getString("FullName"));
                c.setAddress(rs.getString("Address"));
                c.setPhone(rs.getString("Phone"));
                c.setImage(rs.getString("Image"));
                c.setGender(rs.getBoolean("Gender"));
                c.setDateOfBirth(rs.getDate("DateOfBirth"));
                listSales.add(c);
            }
        } catch (SQLException e) {
        }

        return listSales;
    }

    public boolean insertSaleAccount(Sale sale) throws SQLException {
        try {
            String sql = "insert into Accounts(UserName, Password, Role_ID, Status) values (?,?,?,?)";
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, sale.getAccount().getUsername());
            stm.setString(2, sale.getAccount().getPassword());
            stm.setInt(3, sale.getAccount().getRole().getRoleID());
            stm.setBoolean(4, false);
            stm.executeUpdate();
            rs = stm.getGeneratedKeys();
            while (rs.next()) {
                sale.getAccount().setId(rs.getInt(1));
            }
            sql = "insert into Sales (Sale_ID, FullName, [Address], Phone, [Image], Gender, DateOfBirth) values (?,?,?,?,?,?,?)";
            stm = connection.prepareStatement(sql);

            stm.setInt(1, sale.getAccount().getId());
            stm.setNString(2, sale.getFullname());
            stm.setNString(3, sale.getAddress());
            stm.setString(4, sale.getPhone());
            stm.setString(5, sale.getImage());
            stm.setBoolean(6, sale.isGender());
            stm.setDate(7, (java.sql.Date) sale.getDateOfBirth());
            stm.executeUpdate();
            connection.commit();
            rs.close();
            stm.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            // If there is an error then rollback the changes.
            System.out.println("Rolling back data because somthing error");
            if (connection != null) {
                connection.rollback();
            }
            return false;
        }
    }

}
