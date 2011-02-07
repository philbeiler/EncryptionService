package com.beilers.service.dao.password;

public class PasswordKeyInfo {

    private String userId;
    private String key;

    public PasswordKeyInfo(final String userId, final String key) {
        super();
        this.userId = userId;
        this.key = key;
    }

    public PasswordKeyInfo() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }
}
