<%-- 
    Document   : home
    Created on : Oct 26, 2020, 4:41:12 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/mystyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
        <style>
            .box{
                width: 375px;
                margin: auto;  
                padding: 10px;  
                background: white;  
                border-radius: 15px;  
                float: left;
            }
        </style>
        <script>
            function setMinDate() {
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth() + 1; //January = 0;
                var yyyy = today.getFullYear();
                if (dd < 10) {
                    dd = '0' + dd;
                }
                if (mm < 10) {
                    mm = '0' + mm;
                }

                today = yyyy + '-' + mm + '-' + dd;
                document.getElementById("checkinDate").setAttribute("min", today);
                document.getElementById("checkoutDate").setAttribute("min", today);
            }
            function checkDate() {
                var dateForm = document.forms['searchRoomForm'];
                var startDate = new Date(dateForm['txtCheckinDate'].value);
                var endDate = new Date(dateForm['txtCheckoutDate'].value);

                if (startDate >= endDate) {
                    alert("Check-Out Date can not be occur before Check-In Date");
                    return false;
                }
            }
        </script>
    </head>
    <body class="bg" onload="setMinDate()">
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
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                            Search For Room
                        </a>
                    </li>
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
        <div class="collapse" id="collapseExample">
            <div class="box">
                <form action="SearchHotel" method="POST" name="searchRoomForm" onsubmit="return checkDate();">
                    <div style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"> 
                        Hotel Name: <input type="text" placeholder="Search Hotel Name" name="txtSearchName" style="width: 250px; display: inline;"/> </br>
                        Hotel Area: <select name="txtArea" style="width: 259px; display: inline;">
                            <c:forEach var="area" items="${requestScope.AREA}">
                                <option value="${area.areaID}">${area.areaName}</option>
                            </c:forEach>
                        </select> </br>
                        Check-In Date: <input type="date" name="txtCheckinDate" min="min" value="${param.txtCheckinDate}" required style="width: 228px;" id="checkinDate"/></br>
                        Check-Out Date: <input type="date" name="txtCheckoutDate" min="min" value="${param.txtCheckoutDate}" required style="width: 215px;" id="checkoutDate"/></br>
                        Amount of Room: <input type="number" placeholder="Amount of Room" name="txtAmount" style="width: 208px; display: inline;"/>
                        <input type="submit" value="Search Room" class="btn btn-block btn-primary" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"/>
                    </div>
                </form>
            </div>
        </div>
        <h1>Welcome To Online Hotel Booking System</h1>
        <p style="font-family: Tahoma; font-weight: bold; margin: auto; text-align: center; color: yellow;">${requestScope.WARN}</p>
        <table border="1" class="table table-striped table-dark">
            <thead>
                <tr>
                    <th style="text-align: center;">Hotel Info</th>
                    <th style="text-align: center;">View Detail</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty requestScope.HOTEL}" var="hotelList">
                    <c:forEach items="${requestScope.HOTEL}" var="hotel">
                        <tr>
                            <td>
                                <img src="${hotel.hotelImage}" style="width: 20%; height: 30%;"/>
                                <h2>${hotel.hotelName}</h2>
                                Booked: ${hotel.booked} - Available: ${hotel.available}</br>
                                Location: ${hotel.areaName}
                            </td>
                            <td>
                                <form action="ViewHotelDetails" method="POST">
                                    <div style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"> 
                                        <input type="hidden" value="${hotel.hotelID}" name="txtHotelID"/>
                                        <input type="hidden" value="${hotel.hotelName}" name="txtHotelName"/>
                                        <input type="hidden" value="${sessionScope.CHECKIN}" name="CheckIn"/>
                                        <input type="hidden" value="${sessionScope.CHECKOUT}" name="CheckOut"/>
                                        <input type="submit" value="View Details" class="btn btn-block btn-info" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"/>
                                    </div>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${!hotelList}">
                    <tr style="color: red; text-align: center; font-family: Tahoma; font-weight: bold;">
                        <td colspan="2">No Suitable Hotel Found</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </body>
</html>
