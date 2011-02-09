package com.beilers.service.dao.key;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beilers.encryption.diffiehellman.helper.KeyPairHelper;
import com.beilers.service.dao.KeyDaoInterface;

public class PublicKeyDao implements KeyDaoInterface {

    private static final File   DATABASE = new File(System.getProperty("java.io.tmpdir"), "PUBLIC.KEYS");
    private static final Logger LOGGER   = LoggerFactory.getLogger(KeyPairHelper.class);

    @Override
    public String find(final String userId) {
        try {
            if (DATABASE.exists()) {
                final Properties p = new Properties();
                p.load(new FileInputStream(DATABASE));
                return p.getProperty(userId);
            }
        }
        catch (final Exception e) {
            LOGGER.error("Unable to find public key", e);
        }
        return null;
    }

    @Override
    public void save(final PublicKeyInfo publicKeyInfo) {
        final Properties p = new Properties();
        try {
            if (DATABASE.exists()) {
                p.load(new FileInputStream(DATABASE));
            }
            p.put(publicKeyInfo.getUserid(), publicKeyInfo.getKey());
            p.store(new FileOutputStream(DATABASE), "-- no comment --");
        }
        catch (final Exception e) {
            LOGGER.error("Unable to save public key information", e);
        }
    }

    @Override
    public void save(final PrivateKeyInfo privateKeyInfo) {
        final Properties p = new Properties();
        try {
            if (DATABASE.exists()) {
                p.load(new FileInputStream(DATABASE));
            }
            p.put("SYSTEM.PRIVATE", privateKeyInfo.getKey());
            p.store(new FileOutputStream(DATABASE), "-- no comment --");
        }
        catch (final Exception e) {
            LOGGER.error("Unable to save private key information", e);
        }
    }

}
