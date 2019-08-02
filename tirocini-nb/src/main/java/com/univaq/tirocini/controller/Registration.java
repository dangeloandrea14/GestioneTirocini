/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.framework.result.FailureResult;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
public class Registration extends TirociniBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        request.setAttribute("page_title", "Registration");
        request.setAttribute("outline_tpl", "outline_alt.ftl.html");

        TemplateResult res = new TemplateResult(getServletContext());

        String p = request.getParameter("s");
        //nel template il parametro s indica il tipo di registrazione desiderata
        //se non è ancora stato impostato (o non è valido), facciamo scegliere
        //il tipo di registrazione che si vuole effetturare
        
        if (p == null) {
            res.activate("registrationChooser.ftl.html", request, response);
        }
        else if (p.equals("studente")) {
            res.activate("registrazioneStudente.ftl.html", request, response);
        }
        else if (p.equals("azienda")) {
            res.activate("registrazioneAzienda.ftl.html", request, response);
        }
        else {
            res.activate("registrationChooser.ftl.html", request, response);
        }
    }
    
    private void registerNewStudent (HttpServletRequest request, HttpServletResponse response) {
        
    }
    
    private void registrationSuccess () {
        
    }
    
    private void registrationFailure () {
        
    }
    

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            action_default(request, response);

        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Registration servlet";
    }// </editor-fold>

}
