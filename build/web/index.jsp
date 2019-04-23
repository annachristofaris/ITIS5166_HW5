<%-- 
    Document   : index
    Created on : Feb 25, 2019, 7:45:17 PM
    Author     : annachristofaris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
   <jsp:include page ="./header.jsp" />

<body>
 <jsp:include page ="./user-navigation.jsp" />
 

 <jsp:include page ="./site-navigation.jsp" />
 
        <!-- bootstrap for  main content area -->
        <div class="col-9 mainArea">
            <div class="media">
                <img src="./fireplace.jpg" class="mr-3" alt="homepage image">
                <div class="media-body">
                    <h1 class="mt-0">Welcome!</h1>
                    <p>Cozy Corners is a place for you to get guidance, inspiration, and ideas on how to make a house your dream home. </p>
                        <p>Use the categories tab to browse through pictures, articles, and tips. You can save your favorite content, and 
                        view it anytime in your "corner", using the "My Corner" link.
                    </p>
                </div>
            </div>
        </div>
    </div>

        <jsp:include page ="footer.jsp" />
</body>