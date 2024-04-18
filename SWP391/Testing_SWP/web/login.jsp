<%-- 
    Document   : login
    Created on : Jan 14, 2024, 11:46:26 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="">


    <!-- Mirrored from affixtheme.com/html/xmee/demo/login-22.html by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 08 Jan 2024 17:00:21 GMT -->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>SCN | Login and Register</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <!-- Fontawesome CSS -->
        <link rel="stylesheet" href="css/fontawesome-all.min.css">
        <!-- Flaticon CSS -->
        <link rel="stylesheet" href="font/flaticon.css">
        <!-- Star Animation CSS -->
        <link rel="stylesheet" href="css/star-animation.css">
        <!-- Google Web Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&amp;display=swap" rel="stylesheet">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="css/style.css">
    </head>

    <body>
        <!--[if lt IE 8]>
        <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
        <div id="preloader" class="preloader">
            <div class='inner'>
                <div class='line1'></div>
                <div class='line2'></div>
                <div class='line3'></div>
            </div>
        </div>
        <section class="fxt-template-animation fxt-template-layout22" data-bg-image="img/bg22-l.jpg">
            <!-- Star Animation Start Here -->
            <div class="star-animation">
                <div id="stars1"></div>
                <div id="stars2"></div>
                <div id="stars3"></div>
                <div id="stars4"></div>
                <div id="stars5"></div>
            </div>
            <!-- Star Animation End Here -->
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-6 col-12 fxt-none-991">
                        <div class="fxt-header">

                            <div class="fxt-transformY-50 fxt-transition-delay-2">
                                <h1>Welcome To Social Cell Networking</h1>
                            </div>
                            <div class="fxt-transformY-50 fxt-transition-delay-3">
                                <p>Grursus mal suada faci lisis Lorem ipsum dolarorit more ametion consectetur elit. Vesti at bulum nec odio aea the dumm ipsumm ipsum that dolocons rsus mal suada and fadolorit to the dummy consectetur elit the Lorem Ipsum genera.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-12 fxt-bg-color">
                        <div class="fxt-content">
                            <div class="fxt-form">
                                <h2>Login</h2>
                                <p>Login into your pages account</p>
                                <h5 style="color: red">${error}</h5>
                                <h5 style="color: green">${ms}</h5>
                                <form action="login" method="POST">
                                    <div class="form-group">
                                        <label for="user" class="input-label">User Name</label>
                                        <input type="text" id="user-name" class="form-control" name="username" placeholder="username" required="required">
                                    </div>
                                    <div class="form-group">
                                        <label for="password" class="input-label">Password</label>
                                        <input id="password" type="password" class="form-control" name="password" placeholder="********" required="required">
                                        <i toggle="#password" class="fa fa-fw fa-eye toggle-password field-icon"></i>
                                    </div>
                                    <div class="form-group">
                                        <div class="fxt-checkbox-area">
                                            <div class="checkbox">
                                                <input id="checkbox1" type="checkbox">
                                                <label for="checkbox1">Keep me logged in</label>
                                            </div>
                                            <a href="forgot-password.html" class="switcher-text">Forgot Password</a>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="fxt-btn-fill">Log in</button>
                                    </div>
                                </form>
                            </div>
                            <div class="fxt-style-line">
                                <h3>Or Login With Email</h3>
                            </div>
                            <ul class="fxt-socials">

                                <li class="fxt-twitter"><a href="#" title="twitter">Facebook</a></li>
                                <li class="fxt-google"><a href="#" title="google">Google +</a></li>

                            </ul>
                            <div class="fxt-footer">
                                <p>Don't have an account?<a href="register.jsp" class="switcher-text2 inline-text">Register</a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- jquery-->
        <script src="js/jquery.min.js"></script>
        <!-- Bootstrap js -->
        <script src="js/bootstrap.min.js"></script>
        <!-- Imagesloaded js -->
        <script src="js/imagesloaded.pkgd.min.js"></script>
        <!-- Validator js -->
        <script src="js/validator.min.js"></script>
        <!-- Custom Js -->
        <script src="js/main.js"></script>

    </body>


    <!-- Mirrored from affixtheme.com/html/xmee/demo/login-22.html by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 08 Jan 2024 17:00:25 GMT -->
</html>
