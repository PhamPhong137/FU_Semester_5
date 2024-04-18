/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.AccountDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import java.util.Properties;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class ForgotPasswordServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ForgotPasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPasswordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        int otpValue;
        if (email != null && !email.isEmpty()) {
            if (UserDAO.INSTANCE.checkExistedEmail(email)) {
                Random rand = new Random();
                do {
                    otpValue = rand.nextInt(1000000);
                } while (otpValue < 100000);
                if (AccountDAO.INSTANCE.checkExistedOtp(otpValue)) {
                    do {
                        otpValue = rand.nextInt(1000000);
                    } while (otpValue < 100000);
                }
                int id = AccountDAO.INSTANCE.getUserIdByUsernameOrEmail(email);
                AccountDAO.INSTANCE.updateOtpById(id, otpValue);
                final String username = "phongphhe176151@fpt.edu.vn";
                final String password = "jppifeioqlpjgykt";

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
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                    message.setSubject("Email Verification Code");
                    message.setText(otpValue + " is the OTP authentication code on Social Cell Network. "
                            + "The code is valid for 2 minutes. "
                            + "For security purposes, please do not share this code with anyone.");
                    Transport.send(message);
                } catch (MessagingException e) {
                    System.out.println(e.getMessage());
                }
                request.setAttribute("otp", otpValue);
                request.setAttribute("email", email);
                request.getRequestDispatcher("views/verifyOTP.jsp").forward(request, response);
            } else {
                request.setAttribute("errorEmail", "Email does not exist!");
                request.setAttribute("email", email);
                request.getRequestDispatcher("views/forgotpassword.jsp").forward(request, response);
            }
        }
        request.getRequestDispatcher("views/forgotpassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String otp1 = request.getParameter("otp1");
        String otp2 = request.getParameter("otp2");
        String otp3 = request.getParameter("otp3");
        String otp4 = request.getParameter("otp4");
        String otp5 = request.getParameter("otp5");
        String otp6 = request.getParameter("otp6");

        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        String otp_value_raw = otp1 + otp2 + otp3 + otp4 + otp5 + otp6;
        String otp_raw = request.getParameter("otp");

        if (otp_value_raw != null && !otp_value_raw.isEmpty() && otp_raw != null && !otp_raw.isEmpty()) {
            int otp_value = Integer.parseInt(otp_value_raw);
            int otp = Integer.parseInt(otp_raw);
            if (otp == otp_value) {
                request.getRequestDispatcher("views/changepassword.jsp").forward(request, response);
            } else {
                request.setAttribute("errorCode", "The verification code is incorrect. Please re-enter!");
                request.getRequestDispatcher("views/verifyOTP.jsp").forward(request, response);
            }
        }
    }

}
