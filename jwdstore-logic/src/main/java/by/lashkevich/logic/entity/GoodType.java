package by.lashkevich.logic.entity;

import java.util.Objects;

/**
 * The type Good type.
 * @author Roman Lashkevich
 */
public class GoodType {
    private short id;
    private String name;

    /**
     * Instantiates a new Good type.
     *
     * @param id   the id
     * @param name the name
     */
    public GoodType(short id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Instantiates a new Good type.
     */
    public GoodType() {
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(short id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodType goodType = (GoodType) o;
        return id == goodType.id &&
                Objects.equals(name, goodType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "GoodType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
