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
public interface Candidatura {
    
    int getKey();
    
    void setKey(int key);
    
    public Offerta getOfferta();
    
    public void setOfferta(Offerta offerta);
    
    public Studente getStudente();
    
    public void setStudente(Studente studente);
    
    public Date getData();
    
    public void setData(Date date);
    
}
