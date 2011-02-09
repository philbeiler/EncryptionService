package test.com.beilers.service.dao.key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.junit.Test;

import test.com.beilers.SpringUnitTestHelper;

import com.beilers.service.dao.KeyDaoInterface;
import com.beilers.service.dao.key.PublicKeyInfo;

public class PublicKeyDaoTest extends SpringUnitTestHelper {

    @Resource
    private KeyDaoInterface dao;

    @Test
    public void wiring() {
        assertNotNull(dao);
    }

    @Test
    public void crud() {
        final PublicKeyInfo pk = new PublicKeyInfo();
        final String userid = "JOE";
        final String keyData = "TopSecret";
        assertNull(dao.find(userid));
        pk.setKey(keyData);
        pk.setUserid(userid);
        dao.save(pk);
        assertEquals(keyData, dao.find(userid));
        dao.remove(userid);
        assertNull(dao.find(userid));
    }
}
