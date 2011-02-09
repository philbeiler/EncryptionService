package test.com.beilers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.beilers.logging.jdk14.Configuration;
import com.beilers.logging.jdk14.SingleLineFormatter;

public class UnitTestHelper {

    private final File personalTemporaryDirectory = new File(System.getProperty("java.io.tmpdir"),
                                                          System.getProperty("user.name"));
    static {
        final Map<String, String> defaultLogging = new HashMap<String, String>();
        defaultLogging.put("org.springframework", "WARNING");
        defaultLogging.put("com.beilers", "ALL");
        defaultLogging.put("test.com.beilers", "ALL");
        final Configuration configuration = new Configuration();
        configuration.setConfiguration(defaultLogging);
        configuration.setConsoleHandlerFormatter(new SingleLineFormatter());
    }

    protected File getPersonalTemporaryDirectory() {
        if (!personalTemporaryDirectory.exists()) {
            personalTemporaryDirectory.mkdirs();
        }
        return personalTemporaryDirectory;
    }
}
