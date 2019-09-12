/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller.permissions;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;

/**
 *
 * @author carlo
 */
public final class UserObject {
    Studente studente = null;
    Azienda azienda = null;

    public UserObject(Azienda azienda) {
        setAzienda(azienda);
    }

    public UserObject(Studente studente) {
        setStudente(studente);
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }

    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }
}
