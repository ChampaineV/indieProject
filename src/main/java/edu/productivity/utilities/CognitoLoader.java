package edu.productivity.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Properties;

@WebServlet(
        name = "applicationStartup",
        urlPatterns = { "/applicationStartup" },
        loadOnStartup = 1
)
public class CognitoLoader extends HttpServlet implements PropertiesLoader {
    String CLIENT_ID;
    String CLIENT_SECRET;
    String OAUTH_URL;
    String LOGIN_URL;
    String REDIRECT_URL;
    String REGION;
    String POOL_ID;
    private final Logger logger = LogManager.getLogger(this.getClass());
    Properties properties;
    /**
     * Read in the cognito props file and get/set the client id, secret, and required urls
     * for authenticating a user.
     */
    // TODO This code appears in a couple classes, consider using a startup servlet similar to adv java project
    public void init() throws ServletException {
        try {
            properties = loadProperties("/cognito.properties");
            CLIENT_ID = properties.getProperty("client.id");
            CLIENT_SECRET = properties.getProperty("client.secret");
            OAUTH_URL = properties.getProperty("oauthURL");
            LOGIN_URL = properties.getProperty("loginURL");
            REDIRECT_URL = properties.getProperty("redirectURL");
            REGION = properties.getProperty("region");
            POOL_ID = properties.getProperty("poolId");

            ServletContext context = getServletContext();
            context.setAttribute("properties",properties);
            context.setAttribute("client.id",CLIENT_ID);
            context.setAttribute("client.secret",CLIENT_SECRET);
            context.setAttribute("oauthURL",OAUTH_URL);
            context.setAttribute("loginURL",LOGIN_URL);
            context.setAttribute("redirectURL",REDIRECT_URL);
            context.setAttribute("region",REGION);
            context.setAttribute("poolId",POOL_ID);
        } catch (IOException ioException) {
            logger.error("Cannot load properties..." + ioException.getMessage(), ioException);
        } catch (Exception e) {
            logger.error("Error loading properties" + e.getMessage(), e);
        }
    }
}
