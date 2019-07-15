/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.proxy;

import com.univaq.tirocini.data.impl.ValutazioneImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataLayer;

/**
 *
 * @author Andrea
 */
public class ValutazioneProxy extends ValutazioneImpl {
    
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public ValutazioneProxy(DataLayer d){
        super();
        
        this.dataLayer=d;
        this.dirty=true;
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
}
