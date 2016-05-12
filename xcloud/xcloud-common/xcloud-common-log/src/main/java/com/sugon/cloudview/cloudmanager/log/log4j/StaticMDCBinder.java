package com.sugon.cloudview.cloudmanager.log.log4j;

import org.slf4j.spi.MDCAdapter;


/**
 * This implementation is bound to {@link Log4jMDCAdapter}.
 */
public class StaticMDCBinder {


    /**
     * The unique instance of this class.
     */
    public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

    private StaticMDCBinder() {
    }

    /**
     * Currently this method always returns an instance of
     * {@link StaticMDCBinder}.
     */
    public MDCAdapter getMDCA() {
        return new Log4jMDCAdapter();
    }

    public String  getMDCAdapterClassStr() {
        return Log4jMDCAdapter.class.getName();
    }
}