
package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Andrea
 */
public interface StudenteDAO {

Studente createStudente();

Studente createStudente(ResultSet rs) throws DataException;

Studente getStudente(int studente_key) throws DataException;

List<Studente> getStudenti() throws DataException;

String getPasswordFromEmail(String email) throws DataException;

void storeStudente(Studente studente) throws DataException;
    
}
