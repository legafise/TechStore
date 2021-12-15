package by.lashkevich.logic.dao.reader.impl;

import by.lashkevich.logic.dao.reader.PropertiesReader;

import java.util.Properties;

public class DataBasePropertiesReader {
    private static final String PROPERTIES_FILE_PATH = "C:\\IdeaProjects\\JWDStore\\jwdstore-logic\\src\\main\\resources\\db_config.properties";
    private static final String URL_KEY = "url";
    private static final String DRIVER_KEY = "driverName";
    private final Properties properties;

    public DataBasePropertiesReader() {
        properties = PropertiesReader.readProperties(PROPERTIES_FILE_PATH);
    }

    public Properties readProperties() {
        return properties;
    }

    public String readUrl() {
        return properties.getProperty(URL_KEY);
    }

    public String readDriverName() {
        return properties.getProperty(DRIVER_KEY);
    }
}
