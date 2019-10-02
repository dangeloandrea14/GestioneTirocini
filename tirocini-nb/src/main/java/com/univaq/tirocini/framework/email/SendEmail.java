/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.framework.email;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.servlet.ServletContext;

public class SendEmail {

    Properties properties;
    ServletContext context;

    public SendEmail(ServletContext context) {
        this.context = context;
        InitSendEmail();
    }

    public final void InitSendEmail() {
        // Get system properties and setup mail server

        properties = System.getProperties();

        // list of parameters to get from web.xml
        List<String> configs = Arrays.asList(
                "mail.smtp.host",
                "mail.smtp.port",
                "mail.user",
                "mail.password",
                "mail.smtps.auth",
                "mail.default_sender"
        );

        // set all parameters as properties
        for (String cfg : configs) {
            if (context.getInitParameter(cfg) != null) {
                properties.setProperty(cfg, context.getInitParameter(cfg));
            }
        }
    }

    public void SendEmail(Mail mail) {
        SendEmail(mail.getRecipient(), mail.getSubject(), mail.getBody());
    }
    
    public void SendEmail(String to, String subject, String htmlEmailBody) {
        String[] rec = new String[]{to};
        SendEmail(rec, subject, htmlEmailBody);
    }

    public void SendEmail(String[] to, String subject, String htmlEmailBody) {

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(properties.getProperty("mail.default_sender")));

            // Set To: header field of the header.
            for (String a : to)
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(a));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setContent(htmlEmailBody, "text/html");

            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            Logger.getLogger("mail").log(Level.SEVERE, null, mex);
        }
    }

    public void SendFileEmail(String to, String subject, String htmlEmailBody, File[] attachmentsFiles) {

        // Get the default Session object
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(properties.getProperty("mail.default_sender")));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part 
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setContent(htmlEmailBody, "text/html");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            for (File f : attachmentsFiles) {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(f);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(f.getName());
                multipart.addBodyPart(messageBodyPart);
            }

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            Logger.getLogger("mail").log(Level.SEVERE, null, mex);
        }
    }
}
