package org.waddys.xcloud.common.base.concurrency;

import java.util.Date;

public interface Scheduler {

    /**
     * This is called from the TimerTask thread periodically about every one
     * minute.
     */
    public void poll(Date currentTimestamp);
}
