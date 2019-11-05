/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
public class DettagliAzienda extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        String p = SecurityLayer.addSlashes(request.getParameter("a"));
        if (p == null || !p.matches("\\d*")) {
            notFound(request, response);
            return;
        }
         TirocinioDataLayer datalayer = dataLayer(request);
        Azienda azienda;

        azienda = dataLayer(request).getAziendaDAO().getAzienda(SecurityLayer.checkNumeric(p));

        if (azienda == null) {
            notFound(request, response);
            return;
        }
        
         azienda = datalayer.getAziendaDAO().getAzienda(SecurityLayer.checkNumeric(p));
       
        List<com.univaq.tirocini.data.model.Valutazione> voti = datalayer.getValutazioneDAO().getValutazioni(azienda);
        
        int unastella=0,duestelle=0,trestelle=0,quattrostelle=0,cinquestelle=0;
        
        
        for (com.univaq.tirocini.data.model.Valutazione voto : voti){
            
            switch(voto.getStelle()){
                case 1: unastella++;
                    break;
                case 2: duestelle++;
                    break;
                case 3: trestelle++;
                    break;
                case 4: quattrostelle++;
                    break;
                case 5: cinquestelle++;
                    break;
            }
         
        }
        
       
        
        
           request.setAttribute("unastella",unastella);
        request.setAttribute("duestelle",duestelle);
        request.setAttribute("trestelle",trestelle);
        request.setAttribute("quattrostelle",quattrostelle);
        request.setAttribute("cinquestelle",cinquestelle);
        request.setAttribute("page_title", "Dettagli " + azienda.getNome());
        request.setAttribute("aziendap", azienda);
        request.setAttribute("offerte", dataLayer(request).getOffertaDAO().getOfferte(azienda));

        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("dettagliAzienda.ftl.html", request, response);
    }
      @Override
    public String getServletInfo() {
        return "Carica i dettagli dell'azienda scelta.";
    }
    
}
