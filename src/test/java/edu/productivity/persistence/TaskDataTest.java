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
    TaskData taskDao;

    @BeforeEach
    void setUp() {
        taskDao = new TaskData();
        edu.productivity.test.util.Database database = edu.productivity.test.util.Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    @Test
    void insertTaskSuccess() {
        TaskListData taskListDao = new TaskListData();
        TaskList taskList = taskListDao.getById(1);
        String minutesWorked = "01:00:00";
        String taskName = "Organize meeting with group";
        Task newTask = new Task(taskName, minutesWorked, taskList);
        taskList.addTask(newTask);
        int id = taskDao.insert(newTask);
        assertNotEquals(0, id);
    }

    @Test
    void getById() {
        Task retrievedTask = taskDao.getById(3);
        assertNotNull(retrievedTask);
        assertTrue(taskDao.getById(3).equals(retrievedTask));
    }

    @Test
    void saveOrUpdateTasksSuccess() {
        String newTaskName = "Complete next step for indie project before Wednesday";
        Task taskToUpdate = taskDao.getById(3);
        taskToUpdate.setTaskName(newTaskName);
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
        List<Task> tasks = taskDao.getTasksByPropertyLike("taskName", "Write ");
        assertEquals(3, tasks.size());
    }

    @Test
    void getAllTasksSuccess() {
        List<Task> tasks = taskDao.getAllTasks();
        assertEquals(9, tasks.size());
    }

    @Test
    void getByIdVerifyUserSuccess() {
        Task retrievedTask = taskDao.getById(1);
        assertNotNull(retrievedTask);
        assertEquals("Read Chapter 16", retrievedTask.getTaskName());
        assertEquals("Read Book For History Class", retrievedTask.getTaskList().getListName());
    }
}