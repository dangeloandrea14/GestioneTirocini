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
public class BasePermissions implements UserPermissions {
        
    HashSet<String> ForbiddenPages;
    HashSet<String> AllowedPages;    
    String DefaultPage;

    @Override
    public Set<String> getForbiddenPages() {
        return ForbiddenPages;
    }

    public void setForbiddenPages(HashSet<String> ForbiddenPages) {
        this.ForbiddenPages = ForbiddenPages;
    }

    @Override
    public Set<String> getAllowedPages() {
        return AllowedPages;
    }

    public void setAllowedPages(HashSet<String> AllowedPages) {
        this.AllowedPages = AllowedPages;
    }

    @Override
    public String getDefaultPage() {
        return DefaultPage;
    }

    public void setDefaultPage(String DefaultPage) {
        this.DefaultPage = DefaultPage;
    }

    @Override
    public void allowPage(String page) {
        ForbiddenPages.remove(page);
        AllowedPages.add(page);
    }

    @Override
    public void disAllowPage(String page) {
        AllowedPages.remove(page);
    }

    @Override
    public void ForbidPage(String page) {
        ForbiddenPages.add(page);
    }
    
    @Override
    public boolean authorized(HttpServletRequest request) {
        String requested = request.getServletPath();
        if (request.getSession() == null)
        {
            return true;
        }

        if (ForbiddenPages != null && ForbiddenPages.contains(requested)) {
            return false;
        }

        //se non impostato AllowedPages permettiamo tutto
        return (AllowedPages == null || AllowedPages.contains(requested));
    } 
}
