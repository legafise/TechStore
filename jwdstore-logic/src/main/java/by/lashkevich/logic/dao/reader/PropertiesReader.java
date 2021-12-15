package by.lashkevich.logic.dao.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesReader {
    private PropertiesReader() {
    }

    public static Properties readProperties(String propertiesFilePath) throws PropertiesReaderException {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(propertiesFilePath);
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new PropertiesReaderException(e);
        }

        return properties;
    }
}
