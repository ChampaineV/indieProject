package edu.productivity.persistence;

import edu.productivity.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Data access class to crud users.
 * @author lvang
 */
public class UserData {
    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Insert user
     * @param user User to be inserted
     * @return the id of the inserted user
     */
    public int insert(User user) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        id = (int)session.save(user);
        transaction.commit();
        session.close();
        return id;
    }

    /**
     * Get user by first name
     * @param firstName User's first name
     * @return user User with the entered first name
     */
    public User getByFirstName(String firstName) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, firstName);
        session.close();
        return user;
    }
    /**
     * Update user
     * @param user User to be inserted or updated
     */
    public void saveOrUpdate(User user) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(user);
        session.close();
    }

    /**
     * Delete a user
     * @param user User to be deleted
     */
    public void delete(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    /**
     * Returns a list of all the users
     * @return list of all the users
     */
    public List<User> getAll() {

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery( User.class );
        Root<User> root = query.from(User.class);
        List<User> users = session.createQuery( query ).getResultList();

        logger.debug("The list of users " + users);
        session.close();

        return users;
    }
}
