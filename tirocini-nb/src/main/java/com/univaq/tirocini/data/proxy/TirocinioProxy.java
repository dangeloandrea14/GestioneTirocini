/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.proxy;

import com.univaq.tirocini.data.impl.TirocinioImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataLayer;

/**
 *
 * @author Andrea
 */
public class TirocinioProxy extends TirocinioImpl {
    
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public TirocinioProxy(DataLayer d){
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
    public void setAzienda(Azienda azienda){
        super.setAzienda(azienda);
        this.dirty = true;
    }
    
    @Override
    public void setStudente(Studente studente){
        super.setStudente(studente);
        this.dirty = true;
    }
    
    @Override
    public void setInizio(String inizio){
        super.setInizio(inizio);
        this.dirty = true;
    }
    
    @Override
    public void setFine(String fine){
        super.setFine(fine);
        this.dirty = true;
    }
    
    @Override
    public void setSettoreInserimento(String settore){
        super.setSettoreInserimento(settore);
        this.dirty = true;
    }
    
    @Override
    public void setTempoDiAccesso(String tempo){
        super.setTempoDiAccesso(tempo);
        this.dirty = true;
    }
    
    @Override
    public void setNumeroOre(String numeroore){
        super.setNumeroOre(numeroore);
        this.dirty = true;
    }
    
    @Override
    public void setTutoreUniversitario(String tutore){
        super.setTutoreUniversitario(tutore);
        this.dirty = true;
    }
    
    @Override
    public void setTutoreAziendale(String tutorea){
        super.setTutoreAziendale(tutorea);
        this.dirty = true;
    }
    
    @Override
    public void setAttivo(Boolean b){
        super.setAttivo(b);
        this.dirty = true;
    }
    
    @Override
    public void setPathDocumento(String path){
        super.setPathDocumento(path);
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
