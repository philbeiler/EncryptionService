package com.beilers.service.dao.payload;

public class PayloadRequest {

    private String userId;
    private String key;

    public PayloadRequest(final String userId, final String key) {
        super();
        this.userId = userId;
        this.key = key;
    }

    public PayloadRequest() {
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
