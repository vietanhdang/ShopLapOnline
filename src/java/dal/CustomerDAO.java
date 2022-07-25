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
import model.Account;
import model.Customer;
import model.Role;

/**
 *
 * @author hoang
 */
public class CustomerDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    // Get Infor Customer include (UserName,Customer_ID,Address,Phone,FullName ) for 
    public Customer GetInforCustomerForScreenCheckout(int accountId) {
        try {
            String sql = "select c.[Address],c.Phone,c.FullName,a.UserName from Customers c \n"
                    + "inner join Accounts a on a.Account_ID = c.Customer_ID\n"
                    + "where a.Account_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, accountId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setAddress(rs.getString(1));
                customer.setPhone(rs.getString(2));
                customer.setFullname(rs.getString(3));
                Account account = new Account();
                account.setUsername(rs.getString(4));
                customer.setAccount(account);
                return customer;
            }
            rs.close();
            stm.close();
        } catch (SQLException e) {

        }
        return null;
    }

    public ArrayList<Customer> getNewCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            String sql = "select top 5 Customer_ID,FullName,DateOfBirth,Phone from Customers order by CreatedTime desc";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                Account account = new Account();
                account.setId(rs.getInt(1));
                customer.setAccount(account);
                customer.setFullname(rs.getString(2));
                customer.setDateOfBirth(rs.getDate(3));
                customer.setPhone(rs.getString(4));
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return customers;
    }
    
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> listCustomers = new ArrayList<>();
        try {
            String sql = "SELECT Customer_ID, FullName, [Address], Phone, [Image], Gender, DateOfBirth, \n"
                    + "[Status], UserName, [Password] FROM Accounts a \n"
                    + "INNER JOIN Customers c ON c.Customer_ID = a.Account_ID\n"
                    + " WHERE Role_ID = 3";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                Account a = new Account(rs.getString("UserName"), rs.getString("Password"), new Role(3, "Khách hàng"), rs.getBoolean("Status"));
                a.setId(rs.getInt("Customer_ID"));
                c.setAccount(a);
                c.setFullname(rs.getString("FullName"));
                c.setAddress(rs.getString("Address"));
                c.setPhone(rs.getString("Phone"));
                c.setImage(rs.getString("Image"));
                c.setGender(rs.getBoolean("Gender"));
                c.setDateOfBirth(rs.getDate("DateOfBirth"));
                listCustomers.add(c);
            }
        } catch (SQLException e) {
        }

        return listCustomers;
    }
}
