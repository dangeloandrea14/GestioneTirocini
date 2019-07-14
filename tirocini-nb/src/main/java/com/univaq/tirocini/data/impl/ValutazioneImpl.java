/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.impl;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Valutazione;

/**
 *
 * @author Andrea
 */
public class ValutazioneImpl implements Valutazione {
    
    int key;
    String commento;
    Studente studente;
    Azienda azienda;
    
    @Override
    public int getKey(){
        return this.key;
    }
    
    @Override
    public void setKey(int key){
        this.key = key;
    }
    
    @Override
    public String getCommento(){
        return this.commento;
    }
    
    @Override
    public void setCommento(String commento){
        this.commento = commento;
    }
    
    @Override
    public Studente getStudente(){
        return this.studente;
    }
    
    @Override
    public void setStudente(Studente s){
        this.studente = s;
    }
    
    @Override
    public Azienda getAzienda(){
        return this.azienda;
    }
    
    @Override
    public void setAzienda(Azienda a){
        this.azienda = a;
    } 
}
