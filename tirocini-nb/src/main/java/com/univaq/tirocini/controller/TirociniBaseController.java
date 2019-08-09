/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.FailureResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author carlo
 */
public abstract class TirociniBaseController extends HttpServlet {

    @Resource(name = "jdbc/webdb2")
    private DataSource ds;

    protected abstract void action_default(HttpServletRequest request, HttpServletResponse response)
            throws Exception;

    protected void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void prepareDataLayer(HttpServletRequest request, HttpServletResponse response, TirocinioDataLayer datalayer) throws DataException {
        //WARNING: never declare DB-related objects including references to Connection and Statement (as our data layer)
        //as class variables of a servlet. Since servlet instances are reused, concurrent requests may conflict on such
        //variables leading to unexpected results. To always have different connections and statements on a per-request
        //(i.e., per-thread) basis, declare them in the doGet, doPost etc. (or in methods called by them) and 
        //(possibly) pass such variables through the request.
        datalayer.init();
        request.setAttribute("datalayer", datalayer);

        request.setAttribute("class", this.getClass().getSimpleName());
    }

    private void processBaseRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        if (!checkAllowed(request)) {
            try {
                goToDefault(request, response);
            } catch (IOException ex) {
                ex.printStackTrace(); //for debugging only
                (new FailureResult(getServletContext())).activate(
                        (ex.getMessage() != null || ex.getCause() == null) ? ex.getMessage() : ex.getCause().getMessage(), request, response);
            }
            return;
        }

        try (TirocinioDataLayer datalayer = new TirocinioDataLayer(ds)) {
            prepareDataLayer(request, response, datalayer);
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace(); //for debugging only
            (new FailureResult(getServletContext())).activate(
                    (ex.getMessage() != null || ex.getCause() == null) ? ex.getMessage() : ex.getCause().getMessage(), request, response);
        }
    }

    private void processBasePost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try (TirocinioDataLayer datalayer = new TirocinioDataLayer(ds)) {
            prepareDataLayer(request, response, datalayer);
            processPostRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace(); //for debugging only
            (new FailureResult(getServletContext())).activate(
                    (ex.getMessage() != null || ex.getCause() == null) ? ex.getMessage() : ex.getCause().getMessage(), request, response);
        }
    }

    //a base mathod that just calls action_default and handles exceptions
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            action_default(request, response);
        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    //a base mathod that just calls action_default and handles exceptions
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            action_default(request, response);
        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    /* Utility methods */
    protected void notFound(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("NotFound");
        view.forward(request, response);
    }

    protected void goBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("referrer") != null) {
            response.sendRedirect(request.getParameter("referrer"));
        } else {
            response.sendRedirect("Home");
        }
    }

    protected void goToDefault(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = SecurityLayer.checkSession(request);
        if (session != null && session.getAttribute("DefaultPage") != null) {
            response.sendRedirect((String) session.getAttribute("DefaultPage"));
        } else {
            response.sendRedirect("Home");
        }
    }

    /**
     * Controlla che l'utente possa visualizzare la pagine
     *
     */
    private boolean checkAllowed(HttpServletRequest request) {
        HttpSession session = SecurityLayer.checkSession(request);
        String requested = request.getServletPath();
        if (session == null)
        {
            //ogni servlet dovrebbe reindirizzare
            //le richieste senza sessione
            //si può migliorare
            
            //System.out.println("[] -> " + requested);
            return true;
        }
        //System.out.println(session.getAttribute("username") + "->" + requested);

        List<String> forbidden = (List<String>) session.getAttribute("ForbiddenPages");
        if (forbidden != null && forbidden.contains(requested)) {
            return false;
        }

        List<String> allowed = (List<String>) session.getAttribute("AllowedPages");
        //se non impostato AllowedPages permettiamo tutto
        return (allowed == null || allowed.contains(requested));
    }

    ////////////////////////////////////////////////////////////////////////////
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processBaseRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processBasePost(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return this.getClass().getSimpleName() + " servlet";
    }// </editor-fold>
}
