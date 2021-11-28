package by.lashkevich.web.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class ContactsTag extends SimpleTagSupport {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CONTACT_INFO_MESSAGE = "techstore@gmail.com / +3752993754214";

    @Override
    public void doTag() {
        try {
            getJspContext().getOut().write(CONTACT_INFO_MESSAGE);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
