/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.framework.result.StreamResult;
import com.univaq.tirocini.framework.result.TemplateResult;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.univaq.tirocini.pdf.Compile.compile;
import java.io.File;

/**
 *
 * @author Francesco Zappacosta
 */
public class PdfGenerator extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setAttribute("page_title", "Home");
        TemplateResult res = new TemplateResult(getServletContext());

        //res.activate("pdf.ftl.html", request, response);
        String param = request.getParameter("a");
        int id = Integer.parseInt(param);
        Azienda azienda = ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id);
        
        Map<String, String> filling = new HashMap<String, String>();
        
        //BeanUtils.populate(azienda, filling);
        
        filling.put("ente", azienda.getNome());
        filling.put("sedeLegale", azienda.getSede());
        filling.put("cf", azienda.getIva().get());
        filling.put("rappresentante", azienda.getNomeResponsabile());
        filling.put("descrizioneAzienda", azienda.getDescrizione());
        filling.put("corsiStudio", azienda.getCorsoRiferimento());
        
        File toDownload = compile(new File(getServletContext().getRealPath("templates/pdf/Convenzione.pdf")), filling);
        StreamResult sr = new StreamResult(getServletContext());
        sr.activate(toDownload, "Documento Convenzione.pdf", request, response);
        toDownload.delete();

    }
        @Override
    public String getServletInfo() {
        return "Si occupa della generazione e compilazione dei pdf.";
    }

}
