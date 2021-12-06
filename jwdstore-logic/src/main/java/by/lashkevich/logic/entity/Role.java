package by.lashkevich.logic.entity;

import java.util.Arrays;

/**
 * The enum Role.
 * @author Roman Lashkevich
 */
public enum Role {
    /**
     * Admin role.
     */
    ADMIN(1, "admin"),
    /**
     * Moder role.
     */
    MODER(2, "moder"),
    /**
     * User role.
     */
    USER(3, "user"),
    /**
     * Banned role.
     */
    BANNED(4, "banned");

    private final int number;
    private final String name;

    Role(int number, String name) {
        this.number = number;
        this.name = name;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
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
     * Find role by number role.
     *
     * @param roleNumber the role number
     * @return the role
     */
    public static Role findRoleByNumber(int roleNumber) {
        return Arrays.stream(Role.values())
                .filter(currentRole -> currentRole.getNumber() == roleNumber)
                .findFirst()
                .orElse(USER);
    }

    /**
     * Find role by name role.
     *
     * @param roleName the role name
     * @return the role
     */
    public static Role findRoleByName(String roleName) {
        return Arrays.stream(Role.values())
                .filter(currentRole -> currentRole.getName().equals(roleName.toLowerCase()))
                .findFirst()
                .orElse(USER);
    }
}
