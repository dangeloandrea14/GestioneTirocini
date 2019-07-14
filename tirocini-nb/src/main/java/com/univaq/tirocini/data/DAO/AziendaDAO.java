
package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Andrea
 */
public interface AziendaDAO {
    
Azienda createAzienda();

Azienda createAzienda(ResultSet rs) throws DataException;

Azienda getAzienda(int azienda_key) throws DataException;

List<Azienda> getAziende() throws DataException;

void storeAzienda(Azienda azienda) throws DataException;
    
}
