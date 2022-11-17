<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <main class="p-3">
        <h1>Hello ${username}!</h1>
<c:choose>
    <c:when test="${empty taskList}">
        <h1>Create a TaskList!</h1>
    </c:when>
    <c:otherwise>
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
        </c:forEach>
        </div>
    </c:otherwise>
            </c:choose>
        </div>

