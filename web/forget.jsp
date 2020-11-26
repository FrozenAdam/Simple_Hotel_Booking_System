<%-- 
    Document   : forget
    Created on : Nov 1, 2020, 11:04:29 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forget Password Page</title>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/mystyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
        <style>
            .box{
                width: 900px;
            }
        </style>
        <script>
            function validationPassword() {
                var x = document.forms["resetForm"]["txtPassword"].value;
                var y = document.forms["resetForm"]["confirmPassword"].value;
                if (x !== y) {
                    alert("Incorrect Password and Re-Password");
                    return false;
                }
            }
        </script>
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
        <h1>Reset Your Password</h1>
        <div class="box">
            <c:if test="${empty requestScope.RESET}" var="reset">
                <p style="font-family: Tahoma; font-weight: bold;">Please enter your the email address you used to set up your Hotel Booking System Account.</p>
                <form action="VerificationReset" method="POST">
                    <input type="email" name="txtUsername" placeholder="Email" value="${param.txtUsername}" required style="width: 740px;"/>
                    <input type="hidden" name="txtReset" value="RESET"/>
                    <input type="submit" value="Submit" class="btn btn-block btn-primary" style="display: inline; float: inside; font-family: Tahoma; font-weight: bold;"/>
                </form>
            </c:if>
            <c:if test="${!reset}">
                <form action="ResetPassword" method="POST" name="resetForm" onsubmit="return validationPassword()">
                    <p style="color: red;font-family: Tahoma; font-weight: bold;">${requestScope.WARN}</p>
                    <input type="text" name="txtReset" placeholder="Enter Your Code From Email Here" required style="width: 740px;"/>
                    <input type="password" name="txtPassword" placeholder="Password" style="width: 740px;" required/>
                    <input type="password" name="confirmPassword"  placeholder="Re-password" style="width: 740px;" required/>
                    <input type="hidden" name="txtUsername" value="${requestScope.USERNAME}"/>
                    <input type="hidden" name="txtResetCheck" value="${requestScope.RESET}"/>
                    <input type="submit" value="Submit" class="btn btn-block btn-primary" style="display: inline; float: inside; font-family: Tahoma; font-weight: bold;"/>
                </form>
            </c:if>
        </div>
    </body>
</html>
