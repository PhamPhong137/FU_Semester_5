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
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class AddNewAccountByAdminServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddNewAccountByAdminServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddNewAccountByAdminServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("views/createnewaccount.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String f_name = request.getParameter("firstname");
        String l_name = request.getParameter("lastname");
        int gender = Integer.parseInt(request.getParameter("gender"));
        String username = request.getParameter("username");
        String phone_number = request.getParameter("phone_number");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int role = Integer.parseInt(request.getParameter("role"));
        LocalDateTime now = LocalDateTime.now();
        Timestamp creation_date = Timestamp.valueOf(now);

        boolean isUsernameExists = AccountDAO.INSTANCE.checkExistedUser(username);
        boolean isPhoneExists = UserDAO.INSTANCE.checkExistedPhone(phone_number);
        boolean isEmailExists = UserDAO.INSTANCE.checkExistedEmail(email);

        if (!isUsernameExists && !isPhoneExists && !isEmailExists) {
            int userId = UserDAO.INSTANCE.addUser(f_name, l_name, gender, phone_number);
            try {
                AccountDAO.INSTANCE.addAccountByAdmin(userId, username, email, password, role, creation_date);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(AddNewAccountByAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("admin"); 
        }

        if (isUsernameExists) {
            request.setAttribute("errorUsername", "Username already exists!");
        }

        if (isPhoneExists) {
            request.setAttribute("errorPhone", "Phone number already exists!");
        }

        if (isEmailExists) {
            request.setAttribute("errorEmail", "Email already exists!");
        }

        request.setAttribute("firstname", f_name);
        request.setAttribute("lastname", l_name);
        request.setAttribute("gender", gender);
        request.setAttribute("role", role);
        request.setAttribute("username", username);
        request.setAttribute("phone", phone_number);
        request.setAttribute("email", email);
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
