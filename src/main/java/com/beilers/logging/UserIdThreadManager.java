package com.beilers.logging;

import com.beilers.exception.CommonException;

public final class UserIdThreadManager {

    private static final ThreadLocal<String> USER_ID = new ThreadLocal<String>();

    private UserIdThreadManager() {
        super();
    }

    public static void set(final String userId) {
        USER_ID.set(userId);
    }

    public static String get() {
        final String rc = USER_ID.get();
        if (rc == null) {
            throw new CommonException("Unable to access userID ThreadLocal value.");
        }
        return rc;
    }

    public static boolean isSet() {
        return USER_ID.get() != null;
    }

    public static void clear() {
        USER_ID.set(null);
    }
}
