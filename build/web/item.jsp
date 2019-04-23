<%-- 
    Document   : item
    Created on : Feb 25, 2019, 7:47:11 PM
    Author     : annachristofaris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Boolean guestUser = false;
    if (session.getAttribute("guestUser") != null) {
        guestUser = true;
    }
%>

<!DOCTYPE html>
<html lang="en">
    <jsp:include page ="./header.jsp" />

    <body>
        <jsp:include page ="./user-navigation.jsp" />

        <jsp:include page ="./site-navigation.jsp" />

        <!-- bootstrap for  main content area -->
        <div class="col-8">
            <div class ="mainArea">
                <div class="row justify-content-center">
                    <h1>${detailItem.title}</h1> 
                    <!-- bootstrap divider for columns-->
                    <div class="w-100"></div>


                    <!-- checking if user is a guest/not signed in -->
                    <% if (guestUser == true) {
                    %>
                    <!-- adding and feedback is disabled for guest users, and
                    this pop up will notify guest users to sign in or register -->
                    <div class="popup" onclick="myFunction()">  
                        <i class="fas fa-plus-circle"> Save to Your Corner</i>
                        <span class="popuptext" id="myPopup"><a href="./login.jsp"> Sign in</a> to save and rate. <br>
                            <p class="small">
                                New to Cozy Corners? <a href="#">Register here.</a>
                            </p> </span>
                        <i class="fas fa-star starfill"> Feedback</i>   

                    </div>

                    <!-- signed-in users can add items and leave feedback -->
                    <% } else {
                    %>
                    <a href="ProfileController?action=save&itemCode=<c:out value="${detailItem.itemCode}"/>">
                        <i class="fas fa-plus-circle"> Save to Your Corner</i></a>

                    <a href="ProfileController?action=feedback&itemCode=<c:out value="${detailItem.itemCode}"/>">
                        <i class="fas fa-star starfill"> Feedback</i></a><br>
                        <% }
                        %>

                    <!-- bootstrap divider for columns-->
                    <div class="w-100"></div>
                    <img src=${detailItem.imageURL} alt="itemDetail">
                </div>
                <div class="row justify-content-left">
                    <c:out value="${detailItem.descrip}" escapeXml="false"/>
                </div>
                <a href="./categories.jsp"><i class="far fa-arrow-alt-circle-left"> Go Back</i></a>
            </div>
<!--            <a href="./categories.jsp"><i class="far fa-arrow-alt-circle-left"> Go Back</i></a>-->
        </div>

        <jsp:include page ="footer.jsp" />
    </body>

    <!-- javascript for popup notifcation -->
    <script>
        // pop up functionality 
        function myFunction() {
            var popup = document.getElementById("myPopup");
            popup.classList.toggle("show");
        }
    </script>

    <!--    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
        
        <script>
    
        
            $('.fa-star.one').click(function() {
              $('.fa-star').removeClass('starfill');
              $('.fa-star.one').addClass('starfill');
              // POST VALUE TO DATABASE
        //      $.post( "url", {
        //        name: "Donald Duck",
        //        city: "Duckburg"
        //      }, 
        //    function(data) {
        //        $( ".result" ).html( data );
        //      });
            });
            
            $('.fa-star.two').click(function() {
              $('.fa-star').removeClass('starfill');
              $('.fa-star.one, .fa-star.two').addClass('starfill');
            });
            
            $('.fa-star.three').click(function() {
              $('.fa-star').removeClass('starfill');
              $('.fa-star.one, .fa-star.two, .fa-star.three').addClass('starfill');
            });
            
            $('.fa-star.four').click(function() {
              $('.fa-star').removeClass('starfill');
              $('.fa-star.one, .fa-star.two, .fa-star.three, .fa-star.four').addClass('starfill');
            });
           
            </script>-->