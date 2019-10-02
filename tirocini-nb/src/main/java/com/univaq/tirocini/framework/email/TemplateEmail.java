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
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author carlo
 */
public class TemplateEmail {

    public static String CreateEmailFromTemplate(
            HttpServletRequest request, String template, Map datamodel) throws TemplateManagerException, UnsupportedEncodingException {

        // OutputStram containing the email body html
        ByteArrayOutputStream htmlEmailBodyStream = new ByteArrayOutputStream();

        // Use freemarker to populate the template
        // Using TemplateResult requires using this inside a servlet but it's
        // never going to be otherwise. So we can avoid rewriting a lot of code
        
        // exceptions from TemplateManager should be handled here to avoid them
        // being handled as if they were exception in the processing of the
        // web page template
        TemplateResult res = new TemplateResult(request.getServletContext());
        res.activate(template, datamodel, htmlEmailBodyStream);
        
        String emailBody = htmlEmailBodyStream.toString("UTF-8");
        //String emailBody = htmlEmailBodyStream.toString();
        
        return emailBody;
    }
}
