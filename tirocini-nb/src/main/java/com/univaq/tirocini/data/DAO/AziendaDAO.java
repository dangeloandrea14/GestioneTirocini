
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

Azienda getAziendaFromEmail(String email) throws DataException;

List<Azienda> getAziende() throws DataException;

List<Azienda> getAziendeConvenzionate() throws DataException;

List<Azienda> getPaginaAziendeConvenzionate(int page, int itemNum) throws DataException;

int getAziendeConvenzionateCount() throws DataException;

List<Azienda> getAziendeNonConvenzionate() throws DataException;

String getPasswordFromEmail(String email) throws DataException;

void storeAzienda(Azienda azienda) throws DataException;
    
List<Azienda> searchAzienda(String queryString) throws DataException;
}
