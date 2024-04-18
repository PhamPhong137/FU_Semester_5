/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import models.User;

/**
 *
 * @author Admin
 */
public class AddPhoneServlet extends HttpServlet {

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
            out.println("<title>Servlet AddPhoneServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddPhoneServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        String id_raw = request.getParameter("userId");
        String phone_number = request.getParameter("phonenumber");
        if (id_raw != null && !id_raw.isEmpty()) {
            int id = Integer.parseInt(id_raw);
            boolean isPhoneExists = UserDAO.INSTANCE.checkExistedPhone(phone_number);
            if (isPhoneExists) {
                out.println("<div class=\"form-group\">\n"
                        + "                                                    <label for=\"number\" class=\"input-label\">Phone Number</label>\n"
                        + "                                                    <input type=\"text\" id=\"phonenumber\" class=\"form-control\" name=\"phone_number\" value=\"" + phone_number + "\" placeholder=\"phonenumber\" pattern=\"[0-9]{10}\" title=\"Please enter valid number\"\n"
                        + "                                                           maxlength=\"10\" minlength=\"10\" required=\"required\" />\n"
                        + "                                                </div>\n"
                        + "                                                <div class=\"form-group\" style=\"display: flex; justify-content: end\">\n"
                        + "                                                    <button type=\"submit\" class=\"btn btn-primary\" onclick=\"checkPhone(" + id + ")\">Update</button>\n"
                        + "                                                </div>");
            } else {
                UserDAO.INSTANCE.updateUserPhone(id, phone_number);
                User user = UserDAO.INSTANCE.getUserByUserId(id);
                session.setAttribute("currentUser", user);
                out.print("updatesucess");
            }
        }
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
