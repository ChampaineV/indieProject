package edu.productivity.test.util;

import edu.productivity.utilities.PropertiesLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Database implements PropertiesLoader {
    private static Database instance = new Database();
    private final Logger logger = LogManager.getLogger(this.getClass());

    private Properties properties;
    private Connection connection;

    /** private constructor prevents instantiating this class anywhere else
     **/
    private Database() {
        loadProperties();
    }

    /** load the properties file containing the driver, connection url, userid and pwd.
     *
     */
    private void loadProperties() {
        properties = new Properties();
        try {
            properties = loadProperties("/database.properties");
        } catch (IOException ioe) {
            logger.error("Database.loadProperties()...Cannot load the properties file", ioe);
        } catch (Exception e) {
            logger.error("Database.loadProperties()...", e);
        }

    }

    /** get the only Database object available
     @return the single database object
     */
    public static Database getInstance() {
        return instance;
    }

    /** get the database connection
     @return the database connection
     */
    public Connection getConnection() {
        return connection;
    }

    /** attempt to connect to the database
     */
    public void connect() throws Exception {
        if (connection != null)
            return;

        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw new Exception("Database.connect()... Error: MySQL Driver not found");
        }

        String url = properties.getProperty("url");
        connection = DriverManager.getConnection(url, properties.getProperty("username"),  properties.getProperty("password"));
    }

    /** close and clean up the database connection
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Cannot close connection", e);
            }
        }

        connection = null;
    }

    /**
     * Run the sql.
     *
     * @param sqlFile the sql file to be read and executed line by line
     */
    public void runSQL(String sqlFile) {

        Statement stmt = null;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(sqlFile);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)))  {

            connect();
            stmt = connection.createStatement();
            while (true) {
                String sql = br.readLine();
                if (sql == null) {
                    break;
                }
                stmt.executeUpdate(sql);
            }

        } catch (SQLException se) {
            logger.error("SQL Exception", se);
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            disconnect();
        }

    }
}
