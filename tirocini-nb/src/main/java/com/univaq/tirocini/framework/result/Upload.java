/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.framework.result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * Come il codice del prof
 */
public class Upload {

    public File uploadFile(String uploadingName,
            String serverFilename,
            HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, java.io.IOException, NoSuchAlgorithmException {
        Part file_to_upload = request.getPart(uploadingName);

        //we want the sha-1 file digest of the uploaded file
        //vogliamo creare il digest sha-1 del file
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        //create a file (with a unique name) and copy the uploaded file to it
        //creiamo un nuovo file (con nome univoco) e copiamoci il file scaricato
        File uploaded_file = File.createTempFile(serverFilename, "",
                new File(request.getServletContext().getInitParameter("uploads.directory")));
        try (InputStream is = file_to_upload.getInputStream();
                OutputStream os = new FileOutputStream(uploaded_file)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) > 0) {
                //durante la copia, aggreghiamo i byte del file nel digest sha-1
                //while copying, we aggregate the file bytes in the sha-1 digest
                md.update(buffer, 0, read);
                os.write(buffer, 0, read);
            }
        }

        //get the file digest as a string
        //covertiamo il digest in una stringa
        byte[] digest = md.digest();
        String sdigest = "";
        for (byte b : digest) {
            sdigest += String.valueOf(b);
        }

        return uploaded_file;
    }
}
