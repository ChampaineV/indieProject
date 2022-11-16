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
        <main class="p-3">
        <h1>Hello ${userName}!</h1>
        <div class="row row-cols-1 row-cols-md2 g-4">
        <div class="col">
            <c:forEach var="tasklist" items="${taskList}">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${tasklist.taskName}</h5>
                        <p class="card-text">${tasklist.description}</p>
                    </div>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="taskItem" items="${tasklist.tasks}">
                        <li class="list-group-item">${taskItem}</li>
                        </c:forEach>
                    </ul>
                </div>
                </div>
            </c:forEach>
    </c:otherwise>
</body>
</html>
