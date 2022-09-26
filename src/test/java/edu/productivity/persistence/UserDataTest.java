package edu.productivity.persistence;

import edu.productivity.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {

    UserData userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserData();

        edu.productivity.test.util.Database database = edu.productivity.test.util.Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    @Test
    void insert() {
    }

    @Test
    void getByIdSuccess() {
        User retrievedUser = userDao.getById(3);
        assertEquals("Barney", retrievedUser.getFirstName());
        assertEquals("Curry", retrievedUser.getLastName());
        assertEquals("bcurry", retrievedUser.getUserName());
        assertEquals("", retrievedUser.getPassword());
        assertEquals("", retrievedUser.getDateOfBirth());
        assertEquals("", retrievedUser.getId());
    }

    @Test
    void saveOrUpdate() {
        User newUser = new User("Fred", "Flintstone", "fflintstone", LocalDate.parse("1968-01-01"));
        int id = userDao.insert(newUser);
        assertNotEquals(0,id);
        User insertedUser = userDao.getById(id);
        assertEquals("Fred", insertedUser.getFirstName());
        newUser.equals(id, insertedUser.id);
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }
}