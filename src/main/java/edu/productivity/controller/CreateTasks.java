package edu.productivity.controller;

import edu.productivity.entity.Task;
import edu.productivity.entity.TaskList;
import edu.productivity.entity.User;
import edu.productivity.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebServlet(
        urlPatterns = {"/createTasks"}
)
public class CreateTasks extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        GenericDao taskDao = new GenericDao(Task.class);
        GenericDao taskListDao = new GenericDao<>(TaskList.class);
        TaskList taskList = (TaskList) session.getAttribute("newTaskList");
        boolean isComplete = false;
        int numberOfTasksAdded = 0;

        int taskListId = taskListDao.insert(taskList);

        for (String taskName : req.getParameterValues("id")) {
            Task task = new Task(taskName, isComplete, taskList);
            taskDao.insert(task);
            numberOfTasksAdded++;
        }

        TaskList newTaskList = (TaskList) taskListDao.getById(taskListId);

        req.setAttribute("addedTaskList", newTaskList);
        req.setAttribute("numberOfTasksAdded", numberOfTasksAdded);

        session.removeAttribute("newTaskList");
        User user = (User) session.getAttribute("user");
        Set<TaskList> updateTaskLists = user.getTaskLists();
        session.setAttribute("taskLists", updateTaskLists);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);
    }

}
