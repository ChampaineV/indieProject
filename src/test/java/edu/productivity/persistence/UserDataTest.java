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
    void getByIdSuccess() {
        User retrievedUser = userDao.getById(3);
        assertEquals("Morgan", retrievedUser.getFirstName());
        assertEquals("Matthews", retrievedUser.getLastName());
        assertEquals("mMatthews21", retrievedUser.getUserName());
        assertEquals("password3", retrievedUser.getPassword());
        assertEquals(LocalDate.parse("2001-03-16"), retrievedUser.getDateOfBirth());
        assertEquals(3, retrievedUser.getId());
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