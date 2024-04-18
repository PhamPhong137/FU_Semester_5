/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
import dal.FamilyDAO;
import dal.FriendDAO;
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
import models.FamilyMember;
import models.Person;
import models.User;

/**
 *
 * @author thanh
 */
public class FamilyTreeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP {@code GET} and {@code POST} methods. It
     * generates a basic HTML response. This method is mainly for testing
     * purposes.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FamilyTreeShowController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FamilyTreeShowController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP {@code GET} method. It initializes the
     * FamilyTreeController, fetches the family tree starting from a specified
     * root person (or a default one), converts the data to JSON, and forwards
     * it to a JSP for rendering.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        PersonDAO controller = PersonDAO.getInstance();
        FamilyDAO familyDAO = FamilyDAO.INSTANCE;
        String treeId = "";
        int familyIdInt = -1;
        String familyId = request.getParameter("familyId");
        try {
            familyIdInt = Integer.parseInt(familyId);
        } catch (Exception e) {
        }
        String isIndividualTree = request.getParameter("isIndividualTree");
        String roleFamily = "edit";
        FamilyInfo familyInfo = (FamilyInfo) session.getAttribute("familyInfo");
        
         //boolean familyExists = FamilyDAO.INSTANCE.checkFamilyExists(familyIdInt, currentUser.getUser_id());
         
        if (familyId != null && !familyId.isBlank()) {
            //if (!familyExists) request.getRequestDispatcher("views/accesscontrol.jsp").forward(request, response);
            FamilyInfo fi = familyDAO.getFamilyById(familyIdInt);
            FamilyMember fm = new FamilyMember();
            fm = familyDAO.getFamilyMemberById(familyIdInt, currentUser.getUser_id());
            roleFamily = fm.getRole() == 1 ? "edit" : "view";
            request.setAttribute("role", roleFamily);
            session.setAttribute("familyInfo", fi);
            request.setAttribute("state", " for Family");
            treeId = String.valueOf(fi.getTreeId());
        } else if (isIndividualTree != null && !isIndividualTree.isBlank()) {
            request.setAttribute("role", roleFamily);
            request.setAttribute("state", " for Individual");
            session.removeAttribute("familyInfo");
            treeId = String.valueOf(currentUser.getTreeId());
        } else if (familyInfo != null) {
            //if (!familyExists) request.getRequestDispatcher("views/accesscontrol.jsp").forward(request, response);
            FamilyMember fm = new FamilyMember();
            fm = familyDAO.getFamilyMemberById(familyInfo.getFamily_id(), currentUser.getUser_id());
            roleFamily = fm.getRole() == 1 ? "edit" : "view";
            request.setAttribute("role", roleFamily);
            request.setAttribute("state", " for Family");
            treeId = String.valueOf(familyInfo.getTreeId());
        } else {
            request.setAttribute("role", roleFamily);
            request.setAttribute("state", " for Individual");
            treeId = String.valueOf(currentUser.getTreeId());
        }
        
        
        Person rootPerson = controller.getFamilyTreeByTreeId(treeId);
        
        Gson gson = new Gson();
        String json = gson.toJson(rootPerson);
        request.setAttribute("rootPersonJson", json);

        String stateOfScreen = "new";
        if (controller.isAnyNodeExist(treeId)) {
            stateOfScreen = "edit";
        }

        Boolean searchPerformed = (Boolean) request.getAttribute("searchPerformed");
        if (Boolean.TRUE.equals(searchPerformed)) {
            
            List<User> searchResults = (List<User>) request.getAttribute("searchResults");
            if (searchResults.isEmpty()) {
                session.setAttribute("message", "Không tìm thấy người phù hợp!");
            }
            
            Gson searchFriendGson = new Gson();
            String searchResultsJson = searchFriendGson.toJson(searchResults);

            
            request.setAttribute("searchResultsJson", searchResultsJson);
        }
        request.setAttribute("currentUserId", currentUser.getUser_id());
        request.setAttribute("stateOfScreen", stateOfScreen);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/familyTree.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP {@code POST} method. Currently, this method delegates to
     * {@link #processRequest(HttpServletRequest, HttpServletResponse)}.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String actionType = request.getParameter("actionType");
        String nodeIdString = request.getParameter("nodeId");
        String userIdGetString = request.getParameter("userIdGet");
        long nodeId = -1;
        int userIdGet = -1;
        try {
            nodeId = Long.parseLong(nodeIdString);
            userIdGet = Integer.parseInt(userIdGetString);
        } catch (Exception e) {
        }

        String name = request.getParameter("name").trim();
        String birthDate = request.getParameter("birthDate");
        String deathDate = request.getParameter("deathDate");
        String optionalInfo = request.getParameter("optionalInfo").trim();
        String relationshipType = request.getParameter("relation");
        String targetPersonName = request.getParameter("targetName");
        String targetPersonBirth = request.getParameter("targetDob");
        String searchTerm = request.getParameter("searchTerm").trim();
        String death = null;

        FamilyInfo familyInfo = (FamilyInfo) session.getAttribute("familyInfo");
        String treeId;
        User currentUser = (User) session.getAttribute("currentUser");

        if (familyInfo != null) {
            treeId = String.valueOf(familyInfo.getTreeId());
        } else {
            currentUser = (User) session.getAttribute("currentUser");
            treeId = String.valueOf(currentUser.getTreeId());
        }

        PersonDAO p = PersonDAO.getInstance();
        FriendDAO f = FriendDAO.INSTANCE;

        if (!validateInputParameters(request, actionType, name, birthDate, nodeId, targetPersonBirth, relationshipType)) {
            // Nếu không hợp lệ, quay trở lại trang trước hoặc hiển thị thông báo lỗi
            doGet(request, response);
            return;
        }

        switch (actionType) {
            case "add":
                if ("child".equals(relationshipType)) {
                    if (!p.addChild(nodeId, userIdGet, name, birthDate, deathDate, optionalInfo, treeId, targetPersonBirth)) {
                        session.setAttribute("message", "Invalid child add");
                    }
                } else if ("spouse".equals(relationshipType)) {
                    if (name.equalsIgnoreCase(targetPersonName)) {
                        name += " (Spouse)";
                    }
                    if (!p.addSpouse(userIdGet, nodeId, name, birthDate, deathDate, optionalInfo, treeId)) {
                        session.setAttribute("message", "Invalid spouse add");
                    }
                } else if ("none".equals(relationshipType)) {
                    session.setAttribute("message", "Invalid relation");
                }
                break;
            case "update":
                if (!p.updatePerson(nodeId, userIdGet, name, birthDate, deathDate, optionalInfo, treeId)) {
                    session.setAttribute("message", "Invalid update");
                }
                break;
            case "delete":
                p.removePerson(nodeId, treeId);
                break;
            case "create":
                if (familyInfo != null) {
                    treeId = String.valueOf(p.createTreeForFamily(familyInfo.getFamily_id()));
                    int tree = Integer.parseInt(treeId);
                    familyInfo.setTreeId(tree);
                    session.setAttribute("familyInfo", familyInfo);
                } else {
                    treeId = String.valueOf(p.createTreeForUser(currentUser.getUser_id()));
                    int tree = Integer.parseInt(treeId);
                    currentUser.setTreeId(tree);
                    session.setAttribute("currentUser", currentUser);
                }

                if (!p.createNewFamilyTree(treeId, name, birthDate, deathDate, optionalInfo, userIdGet)) {
                    session.setAttribute("message", "The family tree exists");
                }
                break;
            case "search": {
                List<User> searchResults = f.searchFriendByNumberOrName(searchTerm, currentUser.getUser_id());
                // Store the search results in the session or request
                request.setAttribute("searchResults", searchResults);
                // Set a flag to indicate that a search was performed
                request.setAttribute("searchPerformed", true);
                break;
            }
            case "graft": {
                List<User> searchResults = f.searchFriendByNumberOrName(searchTerm, currentUser.getUser_id());
                // Store the search results in the session or request
                request.setAttribute("searchResults", searchResults);
                // Set a flag to indicate that a search was performed
                request.setAttribute("searchPerformed", true);
                request.setAttribute("searchForGraft", true);
                break;
            }
            case "createAddYourself":
                name = currentUser.getF_name() +" "+ currentUser.getL_name();
                birthDate = String.valueOf(currentUser.getDate_birth());
                deathDate = String.valueOf(currentUser.getDate_death());
                if("null".equals(deathDate)) deathDate = death;
                userIdGet = currentUser.getUser_id();

                if (familyInfo != null) {
                    treeId = String.valueOf(p.createTreeForFamily(familyInfo.getFamily_id()));
                    int tree = Integer.parseInt(treeId);
                    familyInfo.setTreeId(tree);
                    session.setAttribute("familyInfo", familyInfo);
                } else {
                    treeId = String.valueOf(p.createTreeForUser(currentUser.getUser_id()));
                    int tree = Integer.parseInt(treeId);
                    currentUser.setTreeId(tree);
                    session.setAttribute("currentUser", currentUser);
                }

                if (!p.createNewFamilyTree(treeId, name, birthDate, deathDate, optionalInfo, userIdGet)) {
                    session.setAttribute("message", "Unable to create your family tree");
                }
                break;

            case "addAddYourself":
                name = currentUser.getF_name() +" "+ currentUser.getL_name();
                birthDate = String.valueOf(currentUser.getDate_birth());
                deathDate = String.valueOf(currentUser.getDate_death());
                if("null".equals(deathDate)) deathDate = death;
                userIdGet = currentUser.getUser_id();
                if ("child".equals(relationshipType)) {
                    if (!p.addChild(nodeId, userIdGet, name, birthDate, deathDate, optionalInfo, treeId, targetPersonBirth)) {
                        session.setAttribute("message", "Invalid child add");
                    }
                } else if ("spouse".equals(relationshipType)) {
                    if (name.equalsIgnoreCase(targetPersonName)) {
                        name += " (In-law)";
                    }
                    if (!p.addSpouse(userIdGet, nodeId, name, birthDate, deathDate, optionalInfo, treeId)) {
                        session.setAttribute("message", "Invalid spouse add");
                    }
                } else if ("none".equals(relationshipType)) {
                    session.setAttribute("message", "Invalid relation");
                }
                break;
            case "updateAddYourself":
                name = currentUser.getF_name() +" "+ currentUser.getL_name();
                birthDate = String.valueOf(currentUser.getDate_birth());
                deathDate = String.valueOf(currentUser.getDate_death());
                if("null".equals(deathDate)) deathDate = death;
                userIdGet = currentUser.getUser_id();
                
                if (!p.updatePerson(nodeId, userIdGet, name, birthDate, deathDate, optionalInfo, treeId)) {
                    session.setAttribute("message", "Invalid update");
                }
                break;
            default:
                session.setAttribute("message", "Invalid action");
                return;
        }
        doGet(request, response);
    }

    /**
     * Returns a short description of this servlet.
     *
     * @return A String containing a brief description of the servlet.
     */
    private boolean validateInputParameters(HttpServletRequest request, String actionType, String name, String birthDate, long nodeId, String targetPersonBirth, String relationshipType) {
        HttpSession session = request.getSession();
        StringBuilder missingFields = new StringBuilder();

        switch (actionType) {
            case "add":
                if (name == null || name.isEmpty()) {
                    missingFields.append("Name, ");
                }
                if (birthDate == null || birthDate.isEmpty()) {
                    missingFields.append("Birth Date, ");
                }
                if ("child".equals(relationshipType) || "spouse".equals(relationshipType)) {
                    if (nodeId == -1) {
                        missingFields.append("Target Person, ");
                    }
                }
                break;
            case "update":
                // Ví dụ: Kiểm tra cho trường hợp cập nhật
                break;
            case "delete":
                if (nodeId == -1) {
                    missingFields.append("Target Person, ");
                }
                break;
            case "create":
                // Ví dụ: Kiểm tra cho trường hợp tạo mới
                if (name == null || name.isEmpty()) {
                    missingFields.append("Name, ");
                }
                break;
            // Thêm các trường hợp khác nếu cần
        }

        if (missingFields.length() > 0) {
            // Loại bỏ dấu phẩy và khoảng trắng cuối cùng
            missingFields.delete(missingFields.length() - 2, missingFields.length());
            session.setAttribute("message", "Missing required fields: " + missingFields.toString());
            return false; // Có trường bỏ trống, không hợp lệ
        }

        return true; // Tất cả các trường cần thiết đều hợp lệ
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
