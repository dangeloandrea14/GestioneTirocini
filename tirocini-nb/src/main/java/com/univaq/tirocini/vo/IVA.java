/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.vo;

/**
 *
 * @author carlo
 */
public class IVA {
    String numero = null;

    public IVA(String numero){
        this.numero = numero;
    }
    
    public String get() {
        return numero;
    }

    public boolean set(String numero) {
        String regex = "\\d{11}";
        if (numero.matches(regex)) {
            this.numero = numero;
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return numero;
    }
    
    /*public boolean test () {
         return (!set("1234567890")&&
         !set("123456789012")&&
         !set("1234567890a")&&
         set("12345678901"));
    }*/
}
