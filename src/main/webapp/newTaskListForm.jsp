<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="head.jsp"%>
<html lang="en">
<body>
<%@include file="nav.jsp"%>
<div class="bg-light p-5 rounded mt-3 container">
<form class="my-3" action="CreateNewTaskList" method="POST">
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
</div>
        <input class="btn btn-success mt-3" type="submit" name="newTaskList" value="Create New Task List"/>
</form>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
        integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>