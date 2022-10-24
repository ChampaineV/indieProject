<%@include file="head.jsp"%>
<html>
<body>
    <%@include file="nav.jsp"%>
<c:choose>
    <c:when test="${empty userName}">
        <h1>Welcome! Sign In!</h1>
    </c:when>
    <c:otherwise>
        <main class="p-3">
        <h1>Hello ${username}!</h1>
        <div class="row row-cols-1 row-cols-md2 g-4">
        <div class="col">
            <div class="card">
                <div class="card-header">${task.taskName}}</div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">${task.description}</li>
                </ul>
            </div>
        </div>
    </c:otherwise>
</body>
</html>
