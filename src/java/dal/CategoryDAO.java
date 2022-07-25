/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;

/**
 *
 * @author Benjamin
 */
public class CategoryDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Category> findAll() {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            String sql = "select * from Categories where status = 1";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Category ca = new Category();
                ca.setId(rs.getInt("Category_ID"));
                ca.setCategoryName(rs.getString("CategoryName"));
                ca.setCategoryDescription(rs.getString("Description"));
                ca.setCategoryImage(rs.getString("PreviewImage"));
                ca.setCreatedTime(Date.from(rs.getTimestamp("CreatedTime").toInstant()));
                ca.setUpdatedTime(Date.from(rs.getTimestamp("UpdatedTime").toInstant()));
                categories.add(ca);
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
        }
        return categories;
    }

    public ArrayList<Category> findAllCategoryAvailable() {
        ArrayList<Category> categories = new ArrayList<>();
        try {
//            String sql = "SELECT C.Category_ID, C.CategoryName, C.PreviewImage, C.[Description], C.CreatedTime, C.UpdatedTime,\n"
//                    + "COUNT(P.Product_ID) as NumOfProduct FROM Categories C\n"
//                    + "JOIN Products P\n"
//                    + "ON C.Category_ID = P.Category_ID\n"
//                    + "GROUP BY C.Category_ID, C.CategoryName, C.PreviewImage, C.[Description], C.CreatedTime, C.UpdatedTime";
            String sql = "SELECT C.Category_ID, C.CategoryName, C.PreviewImage,\n"
                    + "cast (C.[Description] as nvarchar (100)) as [Description],\n"
                    + "C.CreatedTime, C.UpdatedTime,\n"
                    + "COUNT (P.Product_ID) as NumOfProduct FROM Categories C\n"
                    + "JOIN Products P\n"
                    + "ON C.Category_ID = P. Category_ID\n"
                    + "GROUP BY C.Category_ID, C.CategoryName, C.PreviewImage,\n"
                    + "cast (C.[Description] as nvarchar (100)), C.CreatedTime, C.UpdatedTime";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Category ca = new Category();
                ca.setId(rs.getInt("Category_ID"));
                ca.setCategoryName(rs.getString("CategoryName"));
                ca.setCategoryDescription(rs.getString("Description"));
                ca.setCategoryImage(rs.getString("PreviewImage"));
                ca.setCreatedTime(Date.from(rs.getTimestamp("CreatedTime").toInstant()));
                ca.setUpdatedTime(Date.from(rs.getTimestamp("UpdatedTime").toInstant()));
                ca.setTotalProduct(rs.getInt("NumOfProduct"));
                categories.add(ca);
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
        }
        return categories;
    }

    public Category findById(int id) {
        try {
            String sql = "SELECT * FROM Categories\n"
                    + "WHERE Category_ID = ?";
            stm = connection.prepareCall(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                Category ca = new Category();
                ca.setId(rs.getInt("Category_ID"));
                ca.setCategoryName(rs.getString("CategoryName"));
                ca.setCategoryDescription(rs.getString("Description"));
                ca.setCategoryImage(rs.getString("PreviewImage"));
                ca.setCreatedTime(Date.from(rs.getTimestamp("CreatedTime").toInstant()));
                ca.setUpdatedTime(Date.from(rs.getTimestamp("UpdatedTime").toInstant()));
                return ca;
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean createCategory(Category category) {
        try {
            String sql = "INSERT INTO Categories(CategoryName, PreviewImage, [Description], Status) VALUES (?,?,?,?)";
            stm = connection.prepareStatement(sql);
            stm.setNString(1, category.getCategoryName());
            stm.setString(2, category.getCategoryImage());
            stm.setNString(3, category.getCategoryDescription());
            stm.setBoolean(4, true);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {

        }
        return false;
    }

     public int deleteCategory(int cId) {
        try {
            String sql = "DELETE Categories\n"
                    + " WHERE Brand_ID = ?\n";
            stm = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, cId);
            return stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BrandDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }  
    }

    public boolean updateCategory(Category category) {
        try {
            String sql = "UPDATE [dbo].[Categories]\n"
                    + "   SET [CategoryName] = ?\n"
                    + "      ,[PreviewImage] = ?\n"
                    + "      ,[Description] = ?\n"
                    + " WHERE Category_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setNString(1, category.getCategoryName());
            stm.setString(2, category.getCategoryImage());
            stm.setNString(3, category.getCategoryDescription());
            stm.setInt(4, category.getId());
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
