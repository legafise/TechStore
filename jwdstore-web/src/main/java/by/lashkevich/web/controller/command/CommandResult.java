package by.lashkevich.web.controller.command;

import java.util.Objects;

/**
 * The type Command result.
 * @author Roman Lashkevich
 */
public class CommandResult {
    /**
     * The enum Response type.
     */
    public enum ResponseType {
        /**
         * Forward response type.
         */
        FORWARD,
        /**
         * Redirect response type.
         */
        REDIRECT
    }

    private ResponseType responseType;
    private String page;

    /**
     * Instantiates a new Command result.
     */
    public CommandResult() {
        page = "";
    }

    /**
     * Instantiates a new Command result.
     *
     * @param responseType the response type
     * @param page         the page
     */
    public CommandResult(ResponseType responseType, String page) {
        this.responseType = responseType;
        this.page = page;
    }

    /**
     * Gets response type.
     *
     * @return the response type
     */
    public ResponseType getResponseType() {
        return responseType;
    }

    /**
     * Sets response type.
     *
     * @param responseType the response type
     */
    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandResult that = (CommandResult) o;
        return responseType == that.responseType &&
                Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseType, page);
    }
}
