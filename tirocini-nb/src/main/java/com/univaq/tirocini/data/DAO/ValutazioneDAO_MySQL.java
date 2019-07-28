package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Valutazione;
import com.univaq.tirocini.data.proxy.ValutazioneProxy;
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
public class ValutazioneDAO_MySQL extends DAO implements ValutazioneDAO {
    
    private PreparedStatement sValutazioneByID;
    private PreparedStatement sValutazioni, sValutazioniByAzienda, sValutazioniByStudente;
    private PreparedStatement iValutazione, uValutazione, dValutazione;

    public ValutazioneDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            sValutazioneByID = connection.prepareStatement("SELECT * FROM Valutazione WHERE ID=?");
            sValutazioniByStudente = connection.prepareStatement("SELECT ID AS ValutazioneID FROM Valutazione WHERE IDStudente=?");
            sValutazioniByAzienda = connection.prepareStatement("SELECT ID AS ValutazioneID FROM Valutazione WHERE IDAzienda=?");
            sValutazioni = connection.prepareStatement("SELECT ID AS ValutazioneID FROM Valutazione");

            //notare l'ultimo paametro extra di questa chiamata a
            //prepareStatement: lo usiamo per assicurarci che il JDBC
            //restituisca la chiave generata automaticamente per il
            //record inserito
          
            iValutazione = connection.prepareStatement("INSERT INTO Valutazione (Stelle,Commento,IDStudente,IDAzienda) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uValutazione = connection.prepareStatement("UPDATE Valutazione SET Stelle=?,Commento=?,IDStudente=?,IDAzienda=? WHERE ID=?");
            dValutazione = connection.prepareStatement("DELETE FROM Valutazione WHERE ID=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }
    
    
   @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent è una buona pratica...
        //e noi seguiamo il consiglio
        try {

            sValutazioneByID.close();

            sValutazioniByAzienda.close();
            sValutazioniByStudente.close();
            sValutazioni.close();

            iValutazione.close();
            uValutazione.close();
            dValutazione.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }  
 
    
    @Override
    public ValutazioneProxy createValutazione() {
        return new ValutazioneProxy(getDataLayer());
    }

    
    @Override
    public ValutazioneProxy createValutazione(ResultSet rs) throws DataException {
        ValutazioneProxy a = createValutazione();
        try {
             a.setKey(rs.getInt("ID"));
             a.setStelle(rs.getInt("Stelle"));
             a.setCommento(rs.getString("Commento"));
             a.setAziendakey(rs.getInt("IDAzienda"));
             a.setStudentekey(rs.getInt("IDStudente"));
                     
                  
            } catch (SQLException ex) {
                throw new DataException("Impossibile creare la valutazione dal ResultSet.", ex);
            }
            return a;
        }

    @Override
    public Valutazione getValutazione(int valutazione_key) throws DataException {

        try {
            sValutazioneByID.setInt(1, valutazione_key);
            try (ResultSet rs = sValutazioneByID.executeQuery()) {
                if (rs.next()) {
                    return createValutazione(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la valutazione dall'ID.", ex);
        }

        return null;
    }
    
    @Override
    public List<Valutazione> getValutazioni(Azienda azienda) throws DataException {
        List<Valutazione> result = new ArrayList();

        try {
            sValutazioniByAzienda.setInt(1, azienda.getKey());
            try (ResultSet rs = sValutazioniByAzienda.executeQuery()) {
                while (rs.next()) {
                    result.add((Valutazione) getValutazione(rs.getInt("ValutazioneID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le valutazioni relative all'azienda.", ex);
        }
        return result;
    }
    
    @Override
    public List<Valutazione> getValutazioni(Studente studente) throws DataException {
        List<Valutazione> result = new ArrayList();

        try {
            sValutazioniByStudente.setInt(1, studente.getKey());
            try (ResultSet rs = sValutazioniByStudente.executeQuery()) {
                while (rs.next()) {
                    result.add((Valutazione) getValutazione(rs.getInt("ValutazioneID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le valutazioni relative allo studente.", ex);
        }
        return result;
    }
    
    @Override
    public List<Valutazione> getValutazioni() throws DataException {
        List<Valutazione> result = new ArrayList();

        try (ResultSet rs = sValutazioni.executeQuery()) {
            while (rs.next()) {
                result.add((Valutazione) getValutazione(rs.getInt("ValutazioneID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare gli articoli.", ex);
        }
        return result;
    }
    
    @Override
    public void storeValutazione(Valutazione valutazione) throws DataException {
        int key = valutazione.getKey();
        try {
            if (valutazione.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
              
                if (valutazione instanceof ValutazioneProxy && !((ValutazioneProxy) valutazione).isDirty()) {
                    return;
                }

                uValutazione.setInt(1, valutazione.getStelle());
                uValutazione.setString(2, valutazione.getCommento());
                
                if(valutazione.getStudente() != null){
                uValutazione.setInt(3, valutazione.getStudente().getKey()); }
                else {
                    uValutazione.setNull(3, java.sql.Types.INTEGER);
                }
                
                if(valutazione.getAzienda() != null){
                uValutazione.setInt(4, valutazione.getAzienda().getKey());}
                else {
                    uValutazione.setNull(3, java.sql.Types.INTEGER);
                }
             
                uValutazione.setInt(5, valutazione.getKey());
                uValutazione.executeUpdate();
                
                
            } else { //insert
                
iValutazione = connection.prepareStatement("INSERT INTO Valutazione (Stelle,Commento,IDStudente,IDAzienda) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                iValutazione.setInt(1, valutazione.getStelle());
                iValutazione.setString(2, valutazione.getCommento());
                iValutazione.setInt(3, valutazione.getStudente().getKey());
                iValutazione.setInt(4, valutazione.getAzienda().getKey());
              
                if (iValutazione.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    
                    try (ResultSet keys = iValutazione.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            
                            key = keys.getInt(1);
                        }
                    }
                    //aggiorniamo la chiave in caso di inserimento
                    valutazione.setKey(key);
                }
            }

           
            if (valutazione instanceof ValutazioneProxy) {
                ((ValutazioneProxy) valutazione).setDirty(false);
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la valutazione.", ex);
        }
    }

}
