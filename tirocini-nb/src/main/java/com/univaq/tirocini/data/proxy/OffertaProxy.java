/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.proxy;

import com.univaq.tirocini.data.DAO.AziendaDAO;
import com.univaq.tirocini.data.impl.OffertaImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.data.DataLayer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrea
 */
public class OffertaProxy extends OffertaImpl {
    
    int azienda_key = 0;
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public OffertaProxy(DataLayer d){
        super();
        
        this.azienda_key = 0;
        this.dataLayer=d;
        this.dirty=true;
    }
    
    @Override
    public void setKey(int key){
        super.setKey(key);
        this.dirty = true;
    }
    
    
    @Override
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
    public void setAzienda(Azienda azienda){
        super.setAzienda(azienda);
        this.dirty = true;
    }
    
    
    @Override
    public void setLuogo(String luogo){
        super.setLuogo(luogo);
        this.dirty = true;
    }
    
    @Override
    public void setOrari(String orari){
        super.setOrari(orari);
        this.dirty = true;
    }
    
    @Override
    public void setDurata(String durata){
        super.setDurata(durata);
        this.dirty = true;
    }
    
    @Override
    public void setObiettivi(String obiettivi){
        super.setObiettivi(obiettivi);
        this.dirty = true;
    }
    
    @Override
    public void setModalità(String modalità){
        super.setModalità(modalità);
        this.dirty = true;
    }
    
    @Override
    public void setRimborsoSpese(String rimborso){
        super.setRimborsoSpese(rimborso);
        this.dirty = true;
    }
    
    @Override
    public void setAttiva(boolean active){
        super.setAttiva(active);
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
}
