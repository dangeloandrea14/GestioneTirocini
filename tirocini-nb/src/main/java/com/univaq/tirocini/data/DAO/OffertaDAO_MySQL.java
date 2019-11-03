package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.proxy.OffertaProxy;
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
public class OffertaDAO_MySQL extends DAO implements OffertaDAO {

    private PreparedStatement sOffertaByID;
    private PreparedStatement sOfferte, sOfferteAttive, sOfferteByAzienda, sOfferteAttiveByAzienda, sPaginaOfferteAttive, sOfferteAttiveCount;
    private PreparedStatement iOfferta, uOfferta, dOfferta;
    private PreparedStatement sOffertaSearch;

    public OffertaDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        //Precompiliamo tutte le query, iniziando dai select.
        try {
            super.init();

            sOffertaByID = connection.prepareStatement("SELECT * FROM Offerta WHERE ID=?");
            sOfferteByAzienda = connection.prepareStatement("SELECT ID AS OffertaID FROM Offerta WHERE IDAzienda=?");
            sOfferteAttiveByAzienda = connection.prepareStatement("SELECT ID AS OffertaID FROM Offerta WHERE IDAzienda=? AND Attiva=1");
            sOfferte = connection.prepareStatement("SELECT ID AS OffertaID FROM Offerta");
            sOfferteAttive = connection.prepareStatement("SELECT ID AS OffertaID FROM Offerta WHERE Attiva=1");

            sOfferteAttiveCount = connection.prepareStatement("SELECT COUNT(*) FROM Offerta where Attiva=1");
            sPaginaOfferteAttive = connection.prepareStatement("SELECT ID as OffertaID FROM Offerta where Attiva=1 ORDER BY ID DESC LIMIT ?,?");
            sOffertaSearch = connection.prepareStatement("SELECT * FROM Offerta WHERE Luogo LIKE  ?  ");
           
            
            //Ora precompiliamo insert, update, delete
            iOfferta = connection.prepareStatement("INSERT INTO Offerta (IDAzienda,Luogo,Orari,Durata,Obiettivi,Modalità,RimborsoSpese,CFU,Attiva) VALUES(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uOfferta = connection.prepareStatement("UPDATE Offerta SET IDAzienda=?,Luogo=?, Orari=?, Durata=?, Obiettivi=?, Modalità=?, RimborsoSpese=?, CFU=?, Attiva=? WHERE ID=?");
            dOfferta = connection.prepareStatement("DELETE FROM Offerta WHERE ID=?");

        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del DataLayer di Offerta", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent è una buona pratica...
        //e noi seguiamo il consiglio
        try {

            sOffertaByID.close();

            sOfferteByAzienda.close();
            sOfferte.close();
            sOfferteAttive.close();

            iOfferta.close();
            uOfferta.close();
            dOfferta.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public OffertaProxy createOfferta() {
        return new OffertaProxy(getDataLayer());
    }

    @Override
    public OffertaProxy createOfferta(ResultSet rs) throws DataException {
        OffertaProxy a = createOfferta();
        try {
            a.setKey(rs.getInt("ID"));
            a.setAziendakey(rs.getInt("IDAzienda"));
            a.setLuogo(rs.getString("Luogo"));
            a.setOrari(rs.getString("Orari"));
            a.setDurata(rs.getString("Durata"));
            a.setObiettivi(rs.getString("Obiettivi"));
            a.setModalità(rs.getString("Modalità"));
            a.setRimborsoSpese(rs.getString("RimborsoSpese"));
            a.setCFU(rs.getInt("CFU"));
            a.setAttiva(rs.getBoolean("Attiva"));
            a.setDataInserimento(rs.getDate("DataInserimento"));

        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'offerta dal ResultSet", ex);
        }
        return a;

    }

    @Override
    public Offerta getOfferta(int offerta_key) throws DataException {

        try {
            sOffertaByID.setInt(1, offerta_key);
            try (ResultSet rs = sOffertaByID.executeQuery()) {
                if (rs.next()) {
                    return createOfferta(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare l'offerta dall'ID.", ex);
        }

        return null;
    }

    @Override
    public List<Offerta> getOfferte(Azienda azienda) throws DataException {
        List<Offerta> result = new ArrayList();

        try {
            sOfferteByAzienda.setInt(1, azienda.getKey());
            try (ResultSet rs = sOfferteByAzienda.executeQuery()) {
                while (rs.next()) {
                    result.add((Offerta) getOfferta(rs.getInt("OffertaID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le offerte.", ex);
        }
        return result;
    }
    
    @Override
    public List<Offerta> getOfferteAttive(Azienda azienda) throws DataException {
        List<Offerta> result = new ArrayList();

        try {
            sOfferteByAzienda.setInt(1, azienda.getKey());
            try (ResultSet rs = sOfferteByAzienda.executeQuery()) {
                while (rs.next()) {
                    result.add((Offerta) getOfferta(rs.getInt("OffertaID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le offerte.", ex);
        }
        return result;
    }

    @Override
    public List<Offerta> getOfferteAttive() throws DataException {
        List<Offerta> result = new ArrayList();

        try (ResultSet rs = sOfferteAttive.executeQuery()) {
            while (rs.next()) {
                result.add((Offerta) getOfferta(rs.getInt("OffertaID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le offerte.", ex);
        }
        return result;
    }

    @Override
    public List<Offerta> getPaginaOfferteAttive(int page, int itemNum) throws DataException {
        List<Offerta> result = new ArrayList();

        if (page < 1) {
            page = 1;
        }

        try {
            
            sPaginaOfferteAttive.setInt(1, (page - 1) * itemNum); //offset
            
            sPaginaOfferteAttive.setInt(2, itemNum); //row_count
            
            try (ResultSet rs = sPaginaOfferteAttive.executeQuery()) {
                while (rs.next()) {
                    result.add((Offerta) getOfferta(rs.getInt("OffertaID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le offerte.", ex);
        }
        return result;
    }

    @Override
    public int getOfferteAttiveCount() throws DataException {

        int count = 0;

        try {
            try (ResultSet rs = sOfferteAttiveCount.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare il numero delle offerte.", ex);
        }

        return count;
    }

    @Override
    public List<Offerta> getOfferte() throws DataException {
        List<Offerta> result = new ArrayList();

        try (ResultSet rs = sOfferte.executeQuery()) {
            while (rs.next()) {
                result.add((Offerta) getOfferta(rs.getInt("OffertaID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le offerte.", ex);
        }
        return result;
    }

    
    
    @Override
    public List<Offerta> searchOffertaByPlace(String queryString) throws DataException {
        List<Offerta> risultato = new ArrayList();

        try {
            sOffertaSearch.setString(1, "%" + queryString + "%");
            try (ResultSet rs = sOffertaSearch.executeQuery()) {
                while (rs.next()) {
                    risultato.add(createOfferta(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile eseguire la ricerca", ex);
        }

        return risultato;
    }
    
    
    @Override
    public void storeOfferta(Offerta offerta) throws DataException {
        int key = offerta.getKey();
        try {
            if (offerta.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                if (offerta instanceof OffertaProxy && !((OffertaProxy) offerta).isDirty()) {
                    return;
                }

                if (offerta.getAzienda() != null) {
                    uOfferta.setInt(1, offerta.getAzienda().getKey());
                } else {
                    uOfferta.setNull(1, java.sql.Types.INTEGER);
                }

                uOfferta.setString(2, offerta.getLuogo());

                uOfferta.setString(3, offerta.getOrari());

                uOfferta.setString(4, offerta.getDurata());

                uOfferta.setString(5, offerta.getObiettivi());

                uOfferta.setString(6, offerta.getModalità());

                uOfferta.setString(7, offerta.getRimborsoSpese());

                uOfferta.setInt(8, offerta.getCFU());

                uOfferta.setBoolean(9, offerta.isAttiva());

                uOfferta.setInt(10, offerta.getKey());
                uOfferta.executeUpdate();

            } else { //insert

                if (offerta.getAzienda() != null) {
                    iOfferta.setInt(1, offerta.getAzienda().getKey());
                } else {
                    iOfferta.setNull(1, java.sql.Types.INTEGER);
                }

                iOfferta.setString(2, offerta.getLuogo());

                iOfferta.setString(3, offerta.getOrari());

                iOfferta.setString(4, offerta.getDurata());

                iOfferta.setString(5, offerta.getObiettivi());

                iOfferta.setString(6, offerta.getModalità());

                iOfferta.setString(7, offerta.getRimborsoSpese());

                iOfferta.setInt(8, offerta.getCFU());

                iOfferta.setBoolean(9, offerta.isAttiva());

                if (iOfferta.executeUpdate() == 1) {

                }
                try (ResultSet keys = iOfferta.getGeneratedKeys()) {
                    //il valore restituito è un ResultSet con un record
                    //per ciascuna chiave generata (uno solo nel nostro caso)

                    if (keys.next()) {
                        //i campi del record sono le componenti della chiave
                        //(nel nostro caso, un solo intero)

                        key = keys.getInt(1);
                    }
                }

                if (offerta instanceof OffertaProxy) {
                    ((OffertaProxy) offerta).setDirty(false);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile memorizzare l'offerta.", ex);
        }
    }

}
