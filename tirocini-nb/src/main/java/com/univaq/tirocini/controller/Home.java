/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

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
public class Home extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, TemplateManagerException, DataException {
        request.setAttribute("outline_tpl", "outline1.ftl.html");
        request.setAttribute("page_title", "Home");
        request.setAttribute("offerte", ((TirocinioDataLayer) request.getAttribute("datalayer")).getOffertaDAO().getOfferteAttive());
        request.setAttribute("aziende", ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getPaginaAziendeConvenzionate(1, 3));

        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("home.ftl.html", request, response);
    }
    
     @Override
    public String getServletInfo() {
        return "Carica la homepage.";
    }
    
}
