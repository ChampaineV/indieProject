package edu.productivity.persistence;

import edu.productivity.entity.Task;
import edu.productivity.entity.TaskList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskDataTest {

    private final Logger logger = LogManager.getLogger(this.getClass());
    GenericDao genericDao;
    //TODO: cover deleting one-to-many relationships more fully. if a task is deleted, what should happen to the user? What if a user is deleted? Write tests to make sure whatever should happen, does happen.
    @BeforeEach
    void setUp() {
        genericDao = new GenericDao(Task.class);
        edu.productivity.test.util.Database database = edu.productivity.test.util.Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    @Test
    void insertTaskSuccess() {
        GenericDao taskListDao = new GenericDao(TaskList.class);
        TaskList taskList = (TaskList) taskListDao.getById(1);
        boolean isComplete = false;
        String taskName = "Organize meeting with group";
        Task newTask = new Task(taskName, isComplete, taskList);
        taskList.addTask(newTask);
        int id = genericDao.insert(newTask);
        assertNotEquals(0, id);
    }

    @Test
    void getById() {
        Task retrievedTask = (Task) genericDao.getById(3);
        assertNotNull(retrievedTask);
        assertTrue(genericDao.getById(3).equals(retrievedTask));
    }

    @Test
    void saveOrUpdateTasksSuccess() {
        String newTaskName = "Complete next step for indie project before Wednesday";
        Task taskToUpdate = (Task) genericDao.getById(3);
        taskToUpdate.setTaskName(newTaskName);
        genericDao.saveOrUpdate(taskToUpdate);
        Task retrievedTask = (Task) genericDao.getById(3);
        assertTrue(genericDao.getById(3).equals(retrievedTask));
    }

    @Test
    void deleteTaskSuccess() {
        genericDao.delete(genericDao.getById(2));
        assertNull(genericDao.getById(2));
    }

    @Test
    void getTasksByPropertyLikeSuccess() {
        List<Task> tasks = genericDao.getByPropertyLike("taskName", "Write ");
        assertEquals(3, tasks.size());
    }

    @Test
    void getAllTasksSuccess() {
        List<Task> tasks = genericDao.getAll();
        assertEquals(9, tasks.size());
    }

    @Test
    void getByIdVerifyUserSuccess() {
        Task retrievedTask = (Task) genericDao.getById(1);
        assertNotNull(retrievedTask);
        assertEquals("Read Chapter 16", retrievedTask.getTaskName());
        assertEquals("Read Book For History Class", retrievedTask.getTaskList().getListName());
    }
}