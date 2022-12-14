package app.application.model.dto;

public enum Role {


    ROLE_EMPLOYEE(2),
    ROLE_ADMIN(4),
    ROLE_LOGISTIC(3),
    ROLE_GUEST(1);

    public int getValue() {
        return value;
    }

    private int value;

    Role(int value) {
        this.value = value;
    }


}
