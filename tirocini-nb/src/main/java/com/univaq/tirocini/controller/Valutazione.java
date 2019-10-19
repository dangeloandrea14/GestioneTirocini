/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.controller.permissions.UserObject;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.result.UserRole;
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
public class Valutazione extends TirociniBaseController{
             
     
protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {

    request.setAttribute("page_title", "Valutazione");
    
     String p = request.getParameter("a");
        if (p == null || !p.matches("\\d*")) {
            notFound(request, response);
            return;
        }

    Azienda azienda;

    azienda = ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(Integer.parseInt(p));
    
    Boolean already = false;
    
    
    if (SecurityLayer.checkSession(request) == null){ 
            response.sendRedirect("Login");
    }
        
    Studente studente = ((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getStudente();    
            
    List<Tirocinio> tirocini = ((TirocinioDataLayer) request.getAttribute("datalayer")).getTirocinioDAO().getTirocini(studente);
    
    List<com.univaq.tirocini.data.model.Valutazione> valutazioni = ((TirocinioDataLayer) request.getAttribute("datalayer")).getValutazioneDAO().getValutazioni(studente);
    
    //Controlliamo se lo studente ha già valutato quella azienda.
   
    for (com.univaq.tirocini.data.model.Valutazione v: valutazioni){
        if (v.getAzienda().equals(azienda))
        {
            already = true;
        }
    }
    
    if(tirocini.isEmpty()){
        
        response.sendRedirect("Home");
        
    }
    
    
    request.setAttribute("already",already);
    request.setAttribute("azienda", azienda);
    request.setAttribute("studente", studente);
    
    TemplateResult res = new TemplateResult(getServletContext());

    res.activate("valutazione.ftl.html", request, response);
    
    
        
    }


 @Override
    public String getServletInfo() {
        return "Permette allo studente di valutare l'azienda con la quale ha effettuato il tirocinio.";
    }



}
