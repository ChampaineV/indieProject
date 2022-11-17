package edu.productivity.persistence;

import edu.productivity.entity.Task;
import edu.productivity.entity.TaskList;
import edu.productivity.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskListDataTest {

    private final Logger logger = LogManager.getLogger(this.getClass());
    GenericDao genericDao;

    @BeforeEach
    void setUp() {
        genericDao = new GenericDao(TaskList.class);
        edu.productivity.test.util.Database database = edu.productivity.test.util.Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    @Test
    void insertTaskListSuccess() {
        GenericDao userDao = new GenericDao(User.class);
        User user = (User) userDao.getById(2);
        String taskListName = "Work on Lab 4 - JS";
        String description = "Lab 4 will demonstrate on how to use JavaScript in a HTML file.";
        String minutesWorked = "00:59:00";
        TaskList newTaskList = new TaskList(taskListName, description, LocalTime.parse(minutesWorked), LocalDate.parse("2023-01-29"), user);
        int id = genericDao.insert(newTaskList);
        assertTrue(genericDao.getById(id).equals(newTaskList));
    }

    @Test
    void getByIdSuccess() {
        TaskList retrievedTaskList = (TaskList) genericDao.getById(3);
        assertNotNull(retrievedTaskList);
        assertTrue(genericDao.getById(3).equals(retrievedTaskList));
    }

    @Test
    void saveOrUpdateSuccess() {
        String newListName = "Design Vision/Moodboard";
        TaskList toUpdateTaskList =  (TaskList) genericDao.getById(5);
        toUpdateTaskList.setListName(newListName);
        genericDao.saveOrUpdate(toUpdateTaskList);
        assertTrue(genericDao.getById(5).equals(toUpdateTaskList));
    }

    @Test
    void deleteTaskListSuccess() {
        genericDao.delete(genericDao.getById(2));
        assertNull(genericDao.getById(2));
    }

    @Test
    void getTaskListByPropertyLike() {
        List<TaskList> taskLists = genericDao.getByPropertyLike("listName", " Class");
        assertEquals(3, taskLists.size());
    }

    @Test
    void getAllTaskListsSuccess() {
        List<TaskList> taskLists = genericDao.getAll();
        assertEquals(5, taskLists.size());
    }
}