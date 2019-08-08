/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.impl.OffertaImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author carlo
 */
public class newOffer extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        if (SecurityLayer.checkSession(request) != null && request.getSession().getAttribute("azienda") != null) {
            request.setAttribute("page_title", "Nuova offerta tirocinio");

            TemplateResult res = new TemplateResult(getServletContext());

            res.activate("newOffer.ftl.html", request, response);
        } else if (SecurityLayer.checkSession(request) != null) {
            response.sendRedirect("Home");
        } else {
            response.sendRedirect("Login");
        }
    }

    private void action_create(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, IllegalAccessException, InvocationTargetException {
        if (SecurityLayer.checkSession(request) == null || request.getSession().getAttribute("azienda") == null) {
            creation_failed(request, response);
            return;
        }

        
        OffertaImpl o = new OffertaImpl();

        BeanUtils.populate(o, request.getParameterMap());

        o.setAzienda((Azienda) request.getSession().getAttribute("azienda"));
        o.setAttiva(false);
        
        ((TirocinioDataLayer) request.getAttribute("datalayer")).getOffertaDAO().storeOfferta(o);
        
        
        response.sendRedirect("Profile");
    }

    private void creation_failed(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("exception", new Exception("Creation failed"));
        action_error(request, response);
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
                action_default(request, response);

        } catch (Exception  ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        }
    }
    
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
                action_create(request, response);

        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
}
