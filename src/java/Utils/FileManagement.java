/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author vietd
 */
public class FileManagement {

    public static String uploadFile(Part part, String folder) throws IOException {
        String extention = getFileExtension(part.getSubmittedFileName());
        if (extention.equals("")) {
            throw new IOException("File không hợp lệ");
        }
        String nomalizeName = randomUUID() + "." + extention; // create a new name for the image
        Path path = Paths.get(folder); // get the path of the folder
        if (!Files.exists(path)) { // if the folder doesn't exist, create it
            Files.createDirectory(path);
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File outputFilePath = new File(Paths.get(path.toString(), nomalizeName).toString()); // create a file with
            // the name of the
            // image
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) { // create a file output stream to write
                // the image to the file
                InputStream is = part.getInputStream(); // get the input stream of the image
                byte[] data = new byte[is.available()]; // create a byte array to store the image
                is.read(data); // read the image into the byte array
                fos.write(data); // write the image to the file output stream
            }
        } catch (IOException e) {
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return nomalizeName; // return the name of the image
    }

    public static void delete(String fileName, String folder) throws IOException {
        Path path = Paths.get(folder);
        try {
            File file = new File(Paths.get(path.toString(), fileName).toString());
            file.delete();
        } catch (Exception e) {
            throw new EOFException("Không tìm thấy ảnh cũ");
        }
    }

    public static List<String> uploadFiles(String UPLOAD_DIR, String param, HttpServletRequest request, boolean require) throws Exception {
        List<String> fileNames = new ArrayList<>();
        try {
            List<Part> parts = (List<Part>) request.getParts();
            if (parts.size() <= 0) {
                return new ArrayList<>();
            }
            boolean isEmpty = true;
            for (Part part : parts) {
                if (part.getName().equalsIgnoreCase(param) && part.getSize() > 0) {
                    fileNames.add(uploadFile(part, UPLOAD_DIR));
                    isEmpty = false;
                }
            }
            if (isEmpty && require) {
                throw new Exception("Cần phải có hình ảnh");
            }
        } catch (IOException | ServletException e) {
            
        }
        return fileNames;
    }

    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    public static String randomUUID() {
        return java.util.UUID.randomUUID().toString();
    }
}
