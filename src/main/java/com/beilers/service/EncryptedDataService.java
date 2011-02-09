package com.beilers.service;

import com.beilers.service.dao.PayloadDaoInterface;
import com.beilers.service.dao.payload.PayloadRequest;

public class EncryptedDataService {

    private PayloadDaoInterface payloadDao;

    public void setPayloadDao(final PayloadDaoInterface passwordDao) {
        this.payloadDao = passwordDao;
    }

    public void save(final String userId, final String key, final String password) {
        // Need to implement
    }

    public String find(final String userId, final String key) {
        return payloadDao.find(new PayloadRequest(userId, key));
    }
}
