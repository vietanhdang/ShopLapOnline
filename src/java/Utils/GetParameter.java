/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author vietd
 */
public class GetParameter {

    public static String getFolderImage(HttpServletRequest request, String folder) {
        return request.getServletContext().getRealPath("assets/images").replace("build\\", "").concat("\\" + folder)
                .trim();
    }

    public static String getFolderFile(HttpServletRequest request, String folder) {
        return request.getServletContext().getRealPath("assets/files").replace("build\\", "").concat("\\" + folder)
                .trim();
    }

    public static Part getFieldFile(HttpServletRequest request, String fieldName, boolean required) throws Exception {
        Part value = null;
        value = request.getPart(fieldName);
        if (value == null) {
            if (required) {
                String error = "Field " + fieldName + " is required";
                throw new Exception(error);
            } else {
                value = null; // Make empty string null so that you don't need to hassle with equals("")
                // afterwards.
            }
        }
        return value;
    }

    public static String getField(HttpServletRequest request, String fieldName, boolean required) throws Exception {
        String value = null;
        value = request.getParameter(fieldName);
        if (value == null || value.trim().isEmpty()) {
            if (required) {
                String error = "Dữ liệu " + fieldName + " không được để trống";
                throw new Exception(error);
            } else {
                value = null; // Make empty string null so that you don't need to hassle with equals("")
                // afterwards.
            }
        }
        return value;
    }

    public static String[] getFields(HttpServletRequest request, String fieldName, boolean required) throws Exception {
        String[] value = null;
        value = request.getParameterValues(fieldName);
        if (value == null || value.length == 0) {
            if (required) {
                String error = "Dữ liệu " + fieldName + " là bắt buộc";
                throw new Exception(error);
            } else {
                value = null; // Make empty string null so that you don't need to hassle with equals("")
                // afterwards.
            }
        }
        return value;
    }

}
