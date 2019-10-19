package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.impl.TirocinioImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrea
 */
public class StartTirocinio extends TirociniBaseController {
 
   @Override
   protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, ParseException {
       
       
    request.setAttribute("page_title", "Inizializzazione Tirocinio");
   
    try{
             
    String param = request.getParameter("sid");
    String param2 = request.getParameter("oid");
        
    int sid = Integer.parseInt(param);
    int oid = Integer.parseInt(param2);
    
    Studente studente = (Studente) ((TirocinioDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudente(sid);
    Offerta offerta = (Offerta) ((TirocinioDataLayer)request.getAttribute("datalayer")).getOffertaDAO().getOfferta(oid);
    Azienda azienda = offerta.getAzienda();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date inizioj = format.parse(request.getParameter("inizio"));
    java.util.Date finej = format.parse(request.getParameter("fine"));

    java.sql.Date inizio = new java.sql.Date(inizioj.getTime());
    java.sql.Date fine = new java.sql.Date(finej.getTime());
    
    
//Creiamo il tirocinio con il boolean active = false;
    TirocinioImpl t = new TirocinioImpl();
    t.setAzienda(azienda);
    t.setStudente(studente);
    t.setInizio(inizio);
    t.setFine(fine);
    t.setSettoreInserimento(request.getParameter("settoreinserimento"));
    t.setTempoDiAccesso(request.getParameter("tempodiaccesso"));
    t.setNumeroOre(request.getParameter("numeroore"));
    t.setTutoreUniversitario(request.getParameter("tutoreuniversitario"));
    t.setTutoreAziendale(request.getParameter("tutoreaziendale"));
    t.setAttivo(false);
    t.setOfferta(offerta);
    
    ((TirocinioDataLayer) request.getAttribute("datalayer")).getTirocinioDAO().storeTirocinio(t);
    
    
    request.setAttribute("studenteT",studente);
    request.setAttribute("offerta",offerta);
    request.setAttribute("aziendaT",azienda);
    request.setAttribute("tirocinio",t);
    
    TemplateResult res = new TemplateResult(getServletContext());

    res.activate("inizializzazionetirocinio.ftl.html", request, response);
    } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }

    
   }                

    
     @Override
    public String getServletInfo() {
        return "Inserisce i dati del tirocinio nel DB una volta completato il form. ";
    }
    
}
