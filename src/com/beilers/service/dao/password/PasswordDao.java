package com.beilers.service.dao.password;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.beilers.service.dao.PasswordDaoInterface;

public class PasswordDao implements PasswordDaoInterface {

    private static final File DATABASE = new File(System.getProperty("java.io.tmpdir"), "PASSWORDS.TXT");

    @Override
    public String find(final PasswordKeyInfo passwordKeyInfo) {
        try {
            if (DATABASE.exists()) {
                final Properties p = new Properties();
                p.load(new FileInputStream(DATABASE));
                final String key = passwordKeyInfo.getUserId() + "." + passwordKeyInfo.getKey();
                return p.getProperty(key);
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void save(final PasswordInfo passwordInfo) {
        final Properties p = new Properties();
        try {
            if (DATABASE.exists()) {
                p.load(new FileInputStream(DATABASE));
            }
            p.put(passwordInfo.getUserId() + "." + passwordInfo.getKey(), passwordInfo.getPassword());
            p.store(new FileOutputStream(DATABASE), "-- no comment --");
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
