/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Candidatura;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.proxy.CandidaturaProxy;
import com.univaq.tirocini.framework.data.DAO;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrea
 */
public class CandidaturaDAO_MySQL extends DAO implements CandidaturaDAO{
    
    private PreparedStatement sCandidaturaByID;
    private PreparedStatement sCandidature, sCandidatureByStudente, sCandidatureByOfferta;
    private PreparedStatement iCandidatura, uCandidatura, dCandidatura;
    
    
    public CandidaturaDAO_MySQL(DataLayer d) {
        super(d);
    }
    
     @Override
    public void init() throws DataException {
        //Precompiliamo tutte le query, iniziando dai select.
      try{
          super.init();
      
        sCandidaturaByID = connection.prepareStatement("SELECT * FROM Candidatura WHERE ID=?");
        sCandidature = connection.prepareStatement("SELECT ID AS CandidaturaID FROM Candidatura");
        sCandidatureByStudente = connection.prepareStatement("SELECT ID AS CandidaturaID FROM Candidatura WHERE IDStudente=?");
        sCandidatureByOfferta = connection.prepareStatement("SELECT ID AS CandidaturaID FROM Candidatura WHERE IDOfferta=?");
       
        //Ora precompiliamo insert, update, delete
        
        iCandidatura = connection.prepareStatement("INSERT INTO Candidatura (IDOfferta,IDStudente) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
        uCandidatura = connection.prepareStatement("UPDATE Candidatura SET IDOfferta=?, IDStudente=? WHERE ID=?");
        dCandidatura = connection.prepareStatement("DELETE FROM Candidatura WHERE ID=?");

      }
      catch(SQLException ex){
          throw new DataException("Errore durante l'inizializzazione del DataLayer di Candidatura.", ex);
      }
    }
    
    
    @Override
    public void deleteCandidatura(Candidatura c) throws DataException{
        int cid = c.getKey();
        try {
            dCandidatura.setInt(1,cid);
            dCandidatura.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CandidaturaDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
     @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent è una buona pratica...
        //e noi seguiamo il consiglio
        try {

            sCandidaturaByID.close();

            sCandidature.close();
            sCandidatureByStudente.close();
            sCandidatureByOfferta.close();
            
            iCandidatura.close();
            uCandidatura.close();
            dCandidatura.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    
    
    @Override
    public CandidaturaProxy createCandidatura(){
        return new CandidaturaProxy(getDataLayer());
    }
    
    @Override
    public CandidaturaProxy createCandidatura(ResultSet rs) throws DataException{
        CandidaturaProxy a = createCandidatura();
            try {
                a.setKey(rs.getInt("ID"));
                a.setOffertakey(rs.getInt("IDOfferta"));
                a.setStudentekey(rs.getInt("IDStudente"));
                a.setData(rs.getDate("Data"));
                
            } catch (SQLException ex) {
                throw new DataException("Impossibile creare l'offerta dal ResultSet", ex);
            }
            return a;
        
    }
    
    @Override
    public Candidatura getCandidatura(int candidatura_key) throws DataException {

        try {
            sCandidaturaByID.setInt(1, candidatura_key);
            try (ResultSet rs = sCandidaturaByID.executeQuery()) {
                if (rs.next()) {
                    return createCandidatura(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la candidatura dall'ID.", ex);
        }

        return null;
    }
    
    @Override
    public List<Candidatura> getCandidature(Offerta offerta) throws DataException {
        List<Candidatura> result = new ArrayList();

        try {
            sCandidatureByOfferta.setInt(1, offerta.getKey());
            try (ResultSet rs = sCandidatureByOfferta.executeQuery()) {
                while (rs.next()) {
                    result.add((Candidatura) getCandidatura(rs.getInt("CandidaturaID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le candidature dell'offerta selezionata.", ex);
        }
        return result;
    }
    
    @Override
    public List<Candidatura> getCandidature(Studente studente) throws DataException {
        List<Candidatura> result = new ArrayList();

        try {
            sCandidatureByStudente.setInt(1, studente.getKey());
            try (ResultSet rs = sCandidatureByStudente.executeQuery()) {
                while (rs.next()) {
                    result.add((Candidatura) getCandidatura(rs.getInt("CandidaturaID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le candidature dello studente selezionato.", ex);
        }
        return result;
    }
    
    @Override
    public List<Candidatura> getCandidature() throws DataException {
        List<Candidatura> result = new ArrayList();

        try (ResultSet rs = sCandidature.executeQuery()) {
            while (rs.next()) {
                result.add((Candidatura) getCandidatura(rs.getInt("CandidaturaID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le candidature.", ex);
        }
        
        return result;
    }
    
    @Override
    public void storeCandidatura(Candidatura candidatura) throws DataException {
        int key = candidatura.getKey();
        try {
            if (candidatura.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                if (candidatura instanceof CandidaturaProxy && !((CandidaturaProxy) candidatura).isDirty()) {
                    return;
                }
                
                if(candidatura.getOfferta() != null)
                uCandidatura.setInt(1, candidatura.getOfferta().getKey());
                else {
                    uCandidatura.setNull(1, java.sql.Types.INTEGER);
                }
                
                if(candidatura.getStudente() != null)
                uCandidatura.setInt(2, candidatura.getStudente().getKey());
                else {
                    uCandidatura.setNull(2, java.sql.Types.INTEGER);
                }
               
                uCandidatura.executeUpdate();
                
            }
            else { //insert
                
                if(candidatura.getOfferta() != null)
                iCandidatura.setInt(1, candidatura.getOfferta().getKey());
                else {
                    iCandidatura.setNull(1, java.sql.Types.INTEGER);
                }
                
                if(candidatura.getStudente() != null)
                iCandidatura.setInt(2, candidatura.getStudente().getKey());
                else {
                    iCandidatura.setNull(2, java.sql.Types.INTEGER);
                }

                if (iCandidatura.executeUpdate() == 1) {
                    
                }
                    try (ResultSet keys = iCandidatura.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            
                            key = keys.getInt(1);
                        }
                    }

            if (candidatura instanceof CandidaturaProxy) {
                ((CandidaturaProxy) candidatura).setDirty(false);
            }
        }
        }catch (SQLException ex) {
            throw new DataException("Impossibile memorizzare la candidatura.", ex);
        }
    }
    
}
