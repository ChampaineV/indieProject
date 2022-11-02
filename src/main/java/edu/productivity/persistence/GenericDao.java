package edu.productivity.persistence;

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
     * Return an open session from the SessionFactory
     * @return
     */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();

    }

}
