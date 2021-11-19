package by.lashkevich.logic.entity;

import java.util.Objects;

public class Review implements Entity {
    private long id;
    private float rate;
    private String content;
    private User author;

    public Review() {
    }

    public Review(long id, float rate, String content, User author) {
        this.id = id;
        this.rate = rate;
        this.content = content;
        this.author = author;
    }

    public Review(float rate, String content, User author) {
        this.rate = rate;
        this.content = content;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

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
