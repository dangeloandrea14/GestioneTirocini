/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.impl;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;

/**
 *
 * @author Andrea
 */
public class TirocinioImpl implements Tirocinio {
    
    int key;
    
    Azienda azienda;
    
    Studente studente;
    
    String inizio;
    
    String fine;
    
    String settoreInserimento;
    
    String tempoDiAccesso;
    
    String numeroOre;
    
    String tutoreUniversitario;
    
    String tutoreAziendale;
    
    Boolean attivo;
    
    String pathDocumento;
    
    @Override
    public int getKey(){
        return key;
    }
    
    @Override
    public void setKey(int key){
        this.key=key;
    }
    
    @Override
    public Azienda getAzienda(){
        return azienda;
    }
    
    @Override
    public void setAzienda(Azienda azienda){
        this.azienda=azienda;
    }
    
    @Override
    public Studente getStudente(){
        return studente;
    }
    
    @Override
    public void setStudente(Studente studente){
        this.studente=studente;
    }
    
    @Override
    public String getInizio(){
        return inizio;
    }
    
    @Override
    public void setInizio(String inizio){
        this.inizio=inizio;
    }
    
    @Override
    public String getFine(){
        return fine;
    }
    
    @Override
    public void setFine(String fine){
        this.fine=fine;
    }
    
    @Override
    public String getSettoreInserimento(){
        return settoreInserimento;
    }
    
    @Override
    public void setSettoreInserimento(String settore){
        this.settoreInserimento=settore;
    }
    
    @Override
    public String getTempoDiAccesso(){
        return this.tempoDiAccesso;
    }
    
    @Override
    public void setTempoDiAccesso(String tempo){
        this.tempoDiAccesso=tempo;
    }
    
    @Override
    public String getNumeroOre(){
        return this.numeroOre;
    }
    
    @Override
    public void setNumeroOre(String numero){
        this.numeroOre=numero;
    }
    
    @Override
    public String getTutoreUniversitario(){
        return this.tutoreUniversitario;
    }
    
    @Override
    public void setTutoreUniversitario(String tutoreuniversitario){
        this.tutoreUniversitario=tutoreuniversitario;
    }
    
    @Override
    public String getTutoreAziendale(){
        return this.tutoreAziendale;
    }
    
    @Override
    public void setTutoreAziendale(String tutoreaziendale){
        this.tutoreAziendale=tutoreaziendale;
    }
    
    @Override
    public Boolean isAttivo(){
        return this.attivo;
    }
    
    @Override
    public void setAttivo(Boolean b){
        this.attivo=b;
    }
    
    @Override
    public String getPathDocumento(){
        return this.pathDocumento;
    }
    
    @Override
    public void setPathDocumento(String path){
        this.pathDocumento=path;
    }
}