<%-- 
    Document   : signup
    Created on : Oct 26, 2020, 4:41:20 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up Page</title>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/mystyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
        <style>
            .singUpBox{
                font-family: Tahoma;
                font-weight: bold;
            }
            .usernameReg, .passwordReg, .confirmReg, .fullnameReg, .phoneReg, .addressReg{
                width: 339px;
            }
        </style>
        <script>
            function validationSignUp() {
                var x = document.forms["signupForm"]["txtPassword"].value;
                var y = document.forms["signupForm"]["confirmPassword"].value;
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
        <h1>Sign Up</h1>
        <div class="box">
            <form action="Register" method="POST" name="signupForm" onsubmit="return validationSignUp()">
                <font style="color: red; font-family: Tahoma; font-weight: bold; text-align: center;">
                ${requestScope.ERROR}
                </font>
                <div class="singUpBox"> 
                    <input type="email" name="txtUsername" placeholder="Email" value="${param.txtUsername}" class="usernameReg" pattern="([a-zA-Z0-9]{3,30})(@)([a-zA-Z0-9-]{3,30})([.])([a-zA-Z0-9]{2,5})([.][vn]{2})?" required/></br>
                    <input type="password" name="txtPassword" placeholder="Password" class="passwordReg" required/></br>
                    <input type="password" name="confirmPassword"  placeholder="Re-password" class="confirmReg" required/></br>
                    <input type="text" name="txtFullname" placeholder="Fullname" value="${param.txtFullname}" class="fullnameReg" maxlength="50" required/></br>
                    <input type="text" name="txtPhone" placeholder="Phone Number(10 digits)" value="${param.txtPhone}" class="phoneReg" pattern="[0-9]{10}" minlength="10" maxlength="10" required/></br>
                    <input type="text" name="txtAddress" placeholder="Address" value="${param.txtAddress}" class="addressReg" maxlength="50" required/></br>
                    <input type="submit" value="Sign Up" class="btn btn-block btn-primary" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"/>
                </div>
            </form>
        </div>
    </body>
</html>
