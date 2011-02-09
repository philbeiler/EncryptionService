package com.beilers.service;

import java.security.KeyPair;

import com.beilers.encryption.diffiehellman.ParameterInterface;
import com.beilers.encryption.diffiehellman.helper.KeyPairHelper;
import com.beilers.service.dao.KeyDaoInterface;
import com.beilers.service.dao.key.PrivateKeyInfo;
import com.beilers.service.dao.key.PublicKeyInfo;

public class KeyMakerService {

    private KeyDaoInterface    publicKeyDAO;
    private ParameterInterface parameters;

    public String find(final String userId) {
        return publicKeyDAO.find(userId);
    }

    public String create(final String userId) {
        final KeyPairHelper keyPairHelper = new KeyPairHelper();
        final KeyPair keyPair = keyPairHelper.generate(parameters);
        final PublicKeyInfo pki = new PublicKeyInfo();
        pki.setUserid(userId);
        pki.setKey(keyPairHelper.generatePublicKey(keyPair));
        publicKeyDAO.save(pki);
        return keyPairHelper.generatePrivateKey(keyPair);
    }

    public String getPrivateKey() {
        return publicKeyDAO.find("SYSTEM.PRIVATE");
    }

    public void createSystemId() {
        final KeyPairHelper keyPairHelper = new KeyPairHelper();
        final KeyPair keyPair = keyPairHelper.generate(parameters);
        final PublicKeyInfo publicKeyInfo = new PublicKeyInfo();
        publicKeyInfo.setUserid("SYSTEM.PUBLIC");
        publicKeyInfo.setKey(keyPairHelper.generatePublicKey(keyPair));
        publicKeyDAO.save(publicKeyInfo);
        final PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo();
        privateKeyInfo.setKey(keyPairHelper.generatePrivateKey(keyPair));
        publicKeyDAO.save(privateKeyInfo);
    }

    public void setPublicKeyDAO(final KeyDaoInterface publicKeyDAO) {
        this.publicKeyDAO = publicKeyDAO;
    }

    public void setParameters(final ParameterInterface parameters) {
        this.parameters = parameters;
    }

}
