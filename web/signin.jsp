<%-- 
    Document   : login
    Created on : Oct 4, 2020, 2:48:37 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign In Page</title>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/mystyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <style>
            #usernameBox, #passwordBox{
                width: 339px;
            }
            .loginBox{
                font-family: Tahoma;
                font-weight: bold;
            }
        </style>
    </head>
    <body class="bg">
        <nav class="navbar navbar-expand-md bg-dark navbar-dark sticky-top">
            <a class="navbar-brand" href="#">Hotel Booking System</a>
            <div id="navb" class="navbar-collapse collapse hide">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="GetHotel">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="cart.jsp">Booked Room  - (Total: $${sessionScope.CART.total})</a>
                    </li>
                </ul>
                <ul class="nav navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="signup.jsp"><span class="fas fa-user"></span> Sign Up</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="signin.jsp"><span class="fas fa-sign-in-alt"></span> Login</a>
                    </li>
                </ul>
            </div>
        </nav>
        <h1>Welcome To Online Hotel Booking System</h1>
        <div class="box">
            <form action="Login" method="POST" name="loginForm" onsubmit="return validateLogin()">
                <div class="loginBox"> 
                    <font style="color: red; font-family: Tahoma; font-weight: bold; text-align: center;">
                    ${requestScope.SUCCESS}
                    </font>
                    <font style="color: red; font-family: Tahoma; font-weight: bold; text-align: center;">
                    ${requestScope.ERRORNOTE}
                    </font>
                    <input type="email" name="txtUsername" placeholder="Email" value="${param.txtUsername}" id="usernameBox" required/>
                    <input type="password" name="txtPassword" placeholder="Password" id="passwordBox" required/></br>
                    <div class="g-recaptcha" data-sitekey="6LeR7tsZAAAAACt5Mi1UqgKq_6GuJRUN7q7INHUB"></div>
                    <a href="forget.jsp" style="float: right;">Forgot Your Password ?</a>
                </div>
                <input type="submit" value="Sign In" class="btn btn-block btn-primary" style="display: inline; float: inside; font-family: Tahoma; font-weight: bold;"/>
            </form>
        </div>
    </body>
</html>
