package com.univaq.tirocini.controller;

import com.univaq.tirocini.controller.permissions.UserObject;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.email.Mail;
import com.univaq.tirocini.framework.email.SendEmail;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.result.UserRole;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 *
 * @author carlo
 */
public class Profile extends TirociniBaseController {

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
            request.setAttribute("page_title", "Profile");

            TemplateResult res = new TemplateResult(getServletContext());
            
            try {        
                if(request.getSession().getAttribute("type").equals("studente")){

                request.setAttribute("tirocini", ((TirocinioDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirociniAttivi(((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getStudente()));
                request.setAttribute("candidature",((TirocinioDataLayer)request.getAttribute("datalayer")).getCandidaturaDAO().getCandidature(((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getStudente()));
               
                List<Tirocinio> tirociniinattivi = ((TirocinioDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirociniInattivi(((UserObject)((UserRole)request.getSession().getAttribute("userRole")).getUserObject()).getStudente());
                List<Tirocinio> tirocinic = new ArrayList<>();
                
                for(Tirocinio t : tirociniinattivi){
                    
                if((t.getPathDocumento() != null)  && !t.getPathDocumento().isEmpty()){
                    tirocinic.add(t);
                }                     
                }
                
                request.setAttribute("tirocinic", tirocinic);
                }
                
                else if(request.getSession().getAttribute("type").equals("azienda")){
                request.setAttribute("offerte", ((TirocinioDataLayer) request.getAttribute("datalayer")).getOffertaDAO().getOfferte(((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getAzienda()));
                request.setAttribute("tirociniazienda", ((TirocinioDataLayer) request.getAttribute("datalayer")).getTirocinioDAO().getTirociniAttivi(((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getAzienda()));
                request.setAttribute("conv", ((UserObject)((UserRole) request.getSession().getAttribute("userRole")).getUserObject()).getAzienda().isConvenzionata());
                }
                             
                res.activate("profile.ftl.html", request, response);
            } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
            
         
    }
    
     @Override
    public String getServletInfo() {
        return "Carica i profili di studente e di azienda.";
    }
}
