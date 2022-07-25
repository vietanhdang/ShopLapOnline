/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Account;
import model.Admin;
import model.Customer;
import model.Role;
import model.Sale;

/**
 *
 * @author Benjamin
 */
public class AccountDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public int insertCustomerAccount(Customer customer) throws SQLException {
        try {
            String sql = "insert into Accounts(UserName, Password, Role_ID, Status) values (?,?,?,1)";
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, customer.getAccount().getUsername());
            stm.setString(2, customer.getAccount().getPassword());
            stm.setInt(3, customer.getAccount().getRole().getRoleID());
            stm.executeUpdate();
            rs = stm.getGeneratedKeys();
            while (rs.next()) {
                customer.getAccount().setId(rs.getInt(1));
            }
            sql = "insert into Customers (Customer_ID, FullName, [Address], Phone, [Image], Gender, DateOfBirth) values (?,?,?,?,?,?,?)";
            stm = connection.prepareStatement(sql);

            stm.setInt(1, customer.getAccount().getId());
            stm.setNString(2, customer.getFullname());
            stm.setNString(3, customer.getAddress());
            stm.setString(4, customer.getPhone());
            stm.setString(5, customer.getImage());
            stm.setBoolean(6, customer.isGender());
            stm.setDate(7, (java.sql.Date) customer.getDateOfBirth());
            stm.executeUpdate();
            connection.commit();
            rs.close();
            stm.close();
            connection.close();
            return customer.getAccount().getId();
        } catch (SQLException e) {
            // If there is an error then rollback the changes.
            System.out.println("Rolling back data because somthing error");
            if (connection != null) {
                connection.rollback();
            }
            return -1;
        }
    }

    public Customer getCustomerByAccount(Account account) {
        try {
            String sql = "select c.Fullname, c.Address, c.Phone, c.Image, c.Gender, c.DateOfBirth "
                    + "from Customers c, Accounts a \n"
                    + "where a.Account_ID = c.Customer_ID and a.UserName = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, account.getUsername());
            rs = stm.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setFullname(rs.getString("FullName"));
                customer.setAddress(rs.getString("Address"));
                customer.setPhone(rs.getString("Phone"));
                customer.setImage(rs.getString("Image"));
                if (customer.getImage() == null || customer.getImage().trim().equals("")) {
                    customer.setImage("client-1.png");
                }
                customer.setGender(rs.getBoolean("Gender"));
                customer.setDateOfBirth(rs.getDate("DateOfBirth"));
                customer.setAccount(account);
                rs.close();
                stm.close();
                return customer;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public Admin getAdminByAccount(Account account) {
        try {
            String sql = "select c.Fullname, c.Address, c.Phone, c.Image, c.Gender, c.DateOfBirth "
                    + "from Admins c, Accounts a \n"
                    + "where a.Account_ID = c.Admin_ID and a.UserName = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, account.getUsername());
            rs = stm.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setFullname(rs.getString("FullName"));
                admin.setAddress(rs.getString("Address"));
                admin.setPhone(rs.getString("Phone"));
                admin.setImage(rs.getString("Image"));
                if (admin.getImage() == null || admin.getImage().trim().equals("")) {
                    admin.setImage("client-1.png");
                }
                admin.setGender(rs.getBoolean("Gender"));
                admin.setDateOfBirth(rs.getDate("DateOfBirth"));
                admin.setAccount(account);
                rs.close();
                stm.close();
                return admin;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public Sale getSaleByAccount(Account account) {
        try {
            String sql = "select c.Fullname, c.Address, c.Phone, c.Image, c.Gender, c.DateOfBirth "
                    + "from Sales c, Accounts a \n"
                    + "where a.Account_ID = c.Sale_ID and a.UserName = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, account.getUsername());
            rs = stm.executeQuery();
            if (rs.next()) {
                Sale sale = new Sale();
                sale.setFullname(rs.getString("FullName"));
                sale.setAddress(rs.getString("Address"));
                sale.setPhone(rs.getString("Phone"));
                sale.setImage(rs.getString("Image"));
                if (sale.getImage() == null || sale.getImage().trim().equals("")) {
                    sale.setImage("client-1.png");
                }
                sale.setGender(rs.getBoolean("Gender"));
                sale.setDateOfBirth(rs.getDate("DateOfBirth"));
                sale.setAccount(account);
                rs.close();
                stm.close();
                return sale;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public int updateAvatar(int userId, int role, String image) {
        try {
            String userRole = "";
            switch (role) {
                case 3:
                    userRole = "Customer";
                    break;
                case 1:
                    userRole = "Admin";
                    break;
                case 2:
                    userRole = "Sale";
                    break;
            }
            String sql = "Update " + userRole + "s SET [Image] = ? WHERE " + userRole + "_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, image);
            stm.setInt(2, userId);
            stm.executeUpdate();
            return role;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public boolean updateCustomerInfo(Customer customer) {
        try {
            String sql = "Update Customers SET Fullname = ?, "
                    + "[Address] = ?, Phone = ?, Gender = ?, DateOfBirth = ? \n"
                    + "Where Customer_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, customer.getFullname());
            stm.setString(2, customer.getAddress());
            stm.setString(3, customer.getPhone());
            stm.setBoolean(4, customer.isGender());
            stm.setDate(5, (java.sql.Date) customer.getDateOfBirth());
            stm.setInt(6, customer.getAccount().getId());
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateAdminInfo(Admin admin) {
        try {
            String sql = "Update Admins SET Fullname = ?, "
                    + "[Address] = ?, Phone = ?, Gender = ?, DateOfBirth = ? \n"
                    + "Where Admin_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, admin.getFullname());
            stm.setString(2, admin.getAddress());
            stm.setString(3, admin.getPhone());
            stm.setBoolean(4, admin.isGender());
            stm.setDate(5, (java.sql.Date) admin.getDateOfBirth());
            stm.setInt(6, admin.getAccount().getId());
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateSaleInfo(Sale sale) {
        try {
            String sql = "Update Sales SET Fullname = ?, "
                    + "[Address] = ?, Phone = ?, Gender = ?, DateOfBirth = ? \n"
                    + "Where Sale_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, sale.getFullname());
            stm.setString(2, sale.getAddress());
            stm.setString(3, sale.getPhone());
            stm.setBoolean(4, sale.isGender());
            stm.setDate(5, (java.sql.Date) sale.getDateOfBirth());
            stm.setInt(6, sale.getAccount().getId());
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Account findByUsername(String username) {
        try {
            String sql = "select * from Accounts where UserName =  ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            rs = stm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("Account_ID"));
                account.setUsername(rs.getString("UserName"));
                account.setPassword(rs.getString("Password"));
                account.setStatus(rs.getBoolean("Status"));
                account.setRole(new Role(rs.getInt("Role_ID")));
                account.setCreatedTime(rs.getDate("CreatedTime"));
                account.setUpdatedTime(rs.getDate("UpdatedTime"));
                account.setCookie(rs.getString("Cookie"));
                account.setNote("Note");
                return account;
            }
            rs.close();
            stm.close();
        } catch (SQLException e) {

        }
        return null;
    }

    public boolean updateAccountCookie(int accountId, String randomCookie) {
        try {
            String sql = "UPDATE Accounts\n"
                    + "SET Cookie = ?\n"
                    + "where Account_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, randomCookie);
            stm.setInt(2, accountId);
            stm.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public int countAccount(int roleId) {
        try {
            String sql = "SELECT count(*) FROM Accounts where Role_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, roleId);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return -1;
    }

    public int updateValidator(String validator, int userId) {
        try {
            String sql = "Update Accounts set Validator = ? \n"
                    + "where Account_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, validator);
            stm.setInt(2, userId);
            return stm.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public Account checkValidatorToken(String validator, int userId) {
        try {
            String sql = "Select * from Accounts where Account_ID = ? and Validator = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setString(2, validator);
            rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("Account_ID"));
                account.setUsername(rs.getString("UserName"));
                account.setPassword(rs.getString("Password"));
                account.setStatus(rs.getBoolean("Status"));
                account.setRole(new Role(rs.getInt("Role_ID")));
                account.setCreatedTime(rs.getDate("CreatedTime"));
                account.setUpdatedTime(rs.getDate("UpdatedTime"));
                account.setCookie(rs.getString("Cookie"));
                return account;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public int updatePassword(Account account) {
        try {
            String sql = "UPDATE Accounts\n"
                    + "SET Password = ?\n"
                    + "WHERE Account_ID = ?";
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, account.getPassword());
            stm.setInt(2, account.getId());
            return stm.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public boolean changeStatus(int accountId, boolean status) {
        try {
            String sql = "UPDATE [dbo].[Accounts]\n"
                    + "   SET [Status] = ?\n"
                    + " WHERE Account_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setBoolean(1, status);
            stm.setInt(2, accountId);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
