/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrea
 */
public class TirocinioFinito extends TirociniBaseController {


  @Override
   protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       
       try{
           
    request.setAttribute("page_title", "Tirocinio Conclusione");
    
    String param = request.getParameter("tirocinio");
    
    int tid = SecurityLayer.checkNumeric(param);
    
    HttpSession sess = request.getSession();
   
    if (tid != (int) sess.getAttribute("tirocinioid")){
        
        String ex = "Tirocinio errato";
        throw new DataException(ex);
        
    }
    sess.removeAttribute("tirocinioid");
    
    Tirocinio tirocinio = (Tirocinio) dataLayer(request).getTirocinioDAO().getTirocinio(tid);
    
    tirocinio.setAttivo(false);
    
    dataLayer(request).getTirocinioDAO().storeTirocinio(tirocinio);
       
    //Invia mail a studente con link per valutazione.
    
        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("home.ftl.html", request, response); 
        
       } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

 @Override
    public String getServletInfo() {
        return "Servlet per la conclusione del tirocinio.";
    }
    
}
