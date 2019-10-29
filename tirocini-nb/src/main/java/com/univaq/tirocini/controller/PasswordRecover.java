/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.email.Mail;
import com.univaq.tirocini.framework.email.SendEmail;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
public class PasswordRecover extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        if (SecurityLayer.checkSession(request) == null) {
            request.setAttribute("page_title", "Recupera Password");
            request.setAttribute("outline_tpl", "/common/outline_login.ftl.html");

            TemplateResult res = new TemplateResult(getServletContext());

            res.activate("passwordRecover.ftl.html", request, response);
        } else {
            response.sendRedirect("Home");
        }
    }

    
    private void action_recover(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, ServletException, TemplateManagerException {
        String email = request.getParameter("e");

        if (email.isEmpty()) {
            action_default(request, response);
            return;
        }
        
        sendRecoverMail(email);
        
        try {
            request.setAttribute("message", "Email di recupero inviata.");
            request.setAttribute("color", "info");
            request.setAttribute("page_title", "Recupera Password");
            request.setAttribute("username", request.getParameter("u"));
            request.setAttribute("outline_tpl", "/common/outline_login.ftl.html");
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("passwordRecover.ftl.html", request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
        
    }

    private void sendRecoverMail(String email) {
        Mail mail = new Mail();
        mail.setRecipient(email);
        mail.setSubject("Recupero credenziali");
        mail.setBody("Non ancora implementato");
       
        SendEmail sendemail = new SendEmail(getServletContext());
        sendemail.SendEmail(mail);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            action_default(request, response);

        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        }
    }

    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            action_recover(request, response);

        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Recupera la passowrd di un utente.";
    }

}
