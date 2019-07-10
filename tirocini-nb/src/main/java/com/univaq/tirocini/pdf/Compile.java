/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.pdf;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

/**
 *
 * @author carlo
 */
public class Compile {

    private static PDDocument _pdfDocument;

    public static void main(String[] args) {

        String originalPdf = "/home/carlo/Example2.pdf";
        String targetPdf = "/home/carlo/Prova2.pdf";

        try {
            openDocument(originalPdf);

            _pdfDocument.getNumberOfPages();
            //printFields();  //Uncomment to see the fields in this document in console

            setField("Your_First_Name", "Aldo");
            setField("Your_Last_Name", "Dola");
            saveDocument(targetPdf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Complete");
    }

    public static void compile(String input_filename, String output_filename,
            Map<String, String> filling) {
        try {
        openDocument(input_filename);

        compile(filling);
        saveDocument(output_filename);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void compile(Map<String, String> filling) throws IOException {
        for (Map.Entry<String, String> entry : filling.entrySet()) {
            setField(entry.getKey(), entry.getValue());
        }
    }

    protected static void openDocument(String filename) throws IOException {
        _pdfDocument = PDDocument.load(new File(filename));
    }

    protected static void saveDocument(String filename) throws IOException {
        _pdfDocument.save(filename);
        _pdfDocument.close();
    }

    protected static void setField(String name, String value) throws IOException {
        PDDocumentCatalog docCatalog = _pdfDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        if (acroForm == null) {
            System.out.println("No acroForm");
            return;
        }
        PDField field = acroForm.getField(name);
        if (field != null) {
            field.setValue(value);
        } else {
            System.err.println("No field found with name:" + name);
            printFields();
        }
    }

    @SuppressWarnings("rawtypes")
    protected static void printFields() throws IOException {
        PDDocumentCatalog docCatalog = _pdfDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        List fields = acroForm.getFields();
        Iterator fieldsIter = fields.iterator();

        System.out.println(new Integer(fields.size()).toString() + " top-level fields were found on the form");

        while (fieldsIter.hasNext()) {
            PDField field = (PDField) fieldsIter.next();
            processField(field, "|--", field.getPartialName());
        }
    }

    @SuppressWarnings("rawtypes")
    private static void processField(PDField field, String sLevel, String sParent) throws IOException {
        List kids = field.getWidgets();
        if (kids != null) {
            Iterator kidsIter = kids.iterator();
            if (!sParent.equals(field.getPartialName())) {
                sParent = sParent + "." + field.getPartialName();
            }

            System.out.println(sLevel + sParent);

            while (kidsIter.hasNext()) {
                Object pdfObj = kidsIter.next();
                if (pdfObj instanceof PDField) {
                    PDField kid = (PDField) pdfObj;
                    processField(kid, "|  " + sLevel, sParent);
                }
            }
        } else {
            String outputString = sLevel + sParent + "." + field.getPartialName() + ",  type=" + field.getClass().getName();
            System.out.println(outputString);
        }
    }

}
