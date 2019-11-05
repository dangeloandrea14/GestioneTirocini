/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.controller.permissions.UserObject;
import com.univaq.tirocini.data.impl.OffertaImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.result.UserRole;
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
public class NewOffer extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {

        Azienda azienda = ((UserObject) ((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getAzienda();

        if (azienda.isConvenzionata() == false) {
            goBack(request, response);
            return;
        }

        request.setAttribute("page_title", "Nuova offerta tirocinio");

        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("newOffer.ftl.html", request, response);
    }

    private void action_create(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, IllegalAccessException, InvocationTargetException {
        if (SecurityLayer.checkSession(request) == null || !request.getSession().getAttribute("type").equals("azienda")) {
            creation_failed(request, response);
            return;
        }

        OffertaImpl o = new OffertaImpl();

        BeanUtils.populate(o, request.getParameterMap());

        o.setAzienda(((UserObject) ((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getAzienda());
        o.setAttiva(true);

        dataLayer(request).getOffertaDAO().storeOfferta(o);

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

        } catch (Exception ex) {
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

    @Override
    public String getServletInfo() {
        return "Servlet per la creazione di una nuova offerta di tirocinio.";
    }
}
