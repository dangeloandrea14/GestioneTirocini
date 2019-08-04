/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
public class DettagliAzienda extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        String p = request.getParameter("a");
        if (p == null || !p.matches("\\d*")) {
            notFound(request, response);
            return;
        }

        Azienda azienda;

        azienda = ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(Integer.parseInt(p));

        if (azienda == null) {
            notFound(request, response);
            return;
        }

        request.setAttribute("page_title", "Dettagli " + azienda.getNome());
        request.setAttribute("azienda", azienda);

        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("dettagliAzienda.ftl.html", request, response);
    }
}
