package com.beilers.logging.jdk14;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.beilers.logging.LoggingLevel;

public class Configuration {

    private static final String            DEFAULT_LOGGER = "";
    private final Map<LoggingLevel, Level> mappings       = new HashMap<LoggingLevel, Level>();

    public Configuration() {
        mappings.put(LoggingLevel.ALL, Level.ALL);
        mappings.put(LoggingLevel.DEBUG, Level.FINEST);
        mappings.put(LoggingLevel.INFO, Level.INFO);
        mappings.put(LoggingLevel.WARNING, Level.WARNING);
        mappings.put(LoggingLevel.ERROR, Level.SEVERE);
        mappings.put(LoggingLevel.OFF, Level.OFF);
    }

    public void setConfiguration(final Map<String, String> settings) {
        for (final Entry<String, String> entrySet : settings.entrySet()) {
            setCategory(entrySet.getKey(), entrySet.getValue());
        }
    }

    private Level convert(final String value) {
        for (final Entry<LoggingLevel, Level> entry : mappings.entrySet()) {
            if (entry.getKey().name().equals(value)) {
                return entry.getValue();
            }
        }
        return Level.INFO;
    }

    private void setCategory(final String category, final String level) {
        final Logger logger = Logger.getLogger(category);
        final Level currentLevel = logger.getLevel();
        if (currentLevel == null) {
            Logger.getLogger(category).setLevel(convert(level));
        }
    }

    public void setConsoleHandlerFormatter(final Formatter formatter) {
        for (final Handler h : Logger.getLogger(DEFAULT_LOGGER).getHandlers()) {
            if (h.getClass() == ConsoleHandler.class && h.getFormatter().getClass() == SimpleFormatter.class) {
                h.setLevel(Level.ALL);
                h.setFormatter(formatter);
            }
        }
    }

    public void removeConsoleHandler() {
        for (final Handler h : Logger.getLogger(DEFAULT_LOGGER).getHandlers()) {
            if (h.getClass() == ConsoleHandler.class) {
                Logger.getLogger(DEFAULT_LOGGER).removeHandler(h);
            }
        }
    }
}
