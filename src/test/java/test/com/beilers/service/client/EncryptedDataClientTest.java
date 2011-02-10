package test.com.beilers.service.client;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.com.beilers.UnitTestHelper;

import com.beilers.client.encryption.EncryptedDataClient;

public class EncryptedDataClientTest extends UnitTestHelper {

    private static final String URI                   = "http://localhost:8080/EncryptionService";
    private static final String DATABASE_PASSWORD_KEY = "database.password";
    private static final Logger LOGGER                = LoggerFactory.getLogger(EncryptedDataClientTest.class);

    @Test
    @Ignore
    public void simple() {
        final EncryptedDataClient encryptedDataClient = new EncryptedDataClient();
        encryptedDataClient.setEncryptedDataServiceURI(URI);

        LOGGER.info("Requesting Encrypted Information for [{}] from [{}] and received [{}]", new Object[]{DATABASE_PASSWORD_KEY,
                URI, encryptedDataClient.requestData("TESTID", DATABASE_PASSWORD_KEY)});

    }
}
