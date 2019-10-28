/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrea
 */
public class ConclusioneTirocinio extends TirociniBaseController {

    @Override
   protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       
    try{
    request.setAttribute("page_title", "Conclusione Tirocinio");
    String param = SecurityLayer.addSlashes(request.getParameter("i"));
    
    int tid = SecurityLayer.checkNumeric(param);
   
    Tirocinio tirocinio = (Tirocinio) ((TirocinioDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocinio(tid);

    HttpSession sess = request.getSession();
    sess.setAttribute("tirocinioid", tid);
    
    request.setAttribute("tirocinio",tirocinio);
     
     
        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("conclusione.ftl.html", request, response); 
        
       } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }

       
       
    }

      @Override
    public String getServletInfo() {
        return "Carica la pagina di conclusione del tirocinio.";
    }
    
   
}
