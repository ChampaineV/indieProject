package edu.productivity.persistence;

import edu.productivity.entity.Task;
import edu.productivity.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskDataTest {

    private final Logger logger = LogManager.getLogger(this.getClass());
    TaskData taskDao;

    @BeforeEach
    void setUp() {
        taskDao = new TaskData();
        edu.productivity.test.util.Database database = edu.productivity.test.util.Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    @Test
    void insertTaskSuccess() {
        UserData userDao = new UserData();
        User user = userDao.getById(1);
        String taskName = "Organize meeting with group";
        String taskDescription = "Organize a meeting next week to discuss next steps";
        Task newTask = new Task(taskName, taskDescription, user);
        user.addTask(newTask);
        int id = taskDao.insert(newTask);
        assertNotEquals(0, id);
        Task insertedTask = taskDao.getById(id);
        assertTrue(taskDao.getById(id).equals(insertedTask));
    }

    @Test
    void getById() {
        Task retrievedTask = taskDao.getById(3);
        assertNotNull(retrievedTask);
        assertTrue(taskDao.getById(3).equals(retrievedTask));
    }

    @Test
    void saveOrUpdateTasksSuccess() {
        String newDescription = "Complete next step for indie project before Wednesday";
        Task taskToUpdate = taskDao.getById(3);
        taskToUpdate.setDescription(newDescription);
        taskDao.saveOrUpdate(taskToUpdate);
        Task retrievedTask = taskDao.getById(3);
        assertTrue(taskDao.getById(3).equals(retrievedTask));
    }

    @Test
    void deleteTaskSuccess() {
        taskDao.delete(taskDao.getById(2));
        assertNull(taskDao.getById(2));
    }

    @Test
    void getTasksByPropertyLikeSuccess() {
        List<Task> tasks = taskDao.getTasksByPropertyLike("description", " for ");
        assertEquals(4, tasks.size());
    }

    @Test
    void getAllTasksSuccess() {
        List<Task> tasks = taskDao.getAllTasks();
        assertEquals(6, tasks.size());
    }

    @Test
    void getByIdVerifyUserSuccess() {
        Task retrievedTask = taskDao.getById(1);
        assertNotNull(retrievedTask);
        assertEquals("Read American History before Saturday", retrievedTask.getDescription());
        assertEquals("Ellen", retrievedTask.getUser().getFirstName());
    }
}