<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="head.jsp"%>
<html lang="en">
<body>
    <%@include file="nav.jsp"%>
<c:choose>
    <c:when test="${empty userInfo}">
        <h1>Welcome! Sign In!</h1>
    </c:when>
    <c:otherwise>
        <%@include file="loggedIn.jsp"%>
    </c:otherwise>
</c:choose>
    <%@include file="footer.jsp"%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>
