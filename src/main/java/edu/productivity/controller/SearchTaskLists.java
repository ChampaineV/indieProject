package edu.productivity.controller;

import edu.productivity.entity.Task;
import edu.productivity.entity.TaskList;
import edu.productivity.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * A servlet that'll add the user to the database.
 * @author lvang
 */

@Path("/tasklists")
public class SearchTaskLists {
    GenericDao dao;

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/html")
    public Response getTaskLists() {
        dao = new GenericDao(TaskList.class);
        List<TaskList> taskLists = dao.getAll();
    //TODO: Complete service
        String outputString = "<div class=col>";
        for (TaskList taskList : taskLists) {
            outputString += "<div class=\"card\"><div class=\"card-body\">";
            outputString += "<h5 class=\"card-title\">" + taskList.getListName() + "</h5>";
            outputString += "<p class=\"card-text\">" + taskList.getDescription() + "</p></div>";
            outputString += "<ul class=\"list-group list-group-flush\">";
                for (Task taskItem : taskList.getTasks()) {
                    outputString += "<li class=\"list-group-item\">" + taskItem.getTaskName() + "</li>";
                }
            outputString += "</ul>";
        }
        outputString += "</div>";
        return Response.status(200).entity(outputString).build();
    }
}
