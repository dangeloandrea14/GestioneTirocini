/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.FailureResult;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.Password;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
public class Login extends TirociniBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        if (SecurityLayer.checkSession(request) == null) {
            request.setAttribute("page_title", "Login");
            TemplateResult res = new TemplateResult(getServletContext());

            res.activate("login.ftl.html", request, response);
        } else {
            response.sendRedirect("Home");
        }
    }

    //controlla username e password e se validi crea una sessione
    //  non pulitissimo ma per ora ci accontentiamo
    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("u");
        String password = request.getParameter("p");
        //... VALIDAZIONE IDENTITA'...
        //... IDENTITY CHECKS ...

        if (username.isEmpty() || password.isEmpty()) {
            login_failed(request, response);
            return;
        }

        String loginType = "";
        String passwordHash = null;

        try {

            //provo a caricare dagli studenti
            passwordHash = ((TirocinioDataLayer) request.getAttribute("datalayer")).getStudenteDAO().getPasswordFromEmail(username);

            if (passwordHash != null) { //trovato studenti
                loginType = "studente";
            } else {

                //se non trovo uno studente provo con un'azienda
                passwordHash = ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getPasswordFromEmail(username);
                loginType = "azienda";

                if (passwordHash == null) { //se ancora null, login invalido
                    login_failed(request, response);
                    return;
                }
            }

        } catch (DataException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        //verifichiamo la password
        if (!Password.verify(password, passwordHash)) {
            login_failed(request, response);
            return;
        }

        //creiamo la sessione
        int userid = 1;
        SecurityLayer.createSession(request, username, userid, loginType);
        //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
        //if an origin URL has been transmitted, return to it
        goBack(request, response);
    }

    private void login_failed(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("exception", new Exception("Login failed"));
        action_error(request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            if (request.getMethod().equals("POST")) {
                action_login(request, response);
            } else {
                action_default(request, response);
            }

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
        return "Login servlet";
    }// </editor-fold>

}
