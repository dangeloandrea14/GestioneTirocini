package com.univaq.tirocini.controller;

import com.univaq.tirocini.controller.permissions.UserObject;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Candidatura;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.result.UserRole;
import com.univaq.tirocini.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrea
 */

public class CandidaturaServlet extends TirociniBaseController {

   @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       
        
      
       String param = SecurityLayer.addSlashes(request.getParameter("oid"));
        
       int oid = SecurityLayer.checkNumeric(param);
        
       if(request.getSession().getAttribute("type").equals("studente")){
       Studente studente = (((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getStudente());
       Offerta offerta = (Offerta) ((TirocinioDataLayer)request.getAttribute("datalayer")).getOffertaDAO().getOfferta(oid);
       Azienda azienda = offerta.getAzienda();
       List<Candidatura> list = ((TirocinioDataLayer)request.getAttribute("datalayer")).getCandidaturaDAO().getCandidature(studente);
       Iterator it=list.iterator();
       Boolean already=false;
       request.setAttribute("already",false);
       
       //Costruiamo la candidatura
       Candidatura candidatura = ((TirocinioDataLayer)request.getAttribute("datalayer")).getCandidaturaDAO().createCandidatura();
       candidatura.setStudente(studente);
       candidatura.setOfferta(offerta);
       
       //Controlliamo che non sia già presente
         while(it.hasNext()){
           if(( (Candidatura)it.next()).getOfferta().getKey() == offerta.getKey()){
               already=true;
               request.setAttribute("already",true);
           }
             }    
      
      //Se non è presente la memorizziamo.
        if(already == false) 
       ((TirocinioDataLayer)request.getAttribute("datalayer")).getCandidaturaDAO().storeCandidatura(candidatura);
       
       
       request.setAttribute("page_title", "Congratulazioni");
       
       
       request.setAttribute("studentec", studente);
        
       TemplateResult res = new TemplateResult(getServletContext());

        res.activate("congrats.ftl.html", request, response);}
       else{
           response.sendRedirect("Home");
       }
    }
    
    @Override
    public String getServletInfo() {
        return "Permette la candidatura ad una certa offerta da parte di uno studente.";
    }
    
    
}
    

