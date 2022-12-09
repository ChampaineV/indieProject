package edu.productivity.controller;

import com.sun.xml.bind.v2.TODO;
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
        HttpSession session=req.getSession();
        session.setAttribute("uname",n);

        GenericDao dao = new GenericDao(User.class);
        User userInfo = (User) dao.getById(inputId);
        Set<TaskList> taskListInfo = userInfo.getTaskLists();
        req.setAttribute("user", userInfo);
        req.setAttribute("taskList", taskListInfo);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/loggedIn.jsp");
        dispatcher.forward(req, resp);
    }
}
