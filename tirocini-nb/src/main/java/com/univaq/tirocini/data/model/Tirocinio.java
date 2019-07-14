/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.model;

/**
 *
 * @author Andrea
 */
public interface Tirocinio {
    
    int getKey();
    
    void setKey(int key);
    
    Azienda getAzienda();
    
    void setAzienda(Azienda azienda);
    
    Studente getStudente();
    
    void setStudente(Studente studente);
    
    String getInizio();
    
    void setInizio(String inizio);
    
    String getFine();
    
    void setFine(String fine);
    
    String getSettoreInserimento();
    
    void setSettoreInserimento(String settore);
    
    String getTempoDiAccesso();
    
    void setTempoDiAccesso(String tempo);
    
    String getNumeroOre();
    
    void setNumeroOre(String ore);
    
    String getTutoreUniversitario();
    
    void setTutoreUniversitario(String tutoreuniversitario);
    
    String getTutoreAziendale();
    
    void setTutoreAziendale(String tutoreaziendale);
    
    Boolean isAttivo();
    
    void setAttivo(Boolean b);
    
    String getPathDocumento();
    
    void setPathDocumento(String path);
    
}
