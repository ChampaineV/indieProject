<nav class="navbar navbar-expand navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">Productivity App</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
        <c:choose>
            <c:when test="${empty user}">
                <li class="nav-item">
                    <a class="nav-link" href="logIn">Log in/Sign Up</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="nav-item">
                    <a class="nav-link" href="newTaskListForm.jsp">Create New Task List</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-success" href="studyZone.jsp">Let's Get To Work!</a>
                </li>
                </ul>
                </div>
                <h3>Welcome ${userName}</h3>
            </c:otherwise>
    </c:choose>
            </div>
        </div>
</nav>
