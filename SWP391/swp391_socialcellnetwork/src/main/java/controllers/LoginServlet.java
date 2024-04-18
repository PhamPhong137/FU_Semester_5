/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.AccountDAO;
import dal.FamilyDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Account;
import models.FamilyInfo;
import models.User;

/**
 *
 * @author Admin
 */
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userOrGmail = request.getParameter("userOrGmail");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        int user_id = AccountDAO.INSTANCE.getUserIdByUsernameOrEmail(userOrGmail);

        try {
            if (AccountDAO.INSTANCE.checkLogin(userOrGmail, password)) {
                if (AccountDAO.INSTANCE.checkSuspendedAccount(user_id)) {
                    User currentUser = UserDAO.INSTANCE.getUserByUserId(user_id);
                    HttpSession session = request.getSession();
                    session.setAttribute("currentUser", currentUser);
                    Cookie c_user = new Cookie("userOrGmail", userOrGmail);
                    Cookie c_pass = new Cookie("password", password);
                    Cookie c_rem = new Cookie("remember", remember);
                    if (remember != null && !remember.isEmpty()) {
                        c_user.setMaxAge(3600 * 24 * 7);
                        c_pass.setMaxAge(3600 * 24 * 7);
                        c_rem.setMaxAge(3600 * 24 * 7);
                    } else {
                        c_user.setMaxAge(0);
                        c_pass.setMaxAge(0);
                        c_rem.setMaxAge(0);
                    }
                    response.addCookie(c_user);
                    response.addCookie(c_pass);
                    response.addCookie(c_rem);
                    List<FamilyInfo> listfamily = FamilyDAO.INSTANCE.getAllFamilysByUserId(currentUser.getUser_id());
                    session.setAttribute("listfamily", listfamily);
                    Account account = AccountDAO.INSTANCE.getAccountById(user_id);
                    LocalDateTime now = LocalDateTime.now();
                    Timestamp operating_date = Timestamp.valueOf(now);
                    AccountDAO.INSTANCE.updateOperatingDateAccount(user_id, operating_date);
                    if (account.getRole() == 0) {
                        response.sendRedirect("admin");
                    } else {
                        response.sendRedirect("home");
                    }
                } else {
                    request.setAttribute("error1", "Account is suspended!");
                    request.getRequestDispatcher("views/login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Incorrect username or password!");
                request.getRequestDispatcher("views/login.jsp").forward(request, response);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
