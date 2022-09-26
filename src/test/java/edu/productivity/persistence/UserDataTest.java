package edu.productivity.persistence;

import edu.productivity.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a test class for UserData.
 * @author lvang
 */
public class UserDataTest {

    UserData userData;
    User user;

    @BeforeEach
    void setUp() {
        userData = new UserData();
        user = new User();
    }

    @Test
    void testInsertUser() {
    int expected = 1;
    int actual = userData.insert(user);
    assertEquals(expected, actual);
    }
}