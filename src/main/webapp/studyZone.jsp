<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="head.jsp"%>
<html lang="en">
<body>
<nav class="navbar bg-light fixed-top">
  <div class="container-fluid">
    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar">
      <span class="navbar-toggler-icon"></span>
    </button>
    <a href="/index.jsp" type="button" class="btn btn-secondary m-2">Go Back To Home</a>
    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
      <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasNavbarLabel">Your Task Lists</h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <div class="offcanvas-body">
        <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
          <c:forEach items="${taskLists}" var="taskList">
          <li class="nav-item">
            <div class="card rounded">
              <div class="card-body bg-primary">
                <div class="d-flex">
                  <div class="text-white">
                    <h5 class="card-title">${taskList.listName}</h5>
                    <p class="card-text">${taskList.description}</p>
                  </div>
                  <div class="ms-auto">
                    <form action="workOnTask" method="GET">
                      <button type="submit" class="btn btn-success" name="taskToWorkOn" value="${taskList.id}">Let's Work!
                      </button>
                    </form>
                  </div>
                </div>
              </div>
              <ul class="list-group list-group-flush">
                <c:forEach var="taskItem" items="${taskList.tasks}">
                  <li class="list-group-item">${taskItem.taskName}</li>
                </c:forEach>
              </ul>
            </div>
      </li>
      </c:forEach>
      </ul>
    </div>
  </div>
  </div>
</nav>
<div class="px-4 py-5 my-5 text-center">
  <h3>Task List: <c:if test="${empty currentTaskList}">None</c:if> ${currentTaskList}</h3>
  <ul class="list-group">
    <c:if test="${empty currentTaskList.tasks}">Create tasks for your list!</c:if>
  <c:forEach var="item" items="${currentTaskList.tasks}">
    <li class="list-group-item">${item.taskName}</li>
  </c:forEach>
  </ul>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>
