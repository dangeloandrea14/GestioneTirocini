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
public class AziendaNonConvenzionataPermissions extends BasePermissions {

    public AziendaNonConvenzionataPermissions() {
        
        forbiddenPages = null;
        allowedPages = new HashSet<>(Arrays.asList(
                "/Home",
                "/Login",
                "/Logout",
                "/NotFound"
        ));
        defaultPage = "Home";
        
    }

}
