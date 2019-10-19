package com.univaq.tirocini.controller;

import com.google.gson.Gson;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.HTMLResult;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Andrea
 */
public class Updatecommenti extends TirociniBaseController {


    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws Exception {
       
        doGet(request,response);
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            TirocinioDataLayer datalayer = ((TirocinioDataLayer) request.getAttribute("datalayer"));
            
          
            Azienda azienda = datalayer.getAziendaDAO().getAzienda(1);
            
            List<com.univaq.tirocini.data.model.Valutazione> voti = datalayer.getValutazioneDAO().getValutazioni(azienda);
            
            List<String> commenticercati = new ArrayList<>();
            
            for (com.univaq.tirocini.data.model.Valutazione voto : voti){
                
                if (voto.getStelle() == 5){
                    
                    commenticercati.add(voto.getCommento());
                    
                     }
                
            }
            
                
             String json = "ciao";
 
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            
        } catch (DataException ex) {
            Logger.getLogger(Updatecommenti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String getServletInfo() {
        return "Aggiorna i commenti in base al voto scelto nella pagina statistiche. ";
    }
    
    
}
