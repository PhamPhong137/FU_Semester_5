<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <title>TMChat | Socialize</title>
 <!-- Tell the browser to be responsive to screen width -->

 <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <!-- Bootstrap 3.3.7 -->
 <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
 <!-- Font Awesome -->
 <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css">
 <!-- Ionicons -->
 <link rel="stylesheet" href="bower_components/Ionicons/css/ionicons.min.css">
 <!-- Theme style -->
 <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
 <!-- iCheck -->
 <link rel="stylesheet" href="plugins/iCheck/square/blue.css">
 <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
 <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
 <!--[if lt IE 9]>
 <script src="external/html5shiv/html5shiv.min.js" ></script>
 <script src="external/respond/respond.min.js"></script>
 <![endif]-->
 <!-- Google Font -->
 <link rel="stylesheet" href="external/googleapisfonts/fonts.css">



<meta name="google-signin-client_id" content="1010071565133-jcl9rglke1q6c13vuoem7h1i3a5rd8vc.apps.googleusercontent.com">
  <script src="https://apis.google.com/js/platform.js" async defer></script>


</head>
<body class="hold-transition login-page">
<div class="login-box">
 <div class="login-logo">
 <b>Era</b>Text
 </div>
 <!-- /.login-logo -->
 <div class="login-box-body">
 <p class="login-box-msg">Sign in to start your session</p>
<p id="loginErrorSection" class="alert alert-danger hidden" role="alert">
Invalid username / password
</p>
 <div id="usernameGroup" class="form-group has-feedback">
 <input id='username' type="text" class="form-control" placeholder="Username">
 <span class="glyphicon glyphicon-user form-control-feedback"></span>
 </div>
 <div id='passwordGroup' class="form-group has-feedback">
 <input id='password' type="password" class="form-control" placeholder="Password">
 <span class="glyphicon glyphicon-lock form-control-feedback"></span>
 </div>
 <div class="row">
 <div class="col-xs-8">
 <div class="checkbox icheck">
 <label>
 <input id='rememberMe' type="checkbox"> Remember Me
 </label>
 </div>
 </div>
 <!-- /.col -->
 <div class="col-xs-4">
 <button type="button" class="btn btn-primary btn-block btn-flat" onclick='login()'>Sign
In</button>
 </div>
 <!-- /.col -->
 </div>
 <a href="ResetPassword.jsp">I forgot my password</a><br>
 <a href="Register.jsp" class="text-center">Create Account</a>
<div class="social-auth-links text-center">
      <p>- OR -</p>

      <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign up using
        Facebook</a>
      <center><a href="#" class="btn btn-block g-signin2" data-onsuccess="onSignIn1" data-theme="dark" id="google"></a></center>
    </div>
 </div>
 <!-- /.login-box-body -->
</div>
<!-- /.login-box -->


 
<!-- jQuery 3 -->
<script src="bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="plugins/iCheck/icheck.min.js"></script>
<script>
 $(function () {
 $('input').iCheck({
 checkboxClass: 'icheckbox_square-blue',
 radioClass: 'iradio_square-blue',
 increaseArea: '20%' /* optional */
 });
 });
</script>
<script src='js/tmchat-service.js'></script>
<script src='js/tmchat.js'></script>
</body>
</html>