package test.com.beilers.encryption;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.After;
import org.junit.Test;

import com.beilers.encryption.diffiehellman.helper.ByteHelper;
import com.beilers.encryption.diffiehellman.helper.KeyPairHelper;
import com.beilers.encryption.diffiehellman.parameters.DefaultParameters;

public class KeyPairHelperTest {

    private File publicKeyFile  = null;
    private File privateKeyFile = null;

    @After
    public void done() {
        if (publicKeyFile != null) {
            publicKeyFile.delete();
        }
        if (privateKeyFile != null) {
            privateKeyFile.delete();
        }
    }

    @Test
    public void keys() {
        final KeyPairHelper keyPairHelper = new KeyPairHelper();

        final KeyPair keyPair = keyPairHelper.generate(new DefaultParameters().generate());
        assertNotNull(keyPair);
        final String publicKeyString = keyPairHelper.generatePublicKey(keyPair);
        assertNotNull(publicKeyString);
        final String privateKeyString = keyPairHelper.generatePrivateKey(keyPair);
        assertNotNull(privateKeyString);

        keyPairHelper.generateKeyFiles(keyPair, "SYSTEM", new File("."));
        publicKeyFile = new File("SYSTEM.public.key");
        privateKeyFile = new File("SYSTEM.private.key");
        assertNotNull(publicKeyFile);
        assertNotNull(privateKeyFile);
        assertTrue(publicKeyFile.length() + " Length", publicKeyFile.length() >= 590);

        final PublicKey publicKey = keyPairHelper.loadPublicKeyFile(publicKeyFile);
        assertNotNull(publicKey);

        assertEquals(publicKeyString, new ByteHelper().toHex(publicKey.getEncoded()));

        assertTrue(privateKeyString.length() + " Length", publicKeyFile.length() >= 460);

        final PrivateKey privateKey = keyPairHelper.loadPrivateKeyFile(privateKeyFile);
        assertNotNull(privateKey);
        assertEquals(privateKeyString, new ByteHelper().toHex(privateKey.getEncoded()));

    }
}
