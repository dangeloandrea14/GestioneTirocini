/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.framework.result;

import com.univaq.tirocini.framework.security.UserPermissions;

/**
 *
 * @author carlo
 */
public final class UserRole {
    
    public UserRole(UserPermissions permissions, 
            String username,
            String role,
            Object userObject) {
        this.permissions = permissions;
        this.username = username;
        this.role = role;
        this.userObject = userObject;
    }

    private UserPermissions permissions;
    private String username;
    private String role;
    private Object userObject; //dovrebbe avere il nome del ruolo

    public Object getUserObject() {
        return userObject;
    }

    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }

    public UserPermissions getPermissions() {
        return permissions;
    }

    public void setPermissions(UserPermissions permissions) {
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
