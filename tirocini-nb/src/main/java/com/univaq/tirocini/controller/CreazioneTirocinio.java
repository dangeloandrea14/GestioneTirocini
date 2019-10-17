/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shoken
 */
public class CreazioneTirocinio extends TirociniBaseController {

    
    @Override
   protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
    request.setAttribute("page_title", "Creazione Tirocinio");
   
    try{
    String param = request.getParameter("uid");
    String param2 = request.getParameter("oid");
        
    int uid = Integer.parseInt(param);
    int oid = Integer.parseInt(param2);
    
    Studente studente = (Studente) ((TirocinioDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudente(uid);
    Offerta offerta = (Offerta) ((TirocinioDataLayer)request.getAttribute("datalayer")).getOffertaDAO().getOfferta(oid);
    Azienda azienda = offerta.getAzienda();

    request.setAttribute("studenteT",studente);
    request.setAttribute("offerta",offerta);
    request.setAttribute("aziendaT",azienda);
    
    TemplateResult res = new TemplateResult(getServletContext());

    res.activate("creazionetirocinio.ftl.html", request, response);
    } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }

    
   }                

    
    
}
