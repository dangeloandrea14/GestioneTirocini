/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.framework.result;

import com.univaq.tirocini.framework.security.UserPermissions;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;

/**
 *
 * @author carlo Questa classe è solo di esempio per mostrare i campi necessari
 * Nel codice vine poi invece usato un DynaBean
 */
public class UserRole {

    /*
    public UserRole(UserPermissions permissions, String username, String role) {
        this.permissions = permissions;
        this.username = username;
        this.role = role;
    }

    UserPermissions permissions;
    String username;
    String role;
    Object userObject; //dovrebbe avere il nome del ruolo

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
    */

    public static DynaBean createUserRoleBean(UserPermissions permissions, String username,
            String role, Object userObject) throws IllegalAccessException, InstantiationException, InvocationTargetException {

        // first create the properties
        DynaProperty properties[] = new DynaProperty[]{
            new DynaProperty("permissions", UserPermissions.class),
            new DynaProperty("username", String.class),
            new DynaProperty("role", String.class),
            new DynaProperty(role, Object.class) //per pigrizia non volendo cambiare gli ftl
        };

        // next using the properties define the class
        DynaClass userRoleClass = new BasicDynaClass("userRoleObject", null, properties);

        // now, with the class, create a new instance
        DynaBean userRoleBean = userRoleClass.newInstance();

        BeanUtils.setProperty(userRoleBean, "permissions", permissions);
        BeanUtils.setProperty(userRoleBean, "username", username);
        BeanUtils.setProperty(userRoleBean, "role", role);
        //BeanUtils.setProperty(userRoleBean, role, userObject);
        userRoleBean.set(role, role, userObject);

        return userRoleBean;
    }

}
