
package com.univaq.tirocini.data.proxy;

import com.univaq.tirocini.data.impl.StudenteImpl;
import com.univaq.tirocini.framework.data.DataLayer;

/**
 *
 * @author Andrea
 */
public class StudenteProxy extends StudenteImpl {
   
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public StudenteProxy(DataLayer d){
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
      public void setNome(String nome){
          super.setNome(nome);
          this.dirty = true;
      }
      
      @Override
      public void setCognome(String cognome){
          super.setCognome(cognome);
          this.dirty = true;
      }
    
      @Override
      public void setDataNascita(String data){
          super.setDataNascita(data);
          this.dirty = true;
      }
    
      @Override
      public void setLuogoNascita(String luogo){
          super.setLuogoNascita(luogo);
          this.dirty = true;
      }
      
      @Override
      public void setCF(String cf){
          super.setCF(cf);
          this.dirty = true;
      }
      
      @Override
      public void setHandicap(Boolean b){
          super.setHandicap(b);
          this.dirty = true;
      }
    
      @Override
      public void setEmail(String email){
          super.setEmail(email);
          this.dirty = true;
      }
      
      @Override
      public void setRuolo(int ruolo){
          super.setRuolo(ruolo);
          this.dirty = true;
      }
      
      @Override
      public void setResidenza(String residenza){
          super.setResidenza(residenza);
          this.dirty = true;
      }
      
      @Override
      public void setCorsoLaurea(String laurea){
          super.setCorsoLaurea(laurea);
          this.dirty = true;
      }
    
      @Override
      public void setCFU(int CFU){
          super.setCFU(CFU);
          this.dirty = true;
      }
      
      @Override
      public void setTelefono(String telefono){
          super.setTelefono(telefono);
          this.dirty = true;  
      }
      
      @Override
      public void setDiploma(String diploma){
          super.setDiploma(diploma);
          this.dirty = true;
      }
      
      @Override
      public void setLaurea(String laurea){
          super.setLaurea(laurea);
          this.dirty = true;
      }
      
      @Override
      public void setSpecializzazione(String specializzazione){
          super.setSpecializzazione(specializzazione);
          this.dirty = true;
      }
      
      @Override
      public void setPassword(String pass){
          this.setPassword(pass);
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
