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
public class StudentePermissions extends BasePermissions{
  
    public StudentePermissions() {
        
        forbiddenPages = null;allowedPages = (HashSet<String>) new PublicPermissions().getAllowedPages();
        allowedPages.addAll(new HashSet<>(Arrays.asList(
                "/Profile",
                "/Logout",
                "/Valutazione",
                "/Candidatura",
                "/ValutazioneCompletata"
        )));
        defaultPage = "Home";
        
    }
}
