<%-- 
    Document   : login
    Created on : Apr 14, 2019, 9:58:31 AM
    Author     : annachristofaris
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Boolean invalidUser = false;
    if (session.getAttribute("invalidLogin") != null) {
        invalidUser = true;
    }
%>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page ="./header.jsp" />

    <body>
        <jsp:include page ="./user-navigation.jsp" />


        <jsp:include page ="./site-navigation.jsp" />

        <!-- bootstrap used for form -->
        <div class="signIn">
            <div class="container">
                <h3 class="mt-0">Sign In</h3>
                <!-- checking session for invalid login attribute -->
                <% if (invalidUser == true) {
                %>
                
                 <!-- Temporary badge for invalid username and/or password inputs-->
                <div class="invalidLogin">
                    <i class="fas fa-exclamation-triangle"> Invalid username and/or password.</i>               
                </div>
                <% } %>
                <%
                    //removing the invalid session attribute so badge isn't persistant
                    session.removeAttribute("invalidLogin");
                %>
                <form action="ProfileController" method="post">
                    <input type="hidden" name="action" value="signIn"/>

                    <div class="form-group">
                        <label for="validationDefaultUsername" class="form">Email address</label>
                        <input type="email" name="username" class="form-control" id="validationDefaultUsername" placeholder="Enter email" required>
                    </div>

                    <div class="form-group">
                        <label for="exampleInputPassword1" class="form">Password</label>
                        <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Password" required>
                    </div>

                    <button class="btn btn-primary" name="action" value="signIn">Submit</button>

                </form>
            </div>
        </div>
    </div>       


    <jsp:include page ="footer.jsp" />
</body>
