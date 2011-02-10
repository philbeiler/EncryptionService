package com.beilers.service.dao.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beilers.encryption.diffiehellman.helper.KeyPairHelper;

public class PrototypePropertyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyPairHelper.class);
    private final File          propertyFile;

    protected PrototypePropertyDao(final String filename) {
        final File userTmp = new File("/tmp", System.getProperty("user.name"));
        if (!userTmp.exists()) {
            userTmp.mkdirs();
        }
        propertyFile = new File(userTmp, filename);
    }

    public String find(final String key) {
        try {
            LOGGER.info("Accessing properties from file [{}]", propertyFile);
            if (propertyFile.exists()) {
                final Properties p = new Properties();
                p.load(new FileInputStream(propertyFile));
                return p.getProperty(key);
            }
        }
        catch (final Exception e) {
            LOGGER.error("Unable to find public " + key, e);
        }
        return null;
    }

    public void save(final String key, final String value) {
        final Properties p = new Properties();
        try {
            LOGGER.info("Saving properties file [{}]", propertyFile);
            if (propertyFile.exists()) {
                p.load(new FileInputStream(propertyFile));
            }
            p.put(key, value);
            p.store(new FileOutputStream(propertyFile), "-- no comment --");
        }
        catch (final Exception e) {
            LOGGER.error("Unable to save information", e);
        }
    }

    public void remove(final String key) {
        final Properties p = new Properties();
        try {
            LOGGER.info("Removing properties from file [{}]", propertyFile);
            if (propertyFile.exists()) {
                p.load(new FileInputStream(propertyFile));
            }
            p.remove(key);
            p.store(new FileOutputStream(propertyFile), "-- no comment --");
        }
        catch (final Exception e) {
            LOGGER.error("Unable to remove information", e);
        }
    }
}
