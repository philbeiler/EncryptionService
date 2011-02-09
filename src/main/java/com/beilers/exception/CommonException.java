package com.beilers.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("PMD.UseSingleton")
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommonException(final String message) {
        super(message);
    }

    public CommonException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static String getStackTrace(final Throwable t) {
        final StringWriter s = new StringWriter();
        t.printStackTrace(new PrintWriter(s));
        return s.toString();
    }
}
