package edu.productivity.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.Map;

public interface PropertiesLoader {
    Logger logger = LogManager.getLogger(PropertiesLoader.class);
    default Properties loadProperties(String propertiesFilePath) throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFilePath));
        } catch (IOException ioException) {
            logger.error("IOException ", ioException);
        } catch (Exception exception) {
            logger.error("Exception", exception);
        }
        return properties;
    }
}
