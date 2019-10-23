
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.AziendaDAO;
import com.univaq.tirocini.data.DAO.OffertaDAO;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author carlo
 */
public class Admin extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = SecurityLayer.checkSession(request);
        if (session == null || !session.getAttribute("type").equals("admin")) {
            notFound(request, response);
            return;
        }
        request.setAttribute("page_title", "Admin panel");
       
       // request.setAttribute("offerte", ((TirocinioDataLayer) request.getAttribute("datalayer")).getOffertaDAO().getOfferteAttive());
        request.setAttribute("aziende", ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAziendeNonConvenzionate());
        // OffertaDAO offertaDAO = ((TirocinioDataLayer) request.getAttribute("datalayer")).getOffertaDAO();
        AziendaDAO aziendaDAO=((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO(); 
        int numeroAttive= aziendaDAO.getAziendeConvenzionateCount();
        
        request.setAttribute("numOfferte", numeroAttive);
        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("admin.ftl.html", request, response);
    }
}