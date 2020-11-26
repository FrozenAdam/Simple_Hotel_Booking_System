<%-- 
    Document   : bookingdetail
    Created on : Nov 1, 2020, 7:38:55 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Detail Page</title>
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
    </head>
    <body class="bg">
        <c:if test="${sessionScope.USER.role != 1}">
            <c:redirect url="GetHotel"/>
        </c:if>
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
                    <li class="nav-item">
                        <a class="nav-link" href="ViewBooking">Booking History</a>
                    </li>
                </ul>
                <ul class="nav navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#"><span class="fas fa-user"></span>Welcome, ${sessionScope.USER.fullname}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Logout"><span class="fas fa-sign-in-alt"></span> Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
        <h1>Booking History</h1>
        <div class="box">
            <p style="font-family: Tahoma; font-weight: bold; text-align: center;">Search Booking</p>
            <form action="SearchBookingHistory" method="POST">
                BookingID: <input type="text" name="searchBookingID" placeholder="Enter your Booking ID here" style="width: 350px;"/>
                <div style="float: right;">
                    Booked Date: <input type="date" name="searchBookedDate"/>
                </div>
                <input type="submit" value="Search" class="btn btn-block btn-primary" style="display: inline; float: inside; font-family: Tahoma; font-weight: bold;"/>
            </form>
        </div>
        <p style="font-family: Tahoma; font-weight: bold; text-align: center; color: yellow;">${requestScope.WARN}</p>
        <table border="1" class="table table-striped table-dark">
            <thead>
                <tr>
                    <th style="text-align: center;">BookingID</th>
                    <th style="text-align: center;">Booked Date</th>
                    <th style="text-align: center;">Total</th>
                    <th style="text-align: center;">View Detail</th>
                    <th style="text-align: center;">Delete Detail</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty requestScope.LIST}" var="bookingList">
                    <c:forEach items="${requestScope.LIST}" var="list">
                        <tr>
                            <td>${list.bookingID}</td>                          
                            <td>${list.bookedDate}</td>
                            <td>$${list.total}</td>
                            <td>
                                <form action="GetBookingDetail" method="POST">
                                    <div style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"> 
                                        <input type="hidden" value="${list.bookingID}" name="txtBookingID"/>
                                        <input type="submit" value="View Details" class="btn btn-block btn-info" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"/>
                                    </div>
                                </form>
                            </td>
                            <td>
                                <form action="DeleteBooking" method="POST">
                                    <div style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"> 
                                        <input type="hidden" value="${list.bookingID}" name="txtRemoveID"/>
                                        <input type="submit" value="Delete" class="btn btn-block btn-danger" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;" onclick="return confirm('Are you sure you want to delete this ${list.bookingID} ?')"/>
                                    </div>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${!bookingList}">
                    <tr style="color: red; text-align: center; font-family: Tahoma; font-weight: bold;">
                        <td colspan="5">You Haven't Booking Anything</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        <c:if test="${not empty requestScope.DETAIL}">
            <h1>Booking Details</h1>
            <table border="1" class="table table-striped table-light">
                <thead>
                    <tr>
                        <th style="text-align: center;">Hotel Name</th>
                        <th style="text-align: center;">Room Type</th>
                        <th style="text-align: center;">Check In</th>
                        <th style="text-align: center;">Check Out</th>
                        <th style="text-align: center;">Price</th>
                        <th style="text-align: center;">Quantity</th>
                        <th style="text-align: center;">Total</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.DETAIL}" var="detail">
                        <tr>
                            <td>${detail.hotelName}</td>                          
                            <td>${detail.typeName}</td>
                            <td>${detail.inDate}</td>
                            <td>${detail.outDate}</td>
                            <td>$${detail.price}</td>
                            <td>${detail.quantity}</td>
                            <td>$${detail.total}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>
