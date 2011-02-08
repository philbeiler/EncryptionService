package test.com.beilers.service.client;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.com.beilers.UnitTestHelper;

import com.beilers.service.client.EncryptedDataClient;

public class EncryptedDataClientTest extends UnitTestHelper {

    private static final String DATABASE_PASSWORD_KEY = "database.password";
    private static final Logger LOGGER                = LoggerFactory.getLogger(EncryptedDataClientTest.class);

    @Test
    public void simple() {
        final EncryptedDataClient encryptedDataClient = new EncryptedDataClient();
        encryptedDataClient.setEncryptedDataServiceURI("http://localhost:9090/EncryptionService");

        LOGGER.info("Requesting Encrypted Information for [{}] and received [{}]",
                    DATABASE_PASSWORD_KEY,
                    encryptedDataClient.requestData("TESTID", DATABASE_PASSWORD_KEY));
    }
}
