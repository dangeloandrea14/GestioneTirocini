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

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public String getOrari() {
        return orari;
    }

    public void setOrari(String orari) {
        this.orari = orari;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public String getObiettivi() {
        return obiettivi;
    }

    public void setObiettivi(String obiettivi) {
        this.obiettivi = obiettivi;
    }

    public String getModalità() {
        return modalità;
    }

    public void setModalità(String modalità) {
        this.modalità = modalità;
    }

    public String getRimborsoSpese() {
        return rimborsoSpese;
    }

    public void setRimborsoSpese(String rimborsoSpese) {
        this.rimborsoSpese = rimborsoSpese;
    }

    public boolean isAttiva() {
        return attiva;
    }

    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }
}
