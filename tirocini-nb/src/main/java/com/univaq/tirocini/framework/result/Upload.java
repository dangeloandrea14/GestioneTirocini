/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.framework.result;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author carlo
 */
public class Upload {

    public static File uploadFile(String uploadingName,
            String username,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, java.io.IOException, NoSuchAlgorithmException {

        Part part = request.getPart(uploadingName);

        File file = File.createTempFile(username+"-", ".pdf");

        try (InputStream input = part.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return file;
    }
}
