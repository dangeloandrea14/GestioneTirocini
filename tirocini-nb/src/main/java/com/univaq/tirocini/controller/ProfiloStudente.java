/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shoken
 */
public class ProfiloStudente extends TirociniBaseController {
    
    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, TemplateManagerException, DataException {
        
        String param = request.getParameter("id");
        
        int id = Integer.parseInt(param);

        request.setAttribute("page_title", "Profilo test");
        
        Studente studente = (Studente) ((TirocinioDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudente(id);
        request.setAttribute("tirocini", ((TirocinioDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocini(studente));
        request.setAttribute("studentep", studente);
        request.setAttribute("tirociniconclusi", ((TirocinioDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirociniInattivi(studente));
         
     
         TemplateResult res = new TemplateResult(getServletContext());       
        res.activate("profiloStudente.ftl.html", request, response);
        
    }
    
    
 
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
       
    }
    
    
}
