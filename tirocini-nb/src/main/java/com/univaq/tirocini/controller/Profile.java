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
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.result.UserRole;
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
                if(request.getSession().getAttribute("type").equals("studente")){

                request.setAttribute("tirocini", ((TirocinioDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirociniAttivi(((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getStudente()));
                request.setAttribute("candidature",((TirocinioDataLayer)request.getAttribute("datalayer")).getCandidaturaDAO().getCandidature(((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getStudente()));
                request.setAttribute("tirocinic", ((TirocinioDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirociniInattivi(((UserObject)((UserRole)request.getSession().getAttribute("userRole")).getUserObject()).getStudente()));
                
                }
                
                else if(request.getSession().getAttribute("type").equals("azienda")){
                request.setAttribute("offerte", ((TirocinioDataLayer) request.getAttribute("datalayer")).getOffertaDAO().getOfferte(((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getAzienda()));
                request.setAttribute("tirociniazienda", ((TirocinioDataLayer) request.getAttribute("datalayer")).getTirocinioDAO().getTirociniAttivi(((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getAzienda()));
                }
                
                res.activate("profile.ftl.html", request, response);
            } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
            
         
    }
}
