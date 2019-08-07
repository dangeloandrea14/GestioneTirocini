/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Candidatura;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.data.model.Valutazione;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.data.DataLayer;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Andrea
 */
public class TirocinioDataLayer extends DataLayer {
 
    public TirocinioDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }
    
    @Override
    public void init() throws DataException {
        //Registriamo i nostri DAO.
        
        registerDAO(Azienda.class, new AziendaDAO_MySQL(this));
        registerDAO(Offerta.class, new OffertaDAO_MySQL(this));
        registerDAO(Studente.class, new StudenteDAO_MySQL(this));
        registerDAO(Tirocinio.class, new TirocinioDAO_MySQL(this));
        registerDAO(Valutazione.class, new ValutazioneDAO_MySQL(this));  
        registerDAO(Candidatura.class, new CandidaturaDAO_MySQL(this));
    }
    
    //Definiamo gli helpers
    
    public AziendaDAO getAziendaDAO(){
        return (AziendaDAO) getDAO(Azienda.class); 
    }
    
    public OffertaDAO getOffertaDAO(){
        return (OffertaDAO) getDAO(Offerta.class);
    }
    
    public StudenteDAO getStudenteDAO(){
        return (StudenteDAO) getDAO(Studente.class);
    }
    
    public TirocinioDAO getTirocinioDAO(){
        return (TirocinioDAO) getDAO(Tirocinio.class);
    }
    
    public ValutazioneDAO getValutazioneDAO(){
        return (ValutazioneDAO) getDAO(Valutazione.class);
    }
    
    public CandidaturaDAO getCandidaturaDAO(){
        return (CandidaturaDAO) getDAO(Candidatura.class);
    }
    
}
