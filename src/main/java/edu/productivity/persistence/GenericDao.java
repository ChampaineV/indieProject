package edu.productivity.persistence;

import edu.productivity.entity.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

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
     * Return an open session from the SessionFactory
     * @return
     */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();

    }

}
