<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <title>TMChat | Socialize</title>
 <!-- Tell the browser to be responsive to screen width -->
 <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
 <!-- Font Awesome -->
 <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css">
 <!-- Ionicons -->
 <link rel="stylesheet" href="bower_components/Ionicons/css/ionicons.min.css">
 <!-- Theme style -->
 <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
 <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
 page. However, you can choose any other skin. Make sure you
 apply the skin class to the body tag so the changes take effect. -->
 <link rel="stylesheet" href="dist/css/skins/skin-blue.min.css">
 <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
 <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
 <!--[if lt IE 9]>

 <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
 <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
 <![endif]-->
 <!-- Google Font -->
 <link rel="stylesheet"
 href="https://fonts.googleapis.com/css?
family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS | skin-blue |
| | skin-black |
| | skin-purple |
| | skin-yellow |
| | skin-red |
| | skin-green |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed |
| | layout-boxed |
| | layout-top-nav |
| | sidebar-collapse |
| | sidebar-mini |
|---------------------------------------------------------|
-->
<style>
.dropdown-menu {
    width: 200px !important;
}


img {
  display: block;
  max-width: 15%;
  height: auto;
float: left;
}
</style>
<body class="hold-transition skin-blue sidebar-mini" onload="getNewConnections(${member.code})">
<div class="wrapper">
 <!-- Main Header -->
 <header class="main-header">
 <!-- Logo -->
 <a href="index2.html" class="logo">
 <!-- mini logo for sidebar mini 50x50 pixels -->
 <span class="logo-mini"><b>A</b>LT</span>
 <!-- logo for regular state and mobile devices -->
<script src='js/tmchat-service.js'></script>
<script src='js/tmchat.js'></script>
 <span class="logo-lg"><b>Era</b>Text</span>
 </a>
 <!-- Header Navbar -->
 <nav class="navbar navbar-static-top" role="navigation">
 <!-- Sidebar toggle button-->
 <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">

 <span class="sr-only">Toggle navigation</span>
 </a>
 <!-- Navbar Right Menu -->
 <div class="navbar-custom-menu">
 <ul class="nav navbar-nav">
 <!-- Messages: style can be found in dropdown.less-->
 <li class="dropdown messages-menu">
 <!-- Menu toggle button -->


  <div class="dropdown"> 
    <button class="btn btn-primary dropdown-toggle" type="button" id="messages" data-toggle="dropdown" onclick="javascript:getMessage(1)"><i class="fa fa-envelope-o"></i>
    <span class="caret"></span> <span class="label label-success">4</span>
</button>
 <ul class="dropdown-menu" id="drop">
 <li class="header">You have some messages</li>

<ul class="menu">
<!-- start message -->
 <a href="#">
<p><img class="img-responsive img-circle" src="default.jpg" width="32" height="32" alt="User profile picture"><h4><p1></p1>
 <small><i class="fa fa-clock-o"></i> 5 mins</small></h4></p>
 <!-- The message -->
 <p10></p10>
 </a>

<br><br>
 <!-- start message -->
 <a href="#">

<img class="img-responsive img-circle" src="default.jpg" width="32" height="32" alt="User profile picture"><h4><p2></p2>
 <small><i class="fa fa-clock-o"></i> 5 mins</small></h4></p>
 <!-- The message -->
 <p11></p11>
 </a>

<br><br>
 <a href="#">

<img class="img-responsive img-circle" src="default.jpg" width="32" height="32" alt="User profile picture"><h4><p2></p2>
 <small><i class="fa fa-clock-o"></i> 5 mins</small></h4></p>
 <!-- The message -->
 <p11></p11>
 </a>



</ul>
</ul>

</div>







 <!-- /.messages-menu -->
 <!-- Notifications Menu -->
 <li class="dropdown notifications-menu">
 <!-- Menu toggle button -->
 <a href="#" class="dropdown-toggle" data-toggle="dropdown" onclick="javascript:getNotification(${member.code})">
 <i class="fa fa-bell-o"></i>
 <span class="label label-warning">10</span>
 </a>
 <ul class="dropdown-menu">
 <li class="header">You have some friend requests</li>
 <li>
 <!-- Inner Menu: contains the notifications -->
 <ul class="menu">
 <li id="L0"><!-- start notification -->
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">&ensp;<p200 id="p200"></p200><br>
<button type="button" class="btn btn-primary" onclick="acceptFriendRequest('${member.username}','p200','L0')">Accept </button>  <button type="button" class="btn btn-danger" onclick="rejectRequest('${member.username}','p200','L0')">Delete</button>
 </li>
<br>
 <li id="L1"><!-- start notification -->
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">&ensp;<p201 id="p201"></p201><br>
<button type="button" class="btn btn-primary" onclick="acceptFriendRequest('${member.username}','p201','L1')">Accept </button>  <button type="button" class="btn btn-danger" onclick="rejectRequest('${member.username}','p201','L1')">Delete</button>
 </li>
<br>
 <li id="L2"><!-- start notification -->
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">&ensp;<p202 id="p202"></p202><br>
<button type="button" class="btn btn-primary" onclick="acceptFriendRequest('${member.username}','p202','L2')">Accept</button>  <button type="button" class="btn btn-danger" onclick="rejectRequest('${member.username}','p202','L2')">Delete</button>
 </li>
<br>
 <li id="L3"><!-- start notification -->
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture"> &ensp;<p203 id="p203"></p203><br>
<button type="button" class="btn btn-primary" onclick="acceptFriendRequest('${member.username}','p203','L3')">Accept </button>  <button type="button" class="btn btn-danger" onclick="rejectRequest('${member.username}','p203','L3')">Delete</button>
 </li>
<br>
 <li id="L4"><!-- start notification -->
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture"> &ensp;<p204 id="p204"></p204><br>
<button type="button" class="btn btn-primary" onclick="acceptFriendRequest('${member.username}','p204','L4')">Accept </button>  <button type="button" class="btn btn-danger" onclick="rejectRequest('${member.username}','p204','L4')">Delete</button>
 </li>
<br>
 <li id="L5"><!-- start notification -->
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">&ensp;<p205 id="p205"></p205><br>
<button type="button" class="btn btn-primary" onclick="acceptFriendRequest('${member.username}','p205','L5')">Accept </button>  <button type="button" class="btn btn-danger" onclick="rejectRequest('${member.username}','p205','L5')">Delete</button>
 </li>
 <!-- end notification -->
 </ul>
 </li>
 <li class="footer"><a href="#">View all</a></li>
 </ul>
 </li>






 <!-- Tasks Menu -->
 <li class="dropdown tasks-menu">
 <!-- Menu Toggle Button -->
 <a href="#" class="dropdown-toggle" data-toggle="dropdown">
 <i class="fa fa-flag-o"></i>
 <span class="label label-danger">9</span>
 </a>
 <ul class="dropdown-menu">
 <li class="header">You have 9 tasks</li>
 <li>
 <!-- Inner menu: contains the tasks -->
 <ul class="menu">
 <li><!-- Task item -->
 <a href="#">
 <!-- Task title and progress text -->
 <h3>
 Design some buttons
 <small class="pull-right">20%</small>
 </h3>
 <!-- The progress bar -->
 <div class="progress xs">
 <!-- Change the css width attribute to simulate progress -->
 <div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar"
 aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
 <span class="sr-only">20% Complete</span>
 </div>
 </div>
 </a>
 </li>
 <!-- end task item -->

 </ul>
 </li>
 <li class="footer">
 <a href="#">View all tasks</a>
 </li>
 </ul>
 </li>
 <!-- User Account Menu -->
 <li class="dropdown user user-menu">
 <!-- Menu Toggle Button -->
 <a href="#" class="dropdown-toggle" data-toggle="dropdown">
 <!-- The user image in the navbar-->
 <img src="userAvatar" class="user-image" alt="User Image">
 <!-- hidden-xs hides the username on small devices so only the image appears. -->
 <span class="hidden-xs">${member.username}</span>
 </a>
 <ul class="dropdown-menu">
 <!-- The user image in the menu -->
 <li class="user-header">
 <img src="userAvatar" class="img-circle" alt="User Image">
 <p>
 ${member.username}
 <small>Member since January 2020</small>
 </p>
 </li>
 <!-- Menu Body -->
 <li class="user-body">
 <div class="row">

 </div>
 <!-- /.row -->
 </li>
 <!-- Menu Footer-->
 <li class="user-footer">
 <div class="pull-left">
 <a class="btn btn-default btn-flat" onclick="displayProfile()">Profile</a>
 </div>
 <div class="pull-right">
 <a href="/tmchat/index.jsp" class="btn btn-default btn-flat" onclick="signOut()">Sign Out</a>
 </div>
 </li>
 </ul>
 </li>
 <!-- Control Sidebar Toggle Button -->
 <li>
 <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
 </li>
 </ul>
 </div>
 </nav>
 </header>
 <!-- Left side column. contains the logo and sidebar -->
 <aside class="main-sidebar">
 <!-- sidebar: style can be found in sidebar.less -->
 <section class="sidebar">
 <!-- Sidebar user panel (optional) -->
 <div class="user-panel">
 <div class="pull-left image">
 <img src="userAvatar" class="img-circle" alt="User Image">
 </div>
 <div class="pull-left info">
 <p>${member.username}</p>
 <!-- Status -->
 <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
 </div>
 </div>
 <!-- search form (Optional) -->
 <form action="#" method="get" class="sidebar-form">
 <div class="input-group">
 <input type="text" name="q" class="form-control" placeholder="Search...">
 <span class="input-group-btn">
 <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fasearch"></i>
 </button>
 </span>
 </div>
 </form>
 <!-- /.search form -->
 <!-- Sidebar Menu -->
 <ul class="sidebar-menu" data-widget="tree">
 <li class="header">FRIENDS</li>
 <!-- Optionally, you can add icons to the links -->

<button type="button" class="btn btn-primary btn-block btn-flat" onclick='FriendList(${member.code})'>FRIENDS</button>
<font size="5">
<table width="50" id='mytbl' align="center"></table>
</font>

 </ul>
 <!-- /.sidebar-menu -->
 </section>
 <!-- /.sidebar -->
 </aside>
 <!-- Content Wrapper. Contains page content -->
 <div class="content-wrapper">


 <!-- Main content -->
 <section class="content container-fluid">
 <!--------------------------
 | Your Page Content Here |
 -------------------------->








<div>
<big><big><big><big><big><big><big><big><big><b>WELCOME TO MY EraText</b></big></big></big></big></big></big></big></big></big>
</div>










<style>

#addForm:target {
border-collapse: separate;
  border-spacing: 0 15px;
}

</style>



  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->


    <!-- Main content -->
    <section class="content">

      <div class="row">
        <div class="col-md-6" id="profile_update">
    <section class="content-header">
      <h1>
        User Profile
      </h1>
    </section>
          <!-- Profile Image -->
          <div class="box box-primary">
            <div class="box-body box-profile">
              <img class="profile-user-img img-responsive img-circle" src="userAvatar" alt="User profile picture"><br><br>

              <h3 class="profile-username text-center">${member.username}</h3>

              <p class="text-muted text-center">Software Engineer</p>
<br><br>
              <ul class="list-group list-group-unbordered">
               <li class="list-group-item">
                 <a href="#addForm" onclick="showDetails()"><b>Update Profile</b></a>



<table id="addForm">
    <tr>
        <td class="label" style="font-size:20px;color:black">Name:</td>
        <td>
            <input type="text" id="name"/>
        </td>
    </tr><br>
    <tr>
        <td class="label" style="font-size:20px;color:black">Username:</td>
        <td>
            <input type="text" id="username"/>
        </td>
    </tr>
 <tr>
        <td class="label" style="font-size:20px;color:black">Old Password</td>
        <td>
            <input type="text" id="oldPassword"/>
        </td>
    </tr>
 <tr>
        <td class="label" style="font-size:20px;color:black">New Password</td>
        <td>
            <input type="text" id="newPassword"/>
        </td>
    </tr>

 <tr>
        <td class="label" style="font-size:30px;color:black"><a href="#" class="btn btn-primary btn-block" onclick="profileUpdate()"><b>Submit</b></a></td>
 
    </tr>
   
</table>

                </li>

                <li class="list-group-item">
                  <a onclick='return FriendListInProfile("${member.code}")'><b>Friends</b> </a>
<div>
<font size="5">
<table width="50" id="mytbl1" align="center"></table>
</font>
</div>

                </li>
              </ul>

              <button type="button" class="btn btn-danger btn-block" onclick="closeAccTable()"><b>Delete Account</b></button>
<table id="deleteAccForm">
    <tr>
        <td class="label" style="font-size:20px;color:black">Password: </td>
        <td>
            <input type="text" id="pwd1"/>
        </td>
    </tr><br>
 <tr>
        <td class="label" style="font-size:30px;color:black"><a href="index.jsp" class="btn btn-danger btn-block" onclick="deleteAccount('${member.username}')"><b>Close Account</b></a></td>
 
    </tr>
   
</table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->

          <!-- About Me Box -->
          <div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">About Me</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <strong><i class="fa fa-book margin-r-5"></i> Education</strong>

              <p class="text-muted">
                Btech in Computer Science Engineering from SVVV INDORE(M.P.)
              </p>

              <hr>

              <strong><i class="fa fa-map-marker margin-r-5"></i> Location</strong>

              <p class="text-muted">Ujjain,INDIA</p>

              <hr>

              <strong><i class="fa fa-pencil margin-r-5"></i> Skills</strong>

              <p>
                <span class="label label-danger">UI Design</span>
                <span class="label label-success">Coding</span>
                <span class="label label-info">Javascript</span>
                <span class="label label-warning">PHP</span>
                <span class="label label-primary">Node.js</span>
              </p>
             
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->












<!--



<script src="bower_components/jquery/dist/jquery.min.js"></script>



<script src='js/chat.js'></script>
<link rel="stylesheet" type="text/css" href="css/tmchat.css">

        <div class="col-sm-3 col-sm-offset-4 frame">
            <ul></ul>
            <div>
                <div class="msj-rta macro">                        
                    <div class="text text-r" style="background:whitesmoke !important">
                        <input class="mytext" placeholder="Type a message"/>
                    </div> 

                </div>
                <div style="padding:10px;">
                    <span class="glyphicon glyphicon-share-alt"></span>
                </div>                
            </div>
        </div>       


-->













<style>
    .bs-example{
        margin: 50px;   
max-width: 500px;
float: right;  

    }

.my-custom-scrollbar {
  position: relative;
  width: 400px;
  height: 500px;
  overflow: auto;
}

</style>

<div class="bs-example my-custom-scrollbar my-custom-scrollbar-primary" id="newConn">    
    <div class="list-group" >
        <a href="#" class="list-group-item list-group-item-action active">    
          <i style='font-size:24px' class='fa fa-user'>&#xf500;</i>New Connections
    </a>
        <a href="#" class="list-group-item list-group-item-action" id="a0">
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
 <b><p100 id="p100"></p100><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p100','a0')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a0')">Cancel</button>
        </a>
        <a href="#" class="list-group-item list-group-item-action" id="a1">
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
         <p101></p101><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p101','a1')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a1')">Cancel</button>
        </a>
        <a href="#" class="list-group-item list-group-item-action" id="a2">
       <img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
<p102></p102><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p102','a2')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a2')">Cancel</button>
        </a>
 <a href="#" class="list-group-item list-group-item-action" id="a3">
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
<p103></p103><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p103','a3')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a3')">Cancel</button>
        </a>
 <a href="#" class="list-group-item list-group-item-action" id="a4">
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
<p104></p104><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p104','a4')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a4')">Cancel</button>
        </a>
  </a>
 <a href="#" class="list-group-item list-group-item-action" id="a5">
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
<p105></p105><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p105','a5')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a5')">Cancel</button>
        </a>

 <a href="#" class="list-group-item list-group-item-action" id="a6">
  <img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
<p106></p106><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p106','a6')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a6')">Cancel</button>
        </a>
 <a href="#" class="list-group-item list-group-item-action" id="a7">
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
<p107></p107><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p107','a7')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a7')">Cancel</button>
        </a>
 <a href="#" class="list-group-item list-group-item-action" id="a8">
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
<p108></p108><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p108','a8')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a8')">Cancel</button>
        </a>
 <a href="#" class="list-group-item list-group-item-action" id="a9">
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
<p109></p109><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p109','a9')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a9')">Cancel</button>
        </a>
 <a href="#" class="list-group-item list-group-item-action" id="a10">
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
<p110></p110><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p110','a10')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a10')">Cancel</button>
        </a>
 <a href="#" class="list-group-item list-group-item-action" id="a11">
<img class="img-responsive img-circle" src="default.jpg" width="42" height="42" alt="User profile picture">
</i><p111></p111><span class="badge badge-pill badge-primary pull-right"></span><br>
<button type="button" class="btn btn-primary" onclick="addFriendRequest('${member.username}','p111','a11')">Add Friend</button>  <button type="button" class="btn btn-warning" onclick="cancelRequest('a11')">Cancel</button>
        </a>
</b>
    </div>
</div>







 </section>
 <!-- /.content -->
 </div>
 <!-- /.content-wrapper -->
 <!-- Main Footer -->
 <footer class="main-footer">
 <!-- To the right -->
 <div class="pull-right hidden-xs">
 Anything you want
 </div>
 <!-- Default to the left -->
 <strong>Copyright &copy; 2016 <a href="#">Company</a>.</strong> All rights reserved.
 </footer>

 <!-- Control Sidebar -->
 <aside class="control-sidebar control-sidebar-dark">
 <!-- Create the tabs -->
 <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
 <li class="active"><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fahome"></i></a></li>
 <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
 </ul>
 <!-- Tab panes -->
 <div class="tab-content">
 <!-- Home tab content -->
 <div class="tab-pane active" id="control-sidebar-home-tab">
 <h3 class="control-sidebar-heading">Recent Activity</h3>
 <ul class="control-sidebar-menu">
 <li>
 <a href="javascript:;">
 <i class="menu-icon fa fa-birthday-cake bg-red"></i>
 <div class="menu-info">
 <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>
 <p>Will be 23 on April 24th</p>
 </div>
 </a>
 </li>
 </ul>
 <!-- /.control-sidebar-menu -->
 <h3 class="control-sidebar-heading">Tasks Progress</h3>
 <ul class="control-sidebar-menu">
 <li>
 <a href="javascript:;">
 <h4 class="control-sidebar-subheading">
 Custom Template Design
 <span class="pull-right-container">
 <span class="label label-danger pull-right">70%</span>
 </span>
 </h4>
 <div class="progress progress-xxs">
 <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
 </div>
 </a>
 </li>
 </ul>
 <!-- /.control-sidebar-menu -->

 </div>
 <!-- /.tab-pane -->
 <!-- Stats tab content -->
 <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
 <!-- /.tab-pane -->
 <!-- Settings tab content -->
 <div class="tab-pane" id="control-sidebar-settings-tab">
 <form method="post">
 <h3 class="control-sidebar-heading">General Settings</h3>
 <div class="form-group">
 <label class="control-sidebar-subheading">
 Report panel usage
 <input type="checkbox" class="pull-right" checked>
 </label>
 <p>
 Some information about this general settings option
 </p>
 </div>
 <!-- /.form-group -->
 </form>
 </div>
 <!-- /.tab-pane -->
 </div>
 </aside>
 <!-- /.control-sidebar -->
 <!-- Add the sidebar's background. This div must be placed
 immediately after the control sidebar -->
 <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->
<!-- REQUIRED JS SCRIPTS -->
<!-- jQuery 3 -->
<script src="bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/adminlte.min.js"></script>
<!-- Optionally, you can add Slimscroll and FastClick plugins.
 Both of these plugins are recommended to enhance the
 user experience. -->

</body>
</html>