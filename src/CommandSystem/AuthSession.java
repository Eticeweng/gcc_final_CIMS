package CommandSystem;


import CommandSystem.Enum.AuthLevel;

import java.util.UUID;

public class AuthSession {
    private AuthLevel authLevel;
    private UUID uuid;
    private String loginBeacon;
    private String username;
    private String password;

    public AuthSession(AuthLevel authLevel, String loginBeacon, String username, String password) {
        this.authLevel = authLevel;
        this.uuid = UUID.nameUUIDFromBytes(loginBeacon.getBytes());
        this.loginBeacon = loginBeacon;
        this.username = username;
        this.password = password;
    }

    public AuthSession(String serialization){
        String[] fields = serialization.split(" ");
        this.authLevel = fields[0].equals("ADMIN") ? AuthLevel.ADMIN : AuthLevel.COMMON;
        this.uuid = UUID.fromString(fields[1]);
        this.loginBeacon = fields[2];
        this.username = fields[3];
        this.password = fields[4];
    }

    public AuthLevel getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(AuthLevel authLevel) {
        this.authLevel = authLevel;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLoginBeacon() {
        return loginBeacon;
    }

    public void setLoginBeacon(String loginBeacon) {
        this.loginBeacon = loginBeacon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s",
                this.authLevel.toString(),
                this.uuid.toString(),
                this.loginBeacon,
                this.username,
                this.password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthSession that = (AuthSession) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
