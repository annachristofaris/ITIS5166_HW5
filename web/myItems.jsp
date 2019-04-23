<%-- 
    Document   : myItems
    Created on : Feb 25, 2019, 7:47:47 PM
    Author     : annachristofaris
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <%
        Boolean signedIn = false;
        if (session.getAttribute("userCP") != null) {
            signedIn = true;
        }
    %>
    <jsp:include page ="./header.jsp" />

    <body>
        <jsp:include page ="./user-navigation.jsp" />

        <jsp:include page ="./site-navigation.jsp" />

        <!-- bootstrap for  main content area -->
        <div class="col-9 mainArea">
            <div class="row justify-content-center">

                <!--check current session to determine whether to display "sign in" or "sign out"-->
                <% if (signedIn == true) {
                %>
                <h1>${theUser.firstName}'s Corner</h1>
                <% } else {
                %>
                <h2> <a href="login.jsp">Sign in</a> or <a href="#">register</a>  and starting adding to your corner.</h2>

                <% }
                %>

                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Saved Content</th>
                            <th scope="col">Rating</th>
                            <th scope="col">Favorite</th>                         
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="obj" items="${userCP.itemList}">
                            <tr>
                                <th>
                                    <a href="CatalogController?action=details&value=${obj.itemCode}">${obj.item.title}</a>
                                </th>
                                <td>
                                    <c:out value="${obj.rating}/5"/>
                                      
                                </td>
                                <td>
                                    <c:out value="${obj.madeIt}"/>

                                </td>
                                <!--   clicking the wrench icon will take user to ProfileController to update rating and favorite selection-->
                                <td><a href="ProfileController?action=feedback&itemCode=<c:out value="${obj.item.itemCode}"/>">
                                        <i class="fas fa-wrench"></i></a>
                                    <!--   trash icon directs to ProfileController to handle delete request  -->
                                    <a href="ProfileController?action=delete&itemCode=<c:out value="${obj.item.itemCode}"/>">
                                        <i class="far fa-trash-alt"></i></a></td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
            </div>
            <br><br><br>

            <h2>You have not yet shared any content. <a href="#">Click here to upload and share!</a></h2>
        </div>

        <jsp:include page ="footer.jsp" />
    </body>
    
    
            <!--            <td><i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i></td>-->