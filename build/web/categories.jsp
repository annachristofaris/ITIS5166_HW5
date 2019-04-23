

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
 <jsp:include page ="./header.jsp" />
 
<body>
 <jsp:include page ="./user-navigation.jsp" />
 
 <jsp:include page ="./site-navigation.jsp" />

        <!-- bootstrap for  main content area -->
        <div class="col-10 mainArea">

            <div class="cat">
                <h2>Most Popular</h2>
                <div id="test"></div>
                <ul>
                    <c:forEach var="item" items="${popularItems}">
                        <li>
                            <a href="CatalogController?action=details&value=${item.itemCode}">${item.title}</a>
                        </li>
                    </c:forEach>
                </ul>
                <h2>How-to Guides & Design Tips</h2>
               
                <ul>
                    <c:forEach var="item" items="${tipItems}">
                        <li>
                            <a href="CatalogController?action=details&value=${item.itemCode}">${item.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>

     <jsp:include page ="footer.jsp" />
</body>


