package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.AziendaDAO;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrea
 */
public class Statistiche extends TirociniBaseController {
    
 @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, TemplateManagerException, DataException {

       /*  HttpSession session = SecurityLayer.checkSession(request);
        if (session == null || !session.getAttribute("type").equals("admin")) {
            notFound(request, response);
            return;
        } */
        
        
        List<Azienda> listaaziende = ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAziendeConvenzionate();
   
        request.setAttribute("aziende",listaaziende);
        request.setAttribute("page_title", "Statistiche");

        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("statistiche.ftl.html", request, response);
    }
    
    
     @Override
    public String getServletInfo() {
        return "Carica il pannello statistiche dell'amministratore.";
    }
    
}
