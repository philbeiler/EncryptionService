package test.com.beilers.service.dao.payload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.junit.Test;

import test.com.beilers.SpringUnitTestHelper;

import com.beilers.service.dao.PayloadDaoInterface;
import com.beilers.service.dao.payload.Payload;
import com.beilers.service.dao.payload.PayloadRequest;

public class PayloadDaoTest extends SpringUnitTestHelper {

    @Resource
    private PayloadDaoInterface dao;

    @Test
    public void wiring() {
        assertNotNull(dao);
    }

    @Test
    public void crud() {
        final String userId = "JOE";
        final String key = "secret.key";
        final String value = "secret.value";
        final PayloadRequest request = new PayloadRequest(userId, key);
        assertNull(dao.find(request));
        final Payload payload = new Payload();
        payload.setUserId(userId);
        payload.setKey(key);
        payload.setValue(value);
        dao.save(payload);
        assertEquals(value, dao.find(request));
        dao.remove(request);
        assertNull(dao.find(request));
    }
}
