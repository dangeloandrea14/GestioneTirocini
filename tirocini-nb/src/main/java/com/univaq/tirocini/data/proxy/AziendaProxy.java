
package com.univaq.tirocini.data.proxy;

import com.univaq.tirocini.data.impl.AziendaImpl;
import com.univaq.tirocini.framework.data.DataLayer;
import com.univaq.tirocini.vo.IVA;

/**
 *
 * @author Andrea
 */
public class AziendaProxy extends AziendaImpl {
    
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public AziendaProxy(DataLayer d){
        super();
        
        this.dataLayer=d;
        this.dirty=true;
    }
    
    //Estendiamo le funzioni implementate, in particolare i setter: se viene settato qualcosa,
    //il boolean dirty viene messo a true. Così sappiamo se è stata fatta una modifica
    //oppure no.
    
    @Override
    public void setKey(int key) {
        super.setKey(key);
        this.dirty = true;
    }

    @Override
    public void setNome(String nome){
        super.setNome(nome);
        this.dirty = true;
    }
    
    @Override
    public void setSede(String cognome){
        super.setSede(cognome);
        this.dirty = true;
    }
    
    @Override
    public void setIva(IVA iva){
        super.setIva(iva);
        this.dirty = true;
    }
    
    @Override
    public void setForoCompetenza(String foro){
        super.setForoCompetenza(foro);
        this.dirty = true;
    }
    
    
    @Override
    public void setNomeResponsabile(String nome){
        super.setNomeResponsabile(nome);
        this.dirty = true;
    }
    
    @Override
    public void setCognomeResponsabile(String cognome){
        super.setCognomeResponsabile(cognome);
        this.dirty = true;
    }
    
    @Override
    public void setTelefonoResponsabile(String telefono){
        super.setTelefonoResponsabile(telefono);
        this.dirty = true;
    }
    
    @Override
    public void setEmailResponsabile(String email){
        super.setEmailResponsabile(email);
        this.dirty = true;
    }
    
    
    @Override
    public void setNomeCognomeLegale(String nome){
        super.setNomeCognomeLegale(nome);
        this.dirty = true;
    }
    
    @Override
    public void setVoto(int voto){
        super.setVoto(voto);
        this.dirty = true;
    }
    
    @Override
    public void setConvenzionata(boolean b){
        super.setConvenzionata(b);
        this.dirty = true;
    }
    
    @Override
    public void setPassword(String p){
        super.setPassword(p);
        this.dirty = true;
    }
    
    @Override
    public void setPath(String path){
        super.setPath(path);
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
