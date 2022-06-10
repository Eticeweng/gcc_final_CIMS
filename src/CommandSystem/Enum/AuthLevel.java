package CommandSystem.Enum;

import UI.ColorText;

public enum AuthLevel {
    ADMIN(new ColorText().setText("Administrator")
            .setForegroundColor(255, 255, 255)
            .setBackgroundColor(227, 60, 48)
            .make()),
    COMMON("User"),
    VISITOR("Visitor");

    private final String displayName;

    AuthLevel(String displayName) {
        this.displayName = displayName;
    }


    public static AuthLevel parse(String s) {
        if ("admin".equals(s.toLowerCase())) {
            return ADMIN;
        }
        return COMMON;
    }

    public String getAuthName() {
        return displayName;
    }

}

