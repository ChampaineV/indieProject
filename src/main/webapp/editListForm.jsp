<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="head.jsp"%>
<html lang="en">
<body>
<%@include file="nav.jsp"%>
<div class="bg-light p-5 rounded mt-3 container">
    <h2>Editing TaskList</h2>
    <h3>Task List: ${listToEdit.listName}</h3>
    <div class="container">
        <div class="row">
            <form class="my-3" action="updateTaskList" method="POST">
                <div class="row">
                    <h3>Your Tasks</h3>
                    <div>
                        <c:forEach var="taskItem" items="${listToEdit.tasks}">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="${taskItem.id}" name="id" id="${taskItem.id}">
                                <label class="form-check-label" for="${taskItem.id}">
                                        ${taskItem.taskName}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <c:if test="${messages}">
                    <p class="text-danger">${messages}</p>
                </c:if>
                <div class="container mx-auto">
                    <a class="btn btn-secondary col-4 col-sm-2 mt-3 ms-2" href="/index.jsp" role="button">Cancel</a>
                    <button class="btn btn-success mt-3 col-4 col-sm-2" type="submit" name="submitNewTasks">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
        integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>
