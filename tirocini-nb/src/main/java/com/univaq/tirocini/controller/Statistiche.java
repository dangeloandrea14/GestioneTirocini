package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrea
 */
public class Statistiche extends TirociniBaseController {
    
 @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, TemplateManagerException, DataException {

        List<Azienda> listaaziende = dataLayer(request).getAziendaDAO().getAziendeConvenzionate();
   
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
