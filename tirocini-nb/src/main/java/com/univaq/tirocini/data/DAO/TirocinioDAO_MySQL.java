package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.data.proxy.StudenteProxy;
import com.univaq.tirocini.data.proxy.TirocinioProxy;
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
public class TirocinioDAO_MySQL extends DAO implements TirocinioDAO {
    
    private PreparedStatement sTirocinioByID;
    private PreparedStatement sTirocini,sTirociniByAzienda,sTirociniByStudente;
    private PreparedStatement iTirocinio, uTirocinio, dTirocinio;
    
    
    
    public TirocinioDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();

            //precompiliamo tutte le query utilizzate nella classe
            sTirocinioByID = connection.prepareStatement("SELECT * FROM Tirocinio WHERE ID=?");
            sTirocini = connection.prepareStatement("SELECT ID AS TirocinioID FROM Tirocinio");
            sTirociniByStudente = connection.prepareStatement("SELECT ID AS TirocinioID From Tirocinio where IDStudente=?");
            sTirociniByAzienda = connection.prepareStatement("SELECT ID AS TirocinioID From Tirocinio where IDAzienda=?");
            
            iTirocinio = connection.prepareStatement("INSERT INTO Tirocinio (IDAzienda, IDStudente, Inizio, Fine, SettoreInserimento, TempoDiAccesso, NumeroOre, TutoreUniversitario, TutoreAziendale, Attivo, PathDocumento) VALUES(?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uTirocinio = connection.prepareStatement("UPDATE Tirocinio SET IDAzienda=?,IDStudente=?,Inizio=?,Fine=?, SettoreInserimento=?, TempoDiAccesso=?, NumeroOre=?, TutoreUniversitario=?, TutoreAziendale=?, Attivo=?, PathDocumento=? WHERE ID=?");
            dTirocinio = connection.prepareStatement("DELETE FROM Tirocinio WHERE ID=?");

        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del DataLayer di Tirocinio.", ex);
        }
    }
    
    
    @Override
    public TirocinioProxy createTirocinio() {
        return new TirocinioProxy(getDataLayer());
    }

    
    
    @Override
    public TirocinioProxy createTirocinio(ResultSet rs) throws DataException {
    TirocinioProxy a = createTirocinio();
    try {
           
            a.setKey(rs.getInt("ID"));
            a.setAziendakey(rs.getInt("IDAzienda"));
            a.setStudentekey(rs.getInt("IDStudente"));
            a.setInizio(rs.getDate("Inizio"));
            a.setFine(rs.getDate("Fine"));
            a.setSettoreInserimento(rs.getString("SettoreInserimento"));
            a.setTempoDiAccesso(rs.getString("TempoDiAccesso"));
            a.setNumeroOre(rs.getString("NumeroOre"));
            a.setTutoreUniversitario(rs.getString("TutoreUniversitario"));
            a.setTutoreAziendale(rs.getString("TutoreAziendale"));
            a.setAttivo(rs.getBoolean("Attivo"));
            a.setPathDocumento(rs.getString("PathDocumento"));
            
            
            
            } catch (SQLException ex) {
                throw new DataException("Impossibile inizializzare lo studente a partire dal ResultSet", ex);
            }
            return a;
        }
    
    
    
     @Override
    public Tirocinio getTirocinio(int tirocinio_key) throws DataException {

        try {
            sTirocinioByID.setInt(1, tirocinio_key);
            try (ResultSet rs = sTirocinioByID.executeQuery()) {
                if (rs.next()) {
                    return createTirocinio(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare il tirocinio dall'ID.", ex);
        }

        return null;
    }
    
    @Override
    public List<Tirocinio> getTirocini(Azienda azienda) throws DataException {
        List<Tirocinio> result = new ArrayList();

        try {
            sTirociniByAzienda.setInt(1, azienda.getKey());
            try (ResultSet rs = sTirociniByAzienda.executeQuery()) {
                while (rs.next()) {
                    result.add((Tirocinio) getTirocinio(rs.getInt("TirocinioID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare i tirocini dell'azienda.", ex);
        }
        return result;
    }

    @Override
    public List<Tirocinio> getTirocini(Studente studente) throws DataException {
        List<Tirocinio> result = new ArrayList();

        try {
            sTirociniByStudente.setInt(1, studente.getKey());
            try (ResultSet rs = sTirociniByStudente.executeQuery()) {
                while (rs.next()) {
                    result.add((Tirocinio) getTirocinio(rs.getInt("TirocinioID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare i tirocini dello studente.", ex);
        }
        return result;
    }
    
    @Override
    public List<Tirocinio> getTirocini() throws DataException {
        List<Tirocinio> result = new ArrayList();

        try (ResultSet rs = sTirocini.executeQuery()) {
            while (rs.next()) {
                result.add((Tirocinio) getTirocinio(rs.getInt("TirocinioID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare i tirocini.", ex);
        }
        return result;
    }
    
    @Override
    public void storeTirocinio(Tirocinio tirocinio) throws DataException {
        int key = tirocinio.getKey();
        try {
            if (tirocinio.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (tirocinio instanceof TirocinioProxy && !((TirocinioProxy) tirocinio).isDirty()) {
                    return;
                }


                if(tirocinio.getAzienda() != null)
                uTirocinio.setInt(1, tirocinio.getAzienda().getKey());
                else {
                    uTirocinio.setNull(1, java.sql.Types.INTEGER);
                }
                
                if(tirocinio.getStudente() != null)
                uTirocinio.setInt(2, tirocinio.getStudente().getKey());
                else {
                    uTirocinio.setNull(2, java.sql.Types.INTEGER);
                }
                
                uTirocinio.setDate(3,tirocinio.getInizio());
                
                uTirocinio.setDate(4,tirocinio.getFine());
                
                uTirocinio.setString(5, tirocinio.getSettoreInserimento());
                
                uTirocinio.setString(6, tirocinio.getTempoDiAccesso());
                
                uTirocinio.setString(7, tirocinio.getNumeroOre());
                
                uTirocinio.setString(8, tirocinio.getTutoreUniversitario());
                
                uTirocinio.setString(9, tirocinio.getTutoreAziendale());
                
                uTirocinio.setBoolean(10, tirocinio.isAttivo());
                
                uTirocinio.setString(11, tirocinio.getPathDocumento());
                
                
                uTirocinio.setInt(12, tirocinio.getKey());
               
                uTirocinio.executeUpdate();
                
                
            } else { //insert                

                if(tirocinio.getAzienda() != null)
                iTirocinio.setInt(1, tirocinio.getAzienda().getKey());
                else {
                    iTirocinio.setNull(1, java.sql.Types.INTEGER);
                }
                
                if(tirocinio.getStudente() != null)
                iTirocinio.setInt(2, tirocinio.getStudente().getKey());
                 else {
                    iTirocinio.setNull(2, java.sql.Types.INTEGER);
                }
                
                iTirocinio.setDate(3,tirocinio.getInizio());
                
                iTirocinio.setDate(4,tirocinio.getFine());
                
                iTirocinio.setString(5, tirocinio.getSettoreInserimento());
                
                iTirocinio.setString(6, tirocinio.getTempoDiAccesso());
                
                iTirocinio.setString(7, tirocinio.getNumeroOre());
                
                iTirocinio.setString(8, tirocinio.getTutoreUniversitario());
                
                iTirocinio.setString(9, tirocinio.getTutoreAziendale());
                
                iTirocinio.setBoolean(10, tirocinio.isAttivo());
                
                iTirocinio.setString(11, tirocinio.getPathDocumento());
                
                if (iTirocinio.executeUpdate() == 1) {
                    try (ResultSet keys = iTirocinio.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        if (keys.next()) {
                            
                            key = keys.getInt(1);
                        }
                    }
                    //aggiornaimo la chiave in caso di inserimento
                    //after an insert, uopdate the object key
                    tirocinio.setKey(key);
                }
            }
           
            if (tirocinio instanceof TirocinioProxy) {
                ((TirocinioProxy) tirocinio).setDirty(false);
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile memorizzare il tirocinio.", ex);
        }
    }

}
