/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.framework.email;

import javax.servlet.ServletContext;

/**
 *
 * @author carlo
 */
public class Mail {
    private String subject = "";
    private String body = "";
    private String recipient;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public void send(ServletContext context) {
        SendEmail email = new SendEmail(context);
        email.SendEmail(recipient, subject, body);
    }
}
