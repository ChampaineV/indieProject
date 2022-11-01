package edu.productivity.persistence;

import edu.productivity.entity.TaskList;
import edu.productivity.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskListDataTest {

    private final Logger logger = LogManager.getLogger(this.getClass());
    TaskListData taskListDao;

    @BeforeEach
    void setUp() {
        taskListDao = new TaskListData();
        edu.productivity.test.util.Database database = edu.productivity.test.util.Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    @Test
    void insertTaskListSuccess() {
        UserData userDao = new UserData();
        User user = userDao.getById(2);
        String taskListName = "Work on Lab 4 - JS";
        String description = "Lab 4 will demonstrate on how to use JavaScript in a HTML file.";
        String minutesWorked = "00:59:00";
        TaskList newTaskList = new TaskList(taskListName, description, LocalTime.parse(minutesWorked), 1, user);
        int id = taskListDao.insert(newTaskList);
        assertTrue(taskListDao.getById(id).equals(newTaskList));
    }

    @Test
    void getById() {
        TaskList retrievedTaskList = taskListDao.getById(3);
        assertNotNull(retrievedTaskList);
        assertTrue(taskListDao.getById(3).equals(retrievedTaskList));
    }

    @Test
    void saveOrUpdate() {
    }

    @Test
    void delete() {
    }

    @Test
    void getTasksByPropertyLike() {
    }

    @Test
    void getAllTaskLists() {
    }
}