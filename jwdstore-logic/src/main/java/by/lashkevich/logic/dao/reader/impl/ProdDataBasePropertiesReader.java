package by.lashkevich.logic.dao.reader.impl;

import by.lashkevich.logic.dao.reader.DataBasePropertiesReader;
import by.lashkevich.logic.dao.reader.PropertiesReaderException;

import java.util.Properties;

public class ProdDataBasePropertiesReader implements DataBasePropertiesReader {
    private static final String PROPERTIES_FILE_PATH = "C:\\IdeaProjects\\JWDStore\\jwdstore-logic\\src\\main\\resources\\db_config.properties";

    @Override
    public Properties readProperties() throws PropertiesReaderException {
        return readProperties(PROPERTIES_FILE_PATH);
    }
}
