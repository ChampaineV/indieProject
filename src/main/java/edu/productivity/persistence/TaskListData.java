package edu.productivity.persistence;

import edu.productivity.entity.TaskList;
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
 * Data access class to crud taskLists.
 * @author lvang
 */
public class TaskListData {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    /**
     * Insert taskList
     * @param taskList TaskList to be inserted
     * @return the id of the inserted taskList
     */
    public int insert(TaskList taskList) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        id = (int)session.save(taskList);
        transaction.commit();
        session.close();
        return id;
    }

    /**
     * Get taskList by id
     * @param id TaskList's id
     * @return taskList TaskList with the id
     */
    public TaskList getById(int id) {
        Session session = sessionFactory.openSession();
        TaskList taskList = session.get(TaskList.class, id);
        session.close();
        return taskList;
    }
    /**
     * Update taskList
     * @param taskList TaskList to be inserted or updated
     */
    public void saveOrUpdate(TaskList taskList) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(taskList);
        session.close();
    }

    /**
     * Delete a taskList
     * @param taskList TaskList to be deleted
     */
    public void delete(TaskList taskList) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(taskList);
        transaction.commit();
        session.close();
    }

    public List<TaskList> getTaskListsByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for taskList with {} = {}",  propertyName, value);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<TaskList> query = builder.createQuery( TaskList.class );
        Root<TaskList> root = query.from(TaskList.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<TaskList> tasks = session.createQuery( query ).getResultList();
        session.close();
        return tasks;
    }

    /**
     * Returns a list of all the tasks
     * @return list of all the tasks
     */
    public List<TaskList> getAllTaskLists() {

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<TaskList> query = builder.createQuery(TaskList.class);
        Root<TaskList> root = query.from(TaskList.class);
        List<TaskList> taskLists = session.createQuery(query).getResultList();

        logger.debug("The list of taskLists " + taskLists);
        session.close();

        return taskLists;
    }
}
