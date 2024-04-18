/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sendmailmaven;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author ACERR
 */
public class SendMailMaven4 {

    public static void main(String[] args) {
        final String username = "trung20030812@gmail.com";
        final String password = "ztpy fkxb umsr crbz";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            // Create a single Runnable for sending email
            Runnable sendMailTask = () -> {
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("trung12082003@gmail.com"));
                    message.setSubject("Test JavaMailMaven");
                    message.setText("Mail success");
                    
                    // Use Transport.sendMessageAsync() for asynchronous sending
                    Transport.send(message);
                    System.out.println("Email sent successfully!");
                } catch (SendFailedException e) {
                    System.out.println("Send failed: " + e.getMessage());
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            };

            // Submit the task to the executor
            executor.submit(sendMailTask);

            // Shutdown the executor to allow the program to exit
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}