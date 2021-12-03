package by.lashkevich.logic.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Good implements Entity {
    private long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String type;
    private String imgName;
    private List<Review> reviews;

    public Good() {
        reviews = new ArrayList<>();
    }

    public Good(long id, String name, BigDecimal price, String description,
                String type, String imgName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.imgName = imgName;
    }

    public Good(String name, BigDecimal price, String description,
                String type, String imgName) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.imgName = imgName;
        this.reviews = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgURL) {
        this.imgName = imgURL;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Good good = (Good) o;
        return id == good.id &&
                Objects.equals(name, good.name) &&
                Objects.equals(price, good.price) &&
                Objects.equals(description, good.description) &&
                Objects.equals(type, good.type) &&
                Objects.equals(imgName, good.imgName) &&
                Objects.equals(reviews, good.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, type, imgName, reviews);
    }

    @Override
    public String toString() {
        return "\nGood{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", imgURL='" + imgName + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}
