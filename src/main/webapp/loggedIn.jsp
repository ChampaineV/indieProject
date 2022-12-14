<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
    <c:when test="${empty taskLists}">
        <main class="p-3">
        <h1>No task lists available. Create a new task list to get started, ${userName}!</h1>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty addedTaskList}">
            <div class="bg-info p-3">
                <div class="row">
                    <h3>Task was successfully created!</h3>
                    <p>Task list created: ${addedTaskList.listName}</p>
                    <p>Number of Tasks Added: ${numberOfTasksAdded}</p>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty listEdited}">
            <div class="bg-info p-3">
                <div class="row">
                    <h3>Task edit was successful!</h3>
                    <p>The task list, ${listEdited.listName}, was successfully updated!</p>
                </div>
            </div>
        </c:if>
        <div class="row">
            <c:forEach var="tasklist" items="${taskLists}">
                <div class="col-sm-6 col-lg-3 m-3">
                    <div class="card rounded">
                        <div class="card-body bg-primary">
                            <div class="d-flex">
                                <div class="text-white">
                                    <h5 class="card-title">${tasklist.listName}</h5>
                                    <p class="card-text">${tasklist.description}</p>
                                </div>
                                <div class="ms-auto">
                                    <form action="editTaskList" method="GET">
                                        <button type="submit" class="btn btn-secondary" name="editList" value="${tasklist.id}">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                                <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                            </svg>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <ul class="list-group list-group-flush">
                            <c:forEach var="taskItem" items="${tasklist.tasks}">
                                <li class="list-group-item">${taskItem.taskName}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>
</main>

