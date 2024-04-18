/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
import dal.FamilyDAO;
import dal.PersonDAO;
import dal.UserDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.FamilyInfo;
import models.Person;
import models.User;

/**
 *
 * @author thanh
 */
public class GraftTreeServlet extends HttpServlet {

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
            out.println("<title>Servlet GraftTreeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GraftTreeServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        UserDAO userDAO = UserDAO.INSTANCE;
        PersonDAO controller = PersonDAO.getInstance();
        FamilyDAO familyDAO = FamilyDAO.INSTANCE;
        String myTreeId = "";
        String userIdString = request.getParameter("targetId");
        int userId = -1;
        try {
            userId = Integer.parseInt(userIdString);
        } catch (NumberFormatException n) {

        }
        User target = userDAO.getUserByUserId(userId);
        String targetTreeId = String.valueOf(target.getTreeId());
        FamilyInfo familyInfo = (FamilyInfo) session.getAttribute("familyInfo");

        // Xử lý cho myTreeJson
        if (familyInfo != null) {
            myTreeId = String.valueOf(familyInfo.getTreeId());
            request.setAttribute("state", " for Family");
        } else {
            myTreeId = String.valueOf(currentUser.getTreeId());
            request.setAttribute("state", " for Individual");
        }

        Person myRootPerson = controller.getFamilyTreeByTreeId(myTreeId);
        Gson gson = new Gson();
        String myTreeJson = gson.toJson(myRootPerson);
        request.setAttribute("myTreeJson", myTreeJson);

        // Xử lý cho targetTreeJson nếu targetId được cung cấp
        if (targetTreeId != null && !targetTreeId.isBlank()) {
            Person targetRootPerson = controller.getFamilyTreeByTreeId(targetTreeId);
            String targetTreeJson = gson.toJson(targetRootPerson);
            request.setAttribute("targetTreeJson", targetTreeJson);
        }
        request.setAttribute("stateOfGraft", "target");
        request.setAttribute("targetId", userIdString);
        request.removeAttribute("actionGraft");
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/graftTree.jsp");
        dispatcher.forward(request, response);
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
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        UserDAO userDAO = UserDAO.INSTANCE;
        PersonDAO controller = PersonDAO.getInstance();
        FamilyDAO familyDAO = FamilyDAO.INSTANCE;
PrintWriter out = response.getWriter(); 
        FamilyInfo familyInfo = (FamilyInfo) session.getAttribute("familyInfo");
        String myTreeId = "";

        if (familyInfo != null) {
            myTreeId = String.valueOf(familyInfo.getTreeId());
            request.setAttribute("state", " for Family");
        } else {
            myTreeId = String.valueOf(currentUser.getTreeId());
            request.setAttribute("state", " for Individual");
        }
        Person myRootPerson = controller.getFamilyTreeByTreeId(myTreeId);
        Gson gson = new Gson();
        String myTreeJson = gson.toJson(myRootPerson);
        request.setAttribute("myTreeJson", myTreeJson);
        String action = request.getParameter("actionGraft");
        String nodeIdOfTargetString = request.getParameter("nodeIdOfTargetTree");
        String nodeIdOfRootTargetTreeString = request.getParameter("nodeIdOfRootTargetTree");
        String nodeIdOfMyTreeString = request.getParameter("nodeIdOfMyTree");
        String actionOfGraft = request.getParameter("actionOfGraft");
        String targetIdString = request.getParameter("targetIdInForm");

        if("true".equals(action) && actionOfGraft != null){
                 long nodeIdOfMyTree = -1;
                 long nodeIdOfRootTargetTree = -1;
                 try{
                     nodeIdOfMyTree = Long.parseLong(nodeIdOfMyTreeString);
                     nodeIdOfRootTargetTree = Long.parseLong(nodeIdOfRootTargetTreeString);
                 }catch (Exception e){
                     
                 }

                 if(PersonDAO.getInstance().graftTree(nodeIdOfRootTargetTree, nodeIdOfMyTree)){
                     response.sendRedirect("/SocialCellNetwork/familytree");
                     session.setAttribute("message", "Graft successfully");
                     return;
                 } else {
                     response.sendRedirect("/SocialCellNetwork/familytree");
                     session.setAttribute("message", "Graft unsuccessfully! 2 grafting points is not valid!");
                     return;
                 }
        } else if(nodeIdOfTargetString.isBlank()){
            nodeIdOfTargetString = nodeIdOfRootTargetTreeString;
            session.setAttribute("message", "Unusual action");
        }
       
        long nodeId = -1;
        try{
            nodeId = Long.parseLong(nodeIdOfTargetString);
        } catch (NumberFormatException n){
        }
        Person targetPerson = controller.getFamilyTreeByNodeId(nodeId);
        String targetTreeJson = gson.toJson(targetPerson);
        request.setAttribute("nodeIdOfRootTargetTree", nodeIdOfTargetString);
        request.setAttribute("targetTreeJson", targetTreeJson);
        request.setAttribute("stateOfGraft", "my");
        request.setAttribute("actionGraft", "true");
        request.setAttribute("targetId", targetIdString);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/graftTree.jsp");
        dispatcher.forward(request, response);
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
