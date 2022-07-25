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
import model.Brand;

/**
 *
 * @author Benjamin
 */
public class BrandDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Brand> findAll() {
        ArrayList<Brand> brands = new ArrayList<>();
        try {
            String sql = "select * from Brands WHERE status = 1";
            stm = connection.prepareCall(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("Brand_ID"));
                brand.setBrandName(rs.getString("BrandName"));
                brand.setImage(rs.getString("PreviewImage"));
                brand.setDescription(rs.getString("Description"));
                brand.setCreatedTime(Date.from(rs.getTimestamp("CreatedTime").toInstant()));
                brand.setUpdatedTime(Date.from(rs.getTimestamp("UpdatedTime").toInstant()));
                brands.add(brand);
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
        }
        return brands;
    }

    public ArrayList<Brand> findAllBrandAvailable() {
        ArrayList<Brand> brands = new ArrayList<>();
        try {
//            String sql = "SELECT B.Brand_ID, B.BrandName, B.PreviewImage, B.[Description], B.CreatedTime, B.UpdatedTime,\n"
//                    + "COUNT(P.Product_ID) as NumOfProduct FROM Brands B \n"
//                    + "JOIN Products P\n"
//                    + "ON B.Brand_ID = P.Brand_ID\n"
//                    + "GROUP BY B.Brand_ID, B.BrandName,B.PreviewImage, B.[Description], B.CreatedTime, B.UpdatedTime";
            String sql = "SELECT B.Brand_ID, B.BrandName, cast(B.PreviewImage as varchar (50)) as PreviewImage,\n"
                    + "cast (B.[Description] as nvarchar(100)) as [Description],B.CreatedTime, B.UpdatedTime,\n"
                    + "COUNT (P.Product_ID) as NumOfProduct \n"
                    + "FROM Brands B\n"
                    + "JOIN Products P\n"
                    + "ON B.Brand_ID = P.Brand_ID\n"
                    + "GROUP BY B.Brand_ID, B.BrandName, cast (B.PreviewImage as varchar (50)) ,\n"
                    + "cast (B. [Description] as nvarchar (100)), B.CreatedTime, B.UpdatedTime";
            stm = connection.prepareCall(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("Brand_ID"));
                brand.setBrandName(rs.getString("BrandName"));
                brand.setImage(rs.getString("PreviewImage"));
                brand.setDescription(rs.getString("Description"));
                brand.setCreatedTime(Date.from(rs.getTimestamp("CreatedTime").toInstant()));
                brand.setUpdatedTime(Date.from(rs.getTimestamp("UpdatedTime").toInstant()));
                brand.setTotalProduct(rs.getInt("NumOfProduct"));
                brands.add(brand);
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
        }
        return brands;
    }

    public Brand findById(int id) {
        try {
            String sql = "SELECT * FROM Brands\n"
                    + "WHERE Brand_ID = ?";
            stm = connection.prepareCall(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("Brand_ID"));
                brand.setBrandName(rs.getString("BrandName"));
                brand.setImage(rs.getString("PreviewImage"));
                brand.setDescription(rs.getString("Description"));
                brand.setCreatedTime(Date.from(rs.getTimestamp("CreatedTime").toInstant()));
                brand.setUpdatedTime(Date.from(rs.getTimestamp("UpdatedTime").toInstant()));
                return brand;
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
        }

        return null;
    }

    public int createBrand(Brand brand) {
        try {
            String sql = "INSERT INTO Brands (BrandName, PreviewImage, [Description], Status) values (?,?,?,?)";
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setNString(1, brand.getBrandName());
            stm.setString(2, brand.getImage());
            stm.setNString(3, brand.getDescription());
            stm.setBoolean(4, true);
            return stm.executeUpdate();
        } catch (SQLException e) {

        }
        return -1;
    }

    public ArrayList<Brand> findTotalProductIntBrand() {
        ArrayList<Brand> brands = new ArrayList<>();
        try {
            String sql = "select b.brand_id, b.brand_name, count(*) totalProduct from Brands b , Products p\n"
                    + "where b.brand_id = p.brand_id\n"
                    + "group by b.brand_id, b.brand_name";
            stm = connection.prepareCall(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("Brand_ID"));
                brand.setBrandName(rs.getString("BrandName"));
                brand.setTotalProduct(rs.getInt(3));
                brands.add(brand);
            }
        } catch (SQLException e) {
        }
        return brands;
    }

    public int deleteBrand(int bId) {
        try {
            String sql = "DELETE Brands\n"
                    + " WHERE Brand_ID = ?\n";
            stm = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, bId);
            return stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BrandDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }  
    }

    public boolean updateBrand(Brand brand) {
        try {
            String sql = "UPDATE [dbo].[Brands]\n"
                    + "   SET [BrandName] = ?\n"
                    + "      ,[PreviewImage] = ?\n"
                    + "      ,[Description] = ?\n"
                    + " WHERE Brand_ID = ?\n";
            stm = connection.prepareStatement(sql);
            stm.setNString(1, brand.getBrandName());
            stm.setString(2, brand.getImage());
            stm.setNString(3, brand.getDescription());
            stm.setInt(4, brand.getId());
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        boolean isUpdated = new BrandDAO().updateBrand(new Brand("12", null, null, 158323));

        System.out.println(isUpdated);
    }
}
