package edu.productivity.controller;

import edu.productivity.persistence.UserData;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet that'll add the user to the database.
 * @author lvang
 */

@WebServlet(
        urlPatterns = {"/searchUser"}
)
public class SearchUsers {
    //TODO: Ask if SearchUser is relevant to the application? Maybe a SearchTasks instead?
    //TODO: Comment method
    /**
     * Process the HTTP GET request.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserData userData = new UserData();
        if (req.getParameter("submit").equals("search")) {
            req.setAttribute("users", userData.getById(Integer.parseInt(req.getParameter("search"))));
        } else {
            req.setAttribute("users", userData.getAll());
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/results.jsp");
        dispatcher.forward(req, resp);
    }
}
