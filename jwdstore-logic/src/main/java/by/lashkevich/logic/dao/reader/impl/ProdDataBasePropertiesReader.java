package by.lashkevich.logic.dao.reader.impl;

import by.lashkevich.logic.dao.reader.DataBasePropertiesReader;
import by.lashkevich.logic.dao.reader.PropertiesReaderException;

import java.io.File;
import java.util.Objects;
import java.util.Properties;

public class ProdDataBasePropertiesReader implements DataBasePropertiesReader {
    private static final String PROPERTIES_FILE_NAME = "db_config.properties";

    @Override
    public Properties readProperties() throws PropertiesReaderException {
        return readProperties(new File(Objects.requireNonNull(getClass().getClassLoader()
                .getResource(PROPERTIES_FILE_NAME)).getFile()).getAbsolutePath());
    }
}
