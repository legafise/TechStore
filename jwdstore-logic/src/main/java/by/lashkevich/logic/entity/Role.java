package by.lashkevich.logic.entity;

import java.util.Arrays;

public enum Role {
    ADMIN(1, "admin"),
    MODER(2, "moder"),
    USER(3, "user");

    private final int roleNumber;
    private final String roleName;

    Role(int roleNumber, String roleName) {
        this.roleNumber = roleNumber;
        this.roleName = roleName;
    }

    public int getRoleNumber() {
        return roleNumber;
    }

    public String getRoleName() {
        return roleName;
    }

    public static Role findRole(int roleNumber) {
        return Arrays.stream(Role.values())
                .filter(currentRole -> currentRole.getRoleNumber() == roleNumber)
                .findFirst()
                .orElse(USER);
    }
}
