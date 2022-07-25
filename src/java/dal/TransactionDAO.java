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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import model.Account;
import model.Comment;
import model.Order;
import model.OrderDetails;

/**
 *
 * @author Benjamin
 */
public class TransactionDAO extends DBContext {

    private static final Logger LOG = Logger.getLogger(TransactionDAO.class.getName());
    PreparedStatement stm;
    ResultSet rs;

    public int countOrderAll() {
        try {
            String sql = "select count(*) from Orders_Details od, Orders o where\n"
                    + "o.orderId = od.orderId and o.order_status_id !=8";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public ArrayList<Order> findAllOrderByUser(Account u) {
        ArrayList<Order> od = new ArrayList<>();
        try {
            String sql = "select o.Order_ID, o.FullName, o.[Address], o.Phone, o.OrderDate, os.OrderStatus_ID,\n" +
                    "os.OrderStatusName, o.Email, o.TotalTransaction, o.NoteOrder, oh.OrderHistoryTime, o.PayMethod\n" +
                    "from OrderStatus os, Orders o, Accounts c, \n" +
                    "	(SELECT OrderHistory_ID, Order_ID, OrderStatus_ID, OrderHistoryTime\n" +
                    "	FROM OrderHistory\n" +
                    "	WHERE exists(select Order_ID from (select Order_ID, max(OrderStatus_ID) as status_ID, max(OrderHistoryTime) as HistoryTime\n" +
                    "	from OrderHistory group by Order_ID) newestTime\n" +
                    "	where newestTime.Order_ID = OrderHistory.Order_ID\n" +
                    "	and newestTime.HistoryTime = OrderHistory.OrderHistoryTime\n" +
                    "	and newestTime.status_ID = OrderHistory.OrderStatus_ID)) oh\n" +
                    "where oh.Order_ID = o.Order_ID\n" +
                    "and oh.OrderStatus_ID = os.OrderStatus_ID\n" +
                    "and o.Customer_ID = c.Account_ID\n" +
                    "and o.Customer_ID = ?\n" +
                    "order by oh.OrderHistoryTime desc, os.OrderStatus_ID desc";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, u.getId());
            rs = stm.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("Order_ID"));
                o.setId(rs.getInt("Order_ID"));
                o.setFullName(rs.getNString("FullName"));
                o.setEmail(rs.getString("Email"));
                o.setAddress(rs.getNString("Address"));
                o.setPhone(rs.getString("Phone"));
                o.setCreatedTime(rs.getDate("OrderDate"));
                o.setStatusId(rs.getInt("OrderStatus_ID"));
                o.setStatus(rs.getNString("OrderStatusName"));
                o.setTotalTransaction(rs.getLong("TotalTransaction"));
                o.setNotes(rs.getNString("NoteOrder"));
                o.setPayMethod(rs.getInt("PayMethod"));
                o.setUserId(u.getId());
                od.add(o);
            }
        } catch (SQLException e) {
            System.err.println("Get user orders failed   " + e);
        }
        return od;
    }

    public int findOrderInList(List<Order> orders, int orderId) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId() == orderId) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<OrderDetails> getDetailsInOrder(int orderId) {
        ArrayList<OrderDetails> details = new ArrayList<>();
        try {
            String sql = "select d.OrderDetail_ID, d.Product_ID, p.UnitPrice, d.Quantity, "
                    + "p.ProductName, f.Feedback_ID\n"
                    + "from OrderDetails d\n"
                    + "inner join Orders o on d.Order_ID = o.Order_ID\n"
                    + "inner join Products p on p.Product_ID = d.Product_ID\n"
                    + "left join Feedbacks f on f.OrderDetail_ID = d.OrderDetail_ID\n"
                    + "where o.Order_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, orderId);
            rs = stm.executeQuery();
            while (rs.next()) {
                OrderDetails detail = new OrderDetails();
                detail.setId(rs.getInt("OrderDetail_ID"));
                detail.setProductId(rs.getInt("Product_ID"));
                detail.setPrice(rs.getLong("UnitPrice"));
                detail.setQuantity(rs.getInt("Quantity"));
                detail.setProductName(rs.getNString("ProductName"));
                detail.setComment(rs.getInt("Feedback_ID") > 0);
                details.add(detail);
            }
        } catch (Exception e) {
            System.out.println("Get order detail fail   " + e);
        }
        return details;
    }

    public int addOrderStatus(int orderId, int statusId) {
        try {
            String sql = "insert into OrderHistory(Order_ID, OrderStatus_ID, OrderHistoryTime) "
                    + "values (?,?,GETDATE())";
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, orderId);
            stm.setInt(2, statusId);
            return stm.executeUpdate();
        } catch (Exception e) {
            // If there is an error then rollback the changes.
            System.out.println(e);
            try {
                if (connection != null) {
                    connection.rollback();
                    System.err.println("Rolling back data because somthing error");
                }
            } catch (SQLException se2) {
                System.err.println("Cannot rolling back data. " + se2);
            }
        }
        return -1;
    }
    
    public Comment getCommentByUserIAndOrderDetailId(int orderDetailId, int userId) {
        try {
            String sql = "Select F.Feedback_ID, F.Customer_ID, F.OrderDetail_ID, F.Comment, F.Rating, "
                    + "F.CreatedTime, F.UpdatedTime, P.PreviewImage, P.ProductName\n"
                    + "From Feedbacks F, OrderDetails OD, Products P\n"
                    + "Where F.Customer_ID = ? and F.OrderDetail_ID = ? and OD.OrderDetail_ID = F.OrderDetail_ID "
                    + " and P.Product_ID = OD.Product_ID";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, orderDetailId);
            rs = stm.executeQuery();
            if (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("Feedback_ID"));
                comment.setUserId(rs.getInt("Customer_ID"));
                comment.setOrderDetailId(rs.getInt("OrderDetail_ID"));
                comment.setComment(rs.getNString("Comment"));
                comment.setRating(rs.getInt("Rating"));
                comment.setCreatedTime(rs.getDate("CreatedTime"));
                comment.setUpdatedTime(rs.getDate("UpdatedTime"));
                comment.setPreviewImage(rs.getString("PreviewImage"));
                comment.setProductName(rs.getNString("ProductName"));
                return comment;
            }
        } catch (Exception e) {
            System.out.println("Get comment fail   " + e);
        }
        return null;
    }
    
    public boolean updateUserOrderDetailReview(int orderDetailId, int userId, String comment, int rating) {
        try {
            String sql = "Update Feedbacks SET Comment = ?, Rating = ?, UpdatedTime = GETDATE() WHERE Customer_ID = ? and OrderDetail_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, comment);
            stm.setInt(2, rating);
            stm.setInt(3, userId);
            stm.setInt(4, orderDetailId);
            stm.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Update comment fail   " + e);
            return false;
        }
    }
    
    public boolean insertUserOrderDetailReview(int orderDetailId, int userId, String comment, int rating) {
        try {
            String sql = "Insert into Feedbacks(Customer_ID, OrderDetail_ID, Comment, Rating, CreatedTime, UpdatedTime) values (?,?,?,?,GETDATE(),GETDATE())";
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, userId);
            stm.setInt(2, orderDetailId);
            stm.setString(3, comment);
            stm.setInt(4, rating);
            stm.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Insert comment fail   " + e);
            return false;
        }
    }
}
