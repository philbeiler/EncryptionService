package test.com.beilers.service.client;

import org.junit.Test;

import com.beilers.service.client.PasswordManagerClient;

public class PasswordManagerClientTest {

    @Test
    public void simple() {
        System.out.println("----[" + new PasswordManagerClient().getPassword("TESTID", "database.password") + "]");
    }
}
