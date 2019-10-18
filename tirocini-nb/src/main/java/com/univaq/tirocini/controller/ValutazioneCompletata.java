/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.controller.permissions.UserObject;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.impl.ValutazioneImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.result.UserRole;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrea
 */
public class ValutazioneCompletata extends TirociniBaseController {
    
    
  @Override
   protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       
   try{
       
    request.setAttribute("page_title", "Valutazione");
    
     String p = request.getParameter("azienda");
        if (p == null || !p.matches("\\d*")) {
            notFound(request, response);
            return;
        }

    Azienda azienda;

    azienda = ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(Integer.parseInt(p));  
    
     String p2 = request.getParameter("studente");
        if (p2 == null || !p2.matches("\\d*")) {
            notFound(request, response);
            return;
        }

    Studente studente;
    
    studente = ((TirocinioDataLayer) request.getAttribute("datalayer")).getStudenteDAO().getStudente(Integer.parseInt(p2));
    
    //Controlliamo che l'utente non abbia fatto casini
    if (!studente.equals( ((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getStudente()))
    {
        request.setAttribute("message", "Utente errato.");
        action_error(request, response);
    }

           
    request.setAttribute("page_title", "Valutazione Completata");
    
    ValutazioneImpl val = new ValutazioneImpl();
    
    String votostring = request.getParameter("voto");
    
    Integer voto = Integer.parseInt(votostring);
    
    //Controlliamo che l'utente non abbia fatto casini
    if(voto < 0 || voto > 5){
    
        TemplateResult res = new TemplateResult(getServletContext());
        
        request.setAttribute("a", azienda);

        res.activate("valutazione.ftl.html", request, response);
    }
    
    //Costruiamo la valutazione
    val.setStelle(voto);
    val.setAzienda(azienda);
    val.setStudente(studente);
    String commento = request.getParameter("commento");
    
    if(commento.isEmpty()){
    commento="Nessun commento";
    }
    
    val.setCommento(commento);
   
    
    ((TirocinioDataLayer) request.getAttribute("datalayer")).getValutazioneDAO().storeValutazione(val);
    
    
    //Calcoliamo la nuova media dell'azienda
    List<com.univaq.tirocini.data.model.Valutazione> valutazioniazienda =  ((TirocinioDataLayer)request.getAttribute("datalayer")).getValutazioneDAO().getValutazioni(azienda);
    
    int somma=0;
    for(com.univaq.tirocini.data.model.Valutazione valaz : valutazioniazienda){
        
        somma = somma + valaz.getStelle();
        
    }
    
    int media = somma/valutazioniazienda.size();
   
    azienda.setVoto(media);
    
    ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().storeAzienda(azienda);
    
        request.setAttribute("media",media);
        request.setAttribute("message","Valutazione effettuata");
        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("home.ftl.html", request, response); 
        
       } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
   
   

 
}
