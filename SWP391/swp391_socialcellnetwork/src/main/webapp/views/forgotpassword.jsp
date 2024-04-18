
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
        <title>SCN | Forget Password</title>
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.jpg">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
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
                        <h1>Reset your password</h1>
                        <p>Enter your email address and we will send you instructions to reset your password.</p>
                        <span style="color: red; font-size: 17px;">${errorEmail}</span>
                        <form action="forgotpassword" method="get">
                            <input type="email" name="email" placeholder="Email address" required value="${email}" /><br>
                            <button type="submit">Continue</button>
                        </form>
                        <a href="login">Back to Login</a>
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


    </body>



</html>

