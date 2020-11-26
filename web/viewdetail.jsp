<%-- 
    Document   : viewdetail
    Created on : Oct 30, 2020, 2:40:08 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hotel Detail Page</title>
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
        <h1>${requestScope.NAME}</h1>
        <p style="color: yellow; text-align: center; font-family: Tahoma; font-weight: bold;">${requestScope.WARN}</p>
        <table border="1" class="table table-striped table-dark">
            <thead>
                <tr>
                    <th colspan="4" style="text-align: center;">Room Info</th>
                    <th rowspan="2" style="text-align: center; vertical-align: middle;">Booking Room</th>
                </tr>
                <tr>
                    <th style="text-align: center;">Room Type</th>
                    <th style="text-align: center;">Quantity</th>
                    <th style="text-align: center;">Booked</th>
                    <th style="text-align: center;">Available</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.ROOM}" var="room">
                    <tr>
                        <td>${room.roomType}</td>
                        <td>${room.quantity}</td>
                        <td>${room.booked}</td>
                        <td>${room.available}</td>
                        <td>
                            <c:if test="${sessionScope.USER.role == 1}" var="check">
                                <form action="AddToCart" method="POST" style="font-family: Tahoma; font-weight: bold;">
                                    <input type="hidden" name="txtRoomID" value="${room.roomID}"/>
                                    <input type="hidden" name="txtRoomType" value="${room.roomType}"/>
                                    <input type="hidden" name="txtAvailable" value="${room.available}"/>
                                    <input type="hidden" name="txtIn" value="${sessionScope.CHECKIN}"/>
                                    <input type="hidden" name="txtOut" value="${sessionScope.CHECKOUT}"/>
                                    <input type="submit" class="btn btn-block btn-primary" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;" value="Book This Room"/>
                                </form>
                            </c:if>
                            <c:if test="${!check}">
                                <button class="btn btn-block btn-primary" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;" onclick="return alert('You Need To Login In Order To Book Room')">Book This Room</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
