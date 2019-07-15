/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.proxy;

import com.univaq.tirocini.data.impl.OffertaImpl;
import com.univaq.tirocini.framework.data.DataLayer;

/**
 *
 * @author Andrea
 */
public class OffertaProxy extends OffertaImpl {
    
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public OffertaProxy(DataLayer d){
        super();
        
        this.dataLayer=d;
        this.dirty=true;
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

}
