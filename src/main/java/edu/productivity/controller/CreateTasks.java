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
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet(
        urlPatterns = {"/createTasks"}
)
public class CreateTasks extends HttpServlet {


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        TaskList taskList = (TaskList) session.getAttribute("taskList");
        GenericDao dao = new GenericDao(Task.class);
        boolean isComplete = false;
        int numberOfTasksAdded = 0;

        for (String id : req.getParameterValues("id")) {
            String taskName = req.getParameter("task_" + id);
            Task task = new Task(taskName, isComplete, taskList);
            dao.insert(task);
            numberOfTasksAdded++;
        }

        req.setAttribute("numberOfTasksAdded", numberOfTasksAdded);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/newTaskListForm.jsp");
        dispatcher.forward(req, resp);
    }
}
