/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.framework.email;

import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author carlo
 */
public class TemplateEmail {

    public static String CreateEmailFromTemplate(
            HttpServletRequest request, String template) {

        // OutputStram containing the email body html
        ByteArrayOutputStream htmlEmailBodyStream = new ByteArrayOutputStream();

        // Use freemarker to populate the template
        // Using TemplateResult requires using this inside a servlet but it's
        // never going to be otherwise. So we can avoid rewriting a lot of code
        
        // exceptions from TemplateManager should be handled here to avoid them
        // being handled as if they were exception in the processing of the
        // web page template
        String emailBody = null;
        TemplateResult res = new TemplateResult(request.getServletContext());
               
        
        try {
            res.activate(template, request, htmlEmailBodyStream);
            emailBody = htmlEmailBodyStream.toString("UTF-8");
        } catch (TemplateManagerException | UnsupportedEncodingException ex) {        
            Logger.getLogger(TemplateEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return emailBody;
    }
}
