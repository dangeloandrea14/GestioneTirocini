/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.framework.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author carlo
 */
public class Password {
    
    public static String hash(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    
    public static String hash(char[] password) {
        return BCrypt.withDefaults().hashToString(12, password);
    }
    
    public static boolean verify(String password, String hash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
        return result.verified;
    }
    
    public static boolean verify(char[] password, String hash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password, hash);
        return result.verified;
    }
}
