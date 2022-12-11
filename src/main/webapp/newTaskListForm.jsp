<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="head.jsp"%>
<html lang="en">
<body>
<%@include file="nav.jsp"%>
< class="bg-light p-5 rounded mt-3 container">
    <c:choose>
        <c:when test="${empty taskList}">
        <form class="my-3" action="createNewTaskList" method="POST">
            <h2>Create A New Task List</h2>
            <div class="row mb-3">
                <label for="taskListName" class="col-sm-2 col-form-label">Task List Name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control"
                           name="taskListName" id="taskListName"/>
                </div>
            </div>
            <div class="row mb-3">
                <label for="taskDescription" class="col-sm-2 col-form-label">Description</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control"
                           name="taskName" id="taskDescription"/>
                </div>
            </div>
            div class="row mb-3">
            <label class="col-form-label col-sm-2 pt-0">Due Date</label>
            <div class="col-5">
                <input type="date" class="form-control"
                       name="taskName" id="taskDueDate"/>
            </div>
            <input class="btn btn-success mt-3" type="submit" name="newTaskList" value="Create New Task List"/>
        </form>
        </c:when>
<c:otherwise>
    <form class="my-3" action="createTasks" method="POST">
        <h2>Create Tasks</h2>
        <h3>Task List: ${taskList.listName}</h3>
        <p>Description: ${taskList.description}</p>
        <div class="container">
            <div class="row">
                <ul class="list-group">
                    <li class="list-group-item"><input type="text" name="newTask" id="newTask" size="40"/><button class="btn" onclick="addTask();"><i class="bi bi-plus-lg"></i></button></li>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <div class="input-group">
                                <button class="btn btn-primary" type="button" id="button-addon1"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" onclick="addTask();" class="bi bi-plus-lg" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2Z"/>
                                </svg></button>
                                <input type="text" name="newTask" id="submitNewTask" class="form-control">
                            </div>
                        </li>
                    </ul>
            </div>
        </div>
        <input class="btn btn-success mt-3" type="submit" name="createTasks" value="Create New Task List"/>
    </form>
</c:otherwise>
    </c:choose>

</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
        integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>
