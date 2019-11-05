package com.univaq.tirocini.controller;

import com.univaq.tirocini.controller.email.MailTirocini;
import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.impl.AziendaImpl;
import com.univaq.tirocini.data.impl.StudenteImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.Password;
import com.univaq.tirocini.vo.IVA;
import com.univaq.tirocini.vo.IvaConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
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

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {

        if (request.getSession().getAttribute("type") != null) {

            request.setAttribute("message", "Hai già effettuato il login");
            request.setAttribute("color", "danger");
            request.setAttribute("page_title", "Home");
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("home.ftl.html", request, response);

        } else {
            request.setAttribute("page_title", "Registration");

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
                res.activate("registrationChooser(versione da adattare).ftl.html", request, response);
            }
        }
    }

    private void registerNewStudent(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, DataException, TemplateManagerException, UnsupportedEncodingException {
        if (!checkParameter(request, "s", "studente")) {
            registrationFailure(request, response);
            return;
        }

        StudenteImpl s = new StudenteImpl();

        //Validazione form
        if (!((String) request.getParameter("email")).contains("@")) {
            request.setAttribute("exception", "Formato email errato.");
            registrationFailure(request, response);
        }
        if (((String) request.getParameter("password")).length() > 20) {
            request.setAttribute("exception", "Password troppo lunga.");
            registrationFailure(request, response);
        }
        if (((String) request.getParameter("provinciaNascita")).length() > 3) {
            request.setAttribute("exception", "Provincia di nascita non corretta");
            registrationFailure(request, response);
        }

        //i paramentri corrispondono (si spera), quindi possiamo
        //popolare gli oggetti automaticamente
        BeanUtils.populate(s, request.getParameterMap());

        s.setPassword(Password.hash(s.getPassword()));

        if (request.getParameter("specializzazione") == null || request.getParameter("specializzazione").isEmpty()) {
            s.setSpecializzazione("Nessuna");
        }

        //Controlliamo che non ci sia una azienda con quella mail.
        Azienda aziendadup = ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAziendaFromEmail(request.getParameter("email"));

        if ((aziendadup != null)) {

            action_error(request, response);
            return;

        }

        ((TirocinioDataLayer) request.getAttribute("datalayer")).getStudenteDAO().storeStudente(s);

        //Mandiamo la mail al nuovo studente            
        MailTirocini.send(s,
                "Grazie per esserti iscritto",
                "mail/registrazioneStudente.ftl.html",
                request);

        registrationSuccess(request, response);
    }

    private void registerNewCompany(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, DataException, TemplateManagerException, TemplateManagerException, UnsupportedEncodingException {
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

        //Controlliamo che non ci sia uno studente con quella mail.
        Studente studentedup = ((TirocinioDataLayer) request.getAttribute("datalayer")).getStudenteDAO().getStudenteFromEmail(request.getParameter("emailResponsabile"));

        if ((studentedup != null)) {

            action_error(request, response);
            return;

        }

        ((TirocinioDataLayer) request.getAttribute("datalayer")).getAziendaDAO().storeAzienda(a);

        //Mandiamo la mail alla nuova azienda           
        MailTirocini.send(a,
                "Grazie per esserti iscritto",
                "mail/registrazioneAzienda.ftl.html",
                request);
        
        registrationSuccessCompany(request, response);
    }

    private void registerNewUser(HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException, DataException, TemplateManagerException, UnsupportedEncodingException {
        String p = request.getParameter("s");
        if (null == p) {
            registrationFailure(request, response);
        } else {
            switch (p) {
                case "studente":
                    registerNewStudent(request, response);
                    break;
                case "azienda":
                    registerNewCompany(request, response);
                    break;
                default:
                    registrationFailure(request, response);
                    break;
            }
        }
    }

    //controlla la presenza di un parametro con un valore dato
    private boolean checkParameter(HttpServletRequest request, String name, String expected) {
        String par = request.getParameter(name);
        return !(par == null || !par.equals(expected));
    }

    private void registrationSuccess(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("page_title", "Registration complete");

        TemplateResult res = new TemplateResult(getServletContext());

        try {

            request.setAttribute("message", "Registrazione effettuata.");
            request.setAttribute("color", "success");
            request.setAttribute("page_title", "Home");

            res.activate("home.ftl.html", request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    private void registrationSuccessCompany(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("page_title", "Registration complete");

        TemplateResult res = new TemplateResult(getServletContext());

        try {
            res.activate("fragments/registrationCompanySuccess.ftl.html", request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    private void registrationFailure(HttpServletRequest request, HttpServletResponse response) throws DataException {
        String exception = (String) request.getAttribute("exception");
        if (exception != null && !((String) request.getAttribute("exception")).isEmpty()) {
            request.setAttribute("exception", exception);
        } else {
            request.setAttribute("exception", "Registration failed");
        }

        throw new DataException(exception);

    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            action_default(request, response);

        } catch (IOException | TemplateManagerException | ServletException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        }
    }

    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            registerNewUser(request, response);

        } catch (IllegalAccessException | InvocationTargetException |
                TemplateManagerException | UnsupportedEncodingException |
                DataException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Gestisce la registrazione di studenti e aziende.";
    }
}
