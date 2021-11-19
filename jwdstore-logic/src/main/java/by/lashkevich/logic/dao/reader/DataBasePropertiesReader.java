package by.lashkevich.logic.dao.reader;

import by.lashkevich.logic.dao.DaoException;

import java.util.Properties;

public interface DataBasePropertiesReader extends PropertiesReader {
    String URL_KEY = "url";
    String DRIVER_KEY = "driverName";

    default String readUrl() throws PropertiesReaderException {
     return readProperties().getProperty(URL_KEY);
    }

    default String readDriverName() throws PropertiesReaderException {
        return readProperties().getProperty(DRIVER_KEY);
    }
}