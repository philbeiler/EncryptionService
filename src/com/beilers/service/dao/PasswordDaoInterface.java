package com.beilers.service.dao;

import com.beilers.service.dao.password.PasswordInfo;
import com.beilers.service.dao.password.PasswordKeyInfo;

public interface PasswordDaoInterface {

    void save(PasswordInfo passwordInfo);

    String find(PasswordKeyInfo passwordKeyInfo);
}
