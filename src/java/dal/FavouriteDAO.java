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
import model.Favourite;

/**
 *
 * @author pc
 */
public class FavouriteDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public int insertFavourite(Favourite favourite) {
        try {
            String sql = "insert into Favourites (Customer_ID, Product_ID, CreatedTime) values (?,?,GETDATE())";
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, favourite.getUserId());
            stm.setInt(2, favourite.getProductId());
            return stm.executeUpdate();
        } catch (SQLException e) {
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

    public int deleteFavourite(int favouriteId) {
        try {
            String sql = "delete from Favourites where Favourites_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, favouriteId);
            return stm.executeUpdate();
        } catch (Exception e) {
        }
        return -1;
    }

    public List<Favourite> getFavouritesByUserId(int customerId) {
        List<Favourite> favourites = new ArrayList<>();
        try {
            String sql = "select f.Favourites_ID, f.Product_ID, f.CreatedTime, p.ProductName, "
                    + "p.PreviewImage, p.UnitPrice\n"
                    + "from Favourites f, Products p\n"
                    + "where f.Product_ID = p.Product_ID and f.Customer_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, customerId);
            rs = stm.executeQuery();
            while (rs.next()) {                
                Favourite favourite = new Favourite();
                favourite.setId(rs.getInt("Favourites_ID"));
                favourite.setUserId(customerId);
                favourite.setProductId(rs.getInt("Product_ID"));
                favourite.setProductImage(rs.getNString("PreviewImage"));
                favourite.setProductName(rs.getNString("ProductName"));
                favourite.setUnitInStock(1);
                favourite.setUnitPrice(rs.getInt("UnitPrice"));
                favourite.setCreatedTime(rs.getDate("CreatedTime"));
                favourites.add(favourite);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return favourites;
    }
}
