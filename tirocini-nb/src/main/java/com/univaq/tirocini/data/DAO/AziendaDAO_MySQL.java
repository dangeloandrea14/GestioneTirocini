/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.DAO;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.proxy.AziendaProxy;
import com.univaq.tirocini.framework.data.DAO;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.data.DataLayer;
import com.univaq.tirocini.vo.IVA;
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
public class AziendaDAO_MySQL extends DAO implements AziendaDAO {
    
    
    //Nota: le lettere (s,i,u,d) che precedono i nomi stanno per Select, Insert, Update e Delete.
    private PreparedStatement sAziendaByID,sAziendaByEmail;
    private PreparedStatement sAziende,sPassword,sAziendeConvenzionate;
    private PreparedStatement iAzienda, uAzienda, dAzienda;

    public AziendaDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException{
        try{
            super.init();
            
            //Precompiliamo le select di Azienda
            sAziendaByID = connection.prepareStatement("SELECT * from Azienda where ID=?");
            sAziende = connection.prepareStatement("SELECT ID as AziendaID from Azienda");
            sAziendeConvenzionate = connection.prepareStatement("SELECT ID as AziendaID FROM Azienda where Convenzionata=1");
            sPassword = connection.prepareStatement("SELECT Password FROM Azienda where emailResponsabile=?");
            sAziendaByEmail = connection.prepareStatement("SELECT * FROM Azienda where emailResponsabile=?");
            
            
            //Precompiliamo le altre query
            iAzienda = connection.prepareStatement("INSERT INTO Azienda (Nome,Descrizione,Sede,IVA,ForoCompetenza,NomeResponsabile,CognomeResponsabile,TelefonoResponsabile,emailResponsabile,NomeCognomeLegale,Password,PathDocumento,Convenzionata) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uAzienda = connection.prepareStatement("UPDATE Azienda SET Nome=?,Descrizione=?,Sede=?,IVA=?,ForoCompetenza=?,NomeResponsabile=?,CognomeResponsabile=?,TelefonoResponsabile=?,emailResponsabile=?,NomeCognomeLegale=?,Password=?,PathDocumento=?,Convenzionata=? WHERE ID=?");
            dAzienda = connection.prepareStatement("DELETE FROM Azienda WHERE ID=?");
      
        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }
    
     @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent è una buona pratica...
        //e noi seguiamo il consiglio
        try {

            sAziendaByID.close();
            sAziende.close();

            iAzienda.close();
            uAzienda.close();
            dAzienda.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public AziendaProxy createAzienda() {
        return new AziendaProxy(getDataLayer());
    }

    @Override
    public AziendaProxy createAzienda(ResultSet rs) throws DataException {
        AziendaProxy a = createAzienda();
        try {
            a.setKey(rs.getInt("ID"));
            a.setNome(rs.getString("Nome"));
            a.setDescrizione(rs.getString("Descrizione"));
            a.setSede(rs.getString("sede"));
            a.setIva(new IVA(rs.getString("IVA")));
            a.setForoCompetenza(rs.getString("ForoCompetenza"));
            a.setNomeResponsabile(rs.getString("NomeResponsabile"));
            a.setCognomeResponsabile(rs.getString("CognomeResponsabile"));
            a.setTelefonoResponsabile(rs.getString("TelefonoResponsabile"));
            a.setEmailResponsabile(rs.getString("emailResponsabile"));
            a.setNomeCognomeLegale(rs.getString("NomeCognomeLegale"));
            a.setVoto(rs.getInt("Voto"));
            a.setPassword(rs.getString("Password"));
            a.setPath(rs.getString("PathDocumento"));
            a.setConvenzionata(rs.getBoolean("Convenzionata"));
            
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'azienda da questo ResultSet.", ex);
        }
        return a;
    }
    
    @Override
    public Azienda getAzienda(int azienda_key) throws DataException {

        try {
            sAziendaByID.setInt(1, azienda_key);
            try (ResultSet rs = sAziendaByID.executeQuery()) {
                if (rs.next()) {
                    return createAzienda(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare l'azienda dal suo ID.", ex);
        }

        return null;
    }
    
    @Override
    public List<Azienda> getAziende() throws DataException {
        List<Azienda> result = new ArrayList();

        try (ResultSet rs = sAziende.executeQuery()) {
            while (rs.next()) {
                result.add((Azienda) getAzienda(rs.getInt("AziendaID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le aziende.", ex);
        }
        return result;
    }

    @Override
    public List<Azienda> getAziendeConvenzionate() throws DataException {
        List<Azienda> result = new ArrayList();
        
        try (ResultSet rs = sAziendeConvenzionate.executeQuery()){
            while (rs.next()) {
                result.add( (Azienda) getAzienda(rs.getInt("AziendaID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le aziende convenzionate", ex);
        }
        return result;
        
    }
    
    
    @Override
    public Azienda getAziendaFromEmail(String email) throws DataException{
     
    try {
            sAziendaByEmail.setString(1, email);
            try (ResultSet rs = sAziendaByEmail.executeQuery()) {
                if (rs.next()) {
                    return createAzienda(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare l'azienda a partire dall'email.", ex);
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
            throw new DataException("Impossibile trovare l'azienda.", ex);
    }
        return null;
    }
    
    @Override
    public void storeAzienda(Azienda azienda) throws DataException {
        int key = azienda.getKey();
        try {
            if (azienda.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                
                if (azienda instanceof AziendaProxy && !((AziendaProxy) azienda).isDirty()) {
                    return;
                }
                
                //Altrimenti aggiorniamo
                uAzienda.setString(1, azienda.getNome());
                
                uAzienda.setString(2, azienda.getDescrizione());
                
                if(azienda.getSede() != null)
                uAzienda.setString(3, azienda.getSede());
                
                uAzienda.setString(4, azienda.getIva().get());
              
                uAzienda.setString(5, azienda.getForoCompetenza());
                
                uAzienda.setString(6, azienda.getNomeResponsabile());
                
                uAzienda.setString(7, azienda.getCognomeResponsabile());
                
                uAzienda.setString(8, azienda.getTelefonoResponsabile());

                uAzienda.setString(9, azienda.getEmailResponsabile());
            
                uAzienda.setString(10, azienda.getNomeCognomeLegale());  
               
                uAzienda.setString(11, azienda.getPassword());
                
                uAzienda.setString(12, azienda.getPath());
                
                if(azienda.isConvenzionata() != false)
                    uAzienda.setBoolean(13, azienda.isConvenzionata());
                   
                uAzienda.setInt(14, azienda.getKey());
                
                uAzienda.executeUpdate();
                
                
            } else { //facciamo la insert.
                
                iAzienda.setString(1, azienda.getNome());
                
                iAzienda.setString(2, azienda.getDescrizione());
                
                iAzienda.setString(3, azienda.getSede());
                
                iAzienda.setString(4, azienda.getIva().get());
                
                iAzienda.setString(5, azienda.getForoCompetenza());
                
                iAzienda.setString(6, azienda.getNomeResponsabile());
                
                iAzienda.setString(7, azienda.getCognomeResponsabile());
                
                iAzienda.setString(8, azienda.getTelefonoResponsabile());

                iAzienda.setString(9, azienda.getEmailResponsabile());
                  
                iAzienda.setString(10, azienda.getNomeCognomeLegale());  
                
                iAzienda.setString(11, azienda.getPassword());
                
                iAzienda.setString(12, azienda.getPath());
                
                iAzienda.setBoolean(13, azienda.isConvenzionata());

                   
                if (iAzienda.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    try (ResultSet keys = iAzienda.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            key = keys.getInt(1);
                        }
                    }
                    //aggiornaimo la chiave in caso di inserimento
                    //after an insert, uopdate the object key
                    azienda.setKey(key);
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
            
            if (azienda instanceof AziendaProxy) {
                ((AziendaProxy) azienda).setDirty(false);
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile memorizzare l'azienda.", ex);
        }
    }
    
}
