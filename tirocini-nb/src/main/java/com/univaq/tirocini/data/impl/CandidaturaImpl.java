/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.impl;

import com.univaq.tirocini.data.model.Candidatura;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import java.sql.Date;

/**
 *
 * @author Andrea
 */
public class CandidaturaImpl implements Candidatura {
    
    int key;
    Offerta offerta;
    Studente studente;
    Date data;
    
    @Override
    public int getKey(){
        return this.key;
    }
    
    @Override
    public void setKey(int key){
        this.key = key;
    }
    
    @Override
    public Offerta getOfferta(){
        return this.offerta;
    }
    
    @Override
    public void setOfferta(Offerta offerta){
        this.offerta = offerta;
    }
    
    @Override
    public Studente getStudente(){
        return this.studente;
    }
    
    @Override
    public void setStudente(Studente studente){
        this.studente = studente;
    }
    
    @Override
    public Date getData(){
        return this.data;
    }
    
    @Override
    public void setData(Date data){
        this.data = data;
    }
}
