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
        urlPatterns = {"/editTaskList"}
)
public class EditTaskList extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        GenericDao taskListDao = new GenericDao(TaskList.class);
        int taskListToBeEdit = Integer.parseInt(req.getParameter("editList"));
        TaskList taskList = (TaskList) taskListDao.getById(taskListToBeEdit);

        if(session.getAttribute("listToEdit") != null){
            session.removeAttribute("listToEdit");
        }
        session.setAttribute("listToEdit", taskList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/editListForm.jsp");
        dispatcher.forward(req, resp);
    }
}
