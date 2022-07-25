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
import model.Order;
import model.OrderDetails;
import model.Turnover;
import model.OrderFilter;

/**
 *
 * @author Benjamin
 */
public class OrderDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<OrderDetails> getCountTop5ProductIdBestSelling() {
        ArrayList<OrderDetails> countProductsBestSelling = new ArrayList<>();
        try {
            String sql = "select * from Products o , \n"
                    + "(select top 5 sum(Quantity) as 'Quantity Sold', Product_ID from OrderDetails\n"
                    + "group by Product_ID\n"
                    + "order by [Quantity Sold] desc) p\n"
                    + "where o.product_ID = p.Product_ID\n";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                OrderDetails od = new OrderDetails();
                od.setProductId(rs.getInt(2));
                countProductsBestSelling.add(od);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return countProductsBestSelling;
    }

    public ArrayList<Turnover> getMonthlyRevenueOfCurrentYear() {
        ArrayList<Turnover> result = new ArrayList<>();
        try {
            String sql = "with t as (\n"
                    + "select  MONTH (DateAdd(month, -5, GETDATE())) as [Month], YEAR(GETDATE()) as [Year]\n"
                    + "union\n"
                    + "select MONTH (DateAdd(month, -4, GETDATE())) as [Month], YEAR(GETDATE()) as [Year]\n"
                    + "union \n"
                    + "select MONTH (DateAdd(month, -3, GETDATE())) as Month, YEAR(GETDATE()) as Year\n"
                    + "union \n"
                    + "select MONTH (DateAdd(month, -2, GETDATE())) as Month, YEAR(GETDATE()) as Year\n"
                    + "union\n"
                    + "select MONTH (DateAdd(month, -1, GETDATE())) as Month, YEAR(GETDATE()) as Year\n"
                    + "union \n"
                    + "select MONTH (GETDATE()) as Month, YEAR(GETDATE()) as Year),\n"
                    + "r as (select SUM(od.Price*od.Quantity)as Amount,MONTH(o.OrderDate) as [Month],YEAR(o.OrderDate) as [Year]\n"
                    + "from Orders o \n"
                    + "inner join OrderDetails od on od.Order_ID = o.Order_ID\n"
                    + "group by MONTH(o.OrderDate),YEAR(o.OrderDate))\n"
                    + "select t.Month,t.Year,isnull(Amount,0) as Revenue from t \n"
                    + "left join r on t.Year = r.Year and t.Month = r.Month";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Turnover turnover = new Turnover();
                turnover.setMonth(rs.getInt(1));
                turnover.setYear(rs.getInt(2));
                turnover.setRevenue(rs.getLong(3));
                result.add(turnover);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public ArrayList<Order> getInforOrder() {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            String sql = "select top(50) o.Order_ID,o.FullName,o.TotalTransaction,os.OrderStatus_ID,os.OrderStatusName from orders o\n"
                    + "inner join OrderHistory oh on oh.Order_ID = o.Order_ID\n"
                    + "inner join OrderStatus os on os.OrderStatus_ID = oh.OrderStatus_ID order by OrderDate desc";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt(1));
                order.setFullName(rs.getString(2));
                order.setTotalTransaction(rs.getLong(3));
                order.setOrderStatusId(rs.getInt(4));
                order.setStatus(rs.getString(5));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return orders;
    }

    public int totalOfOrdersProcess() {
        try {
            String sql = "select count(o.Order_ID) as TotalOfOrdersProcess from orders o\n"
                    + "where MONTH(o.OrderDate) = MONTH(GETDATE()) and YEAR(o.OrderDate) = YEAR(GETDATE())";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
        }
        return -1;
    }

    public boolean orderProduct(Order order, List<OrderDetails> orderDetails, int accountId) {
        ProductDAO productDAO = new ProductDAO();
        try {

            // b1: insert data from  orders table
            String sql1 = "insert into Orders(Customer_ID,OrderDate,[Address],Phone,FullName,Email,NoteOrder,TotalTransaction,PayMethod)\n"
                    + "values (?,Getdate(),?,?,?,?,?,?,?)";
            // b2: insert data from orderdetails table with attribute order_id taken above
            String sql2 = "insert into OrderDetails (Order_ID, Product_ID, Quantity, Price) values (?,?,?,?)";
            // b3:  insert data from orderhistory table with attribute order_id taken above
            String sql3 = "insert into OrderHistory (Order_ID, OrderStatus_ID,OrderHistoryTime) values (?,1,GETDATE())";
            // b4 : set quantity and status in Products table after insert order detail and orders table 
            String sql4 = "Update Products set Quantity = ? , Status_ID = ? where Product_ID = ?";
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, accountId);
            stm.setString(2, order.getAddress());
            stm.setString(3, order.getPhone());
            stm.setString(4, order.getFullName());
            stm.setString(5, order.getEmail());
            stm.setString(6, order.getNotes());
            stm.setLong(7, order.getTotalTransaction());
            stm.setInt(8, order.getPayMethod());
            stm.executeUpdate();
            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderId(rs.getInt(1));
            }
            stm = connection.prepareStatement(sql2);
            for (OrderDetails orderDetail : orderDetails) {
                stm.setInt(1, order.getOrderId());
                stm.setInt(2, orderDetail.getProductId());
                stm.setInt(3, orderDetail.getQuantity());
                stm.setLong(4, orderDetail.getPrice());
                stm.executeUpdate();
            }
            stm = connection.prepareStatement(sql4);
            for (OrderDetails orderDetail : orderDetails) {
                int remainingAmount = productDAO.currentQuantityOfProduct(orderDetail.getProductId()) - orderDetail.getQuantity();
                stm.setInt(1, remainingAmount);
                if (remainingAmount == 0) {
                    stm.setInt(2, 2);
                } else {
                    stm.setInt(2, 1);
                }
                stm.setInt(3, orderDetail.getProductId());
                stm.executeUpdate();
            }
            stm = connection.prepareStatement(sql3);
            stm.setInt(1, order.getOrderId());
            stm.executeUpdate();
            connection.commit();
            rs.close();
            stm.close();
            connection.close();
            return true;
        } catch (SQLException se) {
        }
        return false;
    }

    public ArrayList<Order> getAllNonCancelOrdersForSale(int saleId, OrderFilter orderFilter) {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            String selectStatuString = " and ";
            int count = 0;
            for (int i = 0; i < orderFilter.getSelectStatus().length; i++) {
                if (orderFilter.getSelectStatus()[i]) {
                    selectStatuString += "status_ID = " + (i + 1) + " or ";
                    count++;
                }
            }
            if (count == 0 || count == 5) {
                selectStatuString = "";
            } else {
                selectStatuString = selectStatuString.substring(0, selectStatuString.length() - 3);
            }
            String sortString = orderFilter.getSortBy() + (orderFilter.isIsAsc() ? " ASC" : " DESC");
            String sql = "SELECT COUNT(*) OVER() as ROW_COUNT, O.Order_ID, O.Customer_ID, O.Sale_ID, O.OrderDate, O.[Address] , O.Phone, O.FullName, O.Email, \n"
                    + "	newestTime.status_ID, OS.OrderStatusName, O.TotalTransaction, O.NoteOrder, O.PayMethod, newestTime.HistoryTime\n"
                    + "	FROM Orders O\n"
                    + "INNER JOIN (SELECT Order_ID, MAX(OrderStatus_ID) AS status_ID, MAX(OrderHistoryTime) AS HistoryTime\n"
                    + "	FROM OrderHistory GROUP BY Order_ID) newestTime ON newestTime.Order_ID = O.Order_ID\n"
                    + "INNER JOIN OrderStatus OS ON OS.OrderStatus_ID = newestTime.status_ID\n"
                    + "WHERE newestTime.status_ID <> 6 and (O.Sale_ID is NULL or O.Sale_ID = ?) and "
                    + "(O.Order_ID LIKE '%" + orderFilter.getSearch() + "%' or O.FullName LIKE N'%" + orderFilter.getSearch() + "%')" + selectStatuString + "\n"
                    + "ORDER BY " + sortString + "\n"
                    + "OFFSET ? ROWS FETCH NEXT " + orderFilter.getOrderPerPage() + " ROWS ONLY";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, saleId);
            stm.setInt(2, (orderFilter.getCurrentPage() - 1) * orderFilter.getOrderPerPage());
            rs = stm.executeQuery();
            int rowCount = -1;
            int totalPage = -1;
            while (rs.next()) {
                if (rowCount == -1) {
                    rowCount = rs.getInt("ROW_COUNT");
                    totalPage = (rowCount / orderFilter.getOrderPerPage()) + (rowCount % orderFilter.getOrderPerPage() == 0 ? 0 : 1);
                }
                Order order = new Order();
                order.setId(rs.getInt("Order_ID"));
                order.setOrderId(rs.getInt("Order_ID"));
                order.setCreatedTime(rs.getDate("OrderDate"));
                order.setAddress(rs.getNString("Address"));
                order.setPhone(rs.getString("Phone"));
                order.setFullName(rs.getNString("Fullname"));
                order.setEmail(rs.getString("Email"));
                order.setStatusId(rs.getInt("status_ID"));
                order.setStatus(rs.getNString("OrderStatusName"));
                order.setTotalTransaction(rs.getLong("TotalTransaction"));
                order.setNotes(rs.getNString("NoteOrder"));
                order.setPayMethod(rs.getInt("PayMethod"));
                order.setUpdatedTime(rs.getDate("HistoryTime"));
                orders.add(order);
            }
            /* make a fake temp order for save outside info -> when use this function MUST get this temp info at first then delete immediately */
            Order sendInfoTemp = new Order();
            sendInfoTemp.setStatusId(rowCount); // save filter result number in temp order status id
            sendInfoTemp.setSaleId(totalPage); // save filter result total page in temp order sale id
            orders.add(sendInfoTemp);
        } catch (Exception e) {
            System.out.println("Get sales order list fail   " + e);
        }
        return orders;
    }

    public boolean addOrderStatusBySale(int orderId, int orderSaleId, int statusId, int saleId) {
        try {
            if (new TransactionDAO().addOrderStatus(orderId, statusId) > 0) {
                if (orderSaleId == 0) {
                    String sql = "Update Orders SET Sale_ID = ? where Order_ID = ?";
                    stm = connection.prepareStatement(sql);
                    stm.setInt(1, saleId);
                    stm.setInt(2, orderId);
                    stm.executeUpdate();
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Update order saleId fail   " + e);
        }
        return false;
    }
}
