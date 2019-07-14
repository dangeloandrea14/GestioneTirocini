/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.impl;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.vo.IVA;

/**
 *
 * @author carlo
 */
public class AziendaImpl implements Azienda {
    int key;
    String nome;
    String sede;
    IVA iva;
    String foroCompetenza;
    String nomeResponsabile;
    String cognomeResponsabile;
    String telefonoResponsabile;
    String emailResponsabile;
    String nomeCognomeLegale;
    int voto;
    boolean Convenzionata;
    
    @Override
    public int getKey(){
        return key;
    }
    
    @Override
    public void setKey(int key){
        this.key = key;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getSede() {
        return sede;
    }

    @Override
    public void setSede(String sede) {
        this.sede = sede;
    }

    @Override
    public IVA getIva() {
        return iva;
    }

    @Override
    public void setIva(IVA iva) {
        this.iva = iva;
    }

    @Override
    public String getForoCompetenza() {
        return foroCompetenza;
    }

    @Override
    public void setForoCompetenza(String foroCompetenza) {
        this.foroCompetenza = foroCompetenza;
    }

    @Override
    public String getNomeResponsabile() {
        return nomeResponsabile;
    }

    @Override
    public void setNomeResponsabile(String nomeResponsabile) {
        this.nomeResponsabile = nomeResponsabile;
    }

    @Override
    public String getCognomeResponsabile() {
        return cognomeResponsabile;
    }

    @Override
    public void setCognomeResponsabile(String cognomeResponsabile) {
        this.cognomeResponsabile = cognomeResponsabile;
    }

    @Override
    public String getTelefonoResponsabile() {
        return telefonoResponsabile;
    }

    @Override
    public void setTelefonoResponsabile(String telefonoResponsabile) {
        this.telefonoResponsabile = telefonoResponsabile;
    }

    @Override
    public String getEmailResponsabile() {
        return emailResponsabile;
    }

    @Override
    public void setEmailResponsabile(String emailResponsabile) {
        this.emailResponsabile = emailResponsabile;
    }

    @Override
    public String getNomeCognomeLegale() {
        return nomeCognomeLegale;
    }

    @Override
    public void setNomeCognomeLegale(String nomeCognomeLegale) {
        this.nomeCognomeLegale = nomeCognomeLegale;
    }

    @Override
    public int getVoto() {
        return voto;
    }

    @Override
    public void setVoto(int voto) {
        this.voto = voto;
    }

    @Override
    public boolean isConvenzionata() {
        return Convenzionata;
    }

    @Override
    public void setConvenzionata(boolean Convenzionata) {
        this.Convenzionata = Convenzionata;
    }

    
}
