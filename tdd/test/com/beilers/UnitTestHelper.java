package test.com.beilers;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@ContextConfiguration(locations = {//
"/com/beilers/service/resources/contexts/ApplicationContext.xml" //
})
public class UnitTestHelper {

    static {

        final Map<String, String> defaultLogging = new HashMap<String, String>();
        defaultLogging.put("org.springframework", "WARNING");
        // new Configuration().setConfiguration(defaultLogging);
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(UnitTestHelper.class);
}
