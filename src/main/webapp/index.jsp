<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="head.jsp"%>
<html lang="en">
<body>
    <%@include file="nav.jsp"%>
<c:choose>
    <c:when test="${empty userName}">
        <h1>Welcome! Sign In!</h1>
    </c:when>
    <c:otherwise>
        <%@include file="loggedIn.jsp"%>
    </c:otherwise>
</c:choose>
</body>
</html>
