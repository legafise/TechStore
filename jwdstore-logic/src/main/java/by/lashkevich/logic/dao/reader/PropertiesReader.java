package by.lashkevich.logic.dao.reader;

import by.lashkevich.logic.dao.DaoException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The interface Properties reader.
 * @author Roman Lashkevich
 */
public interface PropertiesReader {
    /**
     * Read properties properties.
     *
     * @return the properties
     * @throws PropertiesReaderException the properties reader exception
     */
    Properties readProperties() throws PropertiesReaderException;

    /**
     * Read properties properties.
     *
     * @param path the path
     * @return the properties
     * @throws PropertiesReaderException the properties reader exception
     */
    default Properties readProperties(String path) throws PropertiesReaderException {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new PropertiesReaderException(e.getMessage());
        }

        return properties;
    }
}
