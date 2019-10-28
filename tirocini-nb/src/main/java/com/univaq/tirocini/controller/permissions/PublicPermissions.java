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
public class PublicPermissions extends BasePermissions {

    public PublicPermissions() {
        
        /*forbiddenPages = new HashSet<>(Arrays.asList(
                "/Profile",
                "/Admin",
                "/Statistiche",
                "/Statisticheazienda", 
                "/PdfGenerator"
        ));*/
        forbiddenPages = null;
        allowedPages = new HashSet<>(Arrays.asList(
                "/Home",
                "/Login",
                "/Logout",
                "/NotFound",
                "/Companies", 
                "/Offers", 
                "/DettagliOfferta", 
                "/DettagliAzienda", 
                "/student",
                "/Registration"
        ));
        defaultPage = "Home";
        
    }
}
