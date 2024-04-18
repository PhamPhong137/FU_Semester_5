/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sendmailmaven;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author ACERR
 */
public class SendMailMaven6 {

    public static void main(String[] args) {
        final String username = "trung20030812@gmail.com";
        final String password = "ztpy fkxb umsr crbz";

        ExecutorService executor = Executors.newSingleThreadExecutor();
        long startTime = System.currentTimeMillis();
        executor.submit(() -> {
            try {
                sendEmail(username, password);
                System.out.println("Email sent successfully from the thread!");
            } catch (MessagingException e) {
                e.printStackTrace();
            } finally {
                long endTime = System.currentTimeMillis();
                System.out.println("Elapsed time: " + (endTime - startTime) + "ms");
            }
        });

        executor.shutdown();
        try {
            // Wait for a maximum of 7 seconds for the executor to finish
            if (!executor.awaitTermination(7, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    public static void sendEmail(String username, String password) throws AddressException, MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        // Further reduce connection and read timeouts
        props.put("mail.smtp.connectiontimeout", "3000");
        props.put("mail.smtp.timeout", "3000");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("trung12082003@gmail.com")); 
        message.setSubject("Test JavaMailMaven");
        message.setText("Mail success");

        Transport.send(message);
    }
}

