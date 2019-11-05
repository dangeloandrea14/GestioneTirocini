/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.StreamResult;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import com.univaq.tirocini.pdf.Compile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrea
 */
public class ConclusioneTirocinio extends TirociniBaseController {

    /////////////////////////////////////////////
    ////////////////! FIXME !////////////////////
    /////////////////////////////////////////////   
    /*
    DECISAMENTE NON DOVREBBE ESSERE QUI. (Pt. 2!)
     */
    public File prepareFormativo2(Azienda azienda, Studente studente, Tirocinio tirocinio,
            String sede, String totaleOre,
            String descrizione, String risultati) {
        Map<String, String> filling = new HashMap<>();

        filling.put("cognomeTirocinanteFinale", studente.getCognome());
        filling.put("nomeTirocinanteFinale", studente.getNome());
        filling.put("nomeAziendaFinale", azienda.getNome());
        filling.put("codiceFiscaleAziendaFinale", azienda.getIva().toString());
        filling.put("totOreTirocinioFinale", totaleOre);
        filling.put("inizioTirocinioFinale", tirocinio.getInizio().toString());
        filling.put("fineTirocinioFinale", tirocinio.getFine().toString());
        filling.put("sedeTircocinioFinale", sede);
        filling.put("descrizioneTirocinioFinale", descrizione);
        filling.put("attivit&#224;SvolteTirocinioFinale", risultati);
        filling.put("nomeCognomeTirocinanteFinale", studente.getNome() + " " + studente.getCognome());
        return Compile.compile(new File(getServletContext().getRealPath("templates/pdf/Formativo.pdf")), filling);
    }

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {

        request.setAttribute("page_title", "Inizializzazione Tirocinio");

        request.setAttribute("page_title", "Conclusione Tirocinio");
        String param = SecurityLayer.addSlashes(request.getParameter("i"));

        int tid = SecurityLayer.checkNumeric(param);

        Tirocinio tirocinio = (Tirocinio) dataLayer(request).getTirocinioDAO().getTirocinio(tid);

        HttpSession sess = request.getSession();
        sess.setAttribute("tirocinioid", tid);

        request.setAttribute("tirocinio", tirocinio);

        TemplateResult res = new TemplateResult(getServletContext());

        res.activate("conclusione.ftl.html", request, response);
    }

    private void action_download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TemplateManagerException, DataException {

        String param = request.getParameter("tirocinio");
        int tid = SecurityLayer.checkNumeric(param);

        Tirocinio tirocinio = dataLayer(request).getTirocinioDAO().getTirocinio(tid);
        Azienda azienda = tirocinio.getAzienda();
        Studente studente = tirocinio.getStudente();

        File toDownload = prepareFormativo2(azienda, studente, tirocinio,
                request.getParameter("sede"),
                request.getParameter("ore"),
                request.getParameter("descrizione"),
                request.getParameter("risultati"));

        StreamResult sr = new StreamResult(getServletContext());
        sr.activate(toDownload, "Documento Progetto Formativo.pdf", request, response);
        toDownload.delete();
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
            action_download(request, response);

        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Carica la pagina di conclusione del tirocinio.";
    }
}
