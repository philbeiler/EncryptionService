package test.com.beilers;

import java.util.HashMap;
import java.util.Map;

import com.beilers.logging.jdk14.Configuration;
import com.beilers.logging.jdk14.SingleLineFormatter;

public class UnitTestHelper {

    static {

        final Map<String, String> defaultLogging = new HashMap<String, String>();
        defaultLogging.put("org.springframework", "WARNING");
        defaultLogging.put("com.beilers", "ALL");
        defaultLogging.put("test.com.beilers", "ALL");
        final Configuration configuration = new Configuration();
        configuration.setConfiguration(defaultLogging);
        configuration.setConsoleHandlerFormatter(new SingleLineFormatter());
    }
}
