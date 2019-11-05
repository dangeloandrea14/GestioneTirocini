
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.AziendaDAO;
import com.univaq.tirocini.framework.result.TemplateResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
public class Admin extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        request.setAttribute("page_title", "Admin panel");
       
        request.setAttribute("aziende", dataLayer(request).getAziendaDAO().getAziendeNonConvenzionate());
        AziendaDAO aziendaDAO=dataLayer(request).getAziendaDAO(); 
        int numeroAttive= aziendaDAO.getAziendeConvenzionateCount();
        
        request.setAttribute("numOfferte", numeroAttive);
        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("admin.ftl.html", request, response);
    }
    
        @Override
    public String getServletInfo() {
        return "Servlet per il caricamento del pannello di amministrazione. ";
    }
    
    
}