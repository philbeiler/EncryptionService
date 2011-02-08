package com.beilers.logging.jdk14;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.commons.lang.StringUtils;

import com.beilers.exception.CommonException;
import com.beilers.logging.LoggingLevel;
import com.beilers.logging.UserIdThreadManager;

public class SingleLineFormatter extends Formatter {

    protected static final String  NEW_LINE           = System.getProperty("line.separator");
    private static final int       PAD_LENGTH         = determinePad();
    private static final int       INITAL_BUFFER_SIZE = 1000;
    private final SimpleDateFormat dateFormatter;
    private boolean                useShortClassName  = false;
    private boolean                showThreadName     = false;
    private boolean                showThreadUserId   = false;

    public SingleLineFormatter() {
        this(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aaa"));
    }

    public SingleLineFormatter(final SimpleDateFormat dateFormat) {
        super();
        this.dateFormatter = dateFormat;
    }

    public SingleLineFormatter setUseShortClassName() {
        useShortClassName = true;
        return this;
    }

    public void setShowThreadName(final boolean showThreadName) {
        this.showThreadName = showThreadName;
    }

    public void setShowThreadUserId(final boolean showThreadUser) {
        this.showThreadUserId = showThreadUser;
    }

    @Override
    public String format(final LogRecord rec) {
        final StringBuilder buf = new StringBuilder(INITAL_BUFFER_SIZE);
        buf.append(format(rec.getLevel()));
        buf.append(' ').append(dateFormatter.format(new Date(rec.getMillis()))).append(" [");

        if (rec.getSourceClassName() == null) {
            buf.append(rec.getLoggerName());
        }
        else {
            if (useShortClassName) {
                final String className = rec.getSourceClassName();
                buf.append(className.substring(className.lastIndexOf('.') + 1));
            }
            else {
                buf.append(rec.getSourceClassName());
            }
        }

        if (rec.getSourceMethodName() != null) {
            buf.append(".");
            buf.append(rec.getSourceMethodName());
            buf.append("()");
        }
        buf.append("]");

        if (showThreadName) {
            buf.append("[").append(Thread.currentThread().getName()).append("]");
        }

        if (showThreadUserId && UserIdThreadManager.isSet()) {
            buf.append("[" + UserIdThreadManager.get() + "]");
        }
        if (rec.getThrown() != null) {
            buf.append(CommonException.getStackTrace(rec.getThrown()));
        }

        buf.append(" ").append(formatMessage(rec));
        buf.append(NEW_LINE);
        return buf.toString();
    }

    protected String format(final Level level) {
        return StringUtils.rightPad(level.toString(), PAD_LENGTH);
    }

    private static int determinePad() {
        int rc = 0;
        for (final LoggingLevel level : LoggingLevel.values()) {
            final int size = level.name().length();
            if (rc < size) {
                rc = size;
            }
        }
        return rc;
    }
}
