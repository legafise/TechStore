package by.lashkevich.logic.entity;

import java.util.Objects;

/**
 * The type Review.
 * @author Roman Lashkevich
 */
public class Review implements Entity {
    private long id;
    private short rate;
    private String content;
    private User author;

    /**
     * Instantiates a new Review.
     */
    public Review() {
    }

    /**
     * Instantiates a new Review.
     *
     * @param id      the id
     * @param rate    the rate
     * @param content the content
     * @param author  the author
     */
    public Review(long id, short rate, String content, User author) {
        this.id = id;
        this.rate = rate;
        this.content = content;
        this.author = author;
    }

    /**
     * Instantiates a new Review.
     *
     * @param rate    the rate
     * @param content the content
     * @param author  the author
     */
    public Review(short rate, String content, User author) {
        this.rate = rate;
        this.content = content;
        this.author = author;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets rate.
     *
     * @return the rate
     */
    public short getRate() {
        return rate;
    }

    /**
     * Sets rate.
     *
     * @param rate the rate
     */
    public void setRate(short rate) {
        this.rate = rate;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id &&
                Float.compare(review.rate, rate) == 0 &&
                Objects.equals(content, review.content) &&
                Objects.equals(author, review.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rate, content, author);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rate=" + rate +
                ", content='" + content + '\'' +
                ", author=" + author +
                '}';
    }
}
