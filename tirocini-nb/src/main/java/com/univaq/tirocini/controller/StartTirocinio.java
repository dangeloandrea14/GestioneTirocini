package com.univaq.tirocini.controller;

import com.univaq.tirocini.data.DAO.TirocinioDataLayer;
import com.univaq.tirocini.data.impl.TirocinioImpl;
import com.univaq.tirocini.data.model.Azienda;
import com.univaq.tirocini.data.model.Offerta;
import com.univaq.tirocini.data.model.Studente;
import com.univaq.tirocini.data.model.Tirocinio;
import com.univaq.tirocini.framework.data.DataException;
import com.univaq.tirocini.framework.result.StreamResult;
import com.univaq.tirocini.framework.result.TemplateManagerException;
import com.univaq.tirocini.framework.result.TemplateResult;
import com.univaq.tirocini.framework.security.SecurityLayer;
import com.univaq.tirocini.pdf.Compile;
import com.univaq.tirocini.utility.DateUtils;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrea
 */
public class StartTirocinio extends TirociniBaseController {

    /////////////////////////////////////////////
    ////////////////! FIXME !////////////////////
    /////////////////////////////////////////////
    /*
    DECISAMENTE NON DOVREBBE ESSERE QUI. (!)
     */
    public File prepareFormativo1(Azienda azienda, Studente studente, Tirocinio tirocinio,
            String provinciaNascita, String provinciaResidenza,
            String telefonoTutoreUniversitario, String telefonoTutoreAziendale) {
        Map<String, String> filling = new HashMap<>();

        filling.put("nomeTirocinante", studente.getNome() + " " + studente.getCognome());
        filling.put("luogoNascita", studente.getLuogoNascita());
        filling.put("provincia", provinciaNascita);
        filling.put("giorno", DateUtils.getDay(studente.getDataNascita()).toString());
        filling.put("mese", DateUtils.getMonth(studente.getDataNascita()).toString());
        filling.put("anno", DateUtils.getYear(studente.getDataNascita()).toString());
        filling.put("residenza", studente.getResidenza());
        filling.put("residenzaProvincia", provinciaResidenza);
        filling.put("telefono", studente.getTelefono());
        if (studente.isHandicapped()) {
            filling.put("HandicapTrue", "x");
        } else {
            filling.put("HandicapFalse", "x");
        }

        filling.put("checkCorsoLaureaStudente", "x");
        filling.put("corsoLaureaStudente", studente.getCorsoLaurea());

        //BeanUtils.populate(azienda, filling);
        filling.put("nomeAzienda", azienda.getNome());

        filling.put("settoreInserimento", tirocinio.getSettoreInserimento());
        filling.put("tutoreAziendale", tirocinio.getTutoreAziendale());
        filling.put("telefonoTutoreAziendale", telefonoTutoreAziendale);
        filling.put("tutoreUniversitario", tirocinio.getTutoreAziendale());
        filling.put("telefonoTutoreUniversitario", telefonoTutoreUniversitario);
        filling.put("oreTirocinio", tirocinio.getNumeroOre());
        filling.put("nMesiTirocinio", DateUtils.monthsBetweenIgnoreDays(tirocinio.getInizio(), tirocinio.getFine()).toString());
        filling.put("meseInizioTirocinio", tirocinio.getInizio().toString());
        filling.put("meseFineTirocinio", tirocinio.getFine().toString());

        return Compile.compile(new File(getServletContext().getRealPath("templates/pdf/Formativo.pdf")), filling);
    }

    @Override
    protected void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, ParseException {

        request.setAttribute("page_title", "Inizializzazione Tirocinio");

        if (request.getParameter("download") != null) {
            StreamResult sr = new StreamResult(getServletContext());

            HttpSession session = SecurityLayer.checkSession(request);

            if (session == null || session.getAttribute("Formativo.pdf") == null) {
                notFound(request, response);
                return;
            } else {
                File toDownload = (File) session.getAttribute("Formativo.pdf");
                sr.activate(toDownload, "Documento Progetto Formativo.pdf", request, response);
                toDownload.delete();
            }
        }

        try {

            String param = request.getParameter("sid");
            String param2 = request.getParameter("oid");

            int sid = Integer.parseInt(param);
            int oid = Integer.parseInt(param2);

            Studente studente = (Studente) ((TirocinioDataLayer) request.getAttribute("datalayer")).getStudenteDAO().getStudente(sid);
            Offerta offerta = (Offerta) ((TirocinioDataLayer) request.getAttribute("datalayer")).getOffertaDAO().getOfferta(oid);
            Azienda azienda = offerta.getAzienda();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date inizioj = format.parse(request.getParameter("inizio"));
            java.util.Date finej = format.parse(request.getParameter("fine"));

            java.sql.Date inizio = new java.sql.Date(inizioj.getTime());
            java.sql.Date fine = new java.sql.Date(finej.getTime());

//Creiamo il tirocinio con il boolean active = false;
            TirocinioImpl t = new TirocinioImpl();
            t.setAzienda(azienda);
            t.setStudente(studente);
            t.setInizio(inizio);
            t.setFine(fine);
            t.setSettoreInserimento(request.getParameter("settoreinserimento"));
            t.setTempoDiAccesso(request.getParameter("tempodiaccesso"));
            t.setNumeroOre(request.getParameter("numeroore"));
            t.setTutoreUniversitario(request.getParameter("tutoreuniversitario"));
            t.setTutoreAziendale(request.getParameter("tutoreaziendale"));
            t.setAttivo(false);
            t.setOfferta(offerta);

            ((TirocinioDataLayer) request.getAttribute("datalayer")).getTirocinioDAO().storeTirocinio(t);

            File toDownload = prepareFormativo1(azienda, studente, t,
                    request.getParameter("proviNstudente"),
                    request.getParameter("proviRstudente"),
                    request.getParameter("telefonoTutoreUniv"),
                    request.getParameter("telefonoTutoreAzien"));
            //prepareConvenzione(azienda);

            //StreamResult sr = new StreamResult(getServletContext());
            //sr.activate(toDownload, "Documento Progetto Formativo.pdf", request, response);
            //toDownload.delete();
            request.getSession().setAttribute("Formativo.pdf", toDownload);

            request.setAttribute("studenteT", studente);
            request.setAttribute("offerta", offerta);
            request.setAttribute("aziendaT", azienda);
            request.setAttribute("tirocinio", t);

            TemplateResult res = new TemplateResult(getServletContext());

            res.activate("inizializzazionetirocinio.ftl.html", request, response);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Inserisce i dati del tirocinio nel DB una volta completato il form. ";
    }

}
