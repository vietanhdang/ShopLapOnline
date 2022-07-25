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
import model.Product;
import model.Slider;

/**
 *
 * @author hoang
 */
public class SliderDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Slider> getInforSliderHaveSliderColectionStatusEqualOn() {
        // initialize a list
        ArrayList<Slider> sliders = new ArrayList<>();
        try {
            //The query statement retrieves information about sliders with attributes title ,
            //description, imageUrl,productID, from the slidercolection table with the condition status =1
            String sql = "select s.title,s.description,s.imageUrl,s.product_ID from slidercolection sc \n"
                    + "join sliders s on s.SliderColection_ID = sc.SliderColection_ID\n"
                    + "where sc.status = 1";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Slider s = new Slider();
                s.setTitle(rs.getString(1));
                s.setDescription(rs.getString(2));
                s.setImageUrl(rs.getString(3));
                Product p = new Product();
                p.setId(rs.getInt(4));
                s.setProduct(p);
                sliders.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return sliders;
    }
}
