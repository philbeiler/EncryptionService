package com.beilers.encryption.exception;

import java.util.ArrayList;
import java.util.Collection;

import com.beilers.exception.CommonException;

public class EncryptionException extends CommonException {

    private static final long   serialVersionUID = 1L;
    private static final String THIS_CLASS       = "^" + EncryptionException.class.getCanonicalName() + "[^a-zA-Z].*";

    public EncryptionException(final String message) {
        super(message);
    }

    public EncryptionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public Collection<String> getTrace() {
        final Collection<String> traceElements = new ArrayList<String>();
        final StackTraceElement[] elements = getStackTrace();
        for (final StackTraceElement e : elements) {
            traceElements.add(e.toString());
        }
        return traceElements;
    }

    public Collection<String> getTrace(final String pattern) {
        final Collection<String> list = getTrace();
        final Collection<String> matchingList = new ArrayList<String>();
        for (final String s : list) {
            if (s.matches(pattern) && !s.matches(THIS_CLASS)) {
                matchingList.add(s);
            }
        }
        return matchingList;
    }

}
