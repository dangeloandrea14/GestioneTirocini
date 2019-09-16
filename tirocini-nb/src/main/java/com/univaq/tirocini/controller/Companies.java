/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.AziendaDAO;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
public class Companies extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, TemplateManagerException, DataException {

        AziendaDAO aziendaDAO = ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO();
        
        int currentPage;
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        } else {
            currentPage = 1;
        }
        request.setAttribute("page", currentPage);

        String searchString = request.getParameter("q");
        if (searchString != null) {
            request.setAttribute("page_title", "Aziende" + " - " + "\"" + searchString + "\"");
            request.setAttribute("aziende", (aziendaDAO.searchAzienda(searchString)));
            request.setAttribute("searchString", searchString);
        } else {
            request.setAttribute("page_title", "Aziende");
            request.setAttribute("aziende", (aziendaDAO.getPaginaAziendeConvenzionate(currentPage, 20)));
        }

        int numeroConvenzionate = aziendaDAO.getAziendeConvenzionateCount();
        request.setAttribute("numAziende", numeroConvenzionate);
        request.setAttribute("pageCount", numeroConvenzionate / 20);

        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("companies.ftl.html", request, response);
    }
}
