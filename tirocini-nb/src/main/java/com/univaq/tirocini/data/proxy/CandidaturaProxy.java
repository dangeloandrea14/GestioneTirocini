/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.proxy;

import com.univaq.tirocini.data.DAO.AziendaDAO;
import com.univaq.tirocini.data.DAO.OffertaDAO;
import com.univaq.tirocini.data.DAO.StudenteDAO;
import com.univaq.tirocini.data.impl.CandidaturaImpl;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.data.DataLayer;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrea
 */
public class CandidaturaProxy extends CandidaturaImpl {
    
    int offerta_key = 0;
    int studente_key = 0;
    protected boolean dirty;
    protected DataLayer dataLayer;
    
     public CandidaturaProxy(DataLayer d){
        super();
        
        this.offerta_key = 0;
        this.studente_key = 0;
        this.dataLayer=d;
        this.dirty=true;
    }
    
    
     @Override
    public void setKey(int key){
        super.setKey(key);
        this.dirty = true;
    }

    
    @Override
    public Offerta getOfferta() {
        
        if (super.getOfferta() == null && offerta_key > 0) {
            try {
                super.setOfferta(((OffertaDAO) dataLayer.getDAO(Offerta.class)).getOfferta(offerta_key));
            } catch (DataException ex) {
                Logger.getLogger(TirocinioProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        return super.getOfferta();
    } 
    
    
     @Override
     public void setOfferta(Offerta offerta){
         super.setOfferta(offerta);
         this.dirty = true;
     }
     
      public Studente getStudente() {
        
        if (super.getStudente() == null && studente_key > 0) {
            try {
                super.setStudente(((StudenteDAO) dataLayer.getDAO(Studente.class)).getStudente(studente_key));
            } catch (DataException ex) {
                Logger.getLogger(TirocinioProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        return super.getStudente();
    } 
      
    @Override
    public void setStudente(Studente studente){
        super.setStudente(studente);
        this.dirty = true;
    }
    
    @Override
    public void setData(Date data){
        super.setData(data);
        this.dirty = true;
    }
    
     //Questi sono i metodi del proxy.
            
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }

     public void setOffertakey(int offerta_key) {
        this.offerta_key = offerta_key;
        //resettiamo la cache dell'autore
        super.setOfferta(null);
    }
    
     public void setStudentekey(int studente_key){
         this.studente_key = studente_key;
         super.setStudente(null);
     }
}
