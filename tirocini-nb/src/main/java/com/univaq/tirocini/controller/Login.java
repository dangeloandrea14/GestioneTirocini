/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.controller.permissions.Roles;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.result.UserRole;
import com.univaq.tirocini.framework.security.Password;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author carlo
 */
public class Login extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        if (SecurityLayer.checkSession(request) == null) {
            request.setAttribute("page_title", "Login");
            request.setAttribute("outline_tpl", "/common/outline_login.ftl.html");

            TemplateResult res = new TemplateResult(getServletContext());

            res.activate("login.ftl.html", request, response);
        } else {
            response.sendRedirect("Home");
        }
    }

    //controlla username e password e se validi crea una sessione
    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException {
        String username = SecurityLayer.addSlashes(request.getParameter("u"));
        String password = SecurityLayer.addSlashes(request.getParameter("p"));

        //... VALIDAZIONE IDENTITA'...
        //... IDENTITY CHECKS ...
        if (username.isEmpty() || password.isEmpty()) {
            login_failed(request, response);
            return;
        }

        int userid = 1;

        UserRole userRole = null;

        String loginType = "";
        String passwordHash = null;

        Studente s = null;
        Azienda a = null;

        //provo a caricare dagli studenti
        s = dataLayer(request).getStudenteDAO().getStudenteFromEmail(username);

        if (s != null) { //trovato studenti
            userid = s.getKey();
            passwordHash = s.getPassword();
            if (s.getRuolo() == 1) { //usiamo come admin gli studenti con ruolo 1
                loginType = "admin";
            } else {
                loginType = "studente";
            }
        } else {

            //se non trovo uno studente provo con un'azienda
            a = dataLayer(request).getAziendaDAO().getAziendaFromEmail(username);

            if (a == null) { //se ancora null, login invalido
                login_failed(request, response);
                return;
            }
            
            if (a.isConvenzionata()) {
                loginType = "azienda";
            } else {
                loginType = "aziendaNonConvenzionata";
            }
            userid = a.getKey();
            passwordHash = a.getPassword();
        }

        //verifichiamo la password
        if (!Password.verify(password, passwordHash)) {
            login_failed(request, response);
            return;
        }

        //creiamo la sessione
        HttpSession session = SecurityLayer.createSession(request, username, userid, loginType);
        try {
            switch (loginType) {
                case "studente":
                    //se studente o admin carichiamo lo studente
                    userRole = Roles.genStudenteRole(s);
                    break;
                case "azienda":
                    userRole = Roles.genAziendaRole(a);
                    break;
                case "aziendaNonConvenzionata":
                    userRole = Roles.genAziendaNonConvenzionataRole(a);
                    break;
                case "admin":
                    //se studente o admin carichiamo lo studente
                    userRole = Roles.genAdminRole(s);
                    break;
                default:
                    request.setAttribute("exception", new Exception("Login error"));
                    action_error(request, response);
                    return;
            }
        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            return;
        }

        session.setAttribute("userRole", userRole);

        if (loginType.equals("admin")) {
            response.sendRedirect("Admin");
        } else {
            //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
            //if an origin URL has been transmitted, return to it
            goBack(request, response);
        }
    }

    private void login_failed(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("message", "Credenziali non valide");
            request.setAttribute("color", "danger");
            request.setAttribute("page_title", "Login");
            request.setAttribute("username", request.getParameter("u"));
            request.setAttribute("outline_tpl", "/common/outline_login.ftl.html");
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("login.ftl.html", request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
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
            action_login(request, response);

        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Gestisce il login di utenti e aziende.";
    }

}
