/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.controller.permissions.UserObject;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Candidatura;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.result.UserRole;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
public class DettagliOfferta extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {

        String p = request.getParameter("o"); //id offerta

        if (p == null || !p.matches("\\d*")) { //restituiamo not found
            notFound(request, response);
            return;
        }

        Offerta offerta;
        offerta = ((TirocinioDataLayer) request.getAttribute("datalayer")).getOffertaDAO().getOfferta(Integer.parseInt(p));

        if (offerta == null) { //restituiamo not found
            notFound(request, response);
            return;
        }

        //Questo forse si potrebbe fare meglio ma per ora ce lo teniamo così
        //Controlliamo che l'utente non si sia già candidato
        request.setAttribute("already", false);
        if (request.getSession().getAttribute("userRole") != null
                && ((UserObject) ((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getStudente() != null) {
            List<Candidatura> list = ((TirocinioDataLayer) request.getAttribute("datalayer")).getCandidaturaDAO().getCandidature(((UserObject) ((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getStudente());
            Iterator it = list.iterator();

            //Controlliamo che non sia già presente
            while (it.hasNext()) {
                if (((Candidatura) it.next()).getOfferta().getKey() == offerta.getKey()) {
                    request.setAttribute("already", true);
                }
            }
        }

        request.setAttribute("page_title", "Dettagli offerta tirocinio");
        request.setAttribute("offerta", offerta);

        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("dettagliOfferta.ftl.html", request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Carica i dettagli dell'offerta scelta.";
    }
    
    
}
