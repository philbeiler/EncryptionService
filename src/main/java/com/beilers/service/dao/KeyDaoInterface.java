package com.beilers.service.dao;

import com.beilers.service.dao.key.PrivateKeyInfo;
import com.beilers.service.dao.key.PublicKeyInfo;

public interface KeyDaoInterface {

    void save(PublicKeyInfo publicKeyInfo);

    void save(PrivateKeyInfo privateKeyInfo);

    String find(String userId);
}
