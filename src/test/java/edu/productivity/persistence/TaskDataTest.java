package edu.productivity.persistence;

import edu.productivity.entity.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void insert() {
    }

    @Test
    void getById() {
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
}