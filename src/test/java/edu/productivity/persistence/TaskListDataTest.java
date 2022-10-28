package edu.productivity.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskListDataTest {

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