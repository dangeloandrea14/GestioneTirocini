package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.impl.TirocinioImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Candidatura;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrea
 */
public class TirocinioAttivato extends TirociniBaseController {
 
    
   @Override
   protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, ParseException {
       
       
    request.setAttribute("page_title", "Tirocinio");
   
    try{
             
    String param = request.getParameter("sid");
    String param2 = request.getParameter("oid");
    String param3 = request.getParameter("tid");    
    
    int sid = Integer.parseInt(param);
    int oid = Integer.parseInt(param2);
    int tid = Integer.parseInt(param3);
    
    Studente studente = (Studente) ((TirocinioDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudente(sid);
    Offerta offerta = (Offerta) ((TirocinioDataLayer)request.getAttribute("datalayer")).getOffertaDAO().getOfferta(oid);
    Azienda azienda = offerta.getAzienda();
    Tirocinio tirocinio = (Tirocinio) ((TirocinioDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocinio(tid);
    
    
    //Ora il tirocinio viene messo attivo e inizia davvero.
    tirocinio.setPathDocumento("path");
    tirocinio.setAttivo(true);
    
    ((TirocinioDataLayer) request.getAttribute("datalayer")).getTirocinioDAO().storeTirocinio(tirocinio);
    
    
    //Ora le candidature per quell'offerta andranno cancellate, e con esse disattivata l'offerta.
    offerta.setAttiva(false);
    List<Candidatura> toDelete = ((TirocinioDataLayer) request.getAttribute("datalayer")).getCandidaturaDAO().getCandidature(offerta);
    
    for (Candidatura c : toDelete ){
        
        ((TirocinioDataLayer) request.getAttribute("datalayer")).getCandidaturaDAO().deleteCandidatura(c);    
    }
    
    //Qui andrebbero mandate le mail
    
    //reindirizziamo alla home
  
    TemplateResult res = new TemplateResult(getServletContext());

    res.activate("home.ftl.html", request, response);
    } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }

    
   }                
    
    
}
