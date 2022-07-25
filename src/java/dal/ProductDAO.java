/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import Utils.TextProcessing;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Brand;
import model.Category;
import model.Comment;
import model.Product;
import model.ProductDetails;
import Controller.Product.ProductFilter;

import java.util.HashMap;
import java.util.Map;

import model.ProductList;
import model.ProductStatus;
import model.SpecifiedAttribute;
import model.SpecifiedAttributeValue;

/**
 * @author Benjamin
 */
public class ProductDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Product> getTop5ProductBestSelling() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            String sql = "select top  5 p.Product_ID, p.ProductName,p.PreviewImage,p.UnitPrice,p.Quantity,b.BrandName,c.CategoryName,ps.StatusName\n"
                    + "from Products p,\n" + "(select top 5 Product_ID,SUM(Quantity) as 'Total Quantity'\n"
                    + "from OrderDetails group by Product_ID) as d,\n" + "Categories c,\n" + "Brands b,\n"
                    + "ProductStatus ps\n" + "where p.Product_ID = d.Product_ID and\n"
                    + "c.Category_ID = p.Category_ID and\n" + "b.Brand_ID = p.Brand_ID and \n"
                    + "ps.Status_ID = p.Status_ID";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setProductName(rs.getString(2));
                p.setPreviewImage(rs.getString(3));
                p.setUnitPrice(rs.getInt(4));
                p.setQuantity(rs.getInt(5));
                p.setBrand(new Brand(rs.getString(6), null, null));
                p.setCategory(new Category(rs.getString(7), null, null));
                p.setStatus(new ProductStatus(rs.getString(8)));
                products.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return products;
    }

    public ArrayList<ProductStatus> findAllProductStatus() {
        ArrayList<ProductStatus> productStatus = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ProductStatus";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                ProductStatus ps = new ProductStatus();
                ps.setId(rs.getInt("Status_ID"));
                ps.setStatusName(rs.getString("StatusName"));
                ps.setCreatedTime(rs.getDate("CreatedTime"));
                ps.setUpdatedTime(rs.getDate("UpdatedTime"));
                productStatus.add(ps);
            }
        } catch (SQLException e) {
        }
        return productStatus;
    }

    public ProductList findProductByFilter(ProductFilter filter) throws SQLException {
        ProductList productList = null;
        List<Product> products = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select Product.*, RCS.Rating, RCS.Comment, RCS.QuantitySold from (");
            sql.append("select COUNT (*) OVER () AS ROW_COUNT, * from Products P Where Status_ID = ?");
            if (!filter.getCategoryId().isEmpty()) {
                sql.append(" and P.Category_ID in (").append(filter.getCategoryId()).append(") ");
            }
            if (!filter.getBrandId().isEmpty()) {
                sql.append(" and P.Brand_ID in (").append(filter.getBrandId()).append(") ");
            }
            if (!filter.getSpecifiedAttribute().isEmpty()) {
                sql.append(" and P.Product_ID IN ( SELECT PSA.Product_ID FROM Products_SpecifiedAttribute PSA")
                        .append(" WHERE PSA.SpecifiedAttributeValue_ID IN (").append(filter.getSpecifiedAttribute())
                        .append(")")
                        .append(" GROUP BY PSA.Product_ID HAVING COUNT(PSA.Product_ID) =")
                        .append(filter.getNumOfAttributeFitler())
                        .append(") ");
            }
            if (!filter.getSearch().isEmpty()) {
                sql.append(" and P.ProductName like '%").append(filter.getSearch()).append("%' ");
            }
            if (filter.getPriceFrom() != 0 && filter.getPriceTo() != 0) {
                sql.append(" and P.UnitPrice between ").append(filter.getPriceFrom()).append(" and ")
                        .append(filter.getPriceTo())
                        .append(" ");
            }
            sql.append(filter.getSortBy().equals("price") ? " order by P.UnitPrice " : " order by P.UpdatedTime ");
            sql.append(filter.isIsAsc() ? "asc" : "desc");
            sql.append(" OFFSET ").append((filter.getCurrentPage() - 1) * filter.getRecordPerPage())
                    .append(" ROWS FETCH NEXT ").append(filter.getRecordPerPage()).append(" ROWS ONLY ");
            sql.append(") AS Product LEFT JOIN");
            sql.append(" (select od.Product_ID, avg(f.Rating) AS Rating, count(f.Comment) AS Comment,");
            sql.append(" sum(od.Quantity) AS QuantitySold  from Feedbacks f");
            sql.append(
                    " RIGHT JOIN (SELECT od.OrderDetail_ID, od.Order_ID,od.Product_ID, od.Quantity FROM OrderDetails od")
                    .append(" WHERE od.Order_ID NOT IN  (SELECT oh.Order_ID FROM OrderHistory oh WHERE oh.OrderStatus_ID = 6)) AS od")
                    .append(" ON f.OrderDetail_ID = od.OrderDetail_ID AND f.Comment <> ''")
                    .append(" GROUP BY od.Product_ID) AS RCS")
                    .append(" ON Product.Product_ID = RCS.Product_ID");
            stm = connection.prepareStatement(sql.toString());
            stm.setInt(1, filter.getStatusId());
            rs = stm.executeQuery();
            int rowCount = 0;
            while (rs.next()) {
                rowCount = rs.getInt("ROW_COUNT");
                Product p = new Product();
                p.setId(rs.getInt("Product_ID"));
                p.setProductName(rs.getString("ProductName"));
                p.setOriginalPrice(rs.getInt("OriginalPrice"));
                p.setUnitPrice(rs.getInt("UnitPrice"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setInsurance(rs.getInt("Insurance"));
                p.setPreviewImage(rs.getString("PreviewImage"));
                p.setInitialPrice(rs.getInt("InitialPrice"));
                p.setCreatedTime(rs.getDate("CreatedTime"));
                p.setUpdatedTime(rs.getDate("UpdatedTime"));
                ProductStatus productStatus = new ProductStatus();
                productStatus.setId(rs.getInt("Status_ID"));
                p.setStatus(productStatus);
                Brand b = new Brand();
                Category c = new Category();
                b.setId(rs.getInt("Brand_ID"));
                c.setId(rs.getInt("Category_ID"));
                p.setBrand(b);
                p.setCategory(c);
                p.setQuantitySold(rs.getInt("QuantitySold"));
                p.setComment(rs.getInt("Comment"));
                p.setRating(rs.getInt("Rating"));
                products.add(p);
            }
            productList = new ProductList(rowCount, products);
            stm.close();
            rs.close();
        } catch (SQLException e) {
            throw new SQLException("Lỗi truy vấn tìm kiếm của bạn");
        }
        return productList;
    }

    public List<Integer> findMaxMinPrice() {
        List<Integer> prices = new ArrayList<>();
        try {
            String sql = "select max(UnitPrice) as 'max',min(UnitPrice) as 'min' from Products";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                prices.add(rs.getInt("min"));
                prices.add(rs.getInt("max"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return prices;
    }

    public Product findProductById(int productId) {
        try {
            String sql = "SELECT p.*, b.BrandName, c.CategoryName, \n"
                    + "ps.Status_ID, ps.StatusName FROM PRODUCTS p\n"
                    + "join Brands b on p.Brand_ID = b.Brand_ID join Categories c\n"
                    + "on c.Category_ID = p.Category_ID join ProductStatus ps\n"
                    + "on ps.Status_ID = p.Status_ID and p.Status_ID = 1 and p.Product_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("Product_ID"));
                p.setProductName(rs.getString("ProductName"));
                p.setOriginalPrice(rs.getInt("OriginalPrice"));
                p.setUnitPrice(rs.getInt("UnitPrice"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setInsurance(rs.getInt("Insurance"));
                p.setPreviewImage(rs.getString("PreviewImage"));
                p.setInitialPrice(rs.getInt("InitialPrice"));
                p.setCreatedTime(rs.getDate("CreatedTime"));
                p.setUpdatedTime(rs.getDate("UpdatedTime"));
                ProductStatus productStatus = new ProductStatus();
                productStatus.setId(rs.getInt("Status_ID"));
                productStatus.setStatusName(rs.getString("StatusName"));
                p.setStatus(productStatus);
                Brand b = new Brand();
                Category c = new Category();
                b.setBrandName(rs.getString("BrandName"));
                b.setId(rs.getInt("Brand_ID"));
                c.setId(rs.getInt("Category_ID"));
                c.setCategoryName(rs.getString("CategoryName"));
                p.setBrand(b);
                p.setCategory(c);
                return p;
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
        }
        return null;
    }

    public int countAllProduct() {
        try {
            // count products have quantity >0 (product stocking)
            String sql = "SELECT COUNT(*) FROM PRODUCTS where Quantity > 0";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1); // return value int from query count
            }
        } catch (SQLException e) {
            System.out.println("Error in countAllProduct");
        }
        // if the above command block is captured, it will return -1
        return -1;
    }

    public int countOutOfStock(int number) {
        try {
            String sql = "select count(*) from Products where Quantity < ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, number);
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

    public int deleteProduct(int productId) throws SQLException {
        try {
            String sql = "DELETE FROM Products\n" + "WHERE Product_ID = ?";
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, productId);
            return stm.executeUpdate();
        } catch (SQLException e) {

        }
        return -1;
    }

    public ArrayList<Product> findRandomProduct(int number) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            // query random top ? product with key word ORDER BY NEWID() (TOP ? is input
            // parameter)
            String sql = "SELECT TOP (?) p.*, b.BrandName, c.Category_ID, c.CategoryName, ps.Status_ID, ps.StatusName FROM PRODUCTS p\n" +
"join Brands b on p.Brand_ID = b.Brand_ID join Categories c\n" +
"on c.Category_ID = p.Category_ID join ProductStatus ps\n" +
"on ps.Status_ID = p.Status_ID WHERE p.Status_ID = 1 and p.InitialPrice is not null ORDER BY NEWID()";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, number); // set input parameter for query
            rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("Product_ID"));
                p.setProductName(rs.getString("ProductName"));
                p.setOriginalPrice(rs.getInt("OriginalPrice"));
                p.setUnitPrice(rs.getInt("UnitPrice"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setInsurance(rs.getInt("Insurance"));
                p.setPreviewImage(rs.getString("PreviewImage"));
                p.setInitialPrice(rs.getInt("InitialPrice"));
                p.setCreatedTime(rs.getDate("CreatedTime"));
                p.setUpdatedTime(rs.getDate("UpdatedTime"));
                ProductStatus productStatus = new ProductStatus();
                productStatus.setId(rs.getInt("Status_ID"));
                productStatus.setStatusName(rs.getString("StatusName"));
                p.setStatus(productStatus);
                Brand b = new Brand();
                Category c = new Category();
                b.setBrandName(rs.getString("BrandName"));
                b.setId(rs.getInt("Brand_ID"));
                c.setId(rs.getInt("Category_ID"));
                c.setCategoryName(rs.getString("CategoryName"));
                p.setBrand(b);
                p.setCategory(c);
                products.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return products;
    }

    public Product getInforProductByID(int id) {
        try {
            // query get infor need for function top 5 best selling with parameter is
            // product_ID
            String sql = "select p.ProductName,p.PreviewImage,p.UnitPrice,p.Quantity,b.BrandName,c.CategoryName,ps.StatusName from Products p \n"
                    + "inner join Categories c on c.Category_ID = p.Category_ID\n"
                    + "inner join Brands b on b.Brand_ID = p.Brand_ID\n"
                    + "inner join ProductStatus ps on ps.Status_ID = p.Status_ID\n" + "where p.Product_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);// set input parameter for the above query
            rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductName(rs.getString(1));
                p.setPreviewImage(rs.getString(2));
                p.setUnitPrice(rs.getInt(3));
                p.setQuantity(rs.getInt(4));
                p.setBrand(new Brand(rs.getString(5), null, null));
                p.setCategory(new Category(rs.getString(6), null, null));
                p.setStatus(new ProductStatus(rs.getString(7)));
                return p;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<SpecifiedAttribute> getASpecifiedAttributes() {
        List<SpecifiedAttribute> list = new ArrayList<>();
        try {
            String sql = "select * from SpecifiedAttribute";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                SpecifiedAttribute sa = new SpecifiedAttribute();
                sa.setId(rs.getInt("SpecifiedAttribute_ID"));
                sa.setName(rs.getString("SpecifiedAttributeName"));
                sa.setFilterName(rs.getString("SpecifiedAttributeQueryName"));
                list.add(sa);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<SpecifiedAttributeValue> getASpecifiedAttributeValue(int id) {
        List<SpecifiedAttributeValue> list = new ArrayList<>();
        try {
            String sql = "select * from SpecifiedAttributeValue where SpecifiedAttribute_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                SpecifiedAttributeValue sa = new SpecifiedAttributeValue();
                sa.setId(rs.getInt("SpecifiedAttributeValue_ID"));
                sa.setName(rs.getString("SpecifiedAttributeValueName"));
                list.add(sa);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<SpecifiedAttributeValue> findAllAttributeValueOfProduct(int Product_ID) {
        List<SpecifiedAttributeValue> specifiedAttributeValues = new ArrayList<>();
        try {
            String sql = "SELECT SA.SpecifiedAttribute_ID, SA.SpecifiedAttributeName, \n"
                    + "SAV.SpecifiedAttributeValue_ID, SAV.SpecifiedAttributeValueName\n"
                    + "FROM SpecifiedAttribute SA\n" + "JOIN SpecifiedAttributeValue SAV\n"
                    + "ON SA.SpecifiedAttribute_ID = SAV.SpecifiedAttribute_ID\n"
                    + "JOIN Products_SpecifiedAttribute PSA\n"
                    + "ON PSA.SpecifiedAttributeValue_ID = SAV.SpecifiedAttributeValue_ID\n"
                    + "WHERE PSA.Product_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, Product_ID);
            rs = stm.executeQuery();
            while (rs.next()) {
                SpecifiedAttribute attribute = new SpecifiedAttribute();
                attribute.setId(rs.getInt("SpecifiedAttribute_ID"));
                attribute.setName(rs.getString("SpecifiedAttributeName"));
                SpecifiedAttributeValue specifiedAttributeValue = new SpecifiedAttributeValue();
                specifiedAttributeValue.setId(rs.getInt("SpecifiedAttributeValue_ID"));
                specifiedAttributeValue.setName(rs.getString("SpecifiedAttributeValueName"));
                specifiedAttributeValue.setSpecifiedAttribute(attribute);
                specifiedAttributeValues.add(specifiedAttributeValue);
            }
        } catch (SQLException e) {

        }
        return specifiedAttributeValues;
    }

    public ProductDetails findProductDetailById(int id) {
        try {
            String sql = "SELECT P.Product_ID, P.ProductName, P.PreviewImage, P.Insurance, P.OriginalPrice, P.InitialPrice, \n"
                    + "P.UnitPrice, P.Quantity, P.Status_ID, PS.StatusName, P.CreatedTime, P.UpdatedTime, C.Category_ID, C.CategoryName, \n"
                    + "B.Brand_ID, B.BrandName, PD.Images, PD.[Description], F.Feedback_ID, F.Customer_ID, Cus.Fullname, A.Username, \n"
                    + "F.OrderDetail_ID, OH.OrderStatus_ID, OD.Quantity AS QuantitySold, F.Comment, F.Rating, F.CreatedTime, F.UpdatedTime, Cus.[Image], \n"
                    + "SA.SpecifiedAttribute_ID, SA.SpecifiedAttributeName, SAV.SpecifiedAttributeValue_ID, \n"
                    + "SAV.SpecifiedAttributeValueName FROM Products P \n"
                    + "JOIN ProductDetails PD ON PD.ProductDetail_ID = P.Product_ID \n"
                    + "JOIN Categories C ON C.Category_ID = P.Category_ID \n"
                    + "JOIN Brands B ON B.Brand_ID = P.Brand_ID\n"
                    + "JOIN ProductStatus PS ON PS.Status_ID = P.Status_ID\n"
                    + "LEFT JOIN OrderDetails OD ON OD.Product_ID = P.Product_ID\n"
                    + "LEFT JOIN (SELECT Order_ID, max(OrderStatus_ID) as OrderStatus_ID FROM OrderHistory GROUP BY Order_ID) OH "
                    + "ON OD.Order_ID = OH.Order_ID\n"
                    + "LEFT JOIN Feedbacks F ON F.OrderDetail_ID = OD.OrderDetail_ID\n"
                    + "LEFT JOIN Accounts A ON A.Account_ID = F.Customer_ID\n"
                    + "LEFT JOIN Customers Cus ON Cus.Customer_ID = f.Customer_ID\n"
                    + "LEFT JOIN Products_SpecifiedAttribute PSA ON PSA.Product_ID = P.Product_ID\n"
                    + "LEFT JOIN SpecifiedAttributeValue SAV ON PSA.SpecifiedAttributeValue_ID = SAV.SpecifiedAttributeValue_ID\n"
                    + "LEFT JOIN SpecifiedAttribute SA ON SA.SpecifiedAttribute_ID = SAV.SpecifiedAttribute_ID\n"
                    + "WHERE P.Product_ID = ? AND P.Status_ID!=3";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            ProductDetails details = null;
            List<Comment> comments = new ArrayList<>();
            List<SpecifiedAttributeValue> specifiedAttributeValues = new ArrayList<>();
            int orderStatus = 6;
            int sold = 0;
            int rateCount = 0;
            int rate = 0;
            Product p = new Product();
            int feedbackId = -1;
            int attributeValueId = -1;
            while (rs.next()) {
                if (details == null) {
                    details = new ProductDetails();
                    details.setDescription(rs.getString("Description"));
                    details.setImages(TextProcessing.convertTextToImages(rs.getString("Images")));
                    p.setId(rs.getInt("Product_ID"));
                    p.setProductName(rs.getString("ProductName"));
                    p.setOriginalPrice(rs.getInt("OriginalPrice"));
                    p.setUnitPrice(rs.getInt("UnitPrice"));
                    p.setQuantity(rs.getInt("Quantity"));
                    p.setInsurance(rs.getInt("Insurance"));
                    p.setPreviewImage(rs.getString("PreviewImage"));
                    p.setInitialPrice(rs.getInt("InitialPrice"));
                    p.setCreatedTime(rs.getDate("CreatedTime"));
                    p.setUpdatedTime(rs.getDate("UpdatedTime"));
                    ProductStatus productStatus = new ProductStatus(rs.getInt("Status_ID"));
                    productStatus.setStatusName(rs.getString("StatusName"));
                    p.setStatus(productStatus);
                    Brand b = new Brand();
                    Category c = new Category();
                    b.setBrandName(rs.getString("BrandName"));
                    b.setId(rs.getInt("Brand_ID"));
                    c.setId(rs.getInt("Category_ID"));
                    c.setCategoryName(rs.getString("CategoryName"));
                    p.setBrand(b);
                    p.setCategory(c);
                }
                try {
                    orderStatus = rs.getInt("OrderStatus_ID");
                    if (orderStatus != 6) {
                        sold += rs.getInt("QuantitySold");
                        Comment comment = new Comment();
                        comment.setId(rs.getInt("Feedback_ID"));
                        if (comment.getId() != 0 & comment.getId() != feedbackId) {
                            comment.setUserId(rs.getInt("Customer_ID"));
                            comment.setFullname(rs.getString("Fullname"));
                            comment.setUsername(rs.getString("Username"));
                            comment.setOrderDetailId(rs.getInt("OrderDetail_ID"));
                            comment.setComment(rs.getString("Comment"));
                            comment.setRating(rs.getInt("Rating"));
                            rate += comment.getRating();
                            rateCount++;
                            comment.setCreatedTime(rs.getDate("CreatedTime"));
                            comment.setUpdatedTime(rs.getDate("UpdatedTime"));
                            comment.setPreviewImage(rs.getString("Image"));
                            if (comment.getPreviewImage() == null || comment.getPreviewImage().trim().equals("")) {
                                comment.setPreviewImage("client-1.png");
                            }
                            comments.add(comment);
                            feedbackId = comment.getId();
                        }
                    }
                } catch (Exception e) {
                }
                SpecifiedAttributeValue specifiedAttributeValue = new SpecifiedAttributeValue();
                specifiedAttributeValue.setId(rs.getInt("SpecifiedAttributeValue_ID"));
                if (attributeValueId == -1) {
                    attributeValueId = specifiedAttributeValue.getId();
                } else if (attributeValueId == specifiedAttributeValue.getId()) {
                    attributeValueId = 0;
                }
                if (attributeValueId != 0) {
                    SpecifiedAttribute attribute = new SpecifiedAttribute();
                    attribute.setId(rs.getInt("SpecifiedAttribute_ID"));
                    attribute.setName(rs.getString("SpecifiedAttributeName"));
                    specifiedAttributeValue.setName(rs.getString("SpecifiedAttributeValueName"));
                    specifiedAttributeValue.setSpecifiedAttribute(attribute);
                    specifiedAttributeValues.add(specifiedAttributeValue);
                }
            }
            if (rateCount > 0) {
                rate /= rateCount;
            }
            details.setComments(comments);
            p.setQuantitySold(sold);
            p.setRating(rate);
            p.setSpecifiedAttributeValues(specifiedAttributeValues);
            details.setProduct(p);
            return details;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public ProductDetails findAllInfoProductById(int id) {
        String sql = "SELECT P.Product_ID, P.ProductName, P.PreviewImage, P.Insurance, P.OriginalPrice, P.InitialPrice, \n"
                + "P.UnitPrice, P.Quantity, P.Status_ID, P.CreatedTime, P.UpdatedTime, C.Category_ID, C.CategoryName, \n"
                + "B.Brand_ID, B.BrandName, PD.Images, PD.[Description] FROM Products P \n"
                + "JOIN ProductDetails PD ON PD.ProductDetail_ID = P.Product_ID \n"
                + "JOIN Categories C ON C.Category_ID = P.Category_ID \n"
                + "JOIN Brands B ON B.Brand_ID = P.Brand_ID\n"
                + "WHERE P.Product_ID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                ProductDetails details = new ProductDetails();
                details.setDescription(rs.getString("Description"));
                details.setImages(TextProcessing.convertTextToImages(rs.getString("Images")));
                p.setId(rs.getInt("Product_ID"));
                p.setProductName(rs.getString("ProductName"));
                p.setOriginalPrice(rs.getInt("OriginalPrice"));
                p.setUnitPrice(rs.getInt("UnitPrice"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setInsurance(rs.getInt("Insurance"));
                p.setPreviewImage(rs.getString("PreviewImage"));
                p.setInitialPrice(rs.getInt("InitialPrice"));
                p.setCreatedTime(rs.getDate("CreatedTime"));
                p.setUpdatedTime(rs.getDate("UpdatedTime"));
                ProductStatus productStatus = new ProductStatus(rs.getInt("Status_ID"));
                p.setStatus(productStatus);
                Brand b = new Brand();
                Category c = new Category();
                b.setBrandName(rs.getString("BrandName"));
                b.setId(rs.getInt("Brand_ID"));
                c.setId(rs.getInt("Category_ID"));
                c.setCategoryName(rs.getString("CategoryName"));
                p.setBrand(b);
                p.setCategory(c);
                details.setProduct(p);
                return details;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Comment> findCommentsByProductId(int productId) {
        List<Comment> comments = new ArrayList<>();
        try {
            String sql = "SELECT f.Feedback_ID, f.Customer_ID, c.Fullname, a.Username, f.OrderDetail_ID"
                    + ", f.Comment, f.Rating, f.CreatedTime, f.UpdatedTime, c.[Image]\n"
                    + "  FROM Feedbacks f, OrderDetails o, Customers c, Accounts a\n"
                    + "  Where f.OrderDetail_ID = o.OrderDetail_ID and a.Account_ID = f.Customer_ID\n"
                    + "  and c.Customer_ID = f.Customer_ID\n"
                    + "  and o.Product_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("Feedback_ID"));
                comment.setUserId(rs.getInt("Customer_ID"));
                comment.setFullname(rs.getString("Fullname"));
                comment.setUsername(rs.getString("Username"));
                comment.setOrderDetailId(rs.getInt("OrderDetail_ID"));
                comment.setComment(rs.getString("Comment"));
                comment.setRating(rs.getInt("Rating"));
                comment.setCreatedTime(rs.getDate("CreatedTime"));
                comment.setUpdatedTime(rs.getDate("UpdatedTime"));
                comment.setPreviewImage(rs.getString("Image"));
                if (comment.getPreviewImage() == null || comment.getPreviewImage().trim().equals("")) {
                    comment.setPreviewImage("client-1.png");
                }
                comments.add(comment);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return comments;
    }

    public int insertProduct(ProductDetails product) throws Exception {
        Product p = product.getProduct();
        try {
            String insertProduct = "INSERT INTO Products (ProductName, PreviewImage, Insurance, OriginalPrice, InitialPrice, UnitPrice, Quantity, Status_ID, Category_ID, Brand_ID) \n"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            String insertProductDetails = "INSERT INTO ProductDetails(ProductDetail_ID,Images, [Description]) VALUES (?,?,?)";
            String insertProductAttribute = "INSERT INTO Products_SpecifiedAttribute(Product_ID, SpecifiedAttributeValue_ID) VALUES (?,?)";
            String insertSpecifiedAttribute = "insert into SpecifiedAttribute (SpecifiedAttributeName, SpecifiedAttributeQueryName) values (?,?)";
            String insertSpecifiedAttributeValue = "insert into SpecifiedAttributeValue(SpecifiedAttribute_ID, SpecifiedAttributeValueName) values (?,?)";
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(insertProduct, Statement.RETURN_GENERATED_KEYS);
            stm.setNString(1, p.getProductName());
            stm.setString(2, p.getPreviewImage());
            stm.setInt(3, p.getInsurance());
            stm.setInt(4, p.getOriginalPrice());
            stm.setInt(5, p.getInitialPrice());
            stm.setInt(6, p.getUnitPrice());
            stm.setInt(7, p.getQuantity());
            stm.setInt(8, p.getStatus().getId());
            stm.setInt(9, p.getCategory().getId());
            stm.setInt(10, p.getBrand().getId());
            stm.executeUpdate();
            rs = stm.getGeneratedKeys();
            while (rs.next()) {
                p.setId(rs.getInt(1)); // return productID
            }
            stm = connection.prepareStatement(insertProductDetails);
            stm.setInt(1, p.getId());
            stm.setString(2, product.getImagesToDB());
            stm.setString(3, product.getDescription().toString());
            stm.executeUpdate();
            // insert product done
            PreparedStatement stm1 = connection.prepareStatement(insertProductAttribute); // insert productSpecifi
            for (SpecifiedAttributeValue sp : p.getSpecifiedAttributeValues()) {
                int specifiedAttributeId = sp.getSpecifiedAttribute().getId();
                int specifiedAttributeValueId = sp.getId();
                if (sp.getSpecifiedAttribute().getId() == 0) {
                    stm = connection.prepareStatement(insertSpecifiedAttribute, Statement.RETURN_GENERATED_KEYS);
                    stm.setString(1, sp.getSpecifiedAttribute().getName());
                    stm.setString(2, sp.getSpecifiedAttribute().getFilterName());
                    if (stm.executeUpdate() <= 0) {
                        System.out.println("Insert SpecifiedAttribute failed");
                    }
                    rs = stm.getGeneratedKeys();
                    while (rs.next()) {
                        specifiedAttributeId = rs.getInt(1); // lay ra specified attribute value
                    }
                }
                if (sp.getId() == 0) {
                    stm = connection.prepareStatement(insertSpecifiedAttributeValue, Statement.RETURN_GENERATED_KEYS);
                    stm.setInt(1, specifiedAttributeId);
                    stm.setString(2, sp.getName());
                    if (stm.executeUpdate() <= 0) {
                        System.out.println("Insert SpecifiedAttributeValue failed");
                    }
                    rs = stm.getGeneratedKeys();
                    while (rs.next()) {
                        specifiedAttributeValueId = rs.getInt(1); // lay ra id của specifiedAttributeValue
                    }
                }
                stm1.setInt(1, p.getId());
                stm1.setInt(2, specifiedAttributeValueId);
                if (stm1.executeUpdate() <= 0) {
                    System.out.println("Insert ProductAttribute failed");
                }
            }
            connection.commit();
            rs.close();
            stm.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // If there is an error then rollback the changes.
            System.out.println(e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException se2) {
                System.out.println("Rolling back data because somthing error");
            }
            return -1;
        } finally {
            // finally block used to close resources
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException se2) {
                System.out.println("Rolling back data because somthing error");
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                System.out.println(se);
            }
        }
        return p.getId();
    }

    public int editProduct(ProductDetails product) throws Exception {
        Product p = product.getProduct();
        try {
            String updateProduct = "UPDATE Products set UpdatedTime=GETDATE(), ProductName = ?, PreviewImage = ?, Insurance=?, OriginalPrice=?, InitialPrice=?, UnitPrice=?, Quantity=?, Status_ID=?, Category_ID=?, Brand_ID=? \n"
                    + "WHERE Product_ID=?";
            String updateProductDetails = "UPDATE ProductDetails SET Images=?, [Description]=? WHERE ProductDetail_ID=?";
            String deleteAllAttribute = "delete from Products_SpecifiedAttribute\n"
                    + "where Product_ID = ?";
            String insertProductAttribute = "INSERT INTO Products_SpecifiedAttribute(Product_ID, SpecifiedAttributeValue_ID) VALUES (?,?)";
            String insertSpecifiedAttribute = "insert into SpecifiedAttribute (SpecifiedAttributeName, SpecifiedAttributeQueryName) values (?,?)";
            String insertSpecifiedAttributeValue = "insert into SpecifiedAttributeValue(SpecifiedAttribute_ID, SpecifiedAttributeValueName) values (?,?)";
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(updateProduct);
            stm.setNString(1, p.getProductName());
            stm.setString(2, p.getPreviewImage());
            stm.setInt(3, p.getInsurance());
            stm.setInt(4, p.getOriginalPrice());
            stm.setInt(5, p.getInitialPrice());
            stm.setInt(6, p.getUnitPrice());
            stm.setInt(7, p.getQuantity());
            stm.setInt(8, p.getStatus().getId());
            stm.setInt(9, p.getCategory().getId());
            stm.setInt(10, p.getBrand().getId());
            stm.setInt(11, p.getId());
            stm.executeUpdate();

            stm = connection.prepareStatement(updateProductDetails);
            stm.setString(1, product.getImagesToDB());
            stm.setString(2, product.getDescription().toString());
            stm.setInt(3, p.getId());
            stm.executeUpdate();

            stm = connection.prepareStatement(deleteAllAttribute);
            stm.setInt(1, p.getId());
            stm.executeUpdate();

            PreparedStatement stm1 = connection.prepareStatement(insertProductAttribute); // insert productSpecifi
            for (SpecifiedAttributeValue sp : p.getSpecifiedAttributeValues()) {
                int specifiedAttributeId = sp.getSpecifiedAttribute().getId();
                int specifiedAttributeValueId = sp.getId();
                if (sp.getSpecifiedAttribute().getId() == 0) {
                    stm = connection.prepareStatement(insertSpecifiedAttribute, Statement.RETURN_GENERATED_KEYS);
                    stm.setString(1, sp.getSpecifiedAttribute().getName());
                    stm.setString(2, sp.getSpecifiedAttribute().getFilterName());
                    if (stm.executeUpdate() <= 0) {
                        System.out.println("Insert SpecifiedAttribute failed");
                    }
                    rs = stm.getGeneratedKeys();
                    while (rs.next()) {
                        specifiedAttributeId = rs.getInt(1); // lay ra specified attribute value
                    }
                }
                if (sp.getId() == 0) {
                    stm = connection.prepareStatement(insertSpecifiedAttributeValue, Statement.RETURN_GENERATED_KEYS);
                    stm.setInt(1, specifiedAttributeId);
                    stm.setString(2, sp.getName());
                    if (stm.executeUpdate() <= 0) {
                        System.out.println("Insert SpecifiedAttributeValue failed");
                    }
                    rs = stm.getGeneratedKeys();
                    while (rs.next()) {
                        specifiedAttributeValueId = rs.getInt(1); // lay ra id của specifiedAttributeValue
                    }
                }
                stm1.setInt(1, p.getId());
                stm1.setInt(2, specifiedAttributeValueId);
                stm1.executeUpdate();
            }
            connection.commit();
            rs.close();
            stm.close();
            stm1.close();
            connection.close();
        } catch (SQLException e) {
            // If there is an error then rollback the changes.
            try {
                if (connection != null) {
                    connection.rollback();
                }
                return -1;
            } catch (SQLException se2) {
                throw new Exception("Rolling back data because somthing error");
            }

        } finally {
            // finally block used to close resources
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException se2) {
                throw new Exception("Rolling back data because somthing error");
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                throw new Exception(se);
            }
            return 1;
        }
    }

    public int currentQuantityOfProduct(int productId) {
        try {
            String sql = "select Quantity from Products where Product_ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productId);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public Map<SpecifiedAttribute, List<SpecifiedAttributeValue>> findAllSpecifiedAttributeAndValue() {
        Map<SpecifiedAttribute, List<SpecifiedAttributeValue>> m = new HashMap<>();
        try {
            String sql = "select COUNT (*) OVER (PARTITION BY SA.SpecifiedAttribute_ID) AS ROW_COUNT, SA.SpecifiedAttribute_ID, SA.SpecifiedAttributeName, SA.SpecifiedAttributeQueryName\n"
                    + ", SAV.SpecifiedAttributeValue_ID, SAV.SpecifiedAttributeValueName, count(PSA.Product_ID) as NumOfProduct from SpecifiedAttribute SA\n"
                    + "join SpecifiedAttributeValue SAV\n"
                    + "ON SA.SpecifiedAttribute_ID = SAV.SpecifiedAttribute_ID\n"
                    + "join Products_SpecifiedAttribute PSA\n"
                    + "on PSA.SpecifiedAttributeValue_ID = SAV.SpecifiedAttributeValue_ID\n"
                    + "group by SA.SpecifiedAttribute_ID, SA.SpecifiedAttributeName, SA.SpecifiedAttributeQueryName\n"
                    + ", SAV.SpecifiedAttributeValue_ID, SAV.SpecifiedAttributeValueName";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            int count = 0;
            List<SpecifiedAttributeValue> attributeValues = new ArrayList<>();
            while (rs.next()) {
                count++;
                int numOfEachAttribute = rs.getInt("ROW_COUNT");
                attributeValues.add(new SpecifiedAttributeValue(rs.getInt("SpecifiedAttributeValue_ID"),
                        rs.getString("SpecifiedAttributeValueName"), rs.getInt("NumOfProduct")));
                if (count == numOfEachAttribute) {
                    SpecifiedAttribute specifiedAttribute = new SpecifiedAttribute(
                            rs.getString("SpecifiedAttributeName"), rs.getInt("SpecifiedAttribute_ID"),
                            rs.getString("SpecifiedAttributeQueryName"));
                    m.put(specifiedAttribute, attributeValues);
                    attributeValues = new ArrayList<>();
                    count = 0;
                }
            }
        } catch (SQLException e) {
        }
        return m;
    }

    public ProductStatus getStatusByProductId(int statusId) {
        String sql = "select * from ProductStatus where Status_ID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, statusId);
            rs = stm.executeQuery();
            while (rs.next()) {
                return new ProductStatus(rs.getString("StatusName"), rs.getInt("Status_ID"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<ProductStatus> getAllProductStatus() {
        String sql = "select * from ProductStatus";
        List<ProductStatus> status = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                status.add(new ProductStatus(rs.getString("StatusName"), rs.getInt("Status_ID")));
            }
        } catch (SQLException e) {
        }
        return status;
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try {
            String sql = "select * from Products";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("Product_ID"));
                p.setProductName(rs.getNString("ProductName"));
                p.setPreviewImage(rs.getString("PreviewImage"));
                p.setInsurance(rs.getInt("Insurance"));
                p.setOriginalPrice(rs.getInt("OriginalPrice"));
                p.setInitialPrice(rs.getInt("InitialPrice"));
                p.setUnitPrice(rs.getInt("UnitPrice"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setStatus(new ProductDAO().getStatusByProductId(rs.getInt("Status_ID")));
                p.setCategory(new CategoryDAO().findById(rs.getInt("Category_ID")));
                p.setBrand(new BrandDAO().findById(rs.getInt("Brand_ID")));
                p.setCreatedTime(rs.getTimestamp("CreatedTime"));
                p.setUpdatedTime(rs.getTimestamp("UpdatedTime"));
                products.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return products;
    }

    public int changeStatus(int productId, int statusId) {
        String sql = "update Products set Status_ID = ? , UpdatedTime = getdate() where Product_ID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, statusId);
            stm.setInt(2, productId);
            return stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public int updateProductQuick(int productId, String name, int categoryId, int price, int quantity) {
        String sql = "update Products set ProductName = ?, UnitPrice = ?, Quantity = ?, Category_ID = ? , UpdatedTime = getdate() where Product_ID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, name);
            stm.setInt(2, price);
            stm.setInt(3, quantity);
            stm.setInt(4, categoryId);
            stm.setInt(5, productId);
            return stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public boolean checkProductInOrderDetail(int productId) {
        String sql = "select * from OrderDetails where Product_ID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public List<SpecifiedAttributeValue> getSpecifiedAttributeValueOfProduct(int productId) {
        List<SpecifiedAttributeValue> attributeValues = new ArrayList<>();
        String sql = "select sav.SpecifiedAttribute_ID, sav.SpecifiedAttributeName, sav.SpecifiedAttributeQueryName, SA.SpecifiedAttributeValue_ID, sa.SpecifiedAttributeValueName\n"
                + "from SpecifiedAttributeValue SA join Products_SpecifiedAttribute PS \n"
                + "ON PS.SpecifiedAttributeValue_ID = SA.SpecifiedAttributeValue_ID\n"
                + "JOIN SpecifiedAttribute SAV\n"
                + "ON SA.SpecifiedAttribute_ID = sav.SpecifiedAttribute_ID\n"
                + "WHERE PS.Product_ID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productId);
            rs = stm.executeQuery();
            while (rs.next()) {
                SpecifiedAttribute specifiedAttribute = new SpecifiedAttribute(rs.getString("SpecifiedAttributeName"),
                        rs.getInt("SpecifiedAttribute_ID"), rs.getString("SpecifiedAttributeQueryName"));

                SpecifiedAttributeValue attributeValue = new SpecifiedAttributeValue(specifiedAttribute,
                        rs.getString("SpecifiedAttributeValueName"), rs.getInt("SpecifiedAttributeValue_ID"));

                attributeValues.add(attributeValue);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return attributeValues;
    }
}
