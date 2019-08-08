/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Candidatura;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shoken
 */
public class Gestioneofferta extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
            request.setAttribute("page_title","Gestione offerta");
            
            TemplateResult res = new TemplateResult(getServletContext());
            
            try {        
             
            String param = request.getParameter("oid");
        
            int id = Integer.parseInt(param);    
                
            Offerta offerta = ((TirocinioDataLayer)request.getAttribute("datalayer")).getOffertaDAO().getOfferta(id);
            
            //Controlliamo che sia l'azienda a controllare le proprie offerte.
            if(((Azienda) request.getSession().getAttribute("azienda")).getKey() == offerta.getAzienda().getKey() ){
            
            List<Candidatura> list = ((TirocinioDataLayer)request.getAttribute("datalayer")).getCandidaturaDAO().getCandidature(offerta);
                
            request.setAttribute("offerta",offerta);
            request.setAttribute("candidature",list);
                
            res.activate("gestioneofferta.ftl.html", request, response);}
            else{
                response.sendRedirect("Home");
            }
                
            } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
            
         
    } 
    
    
}
