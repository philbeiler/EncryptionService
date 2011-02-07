package com.beilers.service.dao.key;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.beilers.service.dao.KeyDaoInterface;

public class PublicKeyDao implements KeyDaoInterface {

    private static final File DATABASE = new File(System.getProperty("java.io.tmpdir"), "PUBLIC.KEYS");

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

}
