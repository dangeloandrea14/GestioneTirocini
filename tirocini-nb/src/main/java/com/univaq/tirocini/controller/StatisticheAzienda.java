/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.data.DataLayer;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrea
 */
public class StatisticheAzienda extends TirociniBaseController {
    
    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, TemplateManagerException, DataException {

       /*  HttpSession session = SecurityLayer.checkSession(request);
        if (session == null || !session.getAttribute("type").equals("admin")) {
            notFound(request, response);
            return;
        } */
        
        
       TirocinioDataLayer datalayer = ((TirocinioDataLayer) request.getAttribute("datalayer"));
       //Vediamo quale azienda è stata scelta
       //Non inseriamo validazione perché, se l'azienda viene cambiata... non succede nulla. 
       //Vorrebbe dire che l'admin ha cambiato il parametro, ma... ok?
        String p = request.getParameter("a");
        if (p == null || !p.matches("\\d*")) {
            notFound(request, response);
            return;
        }

        Azienda azienda;

        azienda = datalayer.getAziendaDAO().getAzienda(Integer.parseInt(p));
       
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
        
        
        
        request.setAttribute("voti",voti);
        request.setAttribute("unastella",unastella);
        request.setAttribute("duestelle",duestelle);
        request.setAttribute("trestelle",trestelle);
        request.setAttribute("quattrostelle",quattrostelle);
        request.setAttribute("cinquestelle",cinquestelle);
        request.setAttribute("azienda",azienda);
        request.setAttribute("page_title", "Statistiche" + azienda.getNome());

        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("statisticheazienda.ftl.html", request, response);
    }
    
    
}
