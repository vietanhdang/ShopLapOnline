/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.Date;

/**
 *
 * @author vietd
 */
public class Validation {

    public boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z][a-zA-Z0-9]+@[a-zA-Z]{2,}(\\.[a-zA-Z]{2,}){1,3}$");
    }

    public boolean isValidPhoneNumber(String phone) {
        return phone.matches("^[0-9]{10,12}$");
    }
    public boolean isUTF8(String str) {
        return str.matches("^[\\p{ASCII}]*$");
    }
   

    public int isInt(String value, String message) throws Exception {
        int number = 0;
        try {
            number = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new Exception(message);
        }
        return number;
    }

    public double isDouble(String value, String message) throws Exception {
        double number = 0;
        try {
            number = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new Exception(message);
        }
        return number;
    }

    public String isString(String value, int minLength, int maxLength, String message, boolean require) throws Exception {
        if (!require) {
            return value;
        } else {
            if (value.length() >= minLength && value.length() <= maxLength) {
                return value;
            } else {
                throw new Exception(message);
            }
        }
    }

    public boolean isBoolean(String value, String message) throws Exception {
        boolean bool = false;
        try {
            bool = Boolean.parseBoolean(value);
        } catch (Exception e) {
            throw new Exception(message);
        }
        return bool;
    }

    public Date isDate(String value, String message) throws Exception {
        Date date = null;
        try {
            date = Date.valueOf(value);
        } catch (Exception e) {
            throw new Exception(message);
        }
        return date;
    }

}
