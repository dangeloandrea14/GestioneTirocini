package com.univaq.tirocini.data.impl;

import com.univaq.tirocini.data.model.Studente;
import java.sql.Date;

/**
 *
 * @author Andrea
 */
public class StudenteImpl implements Studente {
    
    int key;
    String nome;
    String cognome;
    Date dataDiNascita;
    String luogoDiNascita;
    String CF;
    Boolean handicap;
    String email;
    int ruolo;
    String residenza;
    String corsoDiLaurea;
    int CFU;
    String telefono;
    String diploma;
    String laurea;
    String specializzazione;
    String password;
  
    @Override
   public int getKey(){
       return this.key;
   }
   
   @Override
   public void setKey(int key){
       this.key=key;
   }
   
   @Override
   public String getNome(){
       return this.nome;
   }
   
   @Override
   public void setNome(String nome){
       this.nome = nome;
   }
   
   @Override
   public String getCognome(){
       return this.cognome;
   }
   
   @Override
   public void setCognome(String cognome){
       this.cognome = cognome;
   }
   
   @Override
   public Date getDataNascita(){
       return this.dataDiNascita;
   }
   
   @Override
   public void setDataNascita(Date data){
       this.dataDiNascita = data;
   }
   
   @Override
   public String getLuogoNascita(){
       return this.luogoDiNascita;
   }
   
   @Override
   public void setLuogoNascita(String luogo){
       this.luogoDiNascita = luogo;
   }
   
   @Override
   public String getCF(){
       return this.CF;
   }
   
   @Override
   public void setCF(String CF){
       this.CF = CF;
   }
   
   @Override
   public Boolean isHandicapped(){
       return this.handicap;
   }
   
   @Override
   public void setHandicap(Boolean b){
       this.handicap = b;
   }
   
   @Override
   public String getEmail(){
       return this.email;
   }
   
   @Override
   public void setEmail(String s){
       this.email = s;
   }
   
   @Override
   public int getRuolo(){
       return this.ruolo;
   }
   
   @Override
   public void setRuolo(int ruolo){
       this.ruolo = ruolo;
   }
   
   @Override
   public String getResidenza(){
       return this.residenza;
   }
   
   @Override
   public void setResidenza(String residenza){
       this.residenza = residenza;
   }
   
   @Override
   public String getCorsoLaurea(){
       return this.laurea;
   }
   
   @Override
   public void setCorsoLaurea(String corso){
       this.laurea = corso;
   }
   
   @Override
   public int getCFU(){
       return this.CFU;
   }
   
   @Override
   public void setCFU(int cfu){
       this.CFU = cfu;
   }
   
   @Override
   public String getTelefono(){
       return this.telefono;
   }
   
   @Override
   public void setTelefono(String tel){
       this.telefono = tel;
   }
   
   @Override
   public String getDiploma(){
       return this.diploma;
   }
   
   @Override
   public void setDiploma(String diploma){
       this.diploma = diploma;
   }
   
   @Override
   public String getLaurea(){
       return this.laurea;
   }
   
   @Override
   public void setLaurea(String laurea){
       this.laurea = laurea;
   }
   
   @Override
   public String getSpecializzazione(){
       return this.specializzazione;
   }
   
   @Override
   public void setSpecializzazione(String spec){
       this.specializzazione = spec;
   }
   
   @Override
   public String getPassword(){
       return this.password;
   }
   
   @Override
   public void setPassword(String pass){
       this.password = pass;
   }
    
}
