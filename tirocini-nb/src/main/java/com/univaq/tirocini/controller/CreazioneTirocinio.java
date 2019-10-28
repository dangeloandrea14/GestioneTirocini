/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.controller.permissions.UserObject;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Candidatura;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrea
 */
public class CreazioneTirocinio extends TirociniBaseController {

    
    @Override
   protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
    request.setAttribute("page_title", "Creazione Tirocinio");
   
    try{
        
    String param = SecurityLayer.addSlashes(request.getParameter("uid"));
    String param2 = SecurityLayer.addSlashes(request.getParameter("oid"));
        
    int uid = SecurityLayer.checkNumeric(param);
    int oid = SecurityLayer.checkNumeric(param2);
    
    Studente studente = (Studente) ((TirocinioDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudente(uid);
    Offerta offerta = (Offerta) ((TirocinioDataLayer)request.getAttribute("datalayer")).getOffertaDAO().getOfferta(oid);
    Azienda azienda = offerta.getAzienda();
    
    if(azienda.getKey() != ((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getAzienda().getKey()){
        String ex = "Azienda errata.";
        throw new DataException(ex);
    }
    
    //Controlliamo che l'utente non abbia fatto casini.
    List<Candidatura> liststud = ((TirocinioDataLayer)request.getAttribute("datalayer")).getCandidaturaDAO().getCandidature(offerta);
    Boolean ok = false;
    
    for (Candidatura c : liststud) {
        
        if(c.getStudente().getKey() == studente.getKey()){
            ok = true;
        }
        
    }
    
    if(!ok){
        String ex = "Utente errato";
        throw new DataException(ex);
    }
    request.setAttribute("studenteT",studente);
    request.setAttribute("offerta",offerta);
    request.setAttribute("aziendaT",azienda);
    
    HttpSession session = request.getSession();
    session.setAttribute("studenteid",studente.getKey());
    session.setAttribute("offertaid",offerta.getKey());
    
    TemplateResult res = new TemplateResult(getServletContext());

    res.activate("creazionetirocinio.ftl.html", request, response);
    } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }   
   }                
}
