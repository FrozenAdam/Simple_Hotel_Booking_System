<%-- 
    Document   : cart
    Created on : Oct 17, 2020, 3:16:53 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/mystyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    </head>
    <body class="bg">
        <nav class="navbar navbar-expand-md bg-dark navbar-dark sticky-top">
            <a class="navbar-brand" href="#">Hotel Booking System</a>
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navb" aria-expanded="true">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div id="navb" class="navbar-collapse collapse hide">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="GetHotel">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="cart.jsp">Booked Room  - (Total: $${sessionScope.CART.total})</a>
                    </li>
                    <c:if test="${sessionScope.USER.role == 1}">
                        <li class="nav-item">
                            <a class="nav-link" href="ViewBooking">Booking History</a>
                        </li>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav ml-auto">
                    <c:if test="${empty sessionScope.USER}" var="checkLogin">
                        <li class="nav-item">
                            <a class="nav-link" href="signup.jsp"><span class="fas fa-user"></span> Sign Up</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="signin.jsp"><span class="fas fa-sign-in-alt"></span> Login</a>
                        </li>
                    </c:if>
                    <c:if test="${!checkLogin}">
                        <li class="nav-item">
                            <a class="nav-link" href="#"><span class="fas fa-user"></span>Welcome, ${sessionScope.USER.fullname}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Logout"><span class="fas fa-sign-in-alt"></span> Logout</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </nav>
        <h1>Your Cart</h1>
        <c:if test="${not empty sessionScope.CART}" var="checkCart">
            <p style="font-family: Tahoma; font-weight: bold; text-align: center; color: yellow;">${requestScope.WARN}</p>
            <table border="1" class="table table-striped table-dark">
                <thead>
                    <tr>
                        <th style="text-align: center;">Hotel Name</th>
                        <th style="text-align: center;">Room Type</th>
                        <th style="text-align: center;">Check In Date</th>
                        <th style="text-align: center;">Check Out Date</th>
                        <th style="text-align: center;">Available Room</th>
                        <th style="text-align: center;">In Cart</th>
                        <th style="text-align: center;">Price</th>
                        <th style="text-align: center;">Total</th>
                        <th style="text-align: center;">Update</th>
                        <th style="text-align: center;">Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cart" items="${sessionScope.CART.cart}">
                        <tr>
                            <td>${cart.value.hotelName}</td>
                            <td>${cart.value.roomType}</td>
                            <td>${sessionScope.CHECKIN}</td>
                            <td>${sessionScope.CHECKOUT}</td>
                            <td>${cart.value.available}</td>

                    <form action="UpdateCart" method="POST">
                        <td><input type="number" value="${cart.value.cartQuantity}" min="1" name="txtCartQuantity"/></td>
                        <td>$${cart.value.price}</td>
                        <td>$${cart.value.price * cart.value.cartQuantity}</td>
                        <td>
                            <input type="hidden" name="txtRoomID" value="${cart.value.roomID}"/>
                            <input type="hidden" name="txtAvailable" value="${cart.value.available}"/>
                            <input type="submit" value="Update" class="btn btn-block btn-primary" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"/>
                        </td>
                    </form>
                    <td>
                        <form action="DeleteCart" method="POST">
                            <input type="hidden" name="txtRoomID" value="${cart.value.roomID}"/>
                            <input type="submit" value="Delete" class="btn btn-block btn-danger" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;" onclick="return confirm('Are you sure you want to delete this ${cart.value.roomType} ?')"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="6"></td>
                <td>Total:</td>
                <td>$${sessionScope.CART.total}</td>
                <td colspan="2">
                    <form action="VerificationCart" method="POST">
                        <input type="hidden" value="CONFIRM" name="txtConfirm"/>
                        <input type="submit" value="Confirmation Booking" class="btn btn-block btn-primary" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"/>
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="6"></td>
                <td style="text-align: center;">Enter Your Confirmation Code In Email Here</td>
            <form action="ConfirmCart" method="POST">
                <td><input type="text" placeholder="Your Code" name="txtCode" required></td>
                <td colspan="2">
                    <input type="hidden" value="${requestScope.CODE}" name="txtCheck"/>
                    <input type="submit" value="Complete Booking" class="btn btn-block btn-success" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"/>
                </td>
            </form>
        </tr>
    </tbody>
</table>
</c:if>
<c:if test="${!checkCart}">
    <p style="color: yellow; text-align: center; font-family: Tahoma; font-weight: bold;">Your Cart is Empty</p>
</c:if>
</body>
</html>
