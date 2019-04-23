<%-- 
    Document   : user-navigation
    Created on : Feb 25, 2019, 8:14:31 PM
    Author     : annachristofaris
--%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.util.*" %>
<%
    Boolean signedIn = false;
    if (session.getAttribute("userCP") != null) {
        signedIn = true;
    }
%>

<header>

    <div class="row justify-content-between topnav">
        <div class="col-4">
            <img src="./Logo.png" class="logo" alt="logo">
        </div>

        <div class="col-4">
            <ul>

                <!--check current session to determine whether to display "sign in" or "sign out"-->
                <% if (signedIn == true) {
                %>
                <li> 
                    <a href="ProfileController?action=signout">Sign Out</a>
                </li>
                <% } else {
                %>
                 <li><a href="login.jsp">Sign In</a> <li>
                
                <% }
                %>

                <!-- link to the "my items" page -->
                <li><a href="ProfileController?action=viewMyCorner">My Corner</a></li>
                <li><a href="#" class="disabled">Cart</a></li>
            </ul>
            <% if (signedIn == true) {
            %>
            <p class="username">Hello, ${theUser.firstName}!   </p>
            <% } else {
            %>
            <p class="username">Hello, guest!</p>
            <% }
            %>

        </div>
    </div>

</header>
