/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.StreamResult;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.univaq.tirocini.pdf.Compile;
import static com.univaq.tirocini.pdf.Compile.compile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.nio.file.Files;
/**
 *
 * @author Francesco Zappacosta
 */
public class PdfGenerator extends TirociniBaseController{

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws Exception {
       
        request.setAttribute("page_title", "Home");
        TemplateResult res = new TemplateResult(getServletContext());
        
        //res.activate("pdf.ftl.html", request, response);
    String param = request.getParameter("a");
    int id = Integer.parseInt(param);
           Map<String, String> filling = new HashMap<String,String>();
           filling.put("ente",((TirocinioDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getNome());
           filling.put("sedeLegale",((TirocinioDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getSede());
           filling.put("cf",((TirocinioDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getIva().get());
           filling.put("rappresentante",((TirocinioDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getNomeResponsabile());
           filling.put("descrizioneAzienda",((TirocinioDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getDescrizione());
           filling.put("corsiStudio",((TirocinioDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id).getCorsoRiferimento());
        
        Path tempDir = Files.createTempDirectory("temp");
        compile(getServletContext().getRealPath("templates/pdf/Convenzione.pdf"),tempDir.toAbsolutePath() + "/Convenzione1.pdf",filling);
        
        StreamResult sr = new StreamResult(getServletContext());       
        sr.activate(new File(tempDir.toAbsolutePath() + "/Convenzione1.pdf"), request, response);
        
        tempDir.toFile().delete();
        

    }
    
    
}
