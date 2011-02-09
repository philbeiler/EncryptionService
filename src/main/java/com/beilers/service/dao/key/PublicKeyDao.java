package com.beilers.service.dao.key;

import com.beilers.service.dao.KeyDaoInterface;
import com.beilers.service.dao.example.PrototypePropertyDao;

public class PublicKeyDao extends PrototypePropertyDao implements KeyDaoInterface {

    public PublicKeyDao() {
        super("PUBLIC.KEY.STORAGE.TXT");
    }

    @Override
    public void save(final PublicKeyInfo publicKeyInfo) {
        save(publicKeyInfo.getUserid(), publicKeyInfo.getKey());
    }

    @Override
    public void save(final PrivateKeyInfo privateKeyInfo) {
        save("SYSTEM.PRIVATE", privateKeyInfo.getKey());
    }
}
