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

    public File prepareConvenzione(Azienda azienda) {
        Map<String, String> filling = new HashMap<>();

        //BeanUtils.populate(azienda, filling);
        filling.put("ente", azienda.getNome());
        filling.put("sedeLegale", azienda.getSede());
        filling.put("cf", azienda.getIva().get());
        filling.put("rappresentante", azienda.getNomeResponsabile());
        filling.put("descrizioneAzienda", azienda.getDescrizione());
        filling.put("corsiStudio", azienda.getCorsoRiferimento());

        return compile(new File(getServletContext().getRealPath("templates/pdf/Convenzione.pdf")), filling);
    }

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws Exception {

        TemplateResult res = new TemplateResult(getServletContext());

        String target_file = request.getParameter("f");

        if (target_file.equals("convenzione")) {

            String param = request.getParameter("a");
            int id = Integer.parseInt(param);
            Azienda azienda = ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id);

            File toDownload = prepareConvenzione(azienda);

            StreamResult sr = new StreamResult(getServletContext());
            sr.activate(toDownload, "Documento Convenzione.pdf", request, response);
            toDownload.delete();
        } else if (target_file.equals("formativo")) {

            request.setAttribute("message", "Not Implemented");
            action_error(request, response);
            /*
            String aId = request.getParameter("a");
            String sId = request.getParameter("s");
            int idAzienda = Integer.parseInt(aId);
            int idStudente = Integer.parseInt(sId);

            Azienda azienda = ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(idAzienda);

            Map<String, String> filling = new HashMap<>();

            //BeanUtils.populate(azienda, filling);
            filling.put("ente", azienda.getNome());
            filling.put("sedeLegale", azienda.getSede());
            filling.put("cf", azienda.getIva().get());
            filling.put("rappresentante", azienda.getNomeResponsabile());
            filling.put("descrizioneAzienda", azienda.getDescrizione());
            filling.put("corsiStudio", azienda.getCorsoRiferimento());

            File toDownload = compile(new File(getServletContext().getRealPath("templates/pdf/Formativo.pdf")), filling);
            StreamResult sr = new StreamResult(getServletContext());
            sr.activate(toDownload, "Progetto Formativo.pdf", request, response);
            toDownload.delete();*/
        }
    }

    @Override
    public String getServletInfo() {
        return "Si occupa della generazione e compilazione dei pdf.";
    }

}
