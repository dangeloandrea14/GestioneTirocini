/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.vo;

import org.apache.commons.beanutils.Converter;

/**
 *
 * @author carlo
 */
public class IvaConverter implements Converter {    
    @Override
    public <T> T convert(Class<T> tClass, Object o) {
        String ivaString = (String) o;
        IVA i = new IVA(ivaString);
        
        return (T) i;
    }
}