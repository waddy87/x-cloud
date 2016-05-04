package com.sugon.cloudview.cloudmanager.log.log4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.LogManager;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * Log4jLoggerFactory is an implementation of {@link ILoggerFactory} returning
 * the appropriate named {@link Log4jLoggerAdapter} instance.
 */
public class Log4jLoggerFactory implements ILoggerFactory {

    // key: name (String), value: a Log4jLoggerAdapter;
    ConcurrentMap<String, Logger> loggerMap;


    public Log4jLoggerFactory() {
        loggerMap = new ConcurrentHashMap<String, Logger>();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.slf4j.ILoggerFactory#getLogger(java.lang.String)
     */
    public Logger getLogger(String name) {
        Logger slf4jLogger = loggerMap.get(name);
        if (slf4jLogger != null) {
            return slf4jLogger;
        } else {
            org.apache.log4j.Logger log4jLogger;
            if(name.equalsIgnoreCase(Logger.ROOT_LOGGER_NAME))
                log4jLogger = LogManager.getRootLogger();
            else
                log4jLogger = LogManager.getLogger(name);

            Logger newInstance = new Log4jLoggerAdapter(log4jLogger);
            Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }
}
