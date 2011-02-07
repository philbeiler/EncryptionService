package test.com.beilers.encryption.exception;

import java.util.Collection;

import org.junit.Test;

import com.beilers.encryption.exception.EncryptionException;

public class EncryptionExceptionTest {

    @Test
    public void simple() {
        try {
            throw new EncryptionException("Catch Me");
        }
        catch (final EncryptionException e) {
            final Collection<String> trace = e.getTrace(".*encryption.*");
            for (final String s : trace) {
                System.out.println("---->" + s);
            }
        }
    }
}
