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
        urlPatterns = {"/updateTaskList"}
)
public class UpdateTaskList extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            TaskList taskList = (TaskList) session.getAttribute("listToEdit");
            GenericDao taskDao = new GenericDao<>(Task.class);
            GenericDao taskListDao = new GenericDao(TaskList.class);
            user.removeTaskList(taskList);

        for (String taskId : req.getParameterValues("id")) {
                int id = Integer.parseInt(taskId);
                Task taskToBeDeleted = (Task) taskDao.getById(id);
                taskList.removeTask(taskToBeDeleted);
                taskDao.delete(taskToBeDeleted);
            }
        taskListDao.saveOrUpdate(taskList);
        user.addTaskList(taskList);

        req.setAttribute("listEdited", taskList);
        Set<TaskList> updateTaskLists = user.getTaskLists();
        session.setAttribute("taskLists", updateTaskLists);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);
    }
}
