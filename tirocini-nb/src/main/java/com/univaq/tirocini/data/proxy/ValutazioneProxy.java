/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.proxy;

import com.univaq.tirocini.data.DAO.AziendaDAO;
import com.univaq.tirocini.data.DAO.StudenteDAO;
import com.univaq.tirocini.data.impl.ValutazioneImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.data.DataLayer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrea
 */
public class ValutazioneProxy extends ValutazioneImpl {
    
    int azienda_key = 0;
    int studente_key = 0;
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public ValutazioneProxy(DataLayer d){
        super();
        
        this.azienda_key = 0;
        this.studente_key = 0;
        this.dataLayer=d;
        this.dirty=true;
    }
    
    @Override
    public void setStelle(int stelle){
        super.setStelle(stelle);
        this.dirty = true;
    }
    
     public Azienda getAzienda() {
        //notare come l'autore in relazione venga caricato solo su richiesta
        
        if (super.getAzienda() == null && azienda_key > 0) {
            try {
                super.setAzienda(((AziendaDAO) dataLayer.getDAO(Azienda.class)).getAzienda(azienda_key));
            } catch (DataException ex) {
                Logger.getLogger(TirocinioProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //attenzione: l'autore caricato viene lagato all'oggetto in modo da non 
        //dover venir ricaricato alle richieste successive, tuttavia, questo
        //puo' rende i dati potenzialmente disallineati: se l'autore viene modificato
        //nel DB, qui rimarrà la sua "vecchia" versione
       
        return super.getAzienda();
    }

    @Override
    public Studente getStudente() {
        //notare come l'autore in relazione venga caricato solo su richiesta
        
        if (super.getStudente() == null && studente_key > 0) {
            try {
                super.setStudente(((StudenteDAO) dataLayer.getDAO(Studente.class)).getStudente(studente_key));
            } catch (DataException ex) {
                Logger.getLogger(TirocinioProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //attenzione: l'autore caricato viene lagato all'oggetto in modo da non 
        //dover venir ricaricato alle richieste successive, tuttavia, questo
        //puo' rende i dati potenzialmente disallineati: se l'autore viene modificato
        //nel DB, qui rimarrà la sua "vecchia" versione
       
        return super.getStudente();
    }
    
    @Override
    public void setKey(int key){
        super.setKey(key);
        this.dirty = true;
    }
    
    @Override
    public void setCommento(String commento){
        super.setCommento(commento);
        this.dirty = true;
    }
    
    @Override
    public void setStudente(Studente studente){
        super.setStudente(studente);
        this.dirty = true;
    }
    
    @Override
    public void setAzienda(Azienda azienda){
        super.setAzienda(azienda);
        this.dirty = true;
    }
    
    
    
    
    //Questi sono i metodi del proxy.
            
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }
    
     public void setAziendakey(int azienda_key) {
        this.azienda_key = azienda_key;
        //resettiamo la cache dell'autore
        super.setAzienda(null);
    }

    public void setStudentekey(int studente_key) {
        this.studente_key = studente_key;
        super.setStudente(null);
    }
}
