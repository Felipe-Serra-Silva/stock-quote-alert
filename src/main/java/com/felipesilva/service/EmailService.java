package com.felipesilva.service;

import com.felipesilva.config.AppConfig;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailService {

    private final AppConfig config;

    public EmailService(AppConfig config) {
        this.config = config;
    }

    public void sendEmail(String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", config.getSmtpHost());
        props.put("mail.smtp.port", config.getSmtpPort());

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getSmtpUser(), config.getSmtpPassword());
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(config.getSmtpUser()));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(config.getRecipient()));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}