package edu.productivity.controller;

import com.sun.xml.bind.v2.TODO;
import edu.productivity.entity.Task;
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
        urlPatterns = {"/userTasks"}
)

public class UserTasks extends HttpServlet {

    //TODO: Comment method
    /**
     * Processes the HTTP GET response. Gets the logged-in user's id and searches the database for their information
     * and tasks.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Work on making information and taskLists appear that are related to the logged-in user
        String authTokenHeader = req.getHeader("Authorization");
        HttpSession session = req.getSession();
        ServletContext context = getServletContext();

        GenericDao dao = new GenericDao(TaskList.class);
        GenericDao userDao = new GenericDao(User.class);
        User user = (User) userDao.getById((Integer) session.getAttribute("user_id"));
        String taskListName = req.getParameter("taskListName");
        String description = req.getParameter("taskDescription");
        String dueDate = req.getParameter("taskDueDate");
        TaskList newTaskList = new TaskList(taskListName, description, LocalTime.now(), LocalDate.parse(dueDate), user);
        int id = dao.insert(newTaskList);

        req.setAttribute("taskList", newTaskList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/createTasks.jsp");
        dispatcher.forward(req, resp);
    }
}
