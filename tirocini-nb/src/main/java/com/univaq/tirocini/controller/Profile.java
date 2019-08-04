/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
public class Profile extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
            request.setAttribute("page_title", "Profile");
            if (SecurityLayer.checkSession(request) == null) {
                response.sendRedirect("Home");
            }            
            
            TemplateResult res = new TemplateResult(getServletContext());
            
            try {        
                if(request.getSession().getAttribute("studente") != null){
                request.setAttribute("tirocini", ((TirocinioDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocini((Studente) request.getSession().getAttribute("studente")));
                }
                
                else if(request.getSession().getAttribute("azienda") != null){
                request.setAttribute("offerte", ((TirocinioDataLayer) request.getAttribute("datalayer")).getOffertaDAO().getOfferte((Azienda) request.getSession().getAttribute("azienda")));
                request.setAttribute("tirociniazienda", ((TirocinioDataLayer) request.getAttribute("datalayer")).getTirocinioDAO().getTirocini((Azienda) request.getSession().getAttribute("azienda")));
                }
                
                res.activate("profile.ftl.html", request, response);
            } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
            
         
    }
}
