/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.controller.permissions.UserObject;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.result.Upload;
import com.univaq.tirocini.framework.result.UserRole;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrea
 */
public class Convenzione extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        request.setAttribute("page_title", "Convenzione");
        if (SecurityLayer.checkSession(request) == null) {
            response.sendRedirect("Home");
        }

        TemplateResult res = new TemplateResult(getServletContext());

        try {
            String param = SecurityLayer.addSlashes(request.getParameter("a"));

            int id = SecurityLayer.checkNumeric(param);

            //salvo l'azienda anche come attributo della sessione per l'uso
            //al momento del convenzionamento
            request.setAttribute("aziendac", ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id));
            request.getSession().setAttribute("aziendac", ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id));

            Map<String, String> filling = new HashMap<String, String>();
            filling.put("ente", ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getNome());
            filling.put("sedeLegale", ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getSede());
            filling.put("cf", ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getIva().get());
            filling.put("rappresentante", ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getNomeResponsabile());
            filling.put("descrizioneAzienda", ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getDescrizione());
            filling.put("corsiStudio", ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getCorsoRiferimento());

            res.activate("convenzione.ftl.html", request, response);

        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }

    }

    private void uploadConvenzione(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoSuchAlgorithmException {
        String username = ((UserRole) request.getSession().getAttribute("userRole")).getUsername();
        
        File uploaded = Upload.uploadFile("Convenzione", username, request, response);

        //recupero azienda salvata nella sessione e aggiorno il database
        Azienda azienda = (Azienda) request.getSession().getAttribute("aziendac");
        azienda.setPath(uploaded.getName());
        azienda.setConvenzionata(true);
        
        try {
            ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().storeAzienda(azienda);
        } catch (DataException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
        
        response.sendRedirect("Convenzione");
    }

    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            uploadConvenzione(request, response);

        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
      @Override
    public String getServletInfo() {
        return "Carica il form per la compilazione e il caricamento dei documenti di convenzione.";
    }
    
    
}
