package edu.productivity.persistence;

import edu.productivity.entity.User;
import edu.productivity.entity.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {

    private final Logger logger = LogManager.getLogger(this.getClass());
    UserData userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserData();
        edu.productivity.test.util.Database database = edu.productivity.test.util.Database.getInstance();
        database.runSQL("cleandb.sql");

    }

    @Test
    void insert() {
        User newUser = new User(4, "Felix", "Montgomery", "fMontgomery", "password4", LocalDate.parse("1968-01-01"));
        int id = userDao.insert(newUser);
        assertTrue(userDao.getById(id).equals(newUser));
    }

    @Test
    void insertWithTaskSuccess() {
        String taskName = "Complete Journal Entry";
        String description = "Finish journal entry for Internship class by 10/10/2022";
        User newUser = new User(4, "Felix", "Montgomery", "fMontgomery", "password4", LocalDate.parse("1968-01-01"));
        Task task = new Task(taskName, description, newUser);
        newUser.addTask(task);
        int id = userDao.insert(newUser);
        assertNotEquals(0, id);
        assertNotNull(newUser);
        assertTrue(userDao.getById(id).equals(newUser));
        assertEquals(1, newUser.getTasks().size());
    }

    @Test
    void getByIdSuccess() {
        User retrievedUser = userDao.getById(3);
        assertNotNull(retrievedUser);
        assertEquals("Morgan", retrievedUser.getFirstName());
    }

    @Test
    void saveOrUpdateSuccess() {
        User user =  userDao.getById(1);
        user.setLastName("Rockwell");
        userDao.saveOrUpdate(user);
        assertTrue(userDao.getById(1).equals(user));
    }

    @Test
    void deleteSuccess() {
        userDao.delete(userDao.getById(3));
        assertNull(userDao.getById(3));
    }

    @Test
    void getUsersByPropertyLikeSuccess() {
        User newUser = new User(4, "Alexis", "Smith", "fMontgomery", "password4", LocalDate.parse("1968-01-01"));
        userDao.insert(newUser);
        List<User> users = userDao.getUsersByPropertyLike("lastName", "Smith");
        assertEquals(2, users.size());
    }

    @Test
    void getAllUsersSuccess() {
        List<User> users = userDao.getAll();
        assertEquals(3, users.size());
    }
}