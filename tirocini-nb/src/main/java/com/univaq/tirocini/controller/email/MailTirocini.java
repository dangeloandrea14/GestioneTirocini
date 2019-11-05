/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller.email;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.email.Mail;
import com.univaq.tirocini.framework.email.SendEmail;
import com.univaq.tirocini.framework.email.TemplateEmail;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author carlo
 */
public class MailTirocini {

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
    public static void send(Studente studente, String subject, String template, HttpServletRequest request) throws TemplateManagerException, UnsupportedEncodingException {
        send(studente.getEmail(), subject, template, request);
    }

    public static void send(Azienda azienda, String subject, String template, HttpServletRequest request) throws TemplateManagerException, UnsupportedEncodingException {
        send(azienda.getEmailResponsabile(), subject, template, request);
    }

    public static void send(String recipient,
            String subject, String template, HttpServletRequest request) {
        
        Mail mail = new Mail();

        mail.setRecipient(recipient);
        mail.setSubject(subject);

        mail.setBody(TemplateEmail.CreateEmailFromTemplate(request, template));

        mail.send(request.getServletContext());
    }
}
