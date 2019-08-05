/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Candidatura;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Andrea
 */
public interface CandidaturaDAO {
    
    Candidatura createCandidatura();
    
    Candidatura createCandidatura(ResultSet rs) throws DataException;
    
    Candidatura getCandidatura(int candidatura_key) throws DataException;
    
    List<Candidatura> getCandidature(Studente studente) throws DataException;
    
    List<Candidatura> getCandidature(Offerta offerta) throws DataException;
    
    List<Candidatura> getCandidature() throws DataException;
        
    void storeCandidatura(Candidatura candidatura) throws DataException;
    
    
}
