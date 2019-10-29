/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.proxy.StudenteProxy;
import com.univaq.tirocini.framework.data.DAO;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrea
 */
public class StudenteDAO_MySQL extends DAO implements StudenteDAO {
    
    private PreparedStatement sStudenteByID,sStudenteByEmail;
    private PreparedStatement sStudenti,sPassword;
    private PreparedStatement iStudente, uStudente, dStudente;
    
    public StudenteDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();

            //precompiliamo tutte le query utilizzate nella classe
            sStudenteByID = connection.prepareStatement("SELECT * FROM Studente WHERE ID=?");
            sStudenti = connection.prepareStatement("SELECT ID AS StudenteID FROM Studente");
            sPassword = connection.prepareStatement("Select Password FROM Studente where email=?");
            sStudenteByEmail = connection.prepareStatement("SELECT * FROM Studente WHERE email=?");

            iStudente = connection.prepareStatement("INSERT INTO Studente (Nome,Cognome,DataNascita,LuogoNascita,ProvinciaNascita,CF,Handicap,Email,Ruolo,Residenza,ProvinciaResidenza,CorsoLaurea,NumeroCFU,Telefono,Diploma,Laurea,Specializzazione,Password) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uStudente = connection.prepareStatement("UPDATE Studente SET Nome=?,Cognome=?,DataNascita=?,LuogoNascita=?,ProvinciaNascita=?, CF=?, Handicap=?, Email=?, Ruolo=?, Residenza=?, ProvinciaResidenza=?, CorsoLaurea=?, NumeroCFU=?, Telefono=?, Diploma=?, Laurea=?, Specializzazione=?, Password=? WHERE ID=?");
            dStudente = connection.prepareStatement("DELETE FROM Studente WHERE ID=?");

        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del DataLayer di Studente.", ex);
        }
    }

    
    @Override
    public StudenteProxy createStudente() {
        return new StudenteProxy(getDataLayer());
    }

    @Override
    public StudenteProxy createStudente(ResultSet rs) throws DataException {
    StudenteProxy a = createStudente();
    try {
            a.setKey(rs.getInt("ID"));
            a.setNome(rs.getString("Nome"));
            a.setCognome(rs.getString("Cognome"));
            a.setDataNascita(rs.getDate("DataNascita"));
            a.setLuogoNascita(rs.getString("LuogoNascita"));
            a.setProvinciaNascita(rs.getString("ProvinciaNascita"));
            a.setCF(rs.getString("CF"));
            a.setHandicap(rs.getBoolean("Handicap"));
            a.setEmail(rs.getString("Email"));
            a.setRuolo(rs.getInt("Ruolo"));
            a.setResidenza(rs.getString("Residenza"));
            a.setProvinciaResidenza(rs.getString("ProvinciaResidenza"));
            a.setCorsoLaurea(rs.getString("Laurea"));
            a.setCFU(rs.getInt("NumeroCFU"));
            a.setTelefono(rs.getString("Telefono"));
            a.setDiploma(rs.getString("Diploma"));
            a.setLaurea(rs.getString("Laurea"));
            a.setSpecializzazione(rs.getString("Specializzazione"));
            a.setPassword(rs.getString("Password"));
            
            } catch (SQLException ex) {
                throw new DataException("Impossibile inizializzare lo studente a partire dal ResultSet", ex);
            }
            return a;
        }

    @Override
    public Studente getStudente(int studente_key) throws DataException {

        try {
            sStudenteByID.setInt(1, studente_key);
            try (ResultSet rs = sStudenteByID.executeQuery()) {
                if (rs.next()) {
                    return createStudente(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare lo studente a partire dall'ID.", ex);
        }

        return null;
    }
    
    
   @Override
    public List<Studente> getStudenti() throws DataException {
        List<Studente> result = new ArrayList();

        try (ResultSet rs = sStudenti.executeQuery()) {
            while (rs.next()) {
                result.add((Studente) getStudente(rs.getInt("studenteID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la lista studenti.", ex);
        }
        return result;
    }

    
    @Override
    public Studente getStudenteFromEmail(String email) throws DataException{
     
    try {
            sStudenteByEmail.setString(1, email);
            try (ResultSet rs = sStudenteByEmail.executeQuery()) {
                if (rs.next()) {
                    return createStudente(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare lo studente a partire dall'email.", ex);
        }

        return null;
    }
    
    @Override
    public String getPasswordFromEmail(String email) throws DataException{
        try{ 
        sPassword.setString(1,email);
            try (ResultSet rs= sPassword.executeQuery()){
                if(rs.next()){
                    return rs.getString("Password");
                }
            }
        }
        catch (SQLException ex) {
            throw new DataException("Impossibile trovare l'utente.", ex);
    }
        return null;
    }
    
    @Override
    public void storeStudente(Studente studente) throws DataException {
        int key = studente.getKey();
        try {
            if (studente.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
            
                if (studente instanceof StudenteProxy && !((StudenteProxy) studente).isDirty()) {
                    return;
                }
                
                //Altrimenti aggiorniamo ->
                

                uStudente.setString(1, studente.getNome());
                uStudente.setString(2, studente.getCognome());
                uStudente.setDate(3,studente.getDataNascita());
                uStudente.setString(4,studente.getLuogoNascita());
                uStudente.setString(5,studente.getProvinciaNascita());
                uStudente.setString(6,studente.getCF());
                
                if (studente.isHandicapped() != null)
                uStudente.setBoolean(7, studente.isHandicapped());
                else uStudente.setBoolean(7, false);
                
                uStudente.setString(8, studente.getEmail());
                uStudente.setInt(9, studente.getRuolo());
                uStudente.setString(10, studente.getResidenza());
                uStudente.setString(11, studente.getProvinciaResidenza());
                uStudente.setString(12, studente.getCorsoLaurea());
                uStudente.setInt(13, studente.getCFU());
                uStudente.setString(14, studente.getTelefono());
                uStudente.setString(15, studente.getDiploma());
                uStudente.setString(16, studente.getLaurea());
                uStudente.setString(17, studente.getSpecializzazione());
                uStudente.setString(18, studente.getPassword());
            
                uStudente.setInt(19, studente.getKey());
                uStudente.executeUpdate();
                
                
            } else { //insert
                                
                iStudente.setString(1, studente.getNome());
                iStudente.setString(2, studente.getCognome());
                iStudente.setDate(3, studente.getDataNascita());
                iStudente.setString(4, studente.getLuogoNascita());
                iStudente.setString(5, studente.getProvinciaNascita());
                iStudente.setString(6, studente.getCF());
                
                if (studente.isHandicapped() != null)
                iStudente.setBoolean(7, studente.isHandicapped());
                else iStudente.setBoolean(7, false);
                
                iStudente.setString(8, studente.getEmail());
                iStudente.setInt(9, studente.getRuolo());
                iStudente.setString(10, studente.getResidenza());
                iStudente.setString(11, studente.getProvinciaResidenza());
                iStudente.setString(12, studente.getCorsoLaurea());
                iStudente.setInt(13, studente.getCFU());
                iStudente.setString(14, studente.getTelefono());
                iStudente.setString(15, studente.getDiploma());
                iStudente.setString(16, studente.getLaurea());
                iStudente.setString(17, studente.getSpecializzazione());
                iStudente.setString(18, studente.getPassword());
                
               
                if (iStudente.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    try (ResultSet keys = iStudente.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            key = keys.getInt(1);
                        }
                    }
                    //aggiornaimo la chiave in caso di inserimento
                    studente.setKey(key);
                }
            }

//            //se possibile, restituiamo l'oggetto appena inserito RICARICATO
//            //dal database tramite le API del modello. In tal
//            //modo terremo conto di ogni modifica apportata
//            //durante la fase di inserimento
//   
//            if (key > 0) {
//                article.copyFrom(getArticle(key));
//            }
            //se abbiamo un proxy, resettiamo il suo attributo dirty
           
            if (studente instanceof StudenteProxy) {
                ((StudenteProxy) studente).setDirty(false);
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to store article", ex);
        }
    }
    
    
}
