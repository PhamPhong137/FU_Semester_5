<%-- 
    Document   : admin
    Created on : Feb 18, 2024, 4:42:55 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SCN | Admin</title>
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.jpg">
        <link rel="stylesheet" href="templates/css/bootstrap.min.css">
        <link rel="stylesheet" href="templates/css/fontawesome-all.min.css">
        <link rel="stylesheet" href="css/admin.css"/>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-2 side-bar">
                    <div class="top-fixed">
                        <div class="content"><i class="fas fa-home" style="font-size: 20px;"></i><a href="admin?cp=1">Home</a></div>
                    </div>
                    <div class="bottom bottom-fixed">
                        <div class="img-info"><img src="img/${currentUser.image}" alt="alt"/></div>
                        <div style="margin: 0px 15px">${currentUser.f_name} ${currentUser.l_name}</div> 
                        <a href="logout"><i class="fas fa-sign-out-alt" style="font-size: 22px; padding-top: 5px"></i></a>
                    </div>

                </div>
                <div class="col-lg-10">
                    <div class="header">
                        <div>
                            <button class="btn btn-primary" style="margin-bottom: 5px"><a href="addnewaccount" style="text-decoration: none; color: white">New User <i class="fas fa-user-friends"></i></a></button>
                        </div>
                        <div>
                            <form action="admin">
                                <input type="hidden" name="cp" value="1">
                                <input class="search-input" type="text" name="key" placeholder="Search username or email..." />
                                <button type="submit" class="search-button" onclick="return validateSearch()">
                                    <i class="fas fa-search search-icon"></i>
                                </button>
                            </form>
                        </div> 

                    </div>

                    <!--                    <form action="admin" method="get">
                                            <input type="hidden" name="cp" value="1">
                                            <select name="option">
                                                <option value="all">All</option>
                                                <option value="admin">Admin</option>
                                                <option value="user">User</option>
                                            </select>
                                            <input type="submit" value="Submit">
                                        </form>-->


                    <c:if test="${empty accList}">
                        <div class="alert alert-info" style="margin-top: 5px; margin-bottom: 2px">No accounts were found with the words '${searchKey}'</div>
                        <div style="display: flex; justify-content: center">
                            <div><img src="img/notfoundaccount.jpg" alt="alt" height="400px"/></div>  
                        </div>
                        <h2 style="text-align: center; font-size: 30px;
                            font-weight: bold;
                            margin-bottom: 40px;">Opp ! Not found this account...</h2> 
                        <a href="admin" class="centered-button">Back to account list</a>
                    </c:if>

                    <c:if test="${not empty accList}">
                        <table id="accountTable" class="table align-middle mb-0 bg-white">
                            <thead class="bg-light">
                                <tr>
                                    <th>Account</th>
                                    <th>Role</th>
                                    <th>Status</th>
                                    <th>Creation Date</th>
                                    <th>Operating Date</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${accList}" var="a">
                                    <tr>
                                        <td>
                                            <div class="d-flex align-items-center">
                                                <div>
                                                    <p class="fw-bold mb-1">${a.username}</p>
                                                    <p class="text-muted mb-0">${a.email}</p>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <c:if test="${a.role == 0}">
                                                <button class="btn btn-warning">Admin</button>
                                            </c:if>
                                            <c:if test="${a.role == 1}">
                                                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#popup-updateRole-${a.user_id}">User</button>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${a.role != 0}">
                                                <c:choose>
                                                    <c:when test="${a.isBanned == 1}">
                                                        <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#popup-updateStatus-${a.user_id}">Ban</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#popup-updateStatus-${a.user_id}">Active</button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <c:if test="${a.role == 0}">
                                                <c:choose>
                                                    <c:when test="${a.isBanned == 1}">
                                                        <button class="btn btn-danger">Ban</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="btn btn-success">Active</button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </td>
                                        <td>${a.creation_date.toString().substring(0,10)}</td>
                                        <td>${a.operating_date.toString().substring(0,16)}</td>
                                        <!--<td>
                                        <c:choose>
                                            <c:when test="${a.role != 0}">
                                                    <div>
                                                        <a class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#popup-deleteAccount-${a.user_id}">Delete</a>
                                                    </div>
                                            </c:when>
                                            <c:otherwise>
                                                <td></td>
                                            </c:otherwise>
                                        </c:choose>
                                        </td>-->


                                    </tr>
                                    <!-- popup update role -->
                                <div class="modal fade" id="popup-updateRole-${a.user_id}" aria-hidden="true"
                                     aria-labelledby="exampleModalToggleLabel" tabindex="-1">
                                    <div class="modal-dialog modal-dialog-centered" style="max-width: 400px">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalToggleLabel">Update role</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="admin" method="post">
                                                    <input type="hidden" name="cp" value="${sessionScope.page.getCurrentPage()}"/>    
                                                    <input type="hidden" name="user_id" value="${a.user_id}"/> 
                                                    <input type="hidden" name="key" value="${searchKey}"/>
                                                    <input type="hidden" name="type" value="0">
                                                    <div class="form-group">
                                                        <label for="role_admin" class="input-label" style="font-weight: bold ; margin-right: 30px">
                                                            <input type="radio" id="role_admin" name="role" ${a.role == 0 ? 'checked' : ''} value="0" /> 
                                                            Admin
                                                        </label>
                                                        <label for="role_user" class="input-label" style="font-weight: bold">
                                                            <input type="radio" id="role_user" name="role" ${a.role == 1 ? 'checked' : ''} value="1" /> 
                                                            User
                                                        </label>
                                                    </div>
                                                    <button type="submit" class="btn btn-primary" style="float: right">Update</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- popup update status -->
                                <div class="modal fade" id="popup-updateStatus-${a.user_id}" aria-hidden="true"
                                     aria-labelledby="exampleModalToggleLabel" tabindex="-1">
                                    <div class="modal-dialog modal-dialog-centered" style="max-width: 400px">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalToggleLabel">Update status</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="admin" method="post"> 
                                                    <input type="hidden" name="cp" value="${sessionScope.page.getCurrentPage()}"/>    
                                                    <input type="hidden" name="user_id" value="${a.user_id}"/> 
                                                    <input type="hidden" name="key" value="${searchKey}"/>
                                                    <input type="hidden" name="type" value="1">
                                                    <div class="form-group">
                                                        <label for="active" class="input-label" style="font-weight: bold; margin-right: 30px;">
                                                            <input type="radio" name="isBanned" ${a.isBanned == 0 ? 'checked' : ''} value="0" /> 
                                                            Active
                                                        </label>
                                                        <label for="ban" class="input-label" style="font-weight: bold;">
                                                            <input type="radio" name="isBanned" ${a.isBanned == 1 ? 'checked' : ''} value="1" /> 
                                                            Ban
                                                        </label>
                                                    </div>
                                                    <button type="submit" class="btn btn-primary" style="float: right">Update</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- popup delete account -->
<!--                                <div class="modal fade" id="popup-deleteAccount-${a.user_id}" aria-hidden="true"
                                     aria-labelledby="exampleModalToggleLabel" tabindex="-1">
                                    <div class="modal-dialog modal-dialog-centered" style="max-width: 400px">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalToggleLabel" style="color: red">Alert</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="admin" method="post">  
                                                    <input type="hidden" name="cp" value="${sessionScope.page.getCurrentPage()}"/>    
                                                    <input type="hidden" name="user_id" value="${a.user_id}"/> 
                                                    <input type="hidden" name="key" value="${searchKey}"/>
                                                    <input type="hidden" name="type" value="2">
                                                    <div>
                                                        <p>Are you sure to delete account '${a.username}'?</p> 
                                                    </div>
                                                    <button type="submit" class="btn btn-primary" style="float: right">OK</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>-->
                            </c:forEach>
                            </tbody>
                        </table>
                        <!--Paging -->
                        <%@include file="/components/pagination.jsp" %>
                    </c:if>

                </div>
            </div>
        </div>
        <!-- Bootstrap js -->
        <script src="templates/js/bootstrap.min.js"></script>
        <script>
                                    function validateSearch() {
                                        var keyInput = document.getElementsByName('key')[0];
                                        var key = keyInput.value.trim();
                                        if (key === '') {
                                            event.preventDefault();
                                        }
                                    }
                                    document.addEventListener('DOMContentLoaded', function () {
                                        var password = document.getElementById("password");
                                        var confirmPassword = document.getElementById("re-password");

                                        function validatePassword() {
                                            if (password.value !== confirmPassword.value) {
                                                confirmPassword.setCustomValidity("Passwords do not match");
                                            } else {
                                                confirmPassword.setCustomValidity('');
                                            }
                                        }
                                        password.addEventListener('change', validatePassword);
                                        confirmPassword.addEventListener('keyup', validatePassword);
                                    });
        </script>
    </body>
</html>
