package com.beilers.service.dao;

import com.beilers.service.dao.payload.PayloadRequest;
import com.beilers.service.dao.payload.Payload;

public interface PayloadDaoInterface {

    String find(final PayloadRequest request);

    void save(final Payload request);

    void remove(final PayloadRequest request);
}
