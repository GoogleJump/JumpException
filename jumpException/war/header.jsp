<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->


<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page
  import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name=description content="">
    <meta name=viewport content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="styles/main.css">
    <link rel="stylesheet" href="styles/css/grayscale.css">
    <title>Hello App Engine</title>
  </head>

  <body id="page-top" data-spy="scroll" data-target=".navbar-custom">
     <%// $section = trim($_SERVER['PATH_INFO'], '/');%>
     <body id="page-top" data-spy="scroll" data-target=".navbar-custom">
         <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
             <div class="container">
                 <div class="navbar-header page-scroll">
                     <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse">
                         <i class="fa fa-bars"></i>
                     </button>
                     <a class="navbar-brand" href="#page-top">
                         <i class="fa fa-play-circle"></i>  <span class="light">Alex</span> Cory
                     </a>
                 </div>
                 <!-- Default Nav Links -->
                 <div class="collapse navbar-collapse navbar-left navbar-main-collapse">
                     <ul class="nav navbar-nav">
                         <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
                         <li class="hidden">
                             <a href="#page-top"></a>
                         </li>
                         <li class="home page-scroll">
                             <a href="#Shub">Shub</a>
                         </li>
                         <li class="blog page-scroll <%

                              %>">
                             <a href="#SignUp">Sign Up</a>
                         </li>
                         <!--CHECK HERE TO SEE IF USER LOGGED IN -->
                         <% //if (!$u->isRegistered()):  %>
                           <!-- <li class="register page-scroll <% //echo ($section == 'register') ? 'active' : ''; %>">
                               <a href="register/">Register</a>
                           </li> -->
                         <% //endif; %>
                     </ul>
                 </div>
                 <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
                     <ul class="nav navbar-nav pull-right">
                         <li class="dropdown">
                             <% // if the user HAS SIGNED IN %>
                             <%// if ($u->isRegistered()): %>
                             <!-- <a href="#" class="dropdown-toggle" data-toggle="dropdown"><%// echo $u->getUserName(); %><b class="caret"></b></a>
                             <ul class="dropdown-menu">
                                 <li class="logout"><a href="">Logout</a></li>
                                 <li class="divider"></li> -->
                                 <% // if the user has SINED IN  <AND> they are an ADMIN %>
                                 <% // if ($u->isAdmin()): %>
                                 <!-- <li class="manageposts"><a href="" tabindex="-1">Manage Posts</a></li>
                                     <li class="divider"></li>
                                     <li class="manageposts"><a href="" tabindex="-1">Manage Categories</a></li>
                                     <li class="divider"></li>
                                     <li><a href="">Manage Users</a>
                                     <li class="divider"></li> -->
                                 <%// endif; %>
                                 <!-- <li><a href="">Settings</a></li> -->
                             <%// // Otherwise, if nobody is signed in, show a Login button %>
                             <%// else: // if not registered %>
                                 <!-- The drop down menu -->
                                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">Login<b class="caret"></b></a>
                                 <div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
                                     <form action="" method="post" onsubmit="editor.post()">
                                         <input placeholder="Username" type="text" class="span6" name="user_email" value="<%// echo $email; %>">
                                         <input placeholder="*******" type="password" class="span6" name="user_password" value="<%// echo $password; %>">
                                         <br/>
                                         <input type="hidden" name="uID" value="<%// echo $uID; %>"/>
                                         <button id="submit" type="submit" class="btn btn-primary btn-block" >Login</button>
                                     </form>
                                 </div>
                             <%// endif; %>
                             </ul>
                         </li>
                     </ul>
                 </div>
                 <!-- /.navbar-collapse -->
             </div>
             <!-- /.container -->
         </nav>