/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Benjamin
 */
public class TextProcessing {

    public static List<String> convertTextToImages(String params) {
        List<String> list = new ArrayList<>();
        String[] listImageConvert = params.trim().split("@@@");
        if (listImageConvert.length != 0) {
            for (String string : listImageConvert) {
                list.add(string.trim());
            }
        }
        return list;
    }

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d");
    }

    /**
     * This method is used to get the value of a parameter from the request. and
     * normalize replace all space " " to "-" and remove all accent.
     *
     * @param s
     * @return
     */
    public static String normalizeAttributeQuery(String s) {
        return "filter_laptop_" + removeAccent(s.trim().replaceAll("\\s+", "_").trim().toLowerCase());
    }

}
