/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.controller.permissions.UserObject;
import com.univaq.tirocini.data.impl.TirocinioImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Candidatura;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;
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

/**
 *
 * @author Andrea
 */
public class GestioneOfferta extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        request.setAttribute("page_title", "Gestione offerta");

        TemplateResult res = new TemplateResult(getServletContext());

        try {

            String param = request.getParameter("oid");

            int id = SecurityLayer.checkNumeric(param);

            Offerta offerta = dataLayer(request).getOffertaDAO().getOfferta(id);

            //Controlliamo che sia l'azienda a controllare le proprie offerte.
            if ((((UserObject) ((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getAzienda()).getKey() == offerta.getAzienda().getKey()) {

                List<Candidatura> list = dataLayer(request).getCandidaturaDAO().getCandidature(offerta);

                request.setAttribute("offerta", offerta);
                request.setAttribute("candidature", list);

                res.activate("gestioneOfferta.ftl.html", request, response);
            } else {
                response.sendRedirect("Home");
            }

        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }

    }

    protected void accettaStudente(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DataException {
        Azienda azienda = (((UserObject) ((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getAzienda());

        if (request.getParameter("oid") == null || request.getParameter("uid") == null) {
            notFound(request, response);
            return;
        }

        int oid;
        int uid;

        try {
            oid = Integer.parseInt(request.getParameter("oid"));
            uid = Integer.parseInt(request.getParameter("uid"));
        } catch (NumberFormatException e) {
            notFound(request, response);
            return;
        }

        Offerta offerta = dataLayer(request).getOffertaDAO().getOfferta(oid);

        if (offerta.getAzienda().getKey() != azienda.getKey()) {
            notFound(request, response);
            return;            
        }
        
        List<Candidatura> candidaturas = (dataLayer(request).getCandidaturaDAO().getCandidature(offerta));
        
        boolean candidato = false;
        for (Candidatura c : candidaturas) {
            if (c.getStudente().getKey() == uid)
                candidato = true;
        }
        
        if (!candidato) {
            notFound(request, response);
            return;
        }
        
        Studente studente = dataLayer(request).getStudenteDAO().getStudente(uid);
        
        Tirocinio tirocino = new TirocinioImpl();
        
        tirocino.setAzienda(azienda);
        tirocino.setStudente(studente);
        tirocino.setOfferta(offerta);
        tirocino.setAttivo(false);
        tirocino.setNumeroOre(Integer.toString(offerta.getCFU()*25)); //oppure con la durata dell'offerta?
        
            
        
        
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
            accettaStudente(request, response);

        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
      @Override
    public String getServletInfo() {
        return "Permette diversi tipi di gestione offerta dal profilo di una azienda.";
    }
    

}
