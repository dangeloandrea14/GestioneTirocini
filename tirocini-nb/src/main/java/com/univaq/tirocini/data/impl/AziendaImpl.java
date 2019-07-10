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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public IVA getIva() {
        return iva;
    }

    public void setIva(IVA iva) {
        this.iva = iva;
    }

    public String getForoCompetenza() {
        return foroCompetenza;
    }

    public void setForoCompetenza(String foroCompetenza) {
        this.foroCompetenza = foroCompetenza;
    }

    public String getNomeResponsabile() {
        return nomeResponsabile;
    }

    public void setNomeResponsabile(String nomeResponsabile) {
        this.nomeResponsabile = nomeResponsabile;
    }

    public String getCognomeResponsabile() {
        return cognomeResponsabile;
    }

    public void setCognomeResponsabile(String cognomeResponsabile) {
        this.cognomeResponsabile = cognomeResponsabile;
    }

    public String getTelefonoResponsabile() {
        return telefonoResponsabile;
    }

    public void setTelefonoResponsabile(String telefonoResponsabile) {
        this.telefonoResponsabile = telefonoResponsabile;
    }

    public String getEmailResponsabile() {
        return emailResponsabile;
    }

    public void setEmailResponsabile(String emailResponsabile) {
        this.emailResponsabile = emailResponsabile;
    }

    public String getNomeCognomeLegale() {
        return nomeCognomeLegale;
    }

    public void setNomeCognomeLegale(String nomeCognomeLegale) {
        this.nomeCognomeLegale = nomeCognomeLegale;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public boolean isConvenzionata() {
        return Convenzionata;
    }

    public void setConvenzionata(boolean Convenzionata) {
        this.Convenzionata = Convenzionata;
    }

    
}
