package edu.productivity.controller;

import edu.productivity.entity.Task;
import edu.productivity.entity.User;
import edu.productivity.persistence.TaskData;
import edu.productivity.persistence.UserData;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns = {"/userTasks"}
)

public class UserTasks extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int inputId = Integer.parseInt(req.getParameter("id"));

        UserData userData = new UserData();
        TaskData taskData = new TaskData();
        User userInfo = userData.getById(inputId);
        Task taskInfo = taskData.getById(inputId);
        if(userInfo != null && taskInfo != null) {
            req.setAttribute("user", userInfo);
            req.setAttribute("tasks", taskInfo);
        } else {
            req.setAttribute("user", null);
            req.setAttribute("tasks", null);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/loggedIn.jsp");
        dispatcher.forward(req, resp);
    }
}
