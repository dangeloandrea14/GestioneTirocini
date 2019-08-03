/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.impl.AziendaImpl;
import com.univaq.tirocini.data.impl.StudenteImpl;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.FailureResult;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.Password;
import com.univaq.tirocini.vo.IVA;
import com.univaq.tirocini.vo.IvaConverter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

/**
 *
 * @author carlo
 */
public class Registration extends TirociniBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        request.setAttribute("page_title", "Registration");
        request.setAttribute("outline_tpl", "outline_alt.ftl.html");

        TemplateResult res = new TemplateResult(getServletContext());

        String p = request.getParameter("s");
        //nel template il parametro s indica il tipo di registrazione desiderata
        //se non è ancora stato impostato (o non è valido), facciamo scegliere
        //il tipo di registrazione che si vuole effetturare

        if (p == null) {
            res.activate("registrationChooser.ftl.html", request, response);
        } else if (p.equals("studente")) {
            res.activate("registrazioneStudente.ftl.html", request, response);
        } else if (p.equals("azienda")) {
            res.activate("registrazioneAzienda.ftl.html", request, response);
        } else {
            res.activate("registrationChooser.ftl.html", request, response);
        }
    }

    //TODO
    //Mancano controlli sui campi (lunghezza password, validità part. Iva, ecc.)
    private void registerNewStudent(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, DataException {
        if (!checkParameter(request, "s", "studente")) {
            registrationFailure(request, response);
            return;
        }

        StudenteImpl s = new StudenteImpl();
        //i paramentri corrispndono (si spera), quindi possiamo
        //popolare gli oggetti automaticamente
        BeanUtils.populate(s, request.getParameterMap());

        s.setPassword(Password.hash(s.getPassword()));

        ((TirocinioDataLayer) request.getAttribute("datalayer")).getStudenteDAO().storeStudente(s);

        registrationSuccess(request, response);
    }

    private void registerNewCompany(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, DataException {
        if (!checkParameter(request, "s", "azienda")) {
            registrationFailure(request, response);
            return;
        }

        AziendaImpl a = new AziendaImpl();

        //registriamo il convertitore per la classe IVA
        ConvertUtils.register(new IvaConverter(), IVA.class);

        //i paramentri corrispndono (si spera), quindi possiamo
        //popolare gli oggetti automaticamente
        BeanUtils.populate(a, request.getParameterMap());

        a.setPassword(Password.hash(a.getPassword()));

        ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().storeAzienda(a);

        registrationSuccess(request, response);
    }

    private void registerNewUser(HttpServletRequest request, HttpServletResponse response) 
            throws IllegalAccessException, InvocationTargetException, DataException {
        String p = request.getParameter("s");
        if (p == null) {
            registrationFailure(request, response);
        } else if (p.equals("studente")) {
            registerNewStudent(request, response);
        } else if (p.equals("azienda")) {
            registerNewCompany(request, response);
        } else {
            registrationFailure(request, response);
        }
    }

    //controlla la presenza di un parametro con un valore dato
    private boolean checkParameter(HttpServletRequest request, String name, String expected) {
        String par = request.getParameter(name);
        return !(par == null || !par.equals(expected));
    }

    private void registrationSuccess(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("page_title", "Registration complete");
        request.setAttribute("outline_tpl", "outline_alt.ftl.html");

        TemplateResult res = new TemplateResult(getServletContext());

        try {
            res.activate("registrationCompleted.ftl.html", request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    private void registrationFailure(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("exception", "Registration failed.");
        action_error(request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            if (request.getMethod().equals("POST")) {
                registerNewUser(request, response);
            } else {
                action_default(request, response);
            }

        } catch (IOException | TemplateManagerException | 
                IllegalAccessException | InvocationTargetException | 
                DataException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Registration servlet";
    }// </editor-fold>

}
