/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Andrea
 */
public interface TirocinioDAO {
    
    Tirocinio createTirocinio();
    
    Tirocinio createTirocinio(ResultSet rs) throws DataException;
    
    Tirocinio getTirocinio(int tirocinio_key) throws DataException;
    
    List<Tirocinio> getTirocini(Azienda azienda) throws DataException;
    
    List<Tirocinio> getTirocini(Studente studente) throws DataException;
    
    List<Tirocinio> getTirocini() throws DataException;
    
    void storeTirocinio() throws DataException;
    
}
