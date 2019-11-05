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
 * @author Andrea
 */
public class StatisticheAzienda extends TirociniBaseController {
    
    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, TemplateManagerException, DataException {
        
       TirocinioDataLayer datalayer = dataLayer(request);
       //Vediamo quale azienda è stata scelta
       //Non inseriamo validazione perché, se l'azienda viene cambiata... non succede nulla. 
       //Vorrebbe dire che l'admin ha cambiato il parametro, ma... ok?
        String p = SecurityLayer.addSlashes(request.getParameter("a"));
        if (p == null || !p.matches("\\d*")) {
            notFound(request, response);
            return;
        }

        Azienda azienda;

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
        
        int percentuale5=0,percentuale4=0,percentuale3=0,percentuale2=0,percentuale1=0;
        //Calcoliamo le percentuali
        if(voti.size()>0){
         percentuale5 = cinquestelle*100/voti.size();
         percentuale4 = quattrostelle*100/voti.size();
         percentuale3 = trestelle*100/voti.size();
         percentuale2 = duestelle*100/voti.size();
         percentuale1 = unastella*100/voti.size();
        }
        
        
        //Costruiamo la lista degli studenti impiegata in tirocini con questa azienda.
        List<Tirocinio> listatirocini = datalayer.getTirocinioDAO().getTirocini(azienda);
        int numerotirocini = listatirocini.size();

        //Costruiamo la lista delle offerte pubblicate
        List<Offerta> listaofferte = datalayer.getOffertaDAO().getOfferte(azienda);
        int numeroofferte = listaofferte.size();
        
        
        
        request.setAttribute("percentuale5",percentuale5);
        request.setAttribute("percentuale4",percentuale4);
        request.setAttribute("percentuale3",percentuale3);
        request.setAttribute("percentuale2",percentuale2);
        request.setAttribute("percentuale1",percentuale1);
        request.setAttribute("numerotirocini",numerotirocini);
        request.setAttribute("listatirocini", listatirocini);
        request.setAttribute("numeroofferte",numeroofferte);
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
    
     @Override
    public String getServletInfo() {
        return "Carica le statistiche relative all'azienda selezionata.";
    }
    
}
