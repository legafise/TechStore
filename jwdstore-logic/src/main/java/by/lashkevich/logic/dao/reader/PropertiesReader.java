package by.lashkevich.logic.dao.reader;

import by.lashkevich.logic.dao.DaoException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public interface PropertiesReader {
    Properties readProperties() throws PropertiesReaderException;

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
