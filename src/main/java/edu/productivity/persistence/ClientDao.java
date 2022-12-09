package edu.productivity.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

public class ClientDao {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Get entity by id
     * @param client entity's id to search by
     * @return as entity
     */
    public WebTarget getUserInfoById(Client client) {
        WebTarget target =
                client.target("https://productivity-app-project.auth.us-east-2.amazoncognito.com/oauth2/userInfo");
        return target;
    }
}
