/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.impl;

import com.univaq.tirocini.data.model.Offerta;

/**
 *
 * @author carlo
 */
public class OffertaImpl implements Offerta {
    String luogo;
    String orari;
    String durata;
    String obiettivi;
    String modalità;
    String rimborsoSpese;
    boolean attiva;

    @Override
    public String getLuogo() {
        return luogo;
    }

    @Override
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    @Override
    public String getOrari() {
        return orari;
    }

    @Override
    public void setOrari(String orari) {
        this.orari = orari;
    }

    @Override
    public String getDurata() {
        return durata;
    }

    @Override
    public void setDurata(String durata) {
        this.durata = durata;
    }

    @Override
    public String getObiettivi() {
        return obiettivi;
    }

    @Override
    public void setObiettivi(String obiettivi) {
        this.obiettivi = obiettivi;
    }

    @Override
    public String getModalità() {
        return modalità;
    }

    @Override
    public void setModalità(String modalità) {
        this.modalità = modalità;
    }

    @Override
    public String getRimborsoSpese() {
        return rimborsoSpese;
    }

    @Override
    public void setRimborsoSpese(String rimborsoSpese) {
        this.rimborsoSpese = rimborsoSpese;
    }

    @Override
    public boolean isAttiva() {
        return attiva;
    }
    
    @Override
    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }
}
