package edu.productivity.persistence;

import edu.productivity.entity.TaskList;
import edu.productivity.entity.User;
import edu.productivity.entity.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests and checks the dao methods for User
 */
class UserDataTest {

    private final Logger logger = LogManager.getLogger(this.getClass());

    GenericDao genericDao;
    /**
     * Sets up test database for test methods
     */
    @BeforeEach
    void setUp() {
        genericDao = new GenericDao(User.class);
        edu.productivity.test.util.Database database = edu.productivity.test.util.Database.getInstance();
        database.runSQL("cleandb.sql");

    }

    /**
     * Tests for success when inserting a new user record to the user table. This method creates a new User and inserts
     * it into the user table. Then, the method will check if the user insert was a success.
     */
    @Test
    void insertNewUserSuccess() {
        User newUser = new User("Felix", "Montgomery", LocalDate.parse("1968-01-01"), "Monty68@yahoo.com", "fMontgomery", "password4");
        logger.info(newUser.toString());
        int id = genericDao.insert(newUser);
        assertTrue(genericDao.getById(id).equals(newUser));
    }

    /**
     * Tests for success when inserting a new user with a tasklist foreign key to the user table. This method creates
     * a new User and a new TaskList. The TaskList is added into the User and the User is inserted into the user table.
     * The method then checks for if there's an unexpected id, the user is null, and the user insert was a success.
     */
    @Test
    void insertWithTaskListSuccess() {
        String taskListName = "Complete Journal Entry";
        String description = "Finish journal entry for Internship class by 10/10/2022";
        String minutesWorked = "00:40:00";
        User newUser = new User("Ebony", "Diaz", LocalDate.parse("2001-12-29"), "eDiaz@gmail.com", "eDiaz", "password5");
        TaskList newTaskList = new TaskList(taskListName, description, LocalTime.parse(minutesWorked), LocalDate.parse("2022-12-29"), newUser);
        newUser.addTaskList(newTaskList);
        int id = genericDao.insert(newUser);
        assertNotEquals(0, id);
        assertNotNull(newUser);
        assertTrue(genericDao.getById(id).equals(newUser));
        assertEquals(1, newUser.getTaskLists().size());
    }

    /**
     * Test for success when getting a user by an id. This method gets the record of an id of 3 from the user table and
     * checks if the retrieved user is not null and if the getBy was a success.
     */
    @Test
    void getByIdSuccess() {
        User retrievedUser = (User) genericDao.getById(3);
        assertNotNull(retrievedUser);
        assertTrue(genericDao.getById(3).equals(retrievedUser));
    }

    /**
     * Test for success when saving or updating a record in the user table. This method changes the last name of the
     * user of an id of 1, and checks if the update was a success.
     */
    @Test
    void saveOrUpdateSuccess() {
        String newLastName = "Design Vision/Moodboard";
        User user =  (User) genericDao.getById(1);
        user.setLastName(newLastName);
        genericDao.saveOrUpdate(user);
        assertTrue(genericDao.getById(1).equals(user));
    }

    /**
     * Tests for success when deleting a record from the user table. This method deletes the record of an id of 3, and
     * checks if delete was a success.
     */
    @Test
    void deleteSuccess() {
        genericDao.delete(genericDao.getById(3));
        assertNull(genericDao.getById(3));
    }

    /**
     * Tests for success when getting records of a specific property from the user table.
     * This method asks for "lastname" of "Smith" and checks if the number of records is equal to the expected size.
     */
    @Test
    void getUsersByPropertyLikeSuccess() {
        List<User> users = genericDao.getByPropertyLike("lastName", "Smith");
        assertEquals(1, users.size());
    }

    /**
     * Tests for success when getting all the records from the user table
     */
    @Test
    void getAllUsersSuccess() {
        List<User> users = genericDao.getAll();
        assertEquals(3, users.size());
    }
}