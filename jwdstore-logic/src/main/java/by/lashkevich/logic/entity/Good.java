package by.lashkevich.logic.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Good.
 * @author Roman Lashkevich
 */
public class Good implements Entity {
    private long id;
    private String name;
    private BigDecimal price;
    private String description;
    private GoodType type;
    private String imgName;
    private List<Review> reviews;

    /**
     * Instantiates a new Good.
     */
    public Good() {
        reviews = new ArrayList<>();
        imgName = "";
    }

    /**
     * Instantiates a new Good.
     *
     * @param id          the id
     * @param name        the name
     * @param price       the price
     * @param description the description
     * @param type        the type
     * @param imgName     the img name
     */
    public Good(long id, String name, BigDecimal price, String description,
                GoodType type, String imgName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.imgName = imgName;
    }

    /**
     * Instantiates a new Good.
     *
     * @param name        the name
     * @param price       the price
     * @param description the description
     * @param type        the type
     * @param imgName     the img name
     */
    public Good(String name, BigDecimal price, String description,
                GoodType type, String imgName) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.imgName = imgName;
        this.reviews = new ArrayList<>();
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public GoodType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(GoodType type) {
        this.type = type;
    }

    /**
     * Gets img name.
     *
     * @return the img name
     */
    public String getImgName() {
        return imgName;
    }

    /**
     * Sets img name.
     *
     * @param imgURL the img url
     */
    public void setImgName(String imgURL) {
        this.imgName = imgURL;
    }

    /**
     * Gets reviews.
     *
     * @return the reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets reviews.
     *
     * @param reviews the reviews
     */
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
