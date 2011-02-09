package com.beilers.service.dao.payload;

import com.beilers.service.dao.PayloadDaoInterface;
import com.beilers.service.dao.example.PrototypePropertyDao;

public class PayloadDao extends PrototypePropertyDao implements PayloadDaoInterface {

    public PayloadDao() {
        super("PRIVATE.DATA.STORAGE.TXT");
    }

    @Override
    public void save(final Payload request) {
        save(request.getUserId() + "." + request.getKey(), request.getValue());
    }

    @Override
    public String find(final PayloadRequest request) {
        return find(request.getUserId() + "." + request.getKey());
    }

    @Override
    public void remove(final PayloadRequest request) {
        remove(request.getUserId() + "." + request.getKey());
    }
}
