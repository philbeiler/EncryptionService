package test.com.beilers.encryption;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.security.KeyPair;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.com.beilers.UnitTestHelper;

import com.beilers.encryption.diffiehellman.DiffieHellmanEncryption;
import com.beilers.encryption.diffiehellman.helper.KeyPairHelper;
import com.beilers.encryption.diffiehellman.parameters.DefaultParameters;

public class StoryBoardTest extends UnitTestHelper {

    private static final String APPLICATION        = "Application";
    private static final String ENCRYPTION_SERVICE = "EncryptionService";
    private static final String RESPONSE           = "topsecret";
    private static final String REQUEST_KEY        = "database.password";
    private static final Logger LOGGER             = LoggerFactory.getLogger(StoryBoardTest.class);

    @Test
    public void example() {
        final File uniqueTmpDirectory = getPersonalTemporaryDirectory();
        final KeyPairHelper keyPairHelper = new KeyPairHelper();
        // Generate User #1 Keys - Should be One Time Deal
        final KeyPair systemKeyPair = keyPairHelper.generate(new DefaultParameters());
        keyPairHelper.generateKeyFiles(systemKeyPair, ENCRYPTION_SERVICE, uniqueTmpDirectory);

        // Generate Application Id Key
        final KeyPair applicationKeyPair = keyPairHelper.generate(new DefaultParameters());
        keyPairHelper.generateKeyFiles(applicationKeyPair, APPLICATION, uniqueTmpDirectory);

        // Encrypt Application Request
        final DiffieHellmanEncryption appDHE = new DiffieHellmanEncryption(//
                new File(uniqueTmpDirectory, APPLICATION + ".private.key"), //
                new File(uniqueTmpDirectory, ENCRYPTION_SERVICE + ".public.key"));
        LOGGER.debug("Shared Secret [{}]", appDHE.getSharedSecret()); // Only to verify

        final String encryptedRequest = appDHE.encrypt(REQUEST_KEY);

        // Decrypt Application Request and encrypt response
        final DiffieHellmanEncryption pwmDHE = new DiffieHellmanEncryption(//
                new File(uniqueTmpDirectory, ENCRYPTION_SERVICE + ".private.key"), //
                new File(uniqueTmpDirectory, APPLICATION + ".public.key"));
        LOGGER.debug("Shared Secret [{}]", pwmDHE.getSharedSecret()); // Only to verify
        final String decryptedRequest = pwmDHE.decrypt(encryptedRequest);

        assertEquals(REQUEST_KEY, decryptedRequest);
        LOGGER.debug("Decrypted Request [{}]", decryptedRequest);
        final String encryptedResponse = pwmDHE.encrypt(RESPONSE);

        // Decrypt Response
        final String decryptedResponse = appDHE.decrypt(encryptedResponse);
        LOGGER.debug("Decrypted Response [{}]", decryptedResponse);
        assertEquals(RESPONSE, decryptedResponse);
    }
}
