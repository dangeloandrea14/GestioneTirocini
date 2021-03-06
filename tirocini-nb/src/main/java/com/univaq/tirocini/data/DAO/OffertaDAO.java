package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Andrea
 */
public interface OffertaDAO {
    
    Offerta createOfferta();
    
    Offerta createOfferta(ResultSet rs) throws DataException;
    
    Offerta getOfferta(int offerta_key) throws DataException;
    
    List<Offerta> getOfferte(Azienda azienda) throws DataException;
    
    List<Offerta> getOfferte() throws DataException;
    
    List<Offerta> getOfferteAttive() throws DataException;
    
    List<Offerta> getPaginaOfferteAttive(int page, int itemNum) throws DataException;

    int getOfferteAttiveCount() throws DataException;
    
    void storeOfferta(Offerta offerta) throws DataException;
    
}
