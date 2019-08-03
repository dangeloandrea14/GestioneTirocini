/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.FailureResult;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
public class DettagliOfferta extends TirociniBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        
        String p = request.getParameter("o"); //id offerta
        
        if (p == null || !p.matches("\\d*")) { //restituiamo not found
            notFound(request, response);
            return;
        }

        Offerta offerta;
        offerta = ((TirocinioDataLayer) request.getAttribute("datalayer")).getOffertaDAO().getOfferta(Integer.parseInt(p));

        if (offerta == null) { //restituiamo not found
            notFound(request, response);
            return;
        }

        request.setAttribute("page_title", "Dettagli offerta tirocinio");
        request.setAttribute("offerta", offerta);

        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("dettagliOfferta.ftl.html", request, response);
    }

    private void notFound(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("notFound.html");
        view.forward(request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            action_default(request, response);

        } catch (IOException | TemplateManagerException | DataException ex) {
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
        return "Company details servlet";
    }// </editor-fold>
}
