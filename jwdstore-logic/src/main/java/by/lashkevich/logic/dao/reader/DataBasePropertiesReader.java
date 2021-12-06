package by.lashkevich.logic.dao.reader;

/**
 * The interface Data base properties reader.
 * @author Roman Lashkevich
 * @see PropertiesReader
 */
public interface DataBasePropertiesReader extends PropertiesReader {
    /**
     * The constant URL_KEY.
     */
    String URL_KEY = "url";
    /**
     * The constant DRIVER_KEY.
     */
    String DRIVER_KEY = "driverName";

    /**
     * Read url string.
     *
     * @return the string
     * @throws PropertiesReaderException the properties reader exception
     */
    default String readUrl() throws PropertiesReaderException {
     return readProperties().getProperty(URL_KEY);
    }

    /**
     * Read driver name string.
     *
     * @return the string
     * @throws PropertiesReaderException the properties reader exception
     */
    default String readDriverName() throws PropertiesReaderException {
        return readProperties().getProperty(DRIVER_KEY);
    }
}
