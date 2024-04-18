/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sendmailmaven;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
/**
 *
 * @author ACERR
 */
public class SendMailMaven3{

    private static final String SENDER_EMAIL = "trung20030812@gmail.com";
    private static final String RECIPIENT_EMAIL = "trung12082003@gmail.com";
    private static final String APP_PASSWORD = "ztpy fkxb umsr crbz";
    private static final String EMAIL_SUBJECT = "Test JavaMailMaven";
    private static final String EMAIL_CONTENT = "Mail success";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT_EMAIL));
            message.setSubject(EMAIL_SUBJECT);
            message.setText(EMAIL_CONTENT);

            try (Transport transport = session.getTransport("smtp")) {
                transport.connect("smtp.gmail.com", SENDER_EMAIL, APP_PASSWORD);
                transport.sendMessage(message, message.getAllRecipients());
            }

            System.out.println("Email sent successfully!");
        } catch (AuthenticationFailedException e) {
            System.err.println("Authentication failed. Please check your credentials.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

