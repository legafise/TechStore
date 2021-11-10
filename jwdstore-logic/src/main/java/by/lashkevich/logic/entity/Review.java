package by.lashkevich.logic.entity;

import java.util.Objects;

public class Review implements Entity {
    private long id;
    private float grade;
    private String content;
    private User author;

    public Review(long id, float grade, String content, User author) {
        this.id = id;
        this.grade = grade;
        this.content = content;
        this.author = author;
    }

    public Review(float grade, String content, User author) {
        this.grade = grade;
        this.content = content;
        this.author = author;
    }

    public Review() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
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
                Float.compare(review.grade, grade) == 0 &&
                Objects.equals(content, review.content) &&
                Objects.equals(author, review.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grade, content, author);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", grade=" + grade +
                ", content='" + content + '\'' +
                ", author=" + author +
                '}';
    }
}
