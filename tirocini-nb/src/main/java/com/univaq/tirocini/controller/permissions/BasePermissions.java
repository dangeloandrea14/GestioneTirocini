/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller.permissions;

import java.util.Set;
import com.univaq.tirocini.framework.security.UserPermissions;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author carlo
 */
public abstract class BasePermissions implements UserPermissions {
        
    HashSet<String> forbiddenPages;
    HashSet<String> allowedPages;    
    String defaultPage;

    @Override
    public Set<String> getForbiddenPages() {
        return forbiddenPages;
    }

    public void setForbiddenPages(HashSet<String> ForbiddenPages) {
        this.forbiddenPages = ForbiddenPages;
    }

    @Override
    public Set<String> getAllowedPages() {
        return allowedPages;
    }

    public void setAllowedPages(HashSet<String> AllowedPages) {
        this.allowedPages = AllowedPages;
    }

    @Override
    public String getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(String DefaultPage) {
        this.defaultPage = DefaultPage;
    }

    @Override
    public void allowPage(String page) {
        forbiddenPages.remove(page);
        allowedPages.add(page);
    }

    @Override
    public void disAllowPage(String page) {
        allowedPages.remove(page);
    }

    @Override
    public void ForbidPage(String page) {
        forbiddenPages.add(page);
    }
    
    @Override
    public boolean authorized(HttpServletRequest request) {
        String requested = request.getServletPath();
        /* (request.getSession() == null)
        {
            return true;
        }*/

        if (forbiddenPages != null && forbiddenPages.contains(requested)) {
            return false;
        }

        //se non impostato AllowedPages permettiamo tutto
        return (allowedPages == null || allowedPages.contains(requested));
    } 
}
