/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.RemindEventDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.Remind;
import models.User;

/**
 *
 * @author PC-Phong
 */
public class ShowRemindServlet extends HttpServlet {

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
            out.println("<title>Servlet ShowRemindServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowRemindServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        int event_id = Integer.parseInt(request.getParameter("event_id"));

        List<Remind> showRemindByEventId = RemindEventDAO.INSTANCE.showRemindByEventId(event_id, currentUser.getUser_id());

        out.print("<div class=\"modal-dialog modal-dialog-centered\">\n"
                + "                            <div class=\"modal-content\">\n"
                + "                                <div class=\"modal-header\">\n"
                + "                                    <h1 class=\"modal-title fs-5\" id=\"\">Remind Event</h1>\n"
                + "                                    <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n"
                + "                                </div>\n"
                + "                                <div class=\"modal-body choose-group-item\">\n"
                + "\n"
                + "                                    <input type=\"text\" id=\"text" + event_id + "\" value=\"\"  placeholder=\"Type title here!\" style=\"border-radius: 5px; border: none; background-color: rgba(128, 128, 128, 0.18  )\"/>\n"
                + "                                    <div style=\"display: flex; justify-content: space-between\">\n"
                + "                                        <input type=\"date\" id=\"date" + event_id + "\"  value=\"\"  style=\"width: 60%; margin-bottom: 0px\">\n"
                + "                                        <button style=\"padding: 5px 20px; border-radius: 5px; background-color: aqua; border: none\" onclick=\"addRemind(" + event_id + ")\"  >Add</button>\n"
                + "                                    </div>\n"
                + "\n"
                + "                                    <div style=\"font-size: 23px;font-weight: 600; margin-top: 20px \">List reminds:</div>\n");

        for (Remind e : showRemindByEventId) {
            String remindText;
            if (e.getDay() < 0) {
                remindText = "Remind after " + Math.abs(e.getDay()) + " day";
            } else {
                remindText = "Remind before " + e.getDay() + " day";
            }
            out.println("<div>\n"
                    + "    <div style=\"display: flex; justify-content: space-between\">\n"
                    + "        <div style=\"font-size: 15px\"><i style=\"color:red;cursor: pointer;font-size:20px\" title=\"Delete\" onclick=\"deleteRemind(" + e.getRemind_id() + "," + event_id + ")\" class=\"fas fa-minus-circle\"></i> Title remind: " + e.getRemind_title() + "</div>\n"
                    + "        <div style=\"color: blue;font-size: 15px; font-weight: 500\">" + remindText + "</div>\n"
                    + "    </div>\n"
                    + "    <div style=\"margin-left: 11px;font-size: 12px\">Time remind: " + e.getDate().toString().substring(0, 10) + "</div>\n"
                    + "<br>"
                    + "</div>");
        }

        out.println(" </div>\n"
                + "                                                    </div>\n"
                + "                                                </div>");
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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        int event_id = Integer.parseInt(request.getParameter("event_id"));
        String title = request.getParameter("title");
        String date = request.getParameter("date");

        RemindEventDAO.INSTANCE.addRemindEvent(event_id, title, date, currentUser.getUser_id());

       

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
