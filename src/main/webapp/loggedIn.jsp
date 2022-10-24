<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="head.jsp"%>
<html>
<body>
    <%@include file="nav.jsp"%>
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
            <div class="col">
                <div class="card">
                    <div class="card-header">Task List 2</div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">An item</li>
                        <li class="list-group-item">A second item</li>
                        <li class="list-group-item">A third item</li>
                    </ul>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
