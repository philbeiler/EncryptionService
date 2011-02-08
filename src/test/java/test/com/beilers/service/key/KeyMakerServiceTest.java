package test.com.beilers.service.key;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import test.com.beilers.SpringUnitTestHelper;

import com.beilers.service.KeyMakerService;

public class KeyMakerServiceTest extends SpringUnitTestHelper {

    private static final String TESTID          = "TESTID";
    private final File          privateKeyStore = new File(getUniqueTemporaryDirectory(), TESTID + ".PRIVATE.KEY");

    @Resource
    private KeyMakerService     keyMakerService;

    @Test
    public void sampleUser() {
        assertNotNull(keyMakerService);
        final String privateKey = keyMakerService.create(TESTID);
        assertNotNull(privateKey);
        try {
            FileUtils.writeStringToFile(privateKeyStore, privateKey);
        }
        catch (final IOException e) {
            fail();
        }

        final String savedPublicKey = keyMakerService.find(TESTID);
        assertNotNull(savedPublicKey);
    }

    @Test
    public void systemUser() {
        assertNotNull(keyMakerService);
        keyMakerService.createSystemId();

        final String savedPublicKey = keyMakerService.find("SYSTEM.PUBLIC");
        assertNotNull(savedPublicKey);
        final String savedPrivateKey = keyMakerService.find("SYSTEM.PRIVATE");
        assertNotNull(savedPrivateKey);
    }
}
