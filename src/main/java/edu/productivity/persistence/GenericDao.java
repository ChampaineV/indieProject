package edu.productivity.persistence;

import edu.productivity.entity.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

public class GenericDao<T> {
    private Class<T> type;
    private final Logger logger = LogManager.getLogger(this.class());

    /**
     * Instantiates Generic DAO
     * @param type
     */
    public GenericDao(Class<T> type) {
        this.type = type;
    }

    /**
     * Insert an entity by id
     *
     * @param entity entity to be inserted
     * @return entity's id that was inserted
     */
    public int insert(T entity) {
        int id = 0;
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        id = (int)session.save(entity);
        transaction.commit();
        session.close();
        return id;
    }

    /**
     * Update an entity
     * @param entity entity to be inserted or updated
     */
    public void saveOrUpdate(T entity) {
        Session session = getSession();
        session.saveOrUpdate(entity);
        session.close();
    }

    /**
     * Get entity by id
     * @param id entity's id to search by
     * @return as entity
     */
    public <T>T getById(int id) {
        Session session = getSession();
        T entity = (T)session.get(type, id);
        session.close();
        return entity;
    }

    /**
     * Deletes the entity
     * @param entity entity to be deleted
     */
    public void delete(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }

    public List<T> getByPropertyLike(String propertyName, String value) {
        Session session = getSession();

        logger.debug("Searching for task with {} = {}",  propertyName, value);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<T> list = session.createQuery( query ).getResultList();
        session.close();
        return list;
    }

    /**
     * Return an open session from the SessionFactory
     * @return
     */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();

    }

}
