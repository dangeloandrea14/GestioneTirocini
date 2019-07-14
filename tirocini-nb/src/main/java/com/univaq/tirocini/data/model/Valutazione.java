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
public interface Valutazione {
    
    int getKey();
    
    void setKey(int key);
    
    String getCommento();
    
    void setCommento(String comment);
    
    Studente getStudente();
    
    void setStudente(Studente s);
    
    Azienda getAzienda();
    
    void setAzienda(Azienda a);
    
}
