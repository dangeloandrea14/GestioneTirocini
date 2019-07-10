/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.model;

/**
 *
 * @author carlo
 */
public interface Offerta {
    public String getLuogo();

    public void setLuogo(String luogo);

    public String getOrari();

    public void setOrari(String orari);

    public String getDurata();

    public void setDurata(String durata);

    public String getObiettivi();

    public void setObiettivi(String obiettivi);

    public String getModalità();

    public void setModalità(String modalità);

    public String getRimborsoSpese();

    public void setRimborsoSpese(String rimborsoSpese);

    public boolean isAttiva();

    public void setAttiva(boolean attiva);
}
