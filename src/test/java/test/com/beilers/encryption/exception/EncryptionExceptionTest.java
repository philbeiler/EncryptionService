package test.com.beilers.encryption.exception;

import java.util.Collection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.com.beilers.UnitTestHelper;

import com.beilers.encryption.exception.EncryptionException;

public class EncryptionExceptionTest extends UnitTestHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionExceptionTest.class);

    @Test
    public void simple() {
        try {
            throw new EncryptionException("Catch Me");
        }
        catch (final EncryptionException e) {
            final Collection<String> trace = e.getTrace(".*encryption.*");
            for (final String s : trace) {
                LOGGER.debug("-----> [{}]", s);
            }
        }
    }
}
