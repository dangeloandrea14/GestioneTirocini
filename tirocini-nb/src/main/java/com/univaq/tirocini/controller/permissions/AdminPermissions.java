/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller.permissions;

import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author carlo
 */
public class AdminPermissions extends BasePermissions {

    public AdminPermissions() {
        
        ForbiddenPages = new HashSet<>(Arrays.asList(
                "/Profile"
        ));
        AllowedPages = new HashSet<>(Arrays.asList(
                "/Logout", "/Admin", "/DettagliAzienda", "/Convenzione"
        ));
        DefaultPage = "Admin";
        
    }
}
