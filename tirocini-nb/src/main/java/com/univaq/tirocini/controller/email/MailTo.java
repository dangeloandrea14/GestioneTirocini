/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller.email;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.email.Mail;
import com.univaq.tirocini.framework.email.TemplateEmail;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author carlo
 */
public class MailTo {
    
    /**
     * Send mail to a student, filling the template using datamodel from request
     *
     * @param studente
     * @param subject
     * @param template
     * @param request
     * @throws TemplateManagerException
     * @throws UnsupportedEncodingException
     */
    public static void Student(Studente studente, String subject, String template, HttpServletRequest request) throws TemplateManagerException, UnsupportedEncodingException {
        Mail mail = new Mail();
        
        mail.setRecipient(studente.getEmail());
        mail.setSubject(subject);
        
        Map datamodel = getRequestDataModel(request);
        mail.setBody(TemplateEmail.CreateEmailFromTemplate(request, template, datamodel));
        
        mail.send(request.getServletContext());
    }
    
    public static void Company(Azienda azienda, String subject, String template, HttpServletRequest request) throws TemplateManagerException, UnsupportedEncodingException {
        Mail mail = new Mail();
        
        mail.setRecipient(azienda.getEmailResponsabile());
        mail.setSubject(subject);
        
        Map datamodel = getRequestDataModel(request);
        mail.setBody(TemplateEmail.CreateEmailFromTemplate(request, template, datamodel));
        
        mail.send(request.getServletContext());
    }
    
    private static Map getRequestDataModel(HttpServletRequest request) {
        Map datamodel = new HashMap();
        Enumeration attrs = request.getAttributeNames();
        while (attrs.hasMoreElements()) {
            String attrname = (String) attrs.nextElement();
            /*
            //aggiungiamo solo l'ogetto "mail"
            if(!attrname.equals("mail"))
                continue;
            */
            datamodel.put(attrname, request.getAttribute(attrname));
        }
        return datamodel;
    }
}
