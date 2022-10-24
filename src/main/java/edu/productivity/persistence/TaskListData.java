package edu.productivity.persistence;

import edu.productivity.entity.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Data access class to crud tasks.
 * @author lvang
 */
public class TaskListData {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    /**
     * Insert task
     * @param task Task to be inserted
     * @return the id of the inserted task
     */
    public int insert(Task task) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        id = (int)session.save(task);
        transaction.commit();
        session.close();
        return id;
    }

    /**
     * Get task by id
     * @param id Task's id
     * @return task Task with the id
     */
    public Task getById(int id) {
        Session session = sessionFactory.openSession();
        Task task = session.get(Task.class, id);
        session.close();
        return task;
    }
    /**
     * Update task
     * @param task Task to be inserted or updated
     */
    public void saveOrUpdate(Task task) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(task);
        session.close();
    }

    /**
     * Delete a task
     * @param task Task to be deleted
     */
    public void delete(Task task) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(task);
        transaction.commit();
        session.close();
    }

    public List<Task> getTasksByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for task with {} = {}",  propertyName, value);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> query = builder.createQuery( Task.class );
        Root<Task> root = query.from(Task.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<Task> tasks = session.createQuery( query ).getResultList();
        session.close();
        return tasks;
    }

    /**
     * Returns a list of all the tasks
     * @return list of all the tasks
     */
    public List<Task> getAllTasks() {

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> query = builder.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        List<Task> tasks = session.createQuery(query).getResultList();

        logger.debug("The list of tasks " + tasks);
        session.close();

        return tasks;
    }
}
