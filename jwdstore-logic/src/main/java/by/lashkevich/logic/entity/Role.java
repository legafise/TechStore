package by.lashkevich.logic.entity;

import java.util.Arrays;

public enum Role {
    ADMIN(1),
    MODER(2),
    USER(3);

    private int roleNumber;

    Role(int roleNumber) {
        this.roleNumber = roleNumber;
    }

    public int getRoleNumber() {
        return roleNumber;
    }

    public static Role findRole(int roleNumber) {
        return Arrays.stream(Role.values())
                .filter(currentRole -> currentRole.getRoleNumber() == roleNumber)
                .findFirst()
                .orElse(USER);
    }
}
