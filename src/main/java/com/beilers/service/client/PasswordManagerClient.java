package com.beilers.service.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.beilers.encryption.diffiehellman.DiffieHellmanEncryption;

public class PasswordManagerClient {

    private static final File PRIVATE_KEY_STORE = new File(System.getProperty("java.io.tmpdir"), "TESTID.PRIVATE.KEY");

    private String requestPublicKey() {
        final DefaultHttpClient client = new DefaultHttpClient();

        try {
            final HttpPost method = new HttpPost("http://localhost:9090/EncryptionService/PublicKey");
            final HttpResponse response = client.execute(method);
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        finally {
            client.getConnectionManager().shutdown();
        }

        return null;
    }

    private String requestPassword(final String userid, final String key) {
        final DefaultHttpClient client = new DefaultHttpClient();

        try {
            final HttpPost method = new HttpPost("http://localhost:9090/EncryptionService/Password");
            final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("userid", userid));
            nvps.add(new BasicNameValuePair("key", key));

            method.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

            final HttpResponse response = client.execute(method);
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }

        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        finally {
            client.getConnectionManager().shutdown();
        }

        return null;
    }

    public String getPassword(final String userId, final String key) {

        final String publicKey = requestPublicKey();
        if (publicKey != null) {
            final DiffieHellmanEncryption encrypter = new DiffieHellmanEncryption(PRIVATE_KEY_STORE, publicKey);
            final String encrypted = encrypter.encrypt("database.password");
            return requestPassword(userId, encrypted);
        }
        return null;
    }
}
