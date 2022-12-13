package edu.productivity.controller;

import edu.productivity.entity.TaskList;
import edu.productivity.entity.User;


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
        urlPatterns = {"/createNewTaskList"}
)
public class CreateNewTaskList extends HttpServlet {
    /**
     * Processes the HTTP GET response. Gets the logged-in user's id and searches the database for their information
     * and tasks.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String taskListName = req.getParameter("taskListName");
        String description = req.getParameter("taskDescription");
        String dueDate = req.getParameter("taskDueDate");
        TaskList newTaskList = new TaskList(taskListName, description, LocalTime.now(), LocalDate.parse(dueDate), user);

        session.setAttribute("newTaskList", newTaskList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/newTaskListForm.jsp");
        dispatcher.forward(req, resp);
    }
}
