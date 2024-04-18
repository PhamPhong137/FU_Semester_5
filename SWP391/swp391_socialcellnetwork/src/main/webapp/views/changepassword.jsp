
<%-- 
    Document   : profile
    Created on : Jan 16, 2024, 2:23:26 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html class="no-js" lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>SCN | ChangePassword</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.jpg">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="templates/css/bootstrap.min.css">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="css/resetpassword.css">
    </head>
    <body>
        <div class="container-fluid body-content">
            <div class="row">
                <!-- left nav -->

                <!-- Content -->
                <div class="col-lg-12">
                    <div class="body-content-reset">
                        <input type="hidden" name="emailForgotpassword" value="${email}">
                        <form action="changepassword" method="post">
                            <h1 style="font-size: 3.5rem">New password</h1>
                            <c:if test="${email != null}">
                                <input type="hidden" name="emailForgotpassword" value="${email}">
                                <input type="hidden" name="type" value="0"/>
                                <input style="margin: 40px 0px 10px 0px" id="password" type="password" name="passwordEmailForgotpassword" placeholder="New password" required="required" oninput="validatePassword()"/>
                                <input style="margin: 10px 0px 45px 0px" id="re-password" type="password" name="confirmPassword" placeholder="Confirm new password" required="required" oninput="validatePassword()"/>
                                <button type="submit">Continue</button>
                            </c:if>
                            <c:if test="${currentUser != null}"> 
                                <input type="hidden" name="type" value="1"/>
                                <input type="hidden" name="id" value="${currentUser.user_id}"/>
                                <input style="margin: 40px 0px 10px 0px" type="password" name="currrentUserPassword" placeholder="Current password" required="required" oninput="validatePassword()"/>
                                <span style="color: red; display: flex;margin-left: 30px;
                                      margin-top: -5px; justify-content: left;">${errorCurrentPassword}</span>
                                <input style="margin: 10px" id="password" type="password" name="passwordCurrentUser" placeholder="New password" required="required" oninput="validatePassword()"/>
                                <input style="margin: 10px 0px 45px 0px" id="re-password" type="password" placeholder="Confirm new password" required="required" oninput="validatePassword()"/>
                                <button type="submit">Continue</button>
                                <a href="profile" style="margin-left: 12px">Back to Profile</a>
                            </c:if>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- jquery-->
        <script src="templates/js/jquery.min.js"></script>
        <!-- Bootstrap js -->
        <script src="templates/js/bootstrap.min.js"></script>
        <!-- Validator js -->
        <script src="templates/js/validator.min.js"></script>
        <!-- Custom Js -->
        <script src="js/main.js"></script>
        <script>
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

