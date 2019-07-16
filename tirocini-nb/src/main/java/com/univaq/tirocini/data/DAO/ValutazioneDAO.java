/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Valutazione;
import com.univaq.tirocini.framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Andrea
 */
public interface ValutazioneDAO {
    
    Valutazione createValutazione();
    
    Valutazione createValutazione(ResultSet rs) throws DataException;
    
    Valutazione getValutazione(int valutazione_key) throws DataException;
    
    List<Valutazione> getValutazioni(Studente studente) throws DataException;
    
    List<Valutazione> getValutazioni(Azienda azienda) throws DataException;
    
    List<Valutazione> getValutazioni() throws DataException;
    
    void storeValutazione(Valutazione val) throws DataException;
    
}
