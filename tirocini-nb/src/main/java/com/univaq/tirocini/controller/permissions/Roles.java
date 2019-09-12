/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller.permissions;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.result.UserRole;

/**
 *
 * @author carlo
 */
public class Roles {
    public static UserRole genStudenteBean (Studente studente) throws Exception {
        return new UserRole(new StudentePermissions(), 
                studente.getEmail(), "studente", new UserObject(studente));
    }
    
    public static UserRole genAziendaBean (Azienda azienda) throws Exception {
        return new UserRole(new AziendaPermissions(), 
                azienda.getEmailResponsabile(), "azienda", new UserObject(azienda));
    }
    
    public static UserRole genAdminBean (Studente studente) throws Exception {
        return new UserRole(new AdminPermissions(), 
                studente.getEmail(), "admin", new UserObject(studente));
    }
}
