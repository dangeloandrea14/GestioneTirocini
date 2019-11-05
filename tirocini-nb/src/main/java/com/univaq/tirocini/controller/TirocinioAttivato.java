package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Candidatura;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.result.Upload;
import com.univaq.tirocini.framework.result.UserRole;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrea
 */
public class TirocinioAttivato extends TirociniBaseController {
 
    /**
     * Carica il file e restituisce il path
     **/
    private String uploadFormativo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoSuchAlgorithmException {
        String username = ((UserRole) request.getSession().getAttribute("userRole")).getUsername();
        File uploaded = Upload.uploadFile("utirocinio", username, request, response);
        return uploaded.getName();
    }
    
   @Override
   protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, ParseException {
       
       
    request.setAttribute("page_title", "Tirocinio");
   
    try{
             
    String param = request.getParameter("sid");
    String param2 = request.getParameter("oid");
    String param3 = request.getParameter("tid");    
    
    int sid = SecurityLayer.checkNumeric(param);
    int oid = SecurityLayer.checkNumeric(param2);
    int tid = SecurityLayer.checkNumeric(param3);
    
    Studente studente = (Studente) dataLayer(request).getStudenteDAO().getStudente(sid);
    Offerta offerta = (Offerta) dataLayer(request).getOffertaDAO().getOfferta(oid);
    Azienda azienda = offerta.getAzienda();
    Tirocinio tirocinio = (Tirocinio) dataLayer(request).getTirocinioDAO().getTirocinio(tid);
    
    
    //Ora il tirocinio viene messo attivo e inizia davvero.
    tirocinio.setPathDocumento(uploadFormativo(request, response));
    tirocinio.setAttivo(true);
    
    dataLayer(request).getTirocinioDAO().storeTirocinio(tirocinio);
    
    
    //Ora le candidature per quell'offerta andranno cancellate, e con esse disattivata l'offerta.
    offerta.setAttiva(false);
    List<Candidatura> toDelete = dataLayer(request).getCandidaturaDAO().getCandidature(offerta);
    
    for (Candidatura c : toDelete ){
        
        dataLayer(request).getCandidaturaDAO().deleteCandidatura(c);    
    }
    
    //Qui andrebbero mandate le mail
    
    //reindirizziamo alla home (?)
  
    TemplateResult res = new TemplateResult(getServletContext());

    res.activate("home.ftl.html", request, response);
    } catch (DataException | NoSuchAlgorithmException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }

    
   }                
    
    @Override
    public String getServletInfo() {
        return "Attiva il tirocinio nel DB dopo che il documento firmato è stata caricato.";
    }
    
}
