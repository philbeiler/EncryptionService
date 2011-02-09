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
import com.beilers.encryption.exception.EncryptionException;

public class EncryptedDataClient {

    private static final File TMP_DIRECTORY     = new File(System.getProperty("java.io.tmpdir"), System.getProperty("user.name"));
    private static final File PRIVATE_KEY_STORE = new File(TMP_DIRECTORY, "TESTID.PRIVATE.KEY");
    private String            encryptedDataServiceURI;

    public String getEncryptedDataServiceURI() {
        return encryptedDataServiceURI;
    }

    public void setEncryptedDataServiceURI(final String encryptedDataService) {
        this.encryptedDataServiceURI = encryptedDataService;
    }

    private String requestPublicKey() {
        final DefaultHttpClient client = new DefaultHttpClient();

        try {
            final HttpPost method = new HttpPost(encryptedDataServiceURI + "/PublicKey");
            final HttpResponse response = client.execute(method);
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
            throw new EncryptionException("Invalid response from service");
        }
        catch (final Exception e) {
            throw new EncryptionException("Unsucessfull communication with service", e);
        }
        finally {
            client.getConnectionManager().shutdown();
        }
    }

    private String requestPassword(final String userid, final String key) {
        final DefaultHttpClient client = new DefaultHttpClient();

        try {
            final HttpPost method = new HttpPost(encryptedDataServiceURI + "/EncryptedData");
            final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("userid", userid));
            nvps.add(new BasicNameValuePair("key", key));

            method.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

            final HttpResponse response = client.execute(method);
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
            throw new EncryptionException("Invalid response from service");
        }
        catch (final Exception e) {
            throw new EncryptionException("Unsucessfull communication with service", e);
        }
        finally {
            client.getConnectionManager().shutdown();
        }
    }

    public String requestData(final String userId, final String key) {

        final String publicKey = requestPublicKey();
        if (publicKey != null) {
            final DiffieHellmanEncryption encrypter = new DiffieHellmanEncryption(PRIVATE_KEY_STORE, publicKey);
            final String encrypted = encrypter.encrypt("database.password");
            return requestPassword(userId, encrypted);
        }
        return null;
    }
}
