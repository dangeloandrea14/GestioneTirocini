/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller.permissions;

import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.result.UserRole;
import org.apache.commons.beanutils.DynaBean;

/**
 *
 * @author carlo
 */
public class Roles {
    public static DynaBean genStudenteBean (Studente studente) throws Exception {
        return UserRole.createUserRoleBean(new StudentePermissions(), 
                studente.getEmail(), "studente", studente);
    }
    
    public static DynaBean genAziendaBean (Azienda azienda) throws Exception {
        return UserRole.createUserRoleBean(new AziendaPermissions(), 
                azienda.getEmailResponsabile(), "azienda", azienda);
    }
    
    public static DynaBean genAdminBean (Studente studente) throws Exception {
        return UserRole.createUserRoleBean(new AdminPermissions(), 
                studente.getEmail(), "admin", studente);
    }
}
