/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.framework.security;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author carlo
 */
public interface UserPermissions {
    
    public Set<String> getForbiddenPages();
    public Set<String> getAllowedPages();
    public String getDefaultPage();
    
    public void allowPage(String page);
    public void disAllowPage(String page);
    public void ForbidPage(String page);
    
    public boolean authorized(HttpServletRequest request);
}
