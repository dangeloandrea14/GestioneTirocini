/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.OffertaDAO;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.framework.data.DataException;
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
public class Offers extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, TemplateManagerException, DataException {
        
        OffertaDAO offertaDAO = ((TirocinioDataLayer) request.getAttribute("datalayer")).getOffertaDAO();
        
        int numeroAttive = offertaDAO.getOfferteAttiveCount();
        // Se divisibile per 20 usiamo valore esatto, altrimenti aggiungiamo una pagina
        // (c'è l'ultima pagina incompleta da visualizzare)
        int pageCount = (numeroAttive%20 != 0) ? numeroAttive/20 + 1 : numeroAttive/20;
        
        int currentPage;
        if (request.getParameter("page") != null) {
            currentPage = SecurityLayer.checkNumeric(request.getParameter("page"));
        } else {
            currentPage = 1;
        }
        
        if(currentPage > pageCount) {
            currentPage=pageCount;
        }
     
        request.setAttribute("page", currentPage);
        
        String searchString = request.getParameter("luogo");
        if (searchString != null) {
            request.setAttribute("page_title", "Offerte" + " - " + "\"" + searchString + "\"");
            request.setAttribute("offerte", (offertaDAO.searchOffertaByPlace(searchString)));
            request.setAttribute("searchString", searchString);
        } else {
            request.setAttribute("page_title", "Offerte");
            request.setAttribute("offerte", (offertaDAO.getPaginaOfferteAttive(currentPage, 20)));
        }

        request.setAttribute("numOfferte", numeroAttive);
        request.setAttribute("pageCount", pageCount);

        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("offers.ftl.html", request, response);
            
        
       /* request.setAttribute("page_title", "Offerte di tirocinio");
        request.setAttribute("offerte", offertaDAO.getPaginaOfferteAttive(currentPage, 20));
        
        request.setAttribute("numOfferte", numeroAttive);
        request.setAttribute("pageCount", pageCount);
        
        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("offers.ftl.html", request, response); */
    }
    
      @Override
    public String getServletInfo() {
        return "Carica la pagina delle offerte.";
    }
}
