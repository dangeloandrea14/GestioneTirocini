/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.model;

import java.sql.Date;

/**
 *
 * @author Andrea
 */
public interface Studente {
    
   int getKey();
   
   void setKey(int key);
   
   String getNome();
   
   void setNome(String nome);
   
   String getCognome();
   
   void setCognome(String cognome);
   
   Date getDataNascita();
   
   void setDataNascita(Date data);
   
   String getLuogoNascita();
   
   void setLuogoNascita(String luogo);
   
   String getCF();
   
   void setCF(String CF);
   
   Boolean isHandicapped();
   
   void setHandicap(Boolean b);
   
   String getEmail();
   
   void setEmail(String s);
   
   int getRuolo();
   
   void setRuolo(int ruolo);
   
   String getResidenza();
   
   void setResidenza(String residenza);
   
   String getCorsoLaurea();
   
   void setCorsoLaurea(String corso);
   
   int getCFU();
   
   void setCFU(int cfu);
   
   String getTelefono();
   
   void setTelefono(String tel);
   
   String getDiploma();
   
   void setDiploma(String diploma);
   
   String getLaurea();
   
   void setLaurea(String laurea);
   
   String getSpecializzazione();
   
   void setSpecializzazione(String spec);
   
   String getPassword();
   
   void setPassword(String pass);
    
}
