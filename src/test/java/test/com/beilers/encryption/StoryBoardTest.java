package test.com.beilers.encryption;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.security.KeyPair;

import javax.crypto.spec.DHParameterSpec;

import org.junit.Test;

import com.beilers.encryption.diffiehellman.DiffieHellmanEncryption;
import com.beilers.encryption.diffiehellman.helper.KeyPairHelper;
import com.beilers.encryption.diffiehellman.parameters.DefaultParameters;

public class StoryBoardTest {

    private static final String RESPONSE    = "topsecret";
    private static final String REQUEST_KEY = "database.password";

    @Test
    public void example() {

        final KeyPairHelper keyPairHelper = new KeyPairHelper();
        // Generate User #1 Keys - Should be One Time Deal
        final DHParameterSpec dhSpec = new DefaultParameters().generate();
        final KeyPair passwordManagerKeyPair = keyPairHelper.generate(dhSpec);
        keyPairHelper.generateKeyFiles(passwordManagerKeyPair, "PasswordManager", new File("."));

        // Generate Application Id Key
        final KeyPair applicationKeyPair = keyPairHelper.generate(dhSpec);
        keyPairHelper.generateKeyFiles(applicationKeyPair, "Application1", new File("."));

        // Encrypt Application Request
        final DiffieHellmanEncryption appDHE = new DiffieHellmanEncryption(new File("Application1.private.key"), //
                new File("PasswordManager.public.key"));
        System.out.println(appDHE.getSharedSecret()); // Only to verify

        final String encryptedRequest = appDHE.encrypt(REQUEST_KEY);

        // Decrypt Application Request and encrypt response
        final DiffieHellmanEncryption pwmDHE = new DiffieHellmanEncryption(new File("PasswordManager.private.key"), //
                new File("Application1.public.key"));
        System.out.println(pwmDHE.getSharedSecret()); // Only to verify
        final String decryptedRequest = pwmDHE.decrypt(encryptedRequest);

        assertEquals(REQUEST_KEY, decryptedRequest);
        System.out.println("Decrypted Request: " + decryptedRequest);
        final String encryptedResponse = pwmDHE.encrypt(RESPONSE);

        // Decrypt Response
        final String decryptedResponse = appDHE.decrypt(encryptedResponse);
        System.out.println("Decrypted Response: " + decryptedResponse);
        assertEquals(RESPONSE, decryptedResponse);

    }
}
