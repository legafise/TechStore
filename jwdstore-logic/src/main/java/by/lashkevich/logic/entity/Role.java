package by.lashkevich.logic.entity;

import java.util.Arrays;

public enum Role {
    ADMIN(1, "admin"),
    MODER(2, "moder"),
    USER(3, "user"),
    BANNED(4, "banned");

    private final int number;
    private final String name;

    Role(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public static Role findRoleByNumber(int roleNumber) {
        return Arrays.stream(Role.values())
                .filter(currentRole -> currentRole.getNumber() == roleNumber)
                .findFirst()
                .orElse(USER);
    }

    public static Role findRoleByName(String roleName) {
        return Arrays.stream(Role.values())
                .filter(currentRole -> currentRole.getName().equals(roleName.toLowerCase()))
                .findFirst()
                .orElse(USER);
    }
}
