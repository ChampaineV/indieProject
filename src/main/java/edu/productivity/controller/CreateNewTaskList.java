package edu.productivity.controller;

import edu.productivity.entity.TaskList;
import edu.productivity.entity.User;
import edu.productivity.persistence.ClientDao;
import edu.productivity.persistence.GenericDao;
import org.checkerframework.checker.units.qual.C;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

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

        GenericDao dao = new GenericDao(TaskList.class);
        User user = (User) session.getAttribute("user");
        String taskListName = req.getParameter("taskListName");
        String description = req.getParameter("taskDescription");
        String dueDate = req.getParameter("taskDueDate");
        TaskList newTaskList = new TaskList(taskListName, description, LocalTime.now(), LocalDate.parse(dueDate), user);
        int id = dao.insert(newTaskList);
        TaskList taskList = (TaskList) dao.getById(id);

        req.setAttribute("taskList", taskList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/newTaskListForm.jsp");
        dispatcher.forward(req, resp);
    }
}
